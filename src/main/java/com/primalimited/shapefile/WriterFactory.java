package com.primalimited.shapefile;

/**
 * Factory to create writers based on shape type.
 */
public class WriterFactory {

    /**
     * Create writer from shape type.
     * @param shapeType the shape type.
     * @return new instance of a shapefile writer.
     */
    public Writer create(ShapeType shapeType) {
        switch (shapeType) {
            case NULL -> {
            }
            case POINT -> {
                return new WriterPoint();
            }
            case POLYLINE -> {
            }
            case POLYGON -> {
            }
            case MULTIPOINT -> {
            }
            case POINTZ -> {
                return new WriterPointZ();
            }
            case POLYLINEZ -> {
            }
            case POLYGONZ -> {
            }
            case MULTIPOINTZ -> {
            }
            case POINTM -> {
                return new WriterPointM();
            }
            case POLYLINEM -> {
            }
            case POLYGONM -> {
            }
            case MULTIPOINTM -> {
            }
            case MULTIPATCH -> {
            }
        }
        throw new IllegalStateException(String.format("Writer not yet defined for shape type %s", shapeType.name()));
    }
}
