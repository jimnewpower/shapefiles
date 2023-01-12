package com.primalimited.shapefile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * Shapefile writer for Point record data.
 */
class WriterPoint implements Writer {

    @Override
    public void writeMainFile(
        Header header,
        Dataset dataset,
        BufferedOutputStream mainFileOutputStream
    ) throws IOException {
        writeHeader(header, mainFileOutputStream);

        final int shapeType = header.shapeType().getValue();

        // Point record has the shape type and two doubles (x, y).
        ByteValue contentLength = ByteValue.BYTE_VALUE_INT;//shape type
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//x
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//y

        ByteBuffer recordHeaderBuffer = ByteBuffer
                .allocate((int)ByteValue.RECORD_HEADER.bytes())
                .order(ByteOrder.BIG_ENDIAN);
        ByteBuffer recordBuffer = ByteBuffer
                .allocate((int)contentLength.bytes())
                .order(ByteOrder.LITTLE_ENDIAN);

        int recordIndex = 1;

        List<Point> points = dataset.points();
        for (Point point: points) {
            recordHeaderBuffer.putInt(recordIndex++);
            recordHeaderBuffer.putInt((int)contentLength.to16BitWords());
            mainFileOutputStream.write(recordHeaderBuffer.array());
            recordHeaderBuffer.rewind();

            recordBuffer.putInt(shapeType);
            recordBuffer.putDouble(point.x());
            recordBuffer.putDouble(point.y());
            mainFileOutputStream.write(recordBuffer.array());
            recordBuffer.rewind();
        }

        mainFileOutputStream.flush();
    }

    @Override
    public void writeIndexFile(
        Header header,
        Dataset dataset,
        BufferedOutputStream indexFileOutputStream
    ) throws IOException {
        int nRecords = dataset.points().size();
        writeHeader(header.createIndexHeader(nRecords), indexFileOutputStream);

        ByteValue contentLength = ByteValue.BYTE_VALUE_INT;//shape type
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//x
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//y

        ByteBuffer indexRecordBuffer = ByteBuffer
                .allocate((int)ByteValue.RECORD_HEADER.bytes())
                .order(ByteOrder.BIG_ENDIAN);

        int offset = 0;

        for (int i = 0; i < nRecords; i++) {
            indexRecordBuffer.putInt(offset);
            indexRecordBuffer.putInt((int)contentLength.to16BitWords());
            indexFileOutputStream.write(indexRecordBuffer.array());
            indexRecordBuffer.rewind();
            offset += contentLength.bytes();
        }

        indexFileOutputStream.flush();
    }
}
