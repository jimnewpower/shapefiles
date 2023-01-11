package com.primalimited.shapefile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Writer {
    private final Header header;

    public Writer(Header header) {
        this.header = Objects.requireNonNull(header, "header");
    }

    public void write(
            BufferedOutputStream mainFileOutputStream,
            BufferedOutputStream indexFileOutputStream,
            List<Point> points
    ) throws IOException {
        writeMainFile(mainFileOutputStream, points);
        writeIndexFile(indexFileOutputStream, points);
    }

    private void writeMainFile(
            BufferedOutputStream mainFileOutputStream,
            List<Point> points
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
            BufferedOutputStream indexFileOutputStream,
            List<Point> points
    ) throws IOException {
        ByteValue contentLength = ByteValue.BYTE_VALUE_INT;
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);

        int nRecords = points.size();

        writeIndexFile(indexFileOutputStream, contentLength, nRecords);
    }

    private void writeIndexFile(
            BufferedOutputStream indexFileOutputStream,
            ByteValue contentLength,
            int nRecords
    ) throws IOException {
        writeHeader(header.createIndexHeader(nRecords), indexFileOutputStream);

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

    private void writeHeader(Header header, BufferedOutputStream stream) throws IOException {
        ByteBuffer headerBufferBigEndian = ByteBuffer.allocate(Header.BIG_ENDIAN_N_BYTES).order(ByteOrder.BIG_ENDIAN);
        ByteBuffer headerBufferLittleEndian = ByteBuffer.allocate(Header.LITTLE_ENDIAN_N_BYTES).order(ByteOrder.LITTLE_ENDIAN);

        List<FileValueInt> intFields = header.buildIntegerFields();
        int[] bigEndianIntValues = intFields.stream()
                .filter(fv -> fv.byteOrder() == ByteOrder.BIG_ENDIAN)
                .flatMapToInt(fv -> IntStream.of(fv.value()))
                .toArray();

        int[] littleEndianIntValues = intFields.stream()
                .filter(fv -> fv.byteOrder() == ByteOrder.LITTLE_ENDIAN)
                .flatMapToInt(fv -> IntStream.of(fv.value()))
                .toArray();

        List<FileValueDouble> doubleFields = header.buildDoubleFields();
        double[] littleEndianDoubleValues = doubleFields.stream()
                .flatMapToDouble(fv -> DoubleStream.of(fv.value()))
                .toArray();

        for (int value: bigEndianIntValues) {
            headerBufferBigEndian.putInt(value);
        }

        for (int value: littleEndianIntValues) {
            headerBufferLittleEndian.putInt(value);
        }

        for (double value: littleEndianDoubleValues) {
            headerBufferLittleEndian.putDouble(value);
        }

        stream.write(headerBufferBigEndian.array());
        stream.write(headerBufferLittleEndian.array());
        stream.flush();
    }
}
