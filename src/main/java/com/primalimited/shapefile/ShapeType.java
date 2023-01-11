package com.primalimited.shapefile;

import java.util.Map;

import static java.util.Map.entry;

public enum ShapeType {
    NULL(0, false),
    POINT(1, false),
    POLYLINE(3, true),
    POLYGON(5, true),
    MULTIPOINT(8, true),
    POINTZ(11, false),
    POLYLINEZ(13, true),
    POLYGONZ(15, true),
    MULTIPOINTZ(18, true),
    POINTM(21, false),
    POLYLINEM(23, true),
    POLYGONM(25, true),
    MULTIPOINTM(28, true),
    MULTIPATCH(31, true);

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
    private final boolean variableRecordLength;

    ShapeType(int value, boolean variableRecordLength) {
        this.value = value;
        this.variableRecordLength = variableRecordLength;
    }

    public int getValue() {
        return value;
    }

    public boolean isVariableRecordLength() {
        return variableRecordLength;
    }
}
