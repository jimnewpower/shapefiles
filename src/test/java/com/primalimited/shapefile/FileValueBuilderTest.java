package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileValueBuilderTest {

    @Test
    public void testBytePositionValues() {
        assertThrows(IllegalArgumentException.class, () -> new FileValueBuilder().bytePosition(-1L));
    }

    @Test
    public void testFieldDescription() {
        assertThrows(NullPointerException.class, () -> new FileValueBuilder().fieldDescription(null));
    }

    @Test
    public void testByteOrder() {
        assertThrows(NullPointerException.class, () -> new FileValueBuilder().byteOrder(null));
    }

    @Test
    public void testIntValue() {
        FileValueBuilder builder = new FileValueBuilder();
        builder.doubleValue(32.0);
        assertThrows(IllegalStateException.class, () -> builder.intValue(12));
    }

    @Test
    public void testDoubleValue() {
        FileValueBuilder builder = new FileValueBuilder();
        builder.intValue(17);
        assertThrows(IllegalStateException.class, () -> builder.doubleValue(124.86));
   }
}