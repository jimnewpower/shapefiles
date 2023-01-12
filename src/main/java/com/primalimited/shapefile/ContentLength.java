package com.primalimited.shapefile;

import java.nio.ByteOrder;
import java.util.Objects;

/**
 * Content length, created in nBytes, but using 16-bit words.
 */
class ContentLength {
    private static final String DESCRIPTION = "Content Length (16-bit words)";

    private final ByteValue byteValue;
    private final FileValueInt fileValueInt;

    public ContentLength(ByteValue byteValue) {
        this.byteValue = Objects.requireNonNull(byteValue, "byte value");
        this.fileValueInt = new FileValueBuilder()
                .intValue((int)byteValue.to16BitWords())
                .bytePosition(4)
                .byteOrder(ByteOrder.BIG_ENDIAN)
                .fieldDescription(DESCRIPTION)
                .buildInt();
    }

    public FileValueInt getFileValueInt() {
        return fileValueInt;
    }
}
