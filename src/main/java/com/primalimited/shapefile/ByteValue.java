package com.primalimited.shapefile;

import java.util.Objects;

public record ByteValue(long bytes) {
    // Integer values are always 4 bytes.
    public static final ByteValue BYTE_VALUE_INT = new ByteValue(4);
    // Double values are always 8 bytes.
    public static final ByteValue BYTE_VALUE_DOUBLE = new ByteValue(8);
    // File header for the main (shp) and index (shx) files is 100 bytes.
    public static final ByteValue FILE_HEADER = new ByteValue(100);
    // Record header buffers consist of 2 integers.
    public static final ByteValue RECORD_HEADER = new ByteValue(8);

    public ByteValue {
        if (bytes < 0)
            throw new IllegalArgumentException(String.format("Specified bytes %d must be >= 0.", bytes));
    }

    public long to16BitWords() {
        return bytes / 2;
    }

    public ByteValue add(int nBytes) {
        return new ByteValue(bytes() + nBytes);
    }

    public ByteValue add(ByteValue byteValue) {
        Objects.requireNonNull(byteValue, "byte value");
        return new ByteValue(bytes() + (int)byteValue.bytes());
    }

}
