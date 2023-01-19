package com.primalimited.shapefile;

/**
 * Record that represents a Point (x, y).
 */
public record Point(double x, double y) {
    public Point {
        if (Double.isNaN(x) || Double.isInfinite(x))
            throw new IllegalArgumentException(String.format("Invalid x (%g) argument.", x));
        if (Double.isNaN(y) || Double.isInfinite(y))
            throw new IllegalArgumentException(String.format("Invalid y (%g) argument.", y));
    }
}
