package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundsTest {

    @Test
    public void testValidBounds() {
        assertTrue(Bounds.of(0, 0).isValid());
        assertTrue(Bounds.of(0, 10).isValid());
        assertTrue(Bounds.of(-10, 10).isValid());
    }

    @Test
    public void getters() {
        Bounds bounds = Bounds.of(3.2, 9.6);
        assertEquals(3.2, bounds.getMin(), 1e-10);
        assertEquals(9.6, bounds.getMax(), 1e-10);
        assertEquals(6.4, bounds.getRange(), 1e-10);
    }

    @Test
    public void testContains() {
        assertTrue(Bounds.of(0.0, 1.0).contains(0.0));
        assertTrue(Bounds.of(0.0, 1.0).contains(0.5));
        assertTrue(Bounds.of(0.0, 1.0).contains(1.0));

        assertFalse(Bounds.of(0.0, 1.0).contains(-0.00001));
        assertFalse(Bounds.of(0.0, 1.0).contains(1.00001));
    }

    @Test
    public void testRangeIsZero() {
        assertTrue(Bounds.of(10.0, 10.0).rangeIsZero());
        assertFalse(Bounds.of(1.0, 2.0).rangeIsZero());
    }

    @Test
    public void testBoundValue() {
        Bounds bounds = Bounds.of(-125.0, 125.0);
        assertEquals(bounds.getMin(), bounds.bound(-126.0), 1e-10);
        assertEquals(bounds.getMax(), bounds.bound(126.0), 1e-10);

        double value = -10.0;
        assertEquals(value, bounds.bound(value), 1e-10);

        value = 10.0;
        assertEquals(value, bounds.bound(value), 1e-10);
    }

    @Test
    public void testInvalidBounds() {
        assertThrows(IllegalArgumentException.class, () -> Bounds.of(5.2, 4.9));

    }
}