package com.primalimited.shapefile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public interface Writer {
    Path write(Path directory, String baseFilename, Header header, Dataset dataset) throws IOException;

    default void checkArgs(Path directory, String baseFilename, Header header, Dataset dataset) {
        Objects.requireNonNull(directory, "directory");
        if (!directory.toFile().isDirectory())
            throw new IllegalArgumentException(String.format("directory %s must be a directory", directory));

        Objects.requireNonNull(baseFilename, "base filename");
        try {
            Paths.get(baseFilename);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException(String.format("baseFilename %s contains illegal characters", baseFilename));
        }

        Objects.requireNonNull(header, "header");
        Objects.requireNonNull(dataset, "dataset");
        List<Point> points = dataset.points();
        if (points == null || points.isEmpty())
            throw new IllegalArgumentException("Dataset must contain non-null, non-empty points list.");
    }

    default Path createMainPath(Path directory, String baseFilename) {
        // Strip file extension if there is one.
        String filename = Filenames.removeFileExtension(baseFilename);
        String mainFilename = Filenames.addFileExtension(filename, Constants.MAIN_FILE_EXTENSION);
        return Paths.get(directory.toString(), mainFilename);
    }

    default Path createIndexPath(Path directory, String baseFilename) {
        // Strip file extension if there is one.
        String filename = Filenames.removeFileExtension(baseFilename);
        String indexFilename = Filenames.addFileExtension(filename, Constants.INDEX_FILE_EXTENSION);
        return Paths.get(directory.toString(), indexFilename);
    }

    default void writeHeader(Header header, BufferedOutputStream stream) throws IOException {
        ByteBuffer headerBufferBigEndian = ByteBuffer.allocate(Header.BIG_ENDIAN_N_BYTES).order(ByteOrder.BIG_ENDIAN);
        ByteBuffer headerBufferLittleEndian = ByteBuffer.allocate(Header.LITTLE_ENDIAN_N_BYTES).order(ByteOrder.LITTLE_ENDIAN);

        List<FileValueInt> intFields = header.buildIntegerFields();
        int[] bigEndianIntValues = intFields.stream()
                .filter(fv -> fv.byteOrder() == ByteOrder.BIG_ENDIAN)
                .flatMapToInt(fv -> IntStream.of(fv.value()))
                .toArray();

        int[] littleEndianIntValues = intFields.stream()
                .filter(fv -> fv.byteOrder() == ByteOrder.LITTLE_ENDIAN)
                .flatMapToInt(fv -> IntStream.of(fv.value()))
                .toArray();

        List<FileValueDouble> doubleFields = header.buildDoubleFields();
        double[] littleEndianDoubleValues = doubleFields.stream()
                .flatMapToDouble(fv -> DoubleStream.of(fv.value()))
                .toArray();

        for (int value: bigEndianIntValues) {
            headerBufferBigEndian.putInt(value);
        }

        for (int value: littleEndianIntValues) {
            headerBufferLittleEndian.putInt(value);
        }

        for (double value: littleEndianDoubleValues) {
            headerBufferLittleEndian.putDouble(value);
        }

        stream.write(headerBufferBigEndian.array());
        stream.write(headerBufferLittleEndian.array());
        stream.flush();
    }
}
