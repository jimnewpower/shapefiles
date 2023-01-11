package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumericTest {

    @Test
    public void testValidFloatingPointValues() {
        assertTrue(Numeric.isValid(0.0));
        assertTrue(Numeric.isValid(32654654.9876546));
        assertTrue(Numeric.isValid(-987654.546546));

        assertFalse(Numeric.isValid(Double.NaN));
        assertFalse(Numeric.isValid(Double.NEGATIVE_INFINITY));
        assertFalse(Numeric.isValid(Double.POSITIVE_INFINITY));

        double value = Numeric.MIN_VALID_VALUE;
        assertTrue(Numeric.isValid(value));

        value = Numeric.MIN_VALID_VALUE * 10;
        assertFalse(Numeric.isValid(value));
    }

    @Test
    public void testNoDataValue() {
        assertFalse(Numeric.isValid(Numeric.NO_DATA_VALUE));
    }
}