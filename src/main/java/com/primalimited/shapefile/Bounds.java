package com.primalimited.shapefile;

/**
 * One-dimensional bounds.
 */
public interface Bounds {
    /**
     * Return the minimum value
     * @return the minimum value
     */
    double getMin();

    /**
     * Return the maximum value
     * @return the maximum value
     */
    double getMax();

    /**
     * Return the range of data (e.g. max-min)
     * @return the range of data
     */
    double getRange();

    /**
     * Throws IllegalArgumentException if either argument is invalid, or if
     * min &gt; max.
     *
     * @param min min value
     * @param max max value
     * @throws IllegalArgumentException if min or max is invalid, or if
     * min &gt; max.
     */
    default void validateArguments(double min, double max) {
        if (Double.isNaN(min))
            throw new IllegalArgumentException("min is Not a Number.");
        if (Double.isNaN(max))
            throw new IllegalArgumentException("max is Not a Number.");
        if (Double.isInfinite(min))
            throw new IllegalArgumentException("min is infinite.");
        if (Double.isInfinite(max))
            throw new IllegalArgumentException("max is infinite.");
        if (min > max)
            throw new IllegalArgumentException("min (" + min + ") > max (" + max + ")");
    }

    /**
     * Return true if bounds are valid, false otherwise
     * @return true if bounds are valid, false otherwise
     */
    default boolean isValid() {
        return valid(getMin(), getMax());
    }

    /**
     * Return true if min == max, false otherwise.
     * @return return true if min == max, false otherwise.
     */
    default boolean rangeIsZero() {
        return isValid() && (getMax() - getMin() < 1e-12);
    }

    /**
     * Returns true if this bounds contains the given value.
     *
     * @param value the value to evaluate
     * @return true if this bounds contains the given value,
     * false otherwise.
     */
    default boolean contains(double value) {
        return value >= getMin() && value <= getMax();
    }

    /**
     * Bound the given value to this bounds, i.e. if the value
     * is less than the min of the bounds, return bounds min,
     * if the value is greater than the max of the bounds, return
     * the bounds max, otherwise just return the value.
     *
     * @param value value to bind
     * @return bounds min if value &lt; min, bounds max if
     * value &gt; max, otherwise return the value argument.
     */
    default double bound(double value) {
        return Math.min(getMax(), Math.max(getMin(), value));
    }

    /**
     * Fraction bounds [0..1]
     */
    Bounds FRACTION = Bounds.of(0, 1);

    /**
     * Percent bounds [0..100]
     */
    Bounds PERCENT = Bounds.of(0, 100);

    /**
     * Degrees bounds [0..360]
     */
    Bounds DEGREES = Bounds.of(0, 360);

    /**
     * Latitude bounds [0..90]
     */
    Bounds LATITUDE = Bounds.of(0, 90);

    /**
     * Longitude bounds [0..180]
     */
    Bounds LONGITUDE = Bounds.of(0, 180);

    /**
     * Radians bounds [0..2Pi]
     */
    Bounds RADIANS = Bounds.of(0, 2*Math.PI);

    /**
     * 8-bit color value bounds [0..255]
     */
    Bounds RGB_8_BIT = Bounds.of(0, 255);

    /**
     * Factory method to create bounds given two values, min and max.
     *
     * @param min min value; must be finite and &lt;= max
     * @param max max value; must be finite and &gt;= min
     * @return new instance of a Bounds, initialized with min and max.
     * @throws IllegalArgumentException if min or max is invalid, or if
     * min &gt; max.
     */
    static Bounds of(double min, double max) {
        return immutable(min, max);
    }

    /**
     * Factory method to create immutable bounds given two values,
     * min and max.
     *
     * @param min min value; must be finite and &lt;= max
     * @param max max value; must be finite and &gt;= min
     * @return new instance of a Bounds, initialized with min and max.
     * @throws IllegalArgumentException if min or max is invalid, or if
     * min &gt; max.
     */
    static Bounds immutable(double min, double max) {
        return new ImmutableBounds(min, max);
    }

    /**
     * Return true if both arguments constitute a valid Bounds: min &lt;= max,
     * and both min and max are finite and non-dval.
     *
     * @param min min value
     * @param max max value
     * @return true if both arguments constitute a valid Bounds
     */
    static boolean valid(double min, double max) {
        if (Double.isNaN(min) || Double.isNaN(max) || Double.isInfinite(min) || Double.isInfinite(max))
            return false;
        return min <= max;
    }
}
