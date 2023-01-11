package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundingBoxTest {

    @Test
    public void validBoundingBoxTest() {
        Bounds xBounds = Bounds.of(325.8, 476.92);
        Bounds yBounds = Bounds.of(110.05, 223.01);
        BoundingBox box = new BoundingBox(xBounds, yBounds);
        double[] shapefileBoundingBoxValues = box.getShapefileBoundingBoxValues();
        assertEquals(shapefileBoundingBoxValues[0], xBounds.getMin());
        assertEquals(shapefileBoundingBoxValues[1], yBounds.getMin());
        assertEquals(shapefileBoundingBoxValues[2], xBounds.getMax());
        assertEquals(shapefileBoundingBoxValues[3], yBounds.getMax());
    }
}