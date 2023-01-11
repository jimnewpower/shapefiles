package com.primalimited.shapefile;

import java.nio.ByteOrder;

public class FileValueUnused {
    private static final String UNUSED = "Unused";

    private final FileValueInt fileValueInt;

    public FileValueUnused(int bytePosition) {
        this.fileValueInt = new FileValueBuilder()
                .bytePosition(bytePosition)
                .intValue(0)
                .fieldDescription(UNUSED)
                .byteOrder(ByteOrder.BIG_ENDIAN)
                .buildInt();
    }
}
