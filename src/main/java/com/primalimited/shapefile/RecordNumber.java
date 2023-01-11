package com.primalimited.shapefile;

import java.nio.ByteOrder;

public class RecordNumber {
    private static final String DESCRIPTION = "Record Number";

    private final FileValueInt fileValueInt;

    public RecordNumber(int recordNumber) {
        this.fileValueInt = new FileValueBuilder()
                .intValue(recordNumber)
                .fieldDescription(DESCRIPTION)
                .bytePosition(0L)
                .byteOrder(ByteOrder.BIG_ENDIAN)
                .buildInt();
    }

    public FileValueInt getFileValueInt() {
        return fileValueInt;
    }
}
