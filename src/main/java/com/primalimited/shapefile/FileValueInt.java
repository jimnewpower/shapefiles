package com.primalimited.shapefile;

import java.nio.ByteOrder;

public record FileValueInt(
        long bytePosition,
        String fieldDescription,
        int value,
        ByteOrder byteOrder
) {
}
