package com.primalimited.shapefile;

public class Numeric {
    /*
    Floating point numbers must be numeric values. Positive infinity, negative infinity, and
    Not-a-Number (NaN) values are not allowed in shapefiles. Nevertheless, shapefiles
    support the concept of "no data" values, but they are currently used only for measures.
    Any floating point number smaller than –10^38 is considered by a shapefile reader to
    represent a "no data" value.
     */
    static final double MIN_VALID_VALUE = -10e38;

    public static boolean isValid(Double number) {
        if (number.isInfinite() || number.isNaN())
            return false;

        return number >= MIN_VALID_VALUE;
    }
}
