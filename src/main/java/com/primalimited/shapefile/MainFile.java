package com.primalimited.shapefile;

public class MainFile {

    public ByteValue computeFileLength(
            ShapeType shapeType,
            int nRecords
    ) {
        if (shapeType.isVariableRecordLength())
            throw new IllegalStateException("This method is only for non-variable record length shape types.");

        // Main file header is always 100 bytes
        int nBytes = (int)ByteValue.FILE_HEADER.bytes();

        // Record headers are always 8 bytes (two integers)
        nBytes += (nRecords * (ByteValue.BYTE_VALUE_INT.bytes() * 2));

        // TODO: add each record (note that some records vary in size, based on shape type)
        switch (shapeType) {
            case NULL -> {
                /*
                Position    Field       Value   Type        Number  Byte Order
                Byte 0      Shape Type  0       Integer     1       Little
                 */
                nBytes += (nRecords * ByteValue.BYTE_VALUE_INT.bytes());
            }
            case POINT -> {
                /*
                Position    Field       Value   Type        Number  Byte Order
                Byte 0      Shape Type  1       Integer     1       Little
                Byte 4      X           X       Double      1       Little
                Byte 12     Y           Y       Double      1       Little
                 */
                int recordLengthBytes = (int)ByteValue.BYTE_VALUE_INT.bytes();
                recordLengthBytes += (int)ByteValue.BYTE_VALUE_DOUBLE.bytes();
                recordLengthBytes += (int)ByteValue.BYTE_VALUE_DOUBLE.bytes();
                nBytes += (nRecords * recordLengthBytes);
            }
            case POINTZ -> {
                /*
                Position    Field       Value   Type        Number  Byte Order
                Byte 0      Shape Type  11      Integer     1       Little
                Byte 4      X           X       Double      1       Little
                Byte 12     Y           Y       Double      1       Little
                Byte 20     Z           Z       Double      1       Little
                Byte 28     Measure     M       Double      1       Little
                 */
                int recordLengthBytes = (int)ByteValue.BYTE_VALUE_INT.bytes();
                recordLengthBytes += (int)ByteValue.BYTE_VALUE_DOUBLE.bytes();
                recordLengthBytes += (int)ByteValue.BYTE_VALUE_DOUBLE.bytes();
                recordLengthBytes += (int)ByteValue.BYTE_VALUE_DOUBLE.bytes();
                recordLengthBytes += (int)ByteValue.BYTE_VALUE_DOUBLE.bytes();
                nBytes += (nRecords * recordLengthBytes);
            }
            case POINTM -> {
                /*
                Position    Field       Value   Type        Number  Byte Order
                Byte 0      Shape Type  21      Integer     1       Little
                Byte 4      X           X       Double      1       Little
                Byte 12     Y           Y       Double      1       Little
                Byte 20     M           M       Double      1       Little
                 */
                int recordLengthBytes = (int)ByteValue.BYTE_VALUE_INT.bytes();
                recordLengthBytes += (int)ByteValue.BYTE_VALUE_DOUBLE.bytes();
                recordLengthBytes += (int)ByteValue.BYTE_VALUE_DOUBLE.bytes();
                recordLengthBytes += (int)ByteValue.BYTE_VALUE_DOUBLE.bytes();
                nBytes += (nRecords * recordLengthBytes);
            }
        }
        return new ByteValue(nBytes);
    }
}
