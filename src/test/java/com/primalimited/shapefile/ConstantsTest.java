package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    public void testFileExtensions() {
        assertEquals("shp", Constants.MAIN_FILE_EXTENSION);
        assertEquals("shx", Constants.INDEX_FILE_EXTENSION);
        assertEquals("dbf", Constants.DBASE_FILE_EXTENSION);
        assertEquals("prj", Constants.PRJ_FILE_EXTENSION);
        assertEquals(9994, Constants.FILE_CODE);
        assertEquals(1000, Constants.VERSION);
        assertEquals(100, Constants.FILE_HEADER_SIZE_BYTES.bytes());
        assertEquals(50, Constants.FILE_HEADER_SIZE_BYTES.to16BitWords());
    }
}