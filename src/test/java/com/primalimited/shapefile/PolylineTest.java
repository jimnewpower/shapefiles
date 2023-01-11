package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolylineTest {

    @Test
    public void testPolyline() {
        Point[] points = new Point[] {
                new Point(0, 0),
                new Point(0, 10),
                new Point(10, 10),
                new Point(10, 0)
        };
        BoundingBox boundingBox = new BoundingBox(Bounds.of(0, 10), Bounds.of(0, 10));
        Polyline polyline = new Polyline(boundingBox, 1, List.of(1), List.of(points));
        assertEquals(boundingBox, polyline.boundingBox());
        assertEquals(1, polyline.numParts());
        assertEquals(points.length, polyline.numPoints());
    }
}