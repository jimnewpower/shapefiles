package com.primalimited.shapefile;

public class WriterFactory {
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
            }
            case POLYLINEZ -> {
            }
            case POLYGONZ -> {
            }
            case MULTIPOINTZ -> {
            }
            case POINTM -> {
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
        return null;
    }
}
