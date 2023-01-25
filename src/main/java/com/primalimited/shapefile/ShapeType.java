package com.primalimited.shapefile;

import java.util.Map;

import static java.util.Map.entry;

/**
 * ESRI Shapefile shape type definitions.
 */
public enum ShapeType {
    NULL(0, false) {
        @Override
        ByteValue recordHeader() {
            return new ByteValue(4);//Shape type
        }
    },
    POINT(1, false) {
        @Override
        ByteValue recordHeader() {
            // Point record has the shape type and two doubles (x, y).
            ByteValue contentLength = ByteValue.BYTE_VALUE_INT;//shape type
            contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//x
            contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//y
            return contentLength;
        }
    },
    POLYLINE(3, true) {
        @Override
        ByteValue recordHeader() {
            throw new IllegalStateException(String.format(HAS_VARIABLE_LENGTH_RECORD_HEADERS, this));
        }
    },
    POLYGON(5, true) {
        @Override
        ByteValue recordHeader() {
            throw new IllegalStateException(String.format(HAS_VARIABLE_LENGTH_RECORD_HEADERS, this));
        }
    },
    MULTIPOINT(8, true) {
        @Override
        ByteValue recordHeader() {
            throw new IllegalStateException(String.format(HAS_VARIABLE_LENGTH_RECORD_HEADERS, this));
        }
    },
    POINTZ(11, false) {
        @Override
        ByteValue recordHeader() {
            // PointZ record has the shape type and four doubles (x, y, z, m).
            ByteValue contentLength = ByteValue.BYTE_VALUE_INT;//shape type
            contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//x
            contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//y
            contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//z
            contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//m
            return contentLength;
        }
    },
    POLYLINEZ(13, true) {
        @Override
        ByteValue recordHeader() {
            throw new IllegalStateException(String.format("Shape type %s has variable-length record headers.", this));
        }
    },
    POLYGONZ(15, true) {
        @Override
        ByteValue recordHeader() {
            throw new IllegalStateException(String.format("Shape type %s has variable-length record headers.", this));
        }
    },
    MULTIPOINTZ(18, true) {
        @Override
        ByteValue recordHeader() {
            throw new IllegalStateException(String.format("Shape type %s has variable-length record headers.", this));
        }
    },
    POINTM(21, false) {
        @Override
        ByteValue recordHeader() {
            // PointM record has the shape type and three doubles (x, y, m).
            ByteValue contentLength = ByteValue.BYTE_VALUE_INT;//shape type
            contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//x
            contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//y
            contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//m
            return contentLength;
        }
    },
    POLYLINEM(23, true) {
        @Override
        ByteValue recordHeader() {
            throw new IllegalStateException(String.format("Shape type %s has variable-length record headers.", this));
        }
    },
    POLYGONM(25, true) {
        @Override
        ByteValue recordHeader() {
            throw new IllegalStateException(String.format("Shape type %s has variable-length record headers.", this));
        }
    },
    MULTIPOINTM(28, true) {
        @Override
        ByteValue recordHeader() {
            throw new IllegalStateException(String.format("Shape type %s has variable-length record headers.", this));
        }
    },
    MULTIPATCH(31, true) {
        @Override
        ByteValue recordHeader() {
            throw new IllegalStateException(String.format("Shape type %s has variable-length record headers.", this));
        }
    };

    public static final String HAS_VARIABLE_LENGTH_RECORD_HEADERS = "Shape type %s has variable-length record headers.";
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

    abstract ByteValue recordHeader();

    /**
     * The integer value of a shape type as defined in the ESRI Shapefile
     * technical description.
     *
     * @return the integer value of a shape type.
     */
    public int getValue() {
        return value;
    }

    /**
     * Some records are fixed-length (e.g. Point types), while others are
     * variable-length (e.g. Polyline, Polygon).
     *
     * @return true if shape type records can be variable length, false otherwise.
     */
    public boolean isVariableRecordLength() {
        return variableRecordLength;
    }
}
