package com.primalimited.shapefile;

import java.nio.ByteOrder;
import java.util.Objects;

public class FileValueBuilder {
    private static final int UNSPECIFIED_INT_VALUE = Integer.MIN_VALUE;
    private static final double UNSPECIFIED_DOUBLE_VALUE = Double.MIN_VALUE;

    private long bytePosition;
    private String fieldDescription;
    private ByteOrder byteOrder;
    private int intValue = UNSPECIFIED_INT_VALUE;
    private double doubleValue = UNSPECIFIED_DOUBLE_VALUE;

    public FileValueBuilder bytePosition(long bytePosition) {
        if (bytePosition < 0)
            throw new IllegalArgumentException(String.format("bytePosition argument must be >= 0 (was %d)", bytePosition));
        this.bytePosition = bytePosition;
        return this;
    }

    public FileValueBuilder fieldDescription(String fieldDescription) {
        this.fieldDescription = Objects.requireNonNull(fieldDescription, "fieldDescription");
        return this;
    }

    public FileValueBuilder byteOrder(ByteOrder byteOrder) {
        this.byteOrder = Objects.requireNonNull(byteOrder, "byte order");
        return this;
    }

    public FileValueBuilder intValue(int value) {
        if (this.doubleValue != UNSPECIFIED_DOUBLE_VALUE)
            throw new IllegalStateException(
                    String.format(
                            "Attempting to specify integer value (%d) when double value (%g) has already been set.",
                            value,
                            doubleValue
                    )
            );

        this.intValue = value;
        return this;
    }

    public FileValueBuilder doubleValue(double value) {
        if (this.intValue != UNSPECIFIED_INT_VALUE)
            throw new IllegalStateException(
                    String.format(
                            "Attempting to specify double value (%g) when integer value (%d) has already been set.",
                            value,
                            intValue
                    )
            );

        this.doubleValue = value;
        return this;
    }

    public FileValueDouble buildDouble() {
        return new FileValueDouble(bytePosition, fieldDescription, doubleValue, byteOrder);
    }

    public FileValueInt buildInt() {
        return new FileValueInt(bytePosition, fieldDescription, intValue, byteOrder);
    }
}
