package com.primalimited.shapefile;

public class Constants {
    private Constants() {}

    // File extensions.
    public static final String MAIN_FILE_EXTENSION = "shp";
    public static final String INDEX_FILE_EXTENSION = "shx";
    public static final String DBASE_FILE_EXTENSION = "dbf";
    public static final String PRJ_FILE_EXTENSION = "prj";

    // File header constants.
    public static final int FILE_CODE = 9994;
    public static final int VERSION = 1000;
    public static final ByteValue FILE_HEADER_SIZE_BYTES = new ByteValue(100);
}
