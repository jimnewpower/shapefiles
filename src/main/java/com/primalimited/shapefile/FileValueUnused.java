package com.primalimited.shapefile;

import java.nio.ByteOrder;

public record FileValueUnused(int bytePosition) {
    private static final String UNUSED = "Unused";

    public FileValueInt get() {
        return new FileValueBuilder()
                .bytePosition(bytePosition)
                .intValue(0)
                .fieldDescription(UNUSED)
                .byteOrder(ByteOrder.BIG_ENDIAN)
                .buildInt();
    }
}
