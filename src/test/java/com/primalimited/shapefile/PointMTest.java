package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointMTest {

    @Test
    public void validArgumentsTest() {
        final double x = 4.2;
        final double y = 8.4;
        final double m = 16.8;
        PointM point = new PointM(x, y, m);

        final double tol = 1e-8;
        assertEquals(x, point.x(), tol, "x");
        assertEquals(y, point.y(), tol, "y");
        assertEquals(m, point.m(), tol, "m");
    }

    @Test
    public void invalidXArgumentsTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PointM(Double.POSITIVE_INFINITY, 0.0, 0.0));
        assertThrows(
                IllegalArgumentException.class,
                () -> new PointM(Double.NEGATIVE_INFINITY, 0.0, 0.0));
        assertThrows(
                IllegalArgumentException.class,
                () -> new PointM(Double.NaN, 0.0, 0.0));
    }

    @Test
    public void invalidYArgumentsTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PointM(0.0, Double.POSITIVE_INFINITY, 0.0));
        assertThrows(
                IllegalArgumentException.class,
                () -> new PointM(0.0, Double.NEGATIVE_INFINITY, 0.0));
        assertThrows(
                IllegalArgumentException.class,
                () -> new PointM(0.0, Double.NaN, 0.0));
    }
}