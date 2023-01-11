package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolylineTest {

    @Test
    public void testPolylineSimple() {
        Point[] points = new Point[] {
                new Point(0, 0),
                new Point(0, 10),
                new Point(10, 10),
                new Point(10, 0)
        };

        BoundingBox boundingBox = new BoundingBox(Bounds.of(0, 10), Bounds.of(0, 10));
        Polyline polyline = new Polyline(boundingBox, List.of(points));

        assertEquals(boundingBox, polyline.boundingBox());
        assertEquals(1, polyline.numParts());
        assertEquals(points.length, polyline.numPoints());
    }

    @Test
    public void testPolyline() {
        Point[] points = new Point[] {
                new Point(0, 0),
                new Point(0, 10),
                new Point(10, 10),
                new Point(10, 0)
        };
        ArrayList<Point> list = new ArrayList<>(List.of(points));
        BoundingBox boundingBox = new BoundingBox(Bounds.of(0, 10), Bounds.of(0, 10));
        Polyline polyline = new Polyline(boundingBox, 1, List.of(1), list);
        assertEquals(boundingBox, polyline.boundingBox());
        assertEquals(1, polyline.numParts());
        assertEquals(points.length, polyline.numPoints());

        // Ensure that adding a point to our list after creating the polyline doesn't modify the
        // underlying list in the polyline itself (tests that the polyline record made a defensive
        // copy of the points list).
        list.add(new Point(50, 50));
        assertEquals(points.length, polyline.numPoints());

    }
}