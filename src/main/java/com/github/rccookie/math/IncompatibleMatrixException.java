package com.github.rccookie.math;

public class IncompatibleMatrixException extends RuntimeException {
    public IncompatibleMatrixException(String msg) {
        super(msg);
    }

    public IncompatibleMatrixException(Object m1, Object m2) {
        this("Incompatible matrices: "+m1+" and "+m2);
    }
}
