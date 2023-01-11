package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    public void testFileExtensions() {
        assertEquals("shp", Constants.MAIN_FILE_EXTENSION);
        assertEquals("shx", Constants.INDEX_FILE_EXTENSION);
        assertEquals("dbf", Constants.DBASE_FILE_EXTENSION);
    }
}