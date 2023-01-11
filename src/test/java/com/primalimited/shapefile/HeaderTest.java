package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {

    private Header createMockHeader() {
        ByteValue fileLength = new ByteValue(684);
        ShapeType shapeType = ShapeType.POINT;
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
        for (FileValueInt fileValueInt: list)
            System.out.println(fileValueInt);
    }

    @Test
    public void testBuildingDoubleFields() {
        List<FileValueDouble> list = createMockHeader().buildDoubleFields();
        for (FileValueDouble fileValueDouble: list)
            System.out.println(fileValueDouble);
    }
}