package com.primalimited.shapefile;

import java.util.List;
import java.util.Objects;

public record Polyline(BoundingBox boundingBox, int numParts, List<Integer> partStartIndices, List<Point> points) {
    public Polyline {
        Objects.requireNonNull(boundingBox, "bounding box");

        if (numParts < 1)
            throw new IllegalArgumentException(String.format("numParts must be at least 1 (was %d)", numParts));

        Objects.requireNonNull(partStartIndices, "part start indices");
        if (partStartIndices.isEmpty())
            throw new IllegalArgumentException("partStartIndices is empty");

        // Make defensive copy of list
        partStartIndices = List.copyOf(partStartIndices);

        Objects.requireNonNull(points, "points");
        if (points.isEmpty())
            throw new IllegalArgumentException("points is empty");

        // Make defensive copy of list
        points = List.copyOf(points);
    }

    public int numPoints() {
        return points.size();
    }
}
