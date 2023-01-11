package com.primalimited.shapefile;

public record ByteValue(long bytes) {
    public ByteValue {
        if (bytes < 0)
            throw new IllegalArgumentException(String.format("Specified bytes %d must be >= 0.", bytes));
    }

    public long to16BitWords() {
        return bytes / 2;
    }
}
