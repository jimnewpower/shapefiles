package com.primalimited.shapefile;

/**
 * Bounds implementation that is immutable.
 */
record ImmutableBounds(
        double min,
        double max
) implements Bounds {
    public ImmutableBounds {
        if (min > max)
            throw new IllegalArgumentException(String.format("Specified min %g is greater than max %g.", min, max));
    }

    @Override
    public double getMin() {
        return min;
    }

    @Override
    public double getMax() {
        return max;
    }

    @Override
    public double getRange() {
        return max - min;
    }
}
