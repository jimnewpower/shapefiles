package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ByteValueTest {

    @Test
    public void testByteValue() {
        assertEquals(0, new ByteValue(0).bytes());
        assertEquals(0, new ByteValue(0).to16BitWords());

        assertEquals(2, new ByteValue(2).bytes());
        assertEquals(1, new ByteValue(2).to16BitWords());

        assertEquals(4, new ByteValue(4).bytes());
        assertEquals(2, new ByteValue(4).to16BitWords());
    }

    @Test
    public void testPredefined() {
        assertEquals(4, ByteValue.BYTE_VALUE_INT.bytes());
        assertEquals(2, ByteValue.BYTE_VALUE_INT.to16BitWords());

        assertEquals(8, ByteValue.BYTE_VALUE_DOUBLE.bytes());
        assertEquals(4, ByteValue.BYTE_VALUE_DOUBLE.to16BitWords());

        assertEquals(100, ByteValue.FILE_HEADER.bytes());
        assertEquals(50, ByteValue.FILE_HEADER.to16BitWords());

    }
}