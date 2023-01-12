package com.primalimited.shapefile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.List;

class WriterPoint implements Writer {

    @Override
    public Path write(Path directory, String baseFilename, Header header, Dataset dataset) throws IOException {
        checkArgs(directory, baseFilename, header, dataset);

        Path mainPath = createMainPath(directory, baseFilename);
        Path indexPath = createIndexPath(directory, baseFilename);

        write(header, dataset, mainPath, indexPath);
        return mainPath;
    }

    private void write(
        Header header, Dataset dataset, Path mainPath, Path indexPath
    ) throws IOException {
        try (
            FileOutputStream mainFileOutputStream = new FileOutputStream(mainPath.toFile());
            BufferedOutputStream mainFileBufferedOutputStream = new BufferedOutputStream(mainFileOutputStream);
            FileOutputStream indexFileOutputStream = new FileOutputStream(indexPath.toFile());
            BufferedOutputStream indexFileBufferedOutputStream = new BufferedOutputStream(indexFileOutputStream)
        ) {
            write(header, dataset, mainFileBufferedOutputStream, indexFileBufferedOutputStream);
        }
    }

    void write(
        Header header,
        Dataset dataset,
        BufferedOutputStream mainFileOutputStream,
        BufferedOutputStream indexFileOutputStream
    ) throws IOException {
        writeMainFile(header, dataset.points(), mainFileOutputStream);
        writeIndexFile(header, dataset.points(), indexFileOutputStream);
    }

    private void writeMainFile(
        Header header,
        List<Point> points,
        BufferedOutputStream mainFileOutputStream
    ) throws IOException {
        writeHeader(header, mainFileOutputStream);

        final int shapeType = header.shapeType().getValue();

        // Point record has the shape type and two doubles (x, y).
        ByteValue contentLength = ByteValue.BYTE_VALUE_INT;//shape type
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//x
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//y

        ByteBuffer recordHeaderBuffer = ByteBuffer.allocate((int)ByteValue.RECORD_HEADER.bytes()).order(ByteOrder.BIG_ENDIAN);
        ByteBuffer recordBuffer = ByteBuffer.allocate((int)contentLength.bytes()).order(ByteOrder.LITTLE_ENDIAN);

        int recordIndex = 1;

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

    private void writeIndexFile(
        Header header,
        List<Point> points,
        BufferedOutputStream indexFileOutputStream
    ) throws IOException {
        int nRecords = points.size();
        writeHeader(header.createIndexHeader(nRecords), indexFileOutputStream);

        ByteValue contentLength = ByteValue.BYTE_VALUE_INT;//shape type
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//x
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//y

        ByteBuffer indexRecordBuffer = ByteBuffer.allocate((int)ByteValue.RECORD_HEADER.bytes()).order(ByteOrder.BIG_ENDIAN);

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
