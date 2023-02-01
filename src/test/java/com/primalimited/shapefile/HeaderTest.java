package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {

    private static final String[] INTEGER_FIELD_DESCRIPTIONS = new String[] {
            "Byte 0, Field 'File Code', Value 9994, Type Integer, Byte Order BIG_ENDIAN",
            "Byte 4, Field 'Unused', Value 0, Type Integer, Byte Order BIG_ENDIAN",
            "Byte 8, Field 'Unused', Value 0, Type Integer, Byte Order BIG_ENDIAN",
            "Byte 12, Field 'Unused', Value 0, Type Integer, Byte Order BIG_ENDIAN",
            "Byte 16, Field 'Unused', Value 0, Type Integer, Byte Order BIG_ENDIAN",
            "Byte 20, Field 'Unused', Value 0, Type Integer, Byte Order BIG_ENDIAN",
            "Byte 24, Field 'File Length', Value 106, Type Integer, Byte Order BIG_ENDIAN",
            "Byte 28, Field 'Version', Value 1000, Type Integer, Byte Order LITTLE_ENDIAN",
            "Byte 32, Field 'Shape Type', Value 1, Type Integer, Byte Order LITTLE_ENDIAN"
    };

    private static final String[] DOUBLE_FIELD_DESCRIPTIONS = new String[] {
            "Byte 36, Field 'Xmin', Value -105.0, Type Double, Byte Order LITTLE_ENDIAN",
            "Byte 44, Field 'Ymin', Value 35.0, Type Double, Byte Order LITTLE_ENDIAN",
            "Byte 52, Field 'Xmax', Value -100.0, Type Double, Byte Order LITTLE_ENDIAN",
            "Byte 60, Field 'Ymax', Value 45.0, Type Double, Byte Order LITTLE_ENDIAN",
            "Byte 68, Field 'Zmin', Value 0.0, Type Double, Byte Order LITTLE_ENDIAN",
            "Byte 76, Field 'Zmax', Value 4.0, Type Double, Byte Order LITTLE_ENDIAN",
            "Byte 84, Field 'Mmin', Value 5.0, Type Double, Byte Order LITTLE_ENDIAN",
            "Byte 92, Field 'Mmax', Value 10.0, Type Double, Byte Order LITTLE_ENDIAN"
    };

    private Header createMockHeader() {
        ShapeType shapeType = ShapeType.POINT;
        List<Point> points = List.of(
                new Point(-105, 35),
                new Point(-105, 45),
                new Point(-100, 45),
                new Point(-100, 35)
        );
        ByteValue fileLength = new MainFile().computeFileLength(shapeType, points.size());
        Bounds xBounds = Bounds.of(-105, -100);
        Bounds yBounds = Bounds.of(35, 45);
        BoundingBox xyBoundingBox = new BoundingBox(xBounds, yBounds);
        Bounds zBounds = Bounds.of(0, 4);
        Bounds mBounds = Bounds.of(5, 10);
        return new Header(fileLength, shapeType, xyBoundingBox, zBounds, mBounds);
    }

    @Test
    public void testBuildingIntegerFields() {
        List<FileValueInt> list = createMockHeader().buildIntegerFields();

        int[] bigEndianValues = list.stream()
                .filter(fv -> fv.byteOrder() == ByteOrder.BIG_ENDIAN)
                .flatMapToInt(fv -> IntStream.of(fv.value()))
                .toArray();
        assertEquals("[9994, 0, 0, 0, 0, 0, 106]", Arrays.toString(bigEndianValues));

        int[] littleEndianValues = list.stream()
                .filter(fv -> fv.byteOrder() == ByteOrder.LITTLE_ENDIAN)
                .flatMapToInt(fv -> IntStream.of(fv.value()))
                .toArray();
        assertEquals("[1000, 1]", Arrays.toString(littleEndianValues));

        int count = 0;
        for (FileValueInt fileValueInt: list)
            assertEquals(INTEGER_FIELD_DESCRIPTIONS[count++], fileValueInt.toString());
    }

    @Test
    public void testBuildingDoubleFields() {
        List<FileValueDouble> list = createMockHeader().buildDoubleFields();

        double[] values = list.stream()
                .flatMapToDouble(fv -> DoubleStream.of(fv.value()))
                .toArray();
        assertEquals("[-105.0, 35.0, -100.0, 45.0, 0.0, 4.0, 5.0, 10.0]", Arrays.toString(values));

        int count = 0;
        for (FileValueDouble fileValueDouble: list)
            assertEquals(DOUBLE_FIELD_DESCRIPTIONS[count++], fileValueDouble.toString());
    }
}