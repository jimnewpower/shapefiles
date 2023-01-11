package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTypeTest {
    @Test
    public void testShapeTypes() {
        assertEquals(0, ShapeType.NULL.getValue());
        assertEquals(1, ShapeType.POINT.getValue());
        assertEquals(3, ShapeType.POLYLINE.getValue());
        assertEquals(5, ShapeType.POLYGON.getValue());
        assertEquals(8, ShapeType.MULTIPOINT.getValue());
        assertEquals(11, ShapeType.POINTZ.getValue());
        assertEquals(13, ShapeType.POLYLINEZ.getValue());
        assertEquals(15, ShapeType.POLYGONZ.getValue());
        assertEquals(18, ShapeType.MULTIPOINTZ.getValue());
        assertEquals(21, ShapeType.POINTM.getValue());
        assertEquals(23, ShapeType.POLYLINEM.getValue());
        assertEquals(25, ShapeType.POLYGONM.getValue());
        assertEquals(28, ShapeType.MULTIPOINTM.getValue());
        assertEquals(31, ShapeType.MULTIPATCH.getValue());
    }

    @Test
    public void testGetFromValue() {
        for (ShapeType shapeType : ShapeType.values()) {
            assertEquals(shapeType, ShapeType.getFromValue(shapeType.getValue()));
        }

        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(2));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(4));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(6));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(7));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(9));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(10));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(12));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(14));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(16));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(17));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(19));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(20));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(22));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(24));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(26));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(27));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(29));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(30));
        assertThrows(IllegalArgumentException.class, () -> ShapeType.getFromValue(32));
    }

}