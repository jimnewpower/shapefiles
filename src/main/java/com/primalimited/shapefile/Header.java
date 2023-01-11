package com.primalimited.shapefile;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Header(ByteValue fileLength, ShapeType shapeType, BoundingBox xyBoundingBox, Bounds zBounds, Bounds mBounds) {
    public static final int BIG_ENDIAN_START = 0;
    public static final int BIG_ENDIAN_N_BYTES = 28;
    public static final int LITTLE_ENDIAN_START = 28;
    public static final int LITTLE_ENDIAN_N_BYTES = 72;

    public Header {
        Objects.requireNonNull(fileLength, "file length");
        Objects.requireNonNull(shapeType, "shape type");
        Objects.requireNonNull(xyBoundingBox, "xy bounding box");
        Objects.requireNonNull(zBounds, "z bounds");
        Objects.requireNonNull(mBounds, "m bounds");
    }

    public List<FileValueInt> buildIntegerFields() {
        List<FileValueInt> list = new ArrayList<>();

        /*
        Position    Field       Value       Type    Order
        Byte 0      File Code   9994        Integer Big
        Byte 4      Unused      0           Integer Big
        Byte 8      Unused      0           Integer Big
        Byte 12     Unused      0           Integer Big
        Byte 16     Unused      0           Integer Big
        Byte 20     Unused      0           Integer Big
        Byte 24     File Length File Length Integer Big
        Byte 28     Version     1000        Integer Little
        Byte 32     Shape Type  Shape Type  Integer Little
         */

        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        ByteValue byteValue = new ByteValue(0);

        int nBytes = (int)ByteValue.BYTE_VALUE_INT.bytes();

        // Byte 0: File Code
        list.add(new FileValueInt(byteValue.bytes(), "File Code", Constants.FILE_CODE, byteOrder));
        byteValue = byteValue.add(nBytes);
        // Byte 4: Unused
        list.add(new FileValueUnused((int) byteValue.bytes()).get());
        byteValue = byteValue.add(nBytes);
        // Byte 8: Unused
        list.add(new FileValueUnused((int) byteValue.bytes()).get());
        byteValue = byteValue.add(nBytes);
        // Byte 12: Unused
        list.add(new FileValueUnused((int) byteValue.bytes()).get());
        byteValue = byteValue.add(nBytes);
        // Byte 16: Unused
        list.add(new FileValueUnused((int) byteValue.bytes()).get());
        byteValue = byteValue.add(nBytes);
        // Byte 20: Unused
        list.add(new FileValueUnused((int) byteValue.bytes()).get());
        byteValue = byteValue.add(nBytes);
        // Byte 24: File Length
        list.add(new FileValueInt(byteValue.bytes(), "File Length", (int) fileLength.to16BitWords(), byteOrder));
        byteValue = byteValue.add(nBytes);

        byteOrder = ByteOrder.LITTLE_ENDIAN;

        // Byte 28: Version
        list.add(new FileValueInt(byteValue.bytes(), "Version", Constants.VERSION, byteOrder));
        byteValue = byteValue.add(nBytes);
        // Byte 32: Shape Type
        list.add(new FileValueInt(byteValue.bytes(), "Shape Type", shapeType.getValue(), byteOrder));

        return List.copyOf(list);
    }

    public List<FileValueDouble> buildDoubleFields() {
        List<FileValueDouble> list = new ArrayList<>();

        /*
        Position    Field           Value       Type    Order
        Byte 36     Bounding Box    Xmin        Double  Little
        Byte 44     Bounding Box    Ymin        Double  Little
        Byte 52     Bounding Box    Xmax        Double  Little
        Byte 60     Bounding Box    Ymax        Double  Little
        Byte 68*    Bounding Box    Zmin        Double  Little
        Byte 76*    Bounding Box    Zmax        Double  Little
        Byte 84*    Bounding Box    Mmin        Double  Little
        Byte 92*    Bounding Box    Mmax        Double  Little
         */

        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        ByteValue byteValue = new ByteValue(36);
        int nBytes = (int)ByteValue.BYTE_VALUE_DOUBLE.bytes();

        // Byte 36: Bounding Box Xmin
        list.add(new FileValueDouble(byteValue.bytes(), "Xmin", xyBoundingBox.xBounds().getMin(), byteOrder));
        byteValue = byteValue.add(nBytes);
        // Byte 44: Bounding Box Ymin
        list.add(new FileValueDouble(byteValue.bytes(), "Ymin", xyBoundingBox.yBounds().getMin(), byteOrder));
        byteValue = byteValue.add(nBytes);
        // Byte 52: Bounding Box Xmax
        list.add(new FileValueDouble(byteValue.bytes(), "Xmax", xyBoundingBox.xBounds().getMax(), byteOrder));
        byteValue = byteValue.add(nBytes);
        // Byte 60: Bounding Box Ymax
        list.add(new FileValueDouble(byteValue.bytes(), "Ymax", xyBoundingBox.yBounds().getMax(), byteOrder));
        byteValue = byteValue.add(nBytes);
        // Byte 68: Bounding Box Zmin
        list.add(new FileValueDouble(byteValue.bytes(), "Zmin", zBounds.getMin(), byteOrder));
        byteValue = byteValue.add(nBytes);
        // Byte 76: Bounding Box Zmax
        list.add(new FileValueDouble(byteValue.bytes(), "Zmax", zBounds.getMax(), byteOrder));
        byteValue = byteValue.add(nBytes);
        // Byte 84: Bounding Box Mmin
        list.add(new FileValueDouble(byteValue.bytes(), "Mmin", mBounds.getMin(), byteOrder));
        byteValue = byteValue.add(nBytes);
        // Byte 92: Bounding Box Mmax
        list.add(new FileValueDouble(byteValue.bytes(), "Mmax", mBounds.getMax(), byteOrder));

        return List.copyOf(list);
    }

    public Header createIndexHeader(int nRecords) {
        // Index file has same header format as the main file, but its own file length must be specified.
        int nBytes = 100 + (nRecords * 8);
        ByteValue indexFileLength = new ByteValue(nBytes);
        return new Header(indexFileLength, shapeType, xyBoundingBox, zBounds, mBounds);
    }
}