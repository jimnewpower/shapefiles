package com.primalimited.shapefile;

import java.util.List;

public record Polyline(BoundingBox boundingBox, int numParts, List<Integer> partStartIndices, List<Point> points) {
    public int numPoints() {
        return points.size();
    }
}
