package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    public void testValidPoints() {
        Point point = new Point(10, 20);
        assertEquals(10, point.x(), 1e-10);
        assertEquals(20, point.y(), 1e-10);
    }

    @Test
    public void testInvalidX() {
        assertThrows(IllegalArgumentException.class, () -> new Point(Double.NaN, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new Point(Double.NEGATIVE_INFINITY, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new Point(Double.POSITIVE_INFINITY, 0.0));
    }

    @Test
    public void testInvalidY() {
        assertThrows(IllegalArgumentException.class, () -> new Point(0.0, Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new Point(0.0, Double.NEGATIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> new Point(0.0, Double.POSITIVE_INFINITY));
    }

}