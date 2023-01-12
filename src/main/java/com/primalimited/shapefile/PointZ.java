package com.primalimited.shapefile;

/**
 * Record for a shapefile PointZ, which has values for x, y, z and m (measure).
 */
public record PointZ(double x, double y, double z, double m) {
    public PointZ {
        if (Double.isNaN(x) || Double.isInfinite(x))
            throw new IllegalArgumentException(String.format("Invalid x (%g) argument.", x));
        if (Double.isNaN(y) || Double.isInfinite(y))
            throw new IllegalArgumentException(String.format("Invalid y (%g) argument.", y));
    }
}
