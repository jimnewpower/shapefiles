package com.primalimited.shapefile;

import java.nio.ByteOrder;
import java.util.Objects;

public record FileValueInt(
        long bytePosition,
        String fieldDescription,
        int value,
        ByteOrder byteOrder
) {
    public FileValueInt {
        if (bytePosition < 0)
            throw new IllegalArgumentException(String.format("Specified bytePosition %d must be >= 0.", bytePosition));
        Objects.requireNonNull(fieldDescription, "field description");
        Objects.requireNonNull(byteOrder, "byte order");
    }
}
