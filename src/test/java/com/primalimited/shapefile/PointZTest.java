package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointZTest {

    @Test
    public void pointZTest() {
        PointZ point = new PointZ(3.4, 8.1, 5, 10);
        final double tol = 1e-8;
        assertEquals(3.4, point.x(), tol, "x");
        assertEquals(8.1, point.y(), tol, "y");
        assertEquals(5.0, point.z(), tol, "z");
        assertEquals(10.0, point.m(), tol, "m");
    }

    @Test
    public void illegalXArgumentsTest() {
        assertThrows(IllegalArgumentException.class, () -> new PointZ(Double.POSITIVE_INFINITY, 8.1, 5, 10));
        assertThrows(IllegalArgumentException.class, () -> new PointZ(Double.NEGATIVE_INFINITY, 8.1, 5, 10));
        assertThrows(IllegalArgumentException.class, () -> new PointZ(Double.NaN, 8.1, 5, 10));
    }

    @Test
    public void illegalYArgumentsTest() {
        assertThrows(IllegalArgumentException.class, () -> new PointZ(0.0, Double.POSITIVE_INFINITY, 5, 10));
        assertThrows(IllegalArgumentException.class, () -> new PointZ(0.0, Double.NEGATIVE_INFINITY, 5, 10));
        assertThrows(IllegalArgumentException.class, () -> new PointZ(0.0, Double.NaN, 5, 10));
    }
}