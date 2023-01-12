package com.primalimited.shapefile;

import java.util.Collections;
import java.util.List;

/**
 * Used for generic writer class to get data based on shape type.
 */
public interface Dataset {
    ShapeType shapeType();

    default List<Point> points() {
        return Collections.emptyList();
    }

    static Dataset pointsDataset(List<Point> points) {
        return new Dataset() {
            @Override
            public ShapeType shapeType() {
                return ShapeType.POINT;
            }

            @Override
            public List<Point> points() {
                return points;
            }
        };
    }
}