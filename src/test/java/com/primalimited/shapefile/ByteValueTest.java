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
}