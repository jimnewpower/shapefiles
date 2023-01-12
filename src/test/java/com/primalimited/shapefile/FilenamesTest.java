package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilenamesTest {

    @Test
    public void validFilenamesTest() {
        assertTrue(Filenames.isFilenameValid("valid_filename.txt"));
        assertTrue(Filenames.isFilenameValid("MyFile"));

        assertFalse(Filenames.isFilenameValid("invalid-filename.xyz"));
        assertFalse(Filenames.isFilenameValid("invalid_filename .txt"));
        assertFalse(Filenames.isFilenameValid("invalid_filename?.txt"));
        assertFalse(Filenames.isFilenameValid("invalid_filename\".txt"));
        assertFalse(Filenames.isFilenameValid("invalid_filename*.txt"));
        assertFalse(Filenames.isFilenameValid("invalid_filename<.txt"));
        assertFalse(Filenames.isFilenameValid("invalid_filename>.txt"));
        assertFalse(Filenames.isFilenameValid("invalid_filename|.txt"));
    }

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