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

/**
 * Shapefile writer interface.
 */
public interface Writer {

    /**
     * Write a shapefile.
     * @param directory directory in which the files will be created.
     * @param baseFilename base filename for all shapefiles.
     * @param header header information.
     * @param dataset the shapefile dataset.
     * @return path to main (.shp) file.
     * @throws IOException
     */
    default Path write(Path directory, String baseFilename, Header header, Dataset dataset) throws IOException {
        checkArgs(directory, baseFilename, header, dataset);

        Path mainPath = createMainPath(directory, baseFilename);
        Path indexPath = createIndexPath(directory, baseFilename);

        try (
            BufferedOutputStream mainFileBufferedOutputStream = Streams.create(mainPath);
            BufferedOutputStream indexFileBufferedOutputStream = Streams.create(indexPath)
        ) {
            writeMainFile(header, dataset, mainFileBufferedOutputStream);
            writeIndexFile(header, dataset.nRecords(), indexFileBufferedOutputStream);
        }

        return mainPath;
    }

    /**
     * Write the main (.shp) file.
     * @param header header information.
     * @param dataset the shapefile record data.
     * @param mainFileOutputStream the output stream.
     * @throws IOException
     */
    void writeMainFile(
        Header header,
        Dataset dataset,
        BufferedOutputStream mainFileOutputStream
    ) throws IOException;

    default void writeIndexFile(Header header, int nRecords, BufferedOutputStream indexFileOutputStream) throws IOException {
        writeHeader(header.createIndexHeader(nRecords), indexFileOutputStream);

        ByteValue contentLength = header.shapeType().recordHeader();

        ByteBuffer indexRecordBuffer = ByteBuffer
                .allocate((int)ByteValue.RECORD_HEADER.bytes())
                .order(ByteOrder.BIG_ENDIAN);

        int offset = 0;

        for (int i = 0; i < nRecords; i++) {
            indexRecordBuffer.putInt(offset);
            indexRecordBuffer.putInt((int)contentLength.to16BitWords());
            indexFileOutputStream.write(indexRecordBuffer.array());
            indexRecordBuffer.rewind();
            offset += contentLength.bytes();
        }

        indexFileOutputStream.flush();
    }

    /**
     * Check arguments supplied to write(), throwing IllegalArgumentExceptions as necessary.
     *
     * @param directory directory in which the files will be created.
     * @param baseFilename base filename for all shapefiles.
     * @param header header information.
     * @param dataset the shapefile dataset.
     */
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
    }

    /**
     * Given a directory and a base filename, create the main (.shp) shapefile path.
     * @param directory directory in which the files will be created.
     * @param baseFilename base filename for all shapefiles.
     * @return path object representing the main (.shp) shapefile.
     */
    default Path createMainPath(Path directory, String baseFilename) {
        // Strip file extension if there is one.
        String filename = Filenames.removeFileExtension(baseFilename);
        String mainFilename = Filenames.addFileExtension(filename, Constants.MAIN_FILE_EXTENSION);
        return Paths.get(directory.toString(), mainFilename);
    }

    /**
     * Given a directory and a base filename, create the index (.shx) shapefile path.
     * @param directory directory in which the files will be created.
     * @param baseFilename base filename for all shapefiles.
     * @return path object representing the index (.shx) shapefile.
     */
    default Path createIndexPath(Path directory, String baseFilename) {
        // Strip file extension if there is one.
        String filename = Filenames.removeFileExtension(baseFilename);
        String indexFilename = Filenames.addFileExtension(filename, Constants.INDEX_FILE_EXTENSION);
        return Paths.get(directory.toString(), indexFilename);
    }

    /**
     * Write the shapefile header.
     * @param header shapefile 100-byte header.
     * @param stream the output stream.
     * @throws IOException
     */
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
