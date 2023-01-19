package com.primalimited.shapefile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * Shapefile writer for PointZ data type.
 */
public class WriterPointZ implements Writer {

    @Override
    public void writeMainFile(Header header, Dataset dataset, BufferedOutputStream mainFileOutputStream) throws IOException {
        writeHeader(header, mainFileOutputStream);

        final int shapeType = header.shapeType().getValue();

        // Point record has the shape type and four doubles (x, y, z, m).
        ByteValue contentLength = header.shapeType().recordHeader();

        ByteBuffer recordHeaderBuffer = ByteBuffer
                .allocate((int)ByteValue.RECORD_HEADER.bytes())
                .order(ByteOrder.BIG_ENDIAN);
        ByteBuffer recordBuffer = ByteBuffer
                .allocate((int)contentLength.bytes())
                .order(ByteOrder.LITTLE_ENDIAN);

        int recordIndex = 1;

        /*
        Position Field Value Type Number Order
        Byte 0 Shape Type 11 Integer 1 Little
        Byte 4 X X Double 1 Little
        Byte 12 Y Y Double 1 Little
        Byte 20 Z Z Double 1 Little
        Byte 28 Measure M Double 1 Little
         */

        List<PointZ> pointZs = dataset.pointZs();
        for (PointZ pointZ: pointZs) {
            // Record header
            recordHeaderBuffer.putInt(recordIndex++);
            recordHeaderBuffer.putInt((int)contentLength.to16BitWords());
            mainFileOutputStream.write(recordHeaderBuffer.array());
            recordHeaderBuffer.rewind();

            // Record values
            recordBuffer.putInt(shapeType);
            recordBuffer.putDouble(pointZ.x());
            recordBuffer.putDouble(pointZ.y());
            recordBuffer.putDouble(pointZ.z());
            recordBuffer.putDouble(pointZ.m());
            mainFileOutputStream.write(recordBuffer.array());
            recordBuffer.rewind();
        }

        mainFileOutputStream.flush();
    }
}
