package com.primalimited.shapefile;

public record ByteValue(long bytes) {
    public static final ByteValue BYTE_VALUE_INT = new ByteValue(4);
    public static final ByteValue BYTE_VALUE_DOUBLE = new ByteValue(8);

    public ByteValue {
        if (bytes < 0)
            throw new IllegalArgumentException(String.format("Specified bytes %d must be >= 0.", bytes));
    }

    public long to16BitWords() {
        return bytes / 2;
    }
}
