package com.primalimited.shapefile;

import java.util.Objects;

public record BoundingBox(Bounds xBounds, Bounds yBounds) {
    public BoundingBox {
        Objects.requireNonNull(xBounds, "x bounds");
        Objects.requireNonNull(yBounds, "y bounds");
        if (!xBounds.isValid())
            throw new IllegalArgumentException(String.format("xBounds argument is invalid (%g - %g).", xBounds.getMin(), xBounds.getMax()));
        if (!yBounds.isValid())
            throw new IllegalArgumentException(String.format("yBounds argument is invalid (%g - %g).", yBounds.getMin(), yBounds.getMax()));
    }

    public double[] getShapefileBoundingBoxValues() {
        // The Bounding Box is stored in the order Xmin, Ymin, Xmax, Ymax
        return new double[] { xBounds.getMin(), yBounds.getMin(), xBounds.getMax(), yBounds.getMax() };
    }
}
