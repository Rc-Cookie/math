package com.github.rccookie.math;

import java.util.Arrays;

import com.github.rccookie.json.JsonArray;
import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.json.JsonSerializable;
import com.github.rccookie.util.ArgumentOutOfRangeException;
import com.github.rccookie.util.Cloneable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import static java.lang.Math.abs;

/**
 * A 3x3 float matrix with read-only access.
 */
@SuppressWarnings({"DuplicatedCode"})
public class constFloat3x3 implements Cloneable<float3x3>, JsonSerializable {

    static {
        JsonDeserialization.register(constFloat3x3.class, json -> {
            if(json.isArray()) {
                if(json.get(0).isArray())
                    return fromRows(json.get(0).as(float3.class), json.get(1).as(float3.class), json.get(2).as(float3.class));
                return
                        fromArray(json.as(float[].class));
            }
            if(json.contains("a"))
                return new constFloat3x3(json.get("a").asFloat(), json.get("b").asFloat(), json.get("c").asFloat(),
                        json.get("d").asFloat(), json.get("e").asFloat(), json.get("f").asFloat(),
                        json.get("g").asFloat(), json.get("h").asFloat(), json.get("i").asFloat());
            if(json.contains("a00"))
                return new constFloat3x3(json.get("a00").asFloat(), json.get("a01").asFloat(), json.get("a02").asFloat(),
                        json.get("a10").asFloat(), json.get("a11").asFloat(), json.get("a12").asFloat(),
                        json.get("a20").asFloat(), json.get("a21").asFloat(), json.get("a22").asFloat());
            if(json.contains("m00"))
                return new constFloat3x3(json.get("m00").asFloat(), json.get("m01").asFloat(), json.get("m02").asFloat(),
                        json.get("m10").asFloat(), json.get("m11").asFloat(), json.get("m12").asFloat(),
                        json.get("m20").asFloat(), json.get("m21").asFloat(), json.get("m22").asFloat());
            if(json.contains("a11"))
                return new constFloat3x3(json.get("a11").asFloat(), json.get("a12").asFloat(), json.get("a13").asFloat(),
                        json.get("a21").asFloat(), json.get("a22").asFloat(), json.get("a23").asFloat(),
                        json.get("a31").asFloat(), json.get("a32").asFloat(), json.get("a33").asFloat());
            return new constFloat3x3(json.get("m11").asFloat(), json.get("m12").asFloat(), json.get("m13").asFloat(),
                    json.get("m21").asFloat(), json.get("m22").asFloat(), json.get("m23").asFloat(),
                    json.get("m31").asFloat(), json.get("m32").asFloat(), json.get("m33").asFloat());
        });
    }

    /**
     * The zero matrix.
     */
    public static final constFloat3x3 zero = new constFloat3x3();
    /**
     * The identity matrix.
     */
    public static final constFloat3x3 id = new constFloat3x3(1,0,0,
                                                             0,1,0,
                                                             0,0,1);
    /**
     * A matrix with all components set to 1.
     */
    public static final constFloat3x3 one = new constFloat3x3(1,1,1,
                                                              1,1,1,
                                                              1,1,1);


    /**
     * The components of the matrix.
     */
    protected float a00, a01, a02,
                    a10, a11, a12,
                    a20, a21, a22;

    protected constFloat3x3() { }

    protected constFloat3x3(float a00, float a11, float a22) {
        this.a00 = a00;
        this.a11 = a11;
        this.a22 = a22;
    }

    /**
     * Creates a new matrix.
     */
    public constFloat3x3(float a00, float a01, float a02,
                         float a10, float a11, float a12,
                         float a20, float a21, float a22) {
        this.a00 = a00;
        this.a01 = a01;
        this.a02 = a02;
        this.a10 = a10;
        this.a11 = a11;
        this.a12 = a12;
        this.a20 = a20;
        this.a21 = a21;
        this.a22 = a22;
    }


    /**
     * Returns a string representation of this matrix. Rows are separated by commas, columns
     * by double spaces.
     *
     * @return A string representation of this matrix
     */
    @Override
    public final String toString() {
        return "[" + a00 + "  " + a01 + "  " + a02 +
              ", " + a10 + "  " + a11 + "  " + a12 +
              ", " + a20 + "  " + a21 + "  " + a22 + "]";
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
        return (obj instanceof constFloat3x3 && equals((constFloat3x3) obj)) || (obj instanceof constFloatMxN && ((constFloatMxN) obj).equals(this));
    }

    /**
     * Returns whether the given matrix is equal to this matrix.
     *
     * @param m The matrix to test
     * @return Whether the matrix is equal to this matrix
     */
    public final boolean equals(constFloat3x3 m) {
        return a00 == m.a00 && a01 == m.a01 && a02 == m.a02 &&
               a10 == m.a10 && a11 == m.a11 && a12 == m.a12 &&
               a20 == m.a20 && a21 == m.a21 && a22 == m.a22;
    }

    /**
     * Returns whether the given matrix is approximately equal to this matrix, using the given
     * maximum permitted component-wise difference.
     *
     * @param m The matrix to test
     * @param ep The maximum permitted component-wise difference
     * @return Whether the matrix is approximately equal to this matrix
     */
    public final boolean equals(constFloat3x3 m, float ep) {
        return abs(m.a00 - a00) <= ep && abs(m.a01 - a01) <= ep && abs(m.a02 - a02) <= ep &&
               abs(m.a10 - a10) <= ep && abs(m.a11 - a11) <= ep && abs(m.a12 - a12) <= ep &&
               abs(m.a20 - a20) <= ep && abs(m.a21 - a21) <= ep && abs(m.a22 - a22) <= ep;
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
    public final @NotNull float3x3 clone() {
        return new float3x3(
                a00, a01, a02,
                a10, a11, a12,
                a20, a21, a22
        );
    }

    @Override
    public final Object toJson() {
        return new JsonArray(
                new JsonArray(a00, a01, a02),
                new JsonArray(a10, a11, a12),
                new JsonArray(a20, a21, a22)
        );
    }


    /**
     * Returns top left component of the matrix.
     *
     * @return The component (0,0)
     */
    public final float a00() {
        return a00;
    }

    /**
     * Returns the top center component of the matrix
     *
     * @return The component (0,1)
     */
    public final float a01() {
        return a01;
    }

    /**
     * Returns the top right component of the matrix.
     *
     * @return The component (0,2)
     */
    public final float a02() {
        return a02;
    }

    /**
     * Returns the center left component of the matrix.
     *
     * @return The component (1,0)
     */
    public final float a10() {
        return a10;
    }

    /**
     * Returns the center component of the matrix.
     *
     * @return The component (1,1)
     */
    public final float a11() {
        return a11;
    }

    /**
     * Returns the center right component of the matrix.
     *
     * @return The component (1,2)
     */
    public final float a12() {
        return a12;
    }

    /**
     * Returns the bottom left component of the matrix.
     *
     * @return The component (2,0)
     */
    public final float a20() {
        return a20;
    }

    /**
     * Returns the bottom center component of the matrix.
     *
     * @return The component (2,1)
     */
    public final float a21() {
        return a21;
    }

    /**
     * Returns the bottom right component of the matrix.
     *
     * @return The component (2,2)
     */
    public final float a22() {
        return a22;
    }

    /**
     * Returns the first row of the matrix.
     *
     * @return The first row
     */
    public final float3 row0() {
        return new float3(a00, a01, a02);
    }

    /**
     * Returns the second row of the matrix.
     *
     * @return The second row
     */
    public final float3 row1() {
        return new float3(a10, a11, a12);
    }

    /**
     * Returns the third row of the matrix.
     *
     * @return The third row
     */
    public final float3 row2() {
        return new float3(a20, a21, a22);
    }

    /**
     * Returns the first column of the matrix.
     *
     * @return The first column
     */
    public final float3 column0() {
        return new float3(a00, a10, a20);
    }

    /**
     * Returns the second column of the matrix.
     *
     * @return The second column
     */
    public final float3 column1() {
        return new float3(a01, a11, a21);
    }

    /**
     * Returns the third column of the matrix.
     *
     * @return The third column
     */
    public final float3 column2() {
        return new float3(a02, a12, a22);
    }

    /**
     * Returns the component at the specified index.
     *
     * @param i The row index
     * @param j The column index
     * @return The component at that index
     */
    public final float component(@Range(from = 0, to = 2) int i, @Range(from = 0, to = 2) int j) {
        switch(i) {
            case 0: switch(j) {
                case 0: return a00;
                case 1: return a01;
                case 2: return a02;
            } break;
            case 1: switch(j) {
                case 0: return a10;
                case 1: return a11;
                case 2: return a12;
            } break;
            case 2: switch(j) {
                case 0: return a20;
                case 1: return a21;
                case 2: return a22;
            } break;
        }
        throw new ArgumentOutOfRangeException("Index ("+i+","+j+") for 3x3 matrix");
    }

    /**
     * Returns the row at the specified index.
     *
     * @param i The index of the row
     * @return The row at that index
     */
    public final float3 row(@Range(from = 0, to = 2) int i) {
        switch(i) {
            case 0: return new float3(a00, a01, a02);
            case 1: return new float3(a10, a11, a12);
            case 2: return new float3(a20, a21, a22);
            default: throw new ArgumentOutOfRangeException(i, 0, 4);
        }
    }

    /**
     * Returns the column at the specified index.
     *
     * @param j The index of the column
     * @return The column at that index
     */
    public final float3 column(@Range(from = 0, to = 2) int j) {
        switch(j) {
            case 0: return new float3(a00, a10, a20);
            case 1: return new float3(a01, a11, a21);
            case 2: return new float3(a02, a12, a22);
            default: throw new ArgumentOutOfRangeException(j, 0, 4);
        }
    }

    /**
     * Converts this matrix to an array, writing components row by row.
     *
     * @return This matrix as an array
     */
    public final float[] toArray() {
        return new float[] {
                a00, a01, a02,
                a10, a11, a12,
                a20, a21, a22
        };
    }

    /**
     * Writes the contents of this matrix row by row into the given array.
     *
     * @param arr The array to write into
     * @param offset The index of the first component
     * @return The supplied array
     */
    public final float[] toArray(float[] arr, int offset) {
        arr[offset]   = a00;
        arr[offset+1] = a01;
        arr[offset+2] = a02;
        arr[offset+3] = a10;
        arr[offset+4] = a11;
        arr[offset+5] = a12;
        arr[offset+6] = a20;
        arr[offset+7] = a21;
        arr[offset+8] = a22;
        return arr;
    }

    /**
     * Returns the matrix as array of rows.
     *
     * @return The rows of the matrix, as arrays
     */
    public final float[][] to2dArray() {
        return new float[][] {
                new float[] { a00, a01, a02 },
                new float[] { a10, a11, a12 },
                new float[] { a20, a21, a22 }
        };
    }


    /**
     * Returns whether all components of this matrix are zero.
     *
     * @return Whether all components are 0
     */
    public final boolean isZero() {
        return a00 == 0 && a01 == 0 && a02 == 0 &&
               a10 == 0 && a11 == 0 && a12 == 0 &&
               a20 == 0 && a21 == 0 && a22 == 0;
    }

    /**
     * Returns whether all components of this matrix are approximately zero, using the given
     * maximum permitted component-wise difference.
     *
     * @param ep The maximum permitted component-wise difference from zero
     * @return Whether this matrix is approximately 0
     */
    public final boolean isZero(float ep) {
        return abs(a00) <= ep && abs(a01) <= ep && abs(a02) <= ep &&
               abs(a10) <= ep && abs(a11) <= ep && abs(a12) <= ep &&
               abs(a20) <= ep && abs(a21) <= ep && abs(a22) <= ep;
    }

    /**
     * Returns whether all components of the matrix are finite.
     *
     * @return Whether all components are finite
     */
    public final boolean isFinite() {
        return Float.isFinite(a00) && Float.isFinite(a01) && Float.isFinite(a02) &&
               Float.isFinite(a10) && Float.isFinite(a11) && Float.isFinite(a12) &&
               Float.isFinite(a20) && Float.isFinite(a21) && Float.isFinite(a22);
    }

    /**
     * Returns whether any components of the matrix are infinite.
     *
     * @return Whether any components are infinite
     */
    public final boolean isInfinite() {
        return Float.isInfinite(a00) || Float.isInfinite(a01) || Float.isInfinite(a02) ||
               Float.isInfinite(a10) || Float.isInfinite(a11) || Float.isInfinite(a12) ||
               Float.isInfinite(a20) || Float.isInfinite(a21) || Float.isInfinite(a22);
    }

    /**
     * Returns whether any components of the matrix are {@link Float#NaN}.
     *
     * @return Whether any components are {@link Float#NaN}
     */
    public final boolean isNaN() {
        return Float.isNaN(a00) || Float.isNaN(a01) || Float.isNaN(a02) ||
               Float.isNaN(a10) || Float.isNaN(a11) || Float.isNaN(a12) ||
               Float.isNaN(a20) || Float.isNaN(a21) || Float.isNaN(a22);
    }

    /**
     * Returns whether this matrix is a diagonal matrix.
     *
     * @return Whether this matrix is diagonal
     */
    public final boolean isDiagonal() {
        return a01 == 0 && a02 == 0 &&
               a10 == 0 && a12 == 0 &&
               a20 == 0 && a21 == 0;
    }

    /**
     * Returns whether this matrix is an upper triangular matrix.
     *
     * @return Whether this matrix is upper triangular
     */
    public final boolean isUpperTriangular() {
        return a10 == 0 &&
               a20 == 0 && a21 == 0;
    }

    /**
     * Returns whether this matrix is a lower triangular matrix.
     *
     * @return Whether this matrix is lower triangular
     */
    public final boolean isLowerTriangular() {
        return a01 == 0 && a02 == 0 &&
                           a12 == 0;
    }

    /**
     * Returns whether this matrix is a triangular matrix.
     *
     * @return Whether this matrix is upper or lower triangular
     */
    public final boolean isTriangular() {
        return isUpperTriangular() || isLowerTriangular();
    }


    /**
     * Returns the determinant of the matrix.
     *
     * @return The determinant of the matrix
     */
    public final float det() {
        return a00 * a11 * a22
                - a00 * a12 * a21
                + a01 * a12 * a20
                - a01 * a10 * a22
                + a02 * a10 * a21
                - a01 * a11 * a20;
    }

    /**
     * Returns the trace of the matrix
     *
     * @return The trace of the matrix
     */
    public final float trace() {
        return a00 + a11 + a22;
    }


    /**
     * Returns this matrix negated.
     *
     * @return A new matrix representing this matrix component-wise negated
     */
    public final float3x3 negated() {
        return new float3x3(
                -a00, -a01, -a02,
                -a10, -a11, -a12,
                -a20, -a21, -a22
        );
    }

    /**
     * Returns the transpose of this matrix.
     *
     * @return A new matrix representing the transposition of this matrix
     */
    public final float3x3 transp() {
        return new float3x3(
                a00, a10, a20,
                a01, a11, a21,
                a02, a12, a22
        );
    }

    /**
     * Returns the inverse of this matrix, or <code>null</code> if this matrix is singular.
     *
     * @return The inverse of this matrix or <code>null</code>
     */
    public final float3x3 inverse() {
        return inverse(0);
    }

    /**
     * Returns the inverse of this matrix, or <code>null</code> if this matrix is singular.
     *
     * @param ep The maximum difference from 0 of the determinant for the matrix to be considered
     *           singular
     * @return The inverse of this matrix or <code>null</code>
     */
    public final float3x3 inverse(float ep) {
        float co00 = a11 * a22 - a12 * a21;
        float co01 = -a10 * a22 + a12 * a20;
        float co02 = a10 * a21 - a11 * a20;

        float det = a00 * co00 + a01 * co01 + a02 * co02;
        if(det >= -ep && det <= ep) return null;
        det = 1 / det;

        return new float3x3(
                co00 * det, (-a01 * a22 + a02 * a21) * det, (a01 * a12 - a02 * a11) * det,
                co01 * det, (a00 * a22 - a02 * a20) * det, (-a00 * a12 + a02 * a10) * det,
                co02 * det, (-a00 * a21 + a01 * a20) * det, (a00 * a11 - a01 * a10) * det
        );
    }

    /**
     * Returns the matrix that gets created by omitting the ith row and jth column of this matrix.
     *
     * @param i The row to omit
     * @param j The column to omit
     * @return The (i,j)th submatrix
     */
    public final float2x2 submatrix(@Range(from = 0, to = 2) int i, @Range(from = 0, to = 2) int j) {
        switch(i) {
            case 0: switch(j) {
                case 0: return new float2x2(a11, a12, a21, a22);
                case 1: return new float2x2(a10, a12, a20, a22);
                case 2: return new float2x2(a10, a11, a20, a21);
            } break;
            case 1: switch(j) {
                case 0: return new float2x2(a01, a02, a21, a22);
                case 1: return new float2x2(a00, a02, a20, a22);
                case 2: return new float2x2(a00, a01, a20, a21);
            } break;
            case 2: switch(j) {
                case 0: return new float2x2(a01, a02, a11, a12);
                case 1: return new float2x2(a00, a02, a10, a12);
                case 2: return new float2x2(a00, a01, a10, a11);
            } break;
        }
        throw new ArgumentOutOfRangeException("Index ("+i+","+j+") for 3x3 matrix");
    }

    /**
     * Returns the minor of the given element, that is, the determinant of the submatrix
     * of that element.
     *
     * @param i The row to omit
     * @param j The column to omit
     * @return The (i,j)th minor
     */
    public final float minor(@Range(from = 0, to = 2) int i, @Range(from = 0, to = 2) int j) {
        switch(i) {
            case 0: switch(j) {
                case 0: return a11 * a22 - a12 * a21;
                case 1: return a10 * a22 - a12 * a20;
                case 2: return a10 * a21 - a11 * a20;
            } break;
            case 1: switch(j) {
                case 0: return a01 * a22 - a02 * a21;
                case 1: return a00 * a22 - a02 * a20;
                case 2: return a00 * a21 - a01 * a20;
            } break;
            case 2: switch(j) {
                case 0: return a01 * a12 - a02 * a11;
                case 1: return a00 * a12 - a02 * a10;
                case 2: return a00 * a11 - a01 * a10;
            } break;
        }
        throw new ArgumentOutOfRangeException("Index ("+i+","+j+") for 3x3 matrix");
    }

    /**
     * Returns the cofactor of the given element, that is, the minor of that element multiplied
     * by <code>(-1)^(i+j)</code>.
     *
     * @param i The row to omit in the submatrix
     * @param j The column to omit in the submatrix
     * @return The (i,j)th cofactor
     */
    public final float cofactor(@Range(from = 0, to = 2) int i, @Range(from = 0, to = 2) int j) {
        switch(i) {
            case 0: switch(j) {
                case 0: return a11 * a22 - a12 * a21;
                case 1: return -a10 * a22 + a12 * a20;
                case 2: return a10 * a21 - a11 * a20;
            } break;
            case 1: switch(j) {
                case 0: return -a01 * a22 + a02 * a21;
                case 1: return a00 * a22 - a02 * a20;
                case 2: return -a00 * a21 + a01 * a20;
            } break;
            case 2: switch(j) {
                case 0: return a01 * a12 - a02 * a11;
                case 1: return -a00 * a12 + a02 * a10;
                case 2: return a00 * a11 - a01 * a10;
            } break;
        }
        throw new ArgumentOutOfRangeException("Index ("+i+","+j+") for 3x3 matrix");
    }

    /**
     * Returns the adjugate matrix of this matrix, that is, the transpose of its cofactor matrix.
     *
     * @return This matrix's adjugate matrix
     */
    public final float3x3 adjugate() {
        return new float3x3(
                a11 * a22 - a12 * a21, -a01 * a22 + a02 * a21, a01 * a12 - a02 * a11,
                -a10 * a22 + a12 * a20, a00 * a22 - a02 * a20, -a00 * a12 + a02 * a10,
                a10 * a21 - a11 * a20, -a00 * a21 + a01 * a20, a00 * a11 - a01 * a10
        );
    }


    /**
     * Adds the given value to all components of this matrix.
     *
     * @param x The value to add to all components
     * @return A new matrix with the value added to all components
     */
    public final float3x3 added(float x) {
        return new float3x3(
                a00+x, a01+x, a02+x,
                a10+x, a11+x, a12+x,
                a20+x, a21+x, a22+x
        );
    }

    /**
     * Adds the given matrix component-wise to this matrix.
     *
     * @param m The matrix to add
     * @return A new matrix representing the sum of this and the given matrix
     */
    public final float3x3 added(constFloat3x3 m) {
        return new float3x3(
                a00 + m.a00, a01 + m.a01, a02 + m.a02,
                a10 + m.a10, a11 + m.a11, a12 + m.a12,
                a20 + m.a20, a21 + m.a21, a22 + m.a22
        );
    }

    /**
     * Subtracts the given value from all components of this matrix.
     *
     * @param x The value to subtract from all components
     * @return A new matrix with the value subtracted from all components
     */
    public final float3x3 subed(float x) {
        return new float3x3(
                a00-x, a01-x, a02-x,
                a10-x, a11-x, a12-x,
                a20-x, a21-x, a22-x
        );
    }

    /**
     * Subtracts the given matrix component-wise from this matrix.
     *
     * @param m The matrix to subtract
     * @return A new matrix representing the subtraction of the given matrix from this matrix
     */
    public final float3x3 subed(constFloat3x3 m) {
        return new float3x3(
                a00 - m.a00, a01 - m.a01, a02 - m.a02,
                a10 - m.a10, a11 - m.a11, a12 - m.a12,
                a20 - m.a20, a21 - m.a21, a22 - m.a22
        );
    }

    /**
     * Multiplies all components of this matrix by the given factor.
     *
     * @param f The factor to multiply all components with
     * @return A new matrix with all components multiplied by the given factor
     */
    public final float3x3 scaled(float f) {
        return new float3x3(
                a00*f, a01*f, a02*f,
                a10*f, a11*f, a12*f,
                a20*f, a21*f, a22*f
        );
    }

    /**
     * Adds this matrix component-wise by the given matrix.
     *
     * @param m The matrix to multiply by
     * @return A new matrix representing the component-wise product of this and the given matrix
     */
    public final float3x3 scaled(constFloat3x3 m) {
        return new float3x3(
                a00 - m.a00, a01 - m.a01, a02 - m.a02,
                a10 - m.a10, a11 - m.a11, a12 - m.a12,
                a20 - m.a20, a21 - m.a21, a22 - m.a22
        );
    }

    /**
     * Multiplies this matrix by the given matrix.
     *
     * @param m The matrix to multiply with
     * @return The product of this and the given matrix
     */
    public final float3x3 mult(constFloat3x3 m) {
        return new float3x3(
                a00 * m.a00 + a01 * m.a10 + a02 * m.a20,
                a10 * m.a00 + a11 * m.a10 + a12 * m.a20,
                a20 * m.a00 + a21 * m.a10 + a22 * m.a20,

                a00 * m.a01 + a01 * m.a11 + a02 * m.a21,
                a10 * m.a01 + a11 * m.a11 + a12 * m.a21,
                a20 * m.a01 + a21 * m.a11 + a22 * m.a21,

                a00 * m.a02 + a01 * m.a12 + a02 * m.a22,
                a10 * m.a02 + a11 * m.a12 + a12 * m.a22,
                a20 * m.a02 + a21 * m.a12 + a22 * m.a22
        );
    }

    /**
     * Multiplies this matrix by the given vector.
     *
     * @param v The vector to multiply with
     * @return The product of this matrix and the given vector
     */
    public final float3 mult(constFloat4 v) {
        return new float3(
                a00 * v.x + a01 * v.y + a02 * v.z,
                a10 * v.x + a11 * v.y + a12 * v.z,
                a20 * v.x + a21 * v.y + a22 * v.z
        );
    }



    /**
     * Creates a new matrix with the given values for the diagonal entries, and
     * all other components set to 0.
     *
     * @param a00 The value for the top left component
     * @param a11 The value for the center component
     * @param a22 The value for the bottom right component
     * @return A diagonal matrix with the given component values
     */
    public static constFloat3x3 diag(float a00, float a11, float a22) {
        return new constFloat3x3(a00, a11, a22);
    }

    /**
     * Creates a new matrix from the given rows.
     *
     * @param r0 The first row of the matrix
     * @param r1 The second row of the matrix
     * @param r2 The third row of the matrix
     * @return A new matrix from those rows
     */
    public static constFloat3x3 fromRows(constFloat3 r0, constFloat3 r1, constFloat3 r2) {
        return new constFloat3x3(
                r0.x, r0.y, r0.z,
                r1.x, r1.y, r1.z,
                r2.x, r2.y, r2.z
        );
    }

    /**
     * Creates a new matrix from the given columns.
     *
     * @param c0 The first column of the matrix
     * @param c1 The second column of the matrix
     * @param c2 The third column of the matrix
     * @return A new matrix from those columns
     */
    public static constFloat3x3 fromColumns(constFloat3 c0, constFloat3 c1, constFloat3 c2) {
        return new constFloat3x3(
                c0.x, c1.x, c2.x,
                c0.y, c1.y, c2.y,
                c0.z, c1.z, c2.z
        );
    }

    /**
     * Creates a new matrix from the given array.
     *
     * @param components The components for the matrix, row by row. Must contain at least
     *                   as many elements as the matrix will have components
     * @return A new matrix with the specified components
     */
    public static constFloat3x3 fromArray(float[] components) {
        return new constFloat3x3(
                components[0], components[1], components[2],
                components[3], components[4], components[5],
                components[6], components[7], components[8]
        );
    }

    /**
     * Creates a new matrix by reading the component values from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the top left component, then row by row
     * @return A new matrix with the read components
     */
    public static constFloat3x3 fromArray(float[] arr, int offset) {
        return new constFloat3x3(
                arr[offset], arr[offset+1], arr[offset+2],
                arr[offset+3], arr[offset+4], arr[offset+5],
                arr[offset+6], arr[offset+7], arr[offset+8]
        );
    }
}
