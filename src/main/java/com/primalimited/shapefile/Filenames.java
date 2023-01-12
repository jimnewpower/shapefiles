package com.primalimited.shapefile;

public class Filenames {
    private static final String EXTENSION_PATTERN = "(?<!^)[.]" + "[^.]*$";

    public static String removeFileExtension(String filename) {
        if (filename == null || filename.isEmpty())
            return filename;

        return filename.replaceAll(EXTENSION_PATTERN, "");
    }

    public static String addFileExtension(String baseFilename, String extension) {
        String ext = extension.startsWith(".") ? extension : "." + extension;
        return baseFilename + ext;
    }
}
