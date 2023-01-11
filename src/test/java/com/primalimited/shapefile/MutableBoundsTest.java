package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableBoundsTest {

    @Test
    public void mutableBoundsBadValuesTest() {
        final double tol = 1e-5;

        MutableBounds bounds = new MutableBounds();
        assertFalse(bounds.isValid());
        assertEquals(Double.MAX_VALUE, bounds.getMin(), tol);
        assertEquals(Double.MIN_VALUE, bounds.getMax(), tol);

        bounds.expandTo(Double.NEGATIVE_INFINITY);
        assertFalse(bounds.isValid());
        assertEquals(Double.MAX_VALUE, bounds.getMin(), tol);
        assertEquals(Double.MIN_VALUE, bounds.getMax(), tol);

        bounds.expandTo(Double.POSITIVE_INFINITY);
        assertFalse(bounds.isValid());
        assertEquals(Double.MAX_VALUE, bounds.getMin(), tol);
        assertEquals(Double.MIN_VALUE, bounds.getMax(), tol);

        bounds.expandTo(Double.NaN);
        assertFalse(bounds.isValid());
        assertEquals(Double.MAX_VALUE, bounds.getMin(), tol);
        assertEquals(Double.MIN_VALUE, bounds.getMax(), tol);
    }

    @Test
    public void mutableBoundsTest() {
        final double tol = 1e-5;

        MutableBounds bounds = new MutableBounds();
        bounds.expandTo(5.0);
        assertEquals(5.0, bounds.getMin(), tol);
        assertEquals(5.0, bounds.getMax(), tol);

        bounds.expandTo(10.0);
        assertEquals(5.0, bounds.getMin(), tol);
        assertEquals(10.0, bounds.getMax(), tol);

        bounds.expandTo(7.5);
        assertEquals(5.0, bounds.getMin(), tol);
        assertEquals(10.0, bounds.getMax(), tol);

        bounds.expandTo(Double.NEGATIVE_INFINITY);
        bounds.expandTo(Double.POSITIVE_INFINITY);
        bounds.expandTo(Double.NaN);
        assertEquals(5.0, bounds.getMin(), tol);
        assertEquals(10.0, bounds.getMax(), tol);
    }
}