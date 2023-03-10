package com.primalimited.shapefile;

import java.util.Collections;
import java.util.List;

/**
 * Used for generic writer class to get data based on shape type.
 */
public interface Dataset {
    ShapeType shapeType();
    int nRecords();

    default List<Point> points() {
        return Collections.emptyList();
    }
    default List<PointZ> pointZs() {
        return Collections.emptyList();
    }
    default List<PointM> pointMs() {
        return Collections.emptyList();
    }

    static Dataset pointsDataset(List<Point> points) {
        return new Dataset() {
            @Override
            public ShapeType shapeType() {
                return ShapeType.POINT;
            }

            @Override
            public int nRecords() {
                return points.size();
            }

            @Override
            public List<Point> points() {
                return points;
            }
        };
    }

    static Dataset pointZsDataset(List<PointZ> pointZs) {
        return new Dataset() {
            @Override
            public ShapeType shapeType() {
                return ShapeType.POINTZ;
            }

            @Override
            public int nRecords() {
                return pointZs.size();
            }

            @Override
            public List<PointZ> pointZs() {
                return pointZs;
            }
        };
    }

    static Dataset pointMsDataset(List<PointM> pointMs) {
        return new Dataset() {
            @Override
            public ShapeType shapeType() {
                return ShapeType.POINTM;
            }

            @Override
            public int nRecords() {
                return pointMs.size();
            }

            @Override
            public List<PointM> pointMs() {
                return pointMs;
            }
        };
    }
}
