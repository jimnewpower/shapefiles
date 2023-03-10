package com.primalimited.shapefile;

import java.nio.ByteOrder;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Represents an integer file value with a description and a file position.
 */
public record FileValueInt(
        long bytePosition,
        String fieldDescription,
        int value,
        ByteOrder byteOrder
) implements Supplier<ByteValue> {
    public FileValueInt {
        if (bytePosition < 0)
            throw new IllegalArgumentException(String.format("Specified bytePosition %d must be >= 0.", bytePosition));
        Objects.requireNonNull(fieldDescription, "field description");
        Objects.requireNonNull(byteOrder, "byte order");
    }

    @Override
    public String toString() {
        return
                "Byte " + bytePosition +
                ", Field '" + fieldDescription + '\'' +
                ", Value " + value +
                ", Type Integer" +
                ", Byte Order " + byteOrder;
    }

    @Override
    public ByteValue get() {
        return ByteValue.BYTE_VALUE_INT;
    }
}
