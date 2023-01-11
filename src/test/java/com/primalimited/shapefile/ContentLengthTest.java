package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.*;

class ContentLengthTest {

    @Test
    public void testContentLength() {
        ContentLength contentLength = new ContentLength(new ByteValue(1024));
        assertEquals(512, contentLength.getFileValueInt().value());
        assertEquals(ByteOrder.BIG_ENDIAN, contentLength.getFileValueInt().byteOrder());
        assertEquals(4, contentLength.getFileValueInt().bytePosition());
    }
}