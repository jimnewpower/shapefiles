package com.primalimited.shapefile;

import static java.util.Map.entry;
import java.util.Map;

public enum ShapeType {
    NULL(0),
    POINT(1),
    POLYLINE(3),
    POLYGON(5),
    MULTIPOINT(8),
    POINTZ(11),
    POLYLINEZ(13),
    POLYGONZ(15),
    MULTIPOINTZ(18),
    POINTM(21),
    POLYLINEM(23),
    POLYGONM(25),
    MULTIPOINTM(28),
    MULTIPATCH(31);

    private static Map<Integer, ShapeType> map = null;

    public static ShapeType getFromValue(int value) {
        if (map == null) {
            map = Map.ofEntries(
                    entry(0, NULL),
                    entry(1, POINT),
                    entry(3, POLYLINE),
                    entry(5, POLYGON),
                    entry(8, MULTIPOINT),
                    entry(11, POINTZ),
                    entry(13, POLYLINEZ),
                    entry(15, POLYGONZ),
                    entry(18, MULTIPOINTZ),
                    entry(21, POINTM),
                    entry(23, POLYLINEM),
                    entry(25, POLYGONM),
                    entry(28, MULTIPOINTM),
                    entry(31, MULTIPATCH)
            );
        }

        if (map.containsKey(value))
            return map.get(value);

        throw new IllegalArgumentException(String.format("Specified value %d is invalid.", value));
    }

    private final int value;

    ShapeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
