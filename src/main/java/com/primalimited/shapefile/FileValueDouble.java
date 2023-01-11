package com.primalimited.shapefile;

import java.nio.ByteOrder;
import java.util.Objects;

public record FileValueDouble(
        long bytePosition,
        String fieldDescription,
        double value,
        ByteOrder byteOrder
) {
    public FileValueDouble {
        if (bytePosition < 0)
            throw new IllegalArgumentException(String.format("Specified bytePosition %d must be >= 0.", bytePosition));
        Objects.requireNonNull(fieldDescription, "field description");
        if (Double.isInfinite(value) || Double.isNaN(value))
            throw new IllegalArgumentException(String.format("Specified value %g is invalid.", value));
        Objects.requireNonNull(byteOrder, "byte order");
    }
}
