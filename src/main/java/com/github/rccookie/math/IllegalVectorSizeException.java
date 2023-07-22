package com.github.rccookie.math;

public class IllegalVectorSizeException extends IllegalArgumentException {
    public IllegalVectorSizeException(int size) {
        super("Non-positive vector size: " + size);
    }
}
