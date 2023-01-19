package com.primalimited.shapefile;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriterPointTest {

    private List<Point> createMockPoints() {
        return List.of(
                new Point(-107, 38),
                new Point(-107, 41),
                new Point(-103, 41),
                new Point(-103, 38)
        );
    }

    private Header createMockHeader() {
        ShapeType shapeType = ShapeType.POINT;
        ByteValue fileLength = new MainFile().computeFileLength(shapeType, createMockPoints().size());
        Bounds xBounds = Bounds.of(-107, -103);
        Bounds yBounds = Bounds.of(38, 41);
        BoundingBox xyBoundingBox = new BoundingBox(xBounds, yBounds);
        Bounds zBounds = Bounds.of(0, 4);
        Bounds mBounds = Bounds.of(5, 10);
        return new Header(fileLength, shapeType, xyBoundingBox, zBounds, mBounds);
    }

    @Test
    public void writeFile() throws IOException {
        List<Point> points = createMockPoints();

        Path mainPath = Files.createTempFile("shape", ".shp");

        Path parentDirectory = mainPath.getParent();
        String filename = mainPath.toFile().getName();
        String baseFilename = Filenames.removeFileExtension(filename);

        Header header = createMockHeader();
        Dataset dataset = Dataset.pointsDataset(points);

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
        List<Point> points = createMockPoints();

        try (
            ByteArrayOutputStream mainOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream mainBufferedOutputStream = new BufferedOutputStream(mainOutputStream);
            ByteArrayOutputStream indexOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream indexBufferedOutputStream = new BufferedOutputStream(indexOutputStream)
        ) {
            Header header = createMockHeader();
            Dataset dataset = Dataset.pointsDataset(points);

            WriterPoint writer = (WriterPoint)new WriterFactory().create(header.shapeType());
            writer.writeMainFile(header, dataset, mainBufferedOutputStream);
            writer.writeIndexFile(header, dataset.nRecords(), indexBufferedOutputStream);

            testHeader(header, mainOutputStream.toByteArray());
            testPoints(points, mainOutputStream.toByteArray());
        }
    }

    private void testPoints(List<Point> points, byte[] bytes) {
        ByteBuffer big = ByteBuffer.allocate(bytes.length).order(ByteOrder.BIG_ENDIAN);
        big.put(bytes);
        big.rewind();

        ByteBuffer little = ByteBuffer.allocate(bytes.length).order(ByteOrder.LITTLE_ENDIAN);
        little.put(bytes);
        little.rewind();

        int recordNumber = 1;
        final int recordHeaderLength = 8;//Two integer values

        // Point record has the shape type and two doubles (x, y).
        ByteValue contentLength = ByteValue.BYTE_VALUE_INT;//shape type
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//x
        contentLength = contentLength.add(ByteValue.BYTE_VALUE_DOUBLE);//y

        int position = (int)ByteValue.FILE_HEADER.bytes();

        final double tol = 1e-5;

        for (Point point: points) {
            big.position(position);
            assertEquals(recordNumber, big.getInt(), "position:"+position);
            assertEquals(contentLength.to16BitWords(), big.getInt(), "position:"+position);
            position += recordHeaderLength;

            little.position(position);
            assertEquals(ShapeType.POINT.getValue(), little.getInt());
            assertEquals(point.x(), little.getDouble(), tol, "position:"+position);
            assertEquals(point.y(), little.getDouble(), tol, "position:"+position);
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
        assertEquals(ShapeType.POINT.getValue(), little.getInt());
        assertEquals(-107.0, little.getDouble(), tol);
        assertEquals(38.0, little.getDouble(), tol);
        assertEquals(-103.0, little.getDouble(), tol);
        assertEquals(41.0, little.getDouble(), tol);
        assertEquals(0.0, little.getDouble(), tol);
        assertEquals(4.0, little.getDouble(), tol);
        assertEquals(5.0, little.getDouble(), tol);
        assertEquals(10.0, little.getDouble(), tol);
    }
}