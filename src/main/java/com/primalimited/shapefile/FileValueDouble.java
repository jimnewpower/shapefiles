package com.primalimited.shapefile;

import java.nio.ByteOrder;

public record FileValueDouble(
        long bytePosition,
        String fieldDescription,
        double value,
        ByteOrder byteOrder
) {
}
