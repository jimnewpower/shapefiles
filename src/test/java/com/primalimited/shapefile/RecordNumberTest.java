package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.*;

class RecordNumberTest {

    @Test
    public void testRecordNumber() {
        RecordNumber recordNumber = new RecordNumber(0);
        assertEquals(0, recordNumber.getFileValueInt().value());
        assertEquals(ByteOrder.BIG_ENDIAN, recordNumber.getFileValueInt().byteOrder());
        assertEquals(4, recordNumber.getFileValueInt().get().bytes());
    }
}