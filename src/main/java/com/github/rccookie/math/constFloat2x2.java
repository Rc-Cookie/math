package com.github.rccookie.math;

import java.util.Arrays;

import com.github.rccookie.json.JsonArray;
import com.github.rccookie.json.JsonSerializable;
import com.github.rccookie.util.ArgumentOutOfRangeException;
import com.github.rccookie.util.Cloneable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import static java.lang.Math.abs;

/**
 * A 2x2 float matrix with read-only access.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public class constFloat2x2 implements Cloneable<float2x2>, JsonSerializable {

    /**
     * The zero matrix.
     */
    public static final constFloat2x2 zero = new constFloat2x2();
    /**
     * The identity matrix.
     */
    public static final constFloat2x2 id = new constFloat2x2(1,0,0,1);
    /**
     * The matrix with all components set to 1.
     */
    public static final constFloat2x2 one = new constFloat2x2(1,1,1,1);


    /**
     * The components of the matrix; b is top right, c is bottom left.
     */
    protected float a,b,c,d;

    protected constFloat2x2() { }

    /**
     * Creates a new matrix.
     *
     * @param a The top left component value
     * @param b The top right component value
     * @param c The bottom left component value
     * @param d The bottom right component value
     */
    public constFloat2x2(float a, float b, float c, float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }


    /**
     * Returns a string representation of this matrix. Rows are separated by commas, columns
     * by double spaces.
     *
     * @return A string representation of this matrix
     */
    @Override
    public final String toString() {
        return "[" + a + "  " + b + ", " + c + "  " + d + "]";
    }

    /**
     * Returns whether the given object is equal to this matrix. That is, if it is also a
     * matrix of the same size and has the same component values. The exact class of the matrix
     * does <b>not</b> matter.
     *
     * @param obj The object to test for equality
     * @return Whether the object is equal to this matrix
     */
    @Override
    public final boolean equals(Object obj) {
        return (obj instanceof constFloat2x2 && equals((constFloat2x2) obj)) || (obj instanceof constFloatMxN && ((constFloatMxN) obj).equals(this));
    }

    /**
     * Returns whether the given matrix is equal to this matrix.
     *
     * @param m The matrix to test
     * @return Whether the matrix is equal to this matrix
     */
    public final boolean equals(constFloat2x2 m) {
        return a == m.a && b == m.b && c == m.c && d == m.d;
    }

    /**
     * Returns whether the given matrix is approximately equal to this matrix, using the given
     * maximum permitted component-wise difference.
     *
     * @param m The matrix to test
     * @param ep The maximum permitted component-wise difference
     * @return Whether the matrix is approximately equal to this matrix
     */
    public final boolean equals(constFloat2x2 m, float ep) {
        return abs(m.a - a) <= ep && abs(m.b - b) <= ep && abs(m.c - c) <= ep && abs(m.d - d) <= ep;
    }

    /**
     * Returns a hash code for this matrix.
     *
     * @return A hash code for this matrix
     */
    @Override
    public final int hashCode() {
        return Arrays.deepHashCode(to2dArray());
    }

    /**
     * Returns a copy of this matrix.
     *
     * @return A copy of this matrix
     */
    @Override
    public final @NotNull float2x2 clone() {
        return new float2x2(a, b, c, d);
    }

    @Override
    public final Object toJson() {
        return new JsonArray(new JsonArray(a, b), new JsonArray(c, d));
    }


    /**
     * Returns the top left component of the matrix.
     *
     * @return The top left component
     */
    public final float a00() {
        return a;
    }

    /**
     * Returns the top right component of the matrix.
     *
     * @return The top right component
     */
    public final float a01() {
        return b;
    }

    /**
     * Returns the bottom left component of the matrix.
     *
     * @return The bottom left component
     */
    public final float a10() {
        return c;
    }

    /**
     * Returns the bottom right component of the matrix.
     *
     * @return The bottom right component
     */
    public final float a11() {
        return d;
    }

    /**
     * Returns the top left component of the matrix.
     *
     * @return The top left component
     */
    public final float a() {
        return a;
    }

    /**
     * Returns the top right component of the matrix.
     *
     * @return The top right component
     */
    public final float b() {
        return b;
    }

    /**
     * Returns the bottom left component of the matrix.
     *
     * @return The bottom left component
     */
    public final float c() {
        return c;
    }

    /**
     * Returns the bottom right component of the matrix.
     *
     * @return The bottom right component
     */
    public final float d() {
        return d;
    }

    /**
     * Returns the top row of the matrix.
     *
     * @return The top row
     */
    public final float2 row0() {
        return new float2(a,b);
    }

    /**
     * Returns the bottom row of the matrix.
     *
     * @return The bottom row
     */
    public final float2 row1() {
        return new float2(c,d);
    }

    /**
     * Returns the left column of the matrix.
     *
     * @return The left column
     */
    public final float2 column0() {
        return new float2(a,c);
    }

    /**
     * Returns the right column of the matrix.
     *
     * @return The right column
     */
    public final float2 column1() {
        return new float2(b,d);
    }

    /**
     * Returns the component at the specified index.
     *
     * @param i The row index
     * @param j The column index
     * @return The component at that index
     */
    @SuppressWarnings("ConstantValue")
    public final float component(@Range(from = 0, to = 1) int i, @Range(from = 0, to = 1) int j) {
        if(i == 0) {
            if(j == 0) return a;
            if(j == 1) return b;
        }
        else if(i == 1) {
            if(j == 0) return c;
            if(j == 1) return d;
        }
        throw new ArgumentOutOfRangeException("Index ("+i+","+j+") for 2x2 matrix");
    }

    /**
     * Returns the row at the specified index.
     *
     * @param i The index of the row
     * @return The row at that index
     */
    public final float2 row(@Range(from = 0, to = 1) int i) {
        switch(i) {
            case 0: return new float2(a,b);
            case 1: return new float2(c,d);
            default: throw new ArgumentOutOfRangeException(i, 0, 2);
        }
    }

    /**
     * Returns the column at the specified index.
     *
     * @param j The index of the column
     * @return The column at that index
     */
    public final float2 column(@Range(from = 0, to = 1) int j) {
        switch(j) {
            case 0: return new float2(a,c);
            case 1: return new float2(b,d);
            default: throw new ArgumentOutOfRangeException(j, 0, 2);
        }
    }

    /**
     * Converts this matrix to an array, writing components row by row.
     *
     * @return This matrix as an array
     */
    public final float[] toArray() {
        return new float[] { a, b, c, d };
    }

    /**
     * Writes the contents of this matrix row by row into the given array.
     *
     * @param arr The array to write into
     * @param offset The index of the first component
     * @return The supplied array
     */
    public final float[] toArray(float[] arr, int offset) {
        arr[offset] = a;
        arr[offset+1] = b;
        arr[offset+2] = c;
        arr[offset+3] = d;
        return arr;
    }

    /**
     * Returns the matrix as array of rows.
     *
     * @return The rows of the matrix, as arrays
     */
    public final float[][] to2dArray() {
        return new float[][] { new float[] { a,b }, new float[] { c,d } };
    }


    /**
     * Returns whether all components of this matrix are zero.
     *
     * @return Whether all components are 0
     */
    public final boolean isZero() {
        return a == 0 && b == 0 && c == 0 && d == 0;
    }

    /**
     * Returns whether all components of this matrix are approximately zero, using the given
     * maximum permitted component-wise difference.
     *
     * @param ep The maximum permitted component-wise difference from zero
     * @return Whether this matrix is approximately 0
     */
    public final boolean isZero(float ep) {
        return abs(a) <= ep && abs(b) <= ep && abs(c) <= ep && abs(d) <= ep;
    }

    /**
     * Returns whether all components of the matrix are finite.
     *
     * @return Whether all components are finite
     */
    public final boolean isFinite() {
        return Float.isFinite(a) && Float.isFinite(b) && Float.isFinite(c) && Float.isFinite(d);
    }

    /**
     * Returns whether any components of the matrix are infinite.
     *
     * @return Whether any components are infinite
     */
    public final boolean isInfinite() {
        return Float.isInfinite(a) || Float.isInfinite(b) || Float.isInfinite(c) || Float.isInfinite(d);
    }

    /**
     * Returns whether any components of the matrix are {@link Float#NaN}.
     *
     * @return Whether any components are {@link Float#NaN}
     */
    public final boolean isNaN() {
        return Float.isNaN(a) || Float.isNaN(b) || Float.isNaN(c) || Float.isNaN(d);
    }

    /**
     * Returns whether this matrix is a diagonal matrix.
     *
     * @return Whether this matrix is diagonal
     */
    public final boolean isDiagonal() {
        return b == 0 && c == 0;
    }

    /**
     * Returns whether this matrix is an upper triangular matrix.
     *
     * @return Whether this matrix is upper triangular
     */
    public final boolean isUpperTriangular() {
        return c == 0;
    }

    /**
     * Returns whether this matrix is a lower triangular matrix.
     *
     * @return Whether this matrix is lower triangular
     */
    public final boolean isLowerTriangular() {
        return b == 0;
    }

    /**
     * Returns whether this matrix is a triangular matrix.
     *
     * @return Whether this matrix is upper or lower triangular
     */
    public final boolean isTriangular() {
        return b == 0 || c == 0;
    }


    /**
     * Returns the determinant of the matrix.
     *
     * @return The determinant of the matrix
     */
    public final float det() {
        return a * d - b * c;
    }

    /**
     * Returns the trace of the matrix
     *
     * @return The trace of the matrix
     */
    public final float trace() {
        return a + d;
    }


    /**
     * Returns this matrix negated.
     *
     * @return A new matrix representing this matrix component-wise negated
     */
    public final float2x2 negated() {
        return new float2x2(-a, -b, -c, -d);
    }

    /**
     * Returns the transpose of this matrix.
     *
     * @return A new matrix representing the transposition of this matrix
     */
    public final float2x2 transp() {
        return new float2x2(a, c, b, d);
    }

    /**
     * Returns the inverse of this matrix, or <code>null</code> if this matrix is singular.
     *
     * @return The inverse of this matrix or <code>null</code>
     */
    public final float2x2 inverse() {
        float det = det();
        if(det == 0) return null;
        return new float2x2(d / det, -b / det, -c / det, a / det);
    }

    /**
     * Returns the inverse of this matrix, or <code>null</code> if this matrix is singular.
     *
     * @param ep The maximum difference from 0 of the determinant for the matrix to be considered
     *           singular
     * @return The inverse of this matrix or <code>null</code>
     */
    public final float2x2 inverse(float ep) {
        float det = det();
        if(abs(det) <= ep) return null;
        return new float2x2(d / det, -b / det, -c / det, a / det);
    }

    /**
     * Returns the adjugate matrix of this matrix, that is, the transpose of its cofactor matrix.
     *
     * @return This matrix's adjugate matrix
     */
    public final float2x2 adjugate() {
        return new float2x2(a, -c, -b, d);
    }


    /**
     * Adds the given value to all components of this matrix.
     *
     * @param x The value to add to all components
     * @return A new matrix with the value added to all components
     */
    public final float2x2 added(float x) {
        return new float2x2(a+x, b+x, c+x, d+x);
    }

    /**
     * Adds the given matrix component-wise to this matrix.
     *
     * @param m The matrix to add
     * @return A new matrix representing the sum of this and the given matrix
     */
    public final float2x2 added(constFloat2x2 m) {
        return new float2x2(a + m.a, b + m.b, c + m.c, d + m.d);
    }

    /**
     * Subtracts the given value from all components of this matrix.
     *
     * @param x The value to subtract from all components
     * @return A new matrix with the value subtracted from all components
     */
    public final float2x2 subed(float x) {
        return new float2x2(a-x, b-x, c-x, d-x);
    }

    /**
     * Subtracts the given matrix component-wise from this matrix.
     *
     * @param m The matrix to subtract
     * @return A new matrix representing the subtraction of the given matrix from this matrix
     */
    public final float2x2 subed(constFloat2x2 m) {
        return new float2x2(a - m.a, b - m.b, c - m.c, d - m.d);
    }

    /**
     * Multiplies all components of this matrix by the given factor.
     *
     * @param f The factor to multiply all components with
     * @return A new matrix with all components multiplied by the given factor
     */
    public final float2x2 scaled(float f) {
        return new float2x2(a*f, b*f, c*f, d*f);
    }

    /**
     * Adds this matrix component-wise by the given matrix.
     *
     * @param m The matrix to multiply by
     * @return A new matrix representing the component-wise product of this and the given matrix
     */
    public final float2x2 scaled(constFloat2x2 m) {
        return new float2x2(a * m.a, b * m.b, c * m.c, d * m.d);
    }

    /**
     * Multiplies this matrix by the given matrix.
     *
     * @param m The matrix to multiply with
     * @return The product of this and the given matrix
     */
    public final float2x2 mult(constFloat2x2 m) {
        return new float2x2(a*m.a + b*m.c, c*m.a + d*m.c, a*m.b + b*m.d, c*m.b + d*m.d);
    }

    /**
     * Multiplies this matrix by the given vector.
     *
     * @param v The vector to multiply with
     * @return The product of this matrix and the given vector
     */
    public final float2 mult(constFloat2 v) {
        return new float2(a*v.x + b*v.y, c*v.x + d*v.y);
    }
}
