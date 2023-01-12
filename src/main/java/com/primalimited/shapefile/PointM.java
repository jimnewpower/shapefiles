package com.primalimited.shapefile;

/**
 * Point with x,y and m (measure) value.
 */
public record PointM(double x, double y, double m) {
    public PointM {
        if (Double.isNaN(x) || Double.isInfinite(x))
            throw new IllegalArgumentException(String.format("Invalid x (%g) argument.", x));
        if (Double.isNaN(y) || Double.isInfinite(y))
            throw new IllegalArgumentException(String.format("Invalid y (%g) argument.", y));
    }
}
