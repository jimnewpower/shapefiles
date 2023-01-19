package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriterPointMTest {
    static final ShapeType SHAPE_TYPE = ShapeType.POINTM;
    static final Bounds xBounds = Bounds.of(-107, -103);
    static final Bounds yBounds = Bounds.of(38, 41);
    static final Bounds mBounds = Bounds.of(25, 29);
    static final Bounds zBounds = Bounds.of(0, 0);

    private List<PointM> createMockPoints() {
        return List.of(
                new PointM(-107, 38, 25),
                new PointM(-107, 41, 27),
                new PointM(-103, 41, 28),
                new PointM(-103, 38, 29)
        );
    }

    private Header createMockHeader() {
        ByteValue fileLength = new MainFile().computeFileLength(SHAPE_TYPE, createMockPoints().size());
        BoundingBox xyBoundingBox = new BoundingBox(xBounds, yBounds);
        return new Header(fileLength, SHAPE_TYPE, xyBoundingBox, zBounds, mBounds);
    }

    private Dataset createDataset() {
        return Dataset.pointMsDataset(createMockPoints());
    }

    @Test
    public void writeFile() throws IOException {
        List<PointM> points = createMockPoints();

        Path mainPath = Files.createTempFile("shapem", ".shp");

        Path parentDirectory = mainPath.getParent();
        String filename = mainPath.toFile().getName();
        String baseFilename = Filenames.removeFileExtension(filename);

        Header header = createMockHeader();
        Dataset dataset = createDataset();

        Writer writer = new WriterFactory().create(header.shapeType());
        Path path = writer.write(parentDirectory, baseFilename, header, dataset);
        String indexFilename = path.toFile().getName();
        indexFilename = Filenames.removeFileExtension(indexFilename);
        indexFilename = Filenames.addFileExtension(indexFilename, Constants.INDEX_FILE_EXTENSION);
        Path indexPath = Paths.get(parentDirectory.toString(), indexFilename);

        assertEquals(header.fileLength().bytes(), path.toFile().length(), "file length in bytes");
        assertEquals(header.createIndexHeader(points.size()).fileLength().bytes(), indexPath.toFile().length(), "file length in bytes");

        // Set breakpoint above or comment out these lines to work with the generated files.
        mainPath.toFile().delete();
        indexPath.toFile().delete();
    }

    @Test
    public void testWriter() throws IOException {
        List<PointM> points = createMockPoints();

        try (
            ByteArrayOutputStream mainOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream mainBufferedOutputStream = new BufferedOutputStream(mainOutputStream);
            ByteArrayOutputStream indexOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream indexBufferedOutputStream = new BufferedOutputStream(indexOutputStream)
        ) {
            Header header = createMockHeader();
            Dataset dataset = createDataset();

            WriterPointM writer = (WriterPointM)new WriterFactory().create(header.shapeType());
            writer.writeMainFile(header, dataset, mainBufferedOutputStream);
            writer.writeIndexFile(header, dataset.nRecords(), indexBufferedOutputStream);

            testHeader(header, mainOutputStream.toByteArray());
            testPoints(points, mainOutputStream.toByteArray());
        }
    }

    private void testPoints(List<PointM> points, byte[] bytes) {
        ByteBuffer big = ByteBuffer.allocate(bytes.length).order(ByteOrder.BIG_ENDIAN);
        big.put(bytes);
        big.rewind();

        ByteBuffer little = ByteBuffer.allocate(bytes.length).order(ByteOrder.LITTLE_ENDIAN);
        little.put(bytes);
        little.rewind();

        int recordNumber = 1;
        final int recordHeaderLength = 8;//Two integer values

        // Point record has the shape type and two doubles (x, y).
        ByteValue contentLength = SHAPE_TYPE.recordHeader();

        int position = (int)ByteValue.FILE_HEADER.bytes();

        final double tol = 1e-5;

        for (PointM point: points) {
            // record header
            big.position(position);
            assertEquals(recordNumber, big.getInt(), "position:"+position);
            assertEquals(contentLength.to16BitWords(), big.getInt(), "position:"+position);
            position += recordHeaderLength;

            // record data
            little.position(position);
            assertEquals(SHAPE_TYPE.getValue(), little.getInt());
            assertEquals(point.x(), little.getDouble(), tol, "position:"+position);
            assertEquals(point.y(), little.getDouble(), tol, "position:"+position);
            assertEquals(point.m(), little.getDouble(), tol, "position:"+position);
            position += contentLength.bytes();

            ++recordNumber;
        }

    }

    private void testHeader(Header header, byte[] bytes) {
        /*
        Position Field Value Type Order
        Byte 0 File Code 9994 Integer Big
        Byte 4 Unused 0 Integer Big
        Byte 8 Unused 0 Integer Big
        Byte 12 Unused 0 Integer Big
        Byte 16 Unused 0 Integer Big
        Byte 20 Unused 0 Integer Big
        Byte 24 File Length File Length Integer Big
        Byte 28 Version 1000 Integer Little
        Byte 32 Shape Type Shape Type Integer Little
        Byte 36 Bounding Box Xmin Double Little
        Byte 44 Bounding Box Ymin Double Little
        Byte 52 Bounding Box Xmax Double Little
        Byte 60 Bounding Box Ymax Double Little
        Byte 68* Bounding Box Zmin Double Little
        Byte 76* Bounding Box Zmax Double Little
        Byte 84* Bounding Box Mmin Double Little
        Byte 92* Bounding Box Mmax Double Little
         */
        ByteBuffer big = ByteBuffer.allocate(bytes.length).order(ByteOrder.BIG_ENDIAN);
        big.put(bytes);
        big.rewind();

        assertEquals(Constants.FILE_CODE, big.getInt());
        assertEquals(0, big.getInt());
        assertEquals(0, big.getInt());
        assertEquals(0, big.getInt());
        assertEquals(0, big.getInt());
        assertEquals(0, big.getInt());
        assertEquals(header.fileLength().to16BitWords(), big.getInt());

        ByteBuffer little = ByteBuffer.allocate(bytes.length).order(ByteOrder.LITTLE_ENDIAN);
        little.put(bytes);
        little.rewind();
        little.position(Header.LITTLE_ENDIAN_START);

        final double tol = 1e-5;
        assertEquals(Constants.VERSION, little.getInt());
        assertEquals(SHAPE_TYPE.getValue(), little.getInt());
        assertEquals(xBounds.getMin(), little.getDouble(), tol);
        assertEquals(yBounds.getMin(), little.getDouble(), tol);
        assertEquals(xBounds.getMax(), little.getDouble(), tol);
        assertEquals(yBounds.getMax(), little.getDouble(), tol);
        assertEquals(zBounds.getMin(), little.getDouble(), tol);
        assertEquals(zBounds.getMax(), little.getDouble(), tol);
        assertEquals(mBounds.getMin(), little.getDouble(), tol);
        assertEquals(mBounds.getMax(), little.getDouble(), tol);
    }

}