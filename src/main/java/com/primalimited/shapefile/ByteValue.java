package com.primalimited.shapefile;

import java.util.Objects;

/**
 * A byte value represents the number of bytes, which can be used for
 * sizing of buffers, or positions in files. ESRI Shapefiles use 16-bit
 * words for sizing, so this provides a conversion function.
 */
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

    /**
     * ESRI Shapefiles use 16-bit words for sizing, which is nBytes / 2.
     * @return nBytes / 2.
     */
    public long to16BitWords() {
        return bytes / 2;
    }

    /**
     * Create new ByteValue instance, adding nBytes to the existing value.
     * @param nBytes number of bytes to add.
     * @return new ByteValue instance, adding nBytes to the existing value.
     */
    public ByteValue add(int nBytes) {
        return new ByteValue(bytes() + nBytes);
    }

    /**
     * Create new ByteValue instance, adding byteValue nBytes to the existing value.
     * @param byteValue number of bytes to add.
     * @return new ByteValue instance, adding nBytes to the existing value.
     */
    public ByteValue add(ByteValue byteValue) {
        Objects.requireNonNull(byteValue, "byte value");
        return new ByteValue(bytes() + (int)byteValue.bytes());
    }
}
