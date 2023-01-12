package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilenamesTest {

    @Test
    public void removeExtensionTest() {
        assertEquals("abc", Filenames.removeFileExtension("abc.def"));
        assertEquals(".abc", Filenames.removeFileExtension(".abc.def"));
    }

    @Test
    public void addExtensionTest() {
        assertEquals("abc.def", Filenames.addFileExtension("abc", "def"));
        assertEquals("abc.def", Filenames.addFileExtension("abc", ".def"));
    }
}