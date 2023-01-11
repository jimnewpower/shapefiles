package com.primalimited.shapefile;

public record Point(double x, double y, double z, double m) {
    public Point(double x, double y) {
        this(x, y, Numeric.NO_DATA_VALUE, Numeric.NO_DATA_VALUE);
    }
    public Point {
        if (Double.isNaN(x) || Double.isInfinite(x))
            throw new IllegalArgumentException(String.format("Invalid x (%g) argument.", x));
        if (Double.isNaN(y) || Double.isInfinite(y))
            throw new IllegalArgumentException(String.format("Invalid y (%g) argument.", y));
    }
}
