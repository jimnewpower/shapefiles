package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {

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
        System.out.println(Arrays.toString(bigEndianValues));

        int[] littleEndianValues = list.stream()
                .filter(fv -> fv.byteOrder() == ByteOrder.LITTLE_ENDIAN)
                .flatMapToInt(fv -> IntStream.of(fv.value()))
                .toArray();
        System.out.println(Arrays.toString(littleEndianValues));

        for (FileValueInt fileValueInt: list)
            System.out.println(fileValueInt);
    }

    @Test
    public void testBuildingDoubleFields() {
        List<FileValueDouble> list = createMockHeader().buildDoubleFields();

        double[] values = list.stream()
                .flatMapToDouble(fv -> DoubleStream.of(fv.value()))
                .toArray();
        System.out.println(Arrays.toString(values));

        for (FileValueDouble fileValueDouble: list)
            System.out.println(fileValueDouble);
    }
}