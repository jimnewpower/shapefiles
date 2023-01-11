package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainFileTest {

    @Test
    public void testMainFileLengthForNullShapeType() {
        ShapeType shapeType = ShapeType.NULL;
        int nRecords = 15;
        ByteValue byteValue = new MainFile().computeFileLength(shapeType, nRecords);
        assertEquals(280, byteValue.bytes());
        // This is the value that would be written into the main file header for file length in 16-bit words.
        assertEquals(140, byteValue.to16BitWords());
    }

    @Test
    public void testMainFileLengthForPointShapeType() {
        ShapeType shapeType = ShapeType.POINT;
        int nRecords = 48;
        ByteValue byteValue = new MainFile().computeFileLength(shapeType, nRecords);
        assertEquals(1444, byteValue.bytes());
        // This is the value that would be written into the main file header for file length in 16-bit words.
        assertEquals(722, byteValue.to16BitWords());
    }

    @Test
    public void testMainFileLengthForPointMShapeType() {
        ShapeType shapeType = ShapeType.POINTM;
        int nRecords = 10;
        ByteValue byteValue = new MainFile().computeFileLength(shapeType, nRecords);
        assertEquals(460, byteValue.bytes());
        // This is the value that would be written into the main file header for file length in 16-bit words.
        assertEquals(230, byteValue.to16BitWords());
    }

    @Test
    public void testMainFileLengthForPointZShapeType() {
        ShapeType shapeType = ShapeType.POINTZ;
        int nRecords = 20;
        ByteValue byteValue = new MainFile().computeFileLength(shapeType, nRecords);
        assertEquals(980, byteValue.bytes());
        // This is the value that would be written into the main file header for file length in 16-bit words.
        assertEquals(490, byteValue.to16BitWords());
    }

}