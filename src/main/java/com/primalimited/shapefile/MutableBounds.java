package com.primalimited.shapefile;

public class MutableBounds implements Bounds {
    private double min;
    private double max;

    public MutableBounds() {
        this.min = Double.MAX_VALUE;
        this.max = Double.MIN_VALUE;
    }

    public MutableBounds(double min, double max) {
        if (min > max)
            throw new IllegalArgumentException(String.format("Specified min %g is greater than max %g.", min, max));
        this.min = min;
        this.max = max;
    }

    public Bounds expandTo(double value) {
        if (Double.isInfinite(value) || Double.isNaN(value))
            return this;
        this.min = Math.min(min, value);
        this.max = Math.max(max, value);
        return this;
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
