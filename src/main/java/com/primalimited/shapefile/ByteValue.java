package com.primalimited.shapefile;

public record ByteValue(long bytes) {
    // Integer values are always 4 bytes.
    public static final ByteValue BYTE_VALUE_INT = new ByteValue(4);
    // Double values are always 8 bytes.
    public static final ByteValue BYTE_VALUE_DOUBLE = new ByteValue(8);
    // File header for the main (shp) and index (shx) files is 100 bytes.
    public static final ByteValue FILE_HEADER = new ByteValue(100);

    public ByteValue {
        if (bytes < 0)
            throw new IllegalArgumentException(String.format("Specified bytes %d must be >= 0.", bytes));
    }

    public long to16BitWords() {
        return bytes / 2;
    }
}
