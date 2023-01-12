package com.primalimited.shapefile;

/**
 * Filename utility functions.
 */
public class Filenames {
    private static final String EXTENSION_PATTERN = "(?<!^)[.]" + "[^.]*$";
    private static final String VALID_FILENAME_PATTERN = "^[A-za-z0-9.]{1,255}$";

    /**
     * Validate a filename (should not contain any illegal characters).
     * @param filename filename to evaluate.
     * @return true if this filename is valid, false otherwise.
     */
    public static boolean isFilenameValid(String filename) {
        if (filename == null) {
            return false;
        }
        return filename.matches(VALID_FILENAME_PATTERN);
    }

    /**
     * Remove the extension from a filename.
     * @param filename filename argument.
     * @return filename string without its extension.
     */
    public static String removeFileExtension(String filename) {
        if (filename == null || filename.isEmpty())
            return filename;

        return filename.replaceAll(EXTENSION_PATTERN, "");
    }

    /**
     * Add extension to a filename.
     * @param baseFilename base filename argument.
     * @param extension extension to add.
     * @return string with extension added to the filename.
     */
    public static String addFileExtension(String baseFilename, String extension) {
        String ext = extension.startsWith(".") ? extension : "." + extension;
        return baseFilename + ext;
    }
}
