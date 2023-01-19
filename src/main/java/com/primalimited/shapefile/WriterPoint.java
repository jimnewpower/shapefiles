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
        ByteValue contentLength = header.shapeType().recordHeader();

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
}
