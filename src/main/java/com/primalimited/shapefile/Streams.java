package com.primalimited.shapefile;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Objects;

class Streams {

    /**
     * Create a buffered output stream from a path.
     *
     * @param path path to the file.
     * @return new instance of a buffered output stream.
     * @throws FileNotFoundException
     */
    static BufferedOutputStream create(Path path) throws FileNotFoundException {
        Objects.requireNonNull(path, "path");
        if (path.toFile().exists() && !path.toFile().isFile())
            throw new IllegalArgumentException(String.format("Specified path %s must be a file.", path));

        FileOutputStream mainFileOutputStream = new FileOutputStream(path.toFile());
        return new BufferedOutputStream(mainFileOutputStream);
    }
}
