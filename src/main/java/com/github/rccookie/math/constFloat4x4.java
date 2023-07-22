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
 * A 4x4 float matrix with read-only access.
 */
@SuppressWarnings({"DuplicatedCode"})
public class constFloat4x4 implements Cloneable<float4x4>, JsonSerializable {

    static {
        JsonDeserialization.register(constFloat4x4.class, json -> {
            if(json.isArray()) {
                if(json.get(0).isArray())
                    return fromRows(json.get(0).as(float4.class), json.get(1).as(float4.class), json.get(2).as(float4.class), json.get(3).as(float4.class));
                return fromArray(json.as(float[].class));
            }
            if(json.contains("a"))
                return new constFloat4x4(json.get("a").asFloat(), json.get("b").asFloat(), json.get("c").asFloat(), json.get("d").asFloat(),
                        json.get("e").asFloat(), json.get("f").asFloat(), json.get("g").asFloat(), json.get("h").asFloat(),
                        json.get("i").asFloat(), json.get("j").asFloat(), json.get("k").asFloat(), json.get("l").asFloat(),
                        json.get("m").asFloat(), json.get("n").asFloat(), json.get("o").asFloat(), json.get("p").asFloat());
            if(json.contains("a00"))
                return new constFloat4x4(json.get("a00").asFloat(), json.get("a01").asFloat(), json.get("a02").asFloat(), json.get("a03").asFloat(),
                        json.get("a10").asFloat(), json.get("a11").asFloat(), json.get("a12").asFloat(), json.get("a13").asFloat(),
                        json.get("a20").asFloat(), json.get("a21").asFloat(), json.get("a22").asFloat(), json.get("a23").asFloat(),
                        json.get("a30").asFloat(), json.get("a31").asFloat(), json.get("a32").asFloat(), json.get("a33").asFloat());
            if(json.contains("m00"))
                return new constFloat4x4(json.get("m00").asFloat(), json.get("m01").asFloat(), json.get("m02").asFloat(), json.get("m03").asFloat(),
                        json.get("m10").asFloat(), json.get("m11").asFloat(), json.get("m12").asFloat(), json.get("m13").asFloat(),
                        json.get("m20").asFloat(), json.get("m21").asFloat(), json.get("m22").asFloat(), json.get("m23").asFloat(),
                        json.get("m30").asFloat(), json.get("m31").asFloat(), json.get("m32").asFloat(), json.get("m33").asFloat());
            if(json.contains("a11"))
                return new constFloat4x4(json.get("a11").asFloat(), json.get("a12").asFloat(), json.get("a13").asFloat(), json.get("a14").asFloat(),
                        json.get("a21").asFloat(), json.get("a22").asFloat(), json.get("a23").asFloat(), json.get("a24").asFloat(),
                        json.get("a31").asFloat(), json.get("a32").asFloat(), json.get("a33").asFloat(), json.get("a34").asFloat(),
                        json.get("a41").asFloat(), json.get("a42").asFloat(), json.get("a43").asFloat(), json.get("a44").asFloat());
            return new constFloat4x4(json.get("m11").asFloat(), json.get("m12").asFloat(), json.get("m13").asFloat(), json.get("m14").asFloat(),
                    json.get("m21").asFloat(), json.get("m22").asFloat(), json.get("m23").asFloat(), json.get("m24").asFloat(),
                    json.get("m31").asFloat(), json.get("m32").asFloat(), json.get("m33").asFloat(), json.get("m34").asFloat(),
                    json.get("m41").asFloat(), json.get("m42").asFloat(), json.get("m43").asFloat(), json.get("m44").asFloat());
        });
    }

    /**
     * The zero matrix.
     */
    public static final constFloat4x4 zero = new constFloat4x4();
    /**
     * The identity matrix.
     */
    public static final constFloat4x4 id = new constFloat4x4(1,0,0,0,
                                                             0,1,0,0,
                                                             0,0,1,0,
                                                             0,0,0,1);
    /**
     * A matrix with all components set to 1.
     */
    public static final constFloat4x4 one = new constFloat4x4(1,1,1,1,
                                                              1,1,1,1,
                                                              1,1,1,1,
                                                              1,1,1,1);


    /**
     * The components of the matrix.
     */
    protected float a00, a01, a02, a03,
                    a10, a11, a12, a13,
                    a20, a21, a22, a23,
                    a30, a31, a32, a33;

    protected constFloat4x4() { }

    protected constFloat4x4(float a00, float a11, float a22, float a33) {
        this.a00 = a00;
        this.a11 = a11;
        this.a22 = a22;
        this.a33 = a33;
    }

    /**
     * Creates a new matrix.
     */
    public constFloat4x4(float a00, float a01, float a02, float a03,
                         float a10, float a11, float a12, float a13,
                         float a20, float a21, float a22, float a23,
                         float a30, float a31, float a32, float a33) {
        this.a00 = a00;
        this.a01 = a01;
        this.a02 = a02;
        this.a03 = a03;
        this.a10 = a10;
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a20 = a20;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a30 = a30;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
    }

    /**
     * Creates a new matrix.
     *
     * @param row0 The components for the first row
     * @param row1 The components for the second row
     * @param row2 The components for the third row
     * @param row3 The components for the fourth row
     */
    public constFloat4x4(constFloat4 row0, constFloat4 row1, constFloat4 row2, constFloat4 row3) {
        this.a00 = row0.x;
        this.a01 = row0.y;
        this.a02 = row0.z;
        this.a03 = row0.w;
        this.a10 = row1.x;
        this.a11 = row1.y;
        this.a12 = row1.z;
        this.a13 = row1.w;
        this.a20 = row2.x;
        this.a21 = row2.y;
        this.a22 = row2.z;
        this.a23 = row2.w;
        this.a30 = row3.x;
        this.a31 = row3.y;
        this.a32 = row3.z;
        this.a33 = row3.w;
    }


    /**
     * Returns a string representation of this matrix. Rows are separated by commas, columns
     * by double spaces.
     *
     * @return A string representation of this matrix
     */
    @Override
    public final String toString() {
        return "[" + a00 + "  " + a01 + "  " + a02 + "  " + a03 +
              ", " + a10 + "  " + a11 + "  " + a12 + "  " + a13 +
              ", " + a20 + "  " + a21 + "  " + a22 + "  " + a23 +
              ", " + a30 + "  " + a31 + "  " + a32 + "  " + a33 + "]";
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
        return (obj instanceof constFloat4x4 && equals((constFloat4x4) obj)) || (obj instanceof constFloatMxN && ((constFloatMxN) obj).equals(this));
    }

    /**
     * Returns whether the given matrix is equal to this matrix.
     *
     * @param m The matrix to test
     * @return Whether the matrix is equal to this matrix
     */
    public final boolean equals(constFloat4x4 m) {
        return a00 == m.a00 && a01 == m.a01 && a02 == m.a02 && a03 == m.a03 &&
               a10 == m.a10 && a11 == m.a11 && a12 == m.a12 && a13 == m.a13 &&
               a20 == m.a20 && a21 == m.a21 && a22 == m.a22 && a23 == m.a23 &&
               a30 == m.a30 && a31 == m.a31 && a32 == m.a32 && a33 == m.a33;
    }

    /**
     * Returns whether the given matrix is approximately equal to this matrix, using the given
     * maximum permitted component-wise difference.
     *
     * @param m The matrix to test
     * @param ep The maximum permitted component-wise difference
     * @return Whether the matrix is approximately equal to this matrix
     */
    public final boolean equals(constFloat4x4 m, float ep) {
        return abs(m.a00 - a00) <= ep && abs(m.a01 - a01) <= ep && abs(m.a02 - a02) <= ep && abs(m.a03 - a03) <= ep &&
               abs(m.a10 - a10) <= ep && abs(m.a11 - a11) <= ep && abs(m.a12 - a12) <= ep && abs(m.a13 - a13) <= ep &&
               abs(m.a20 - a20) <= ep && abs(m.a21 - a21) <= ep && abs(m.a22 - a22) <= ep && abs(m.a23 - a23) <= ep &&
               abs(m.a30 - a30) <= ep && abs(m.a31 - a31) <= ep && abs(m.a32 - a32) <= ep && abs(m.a33 - a33) <= ep;
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
    public final @NotNull float4x4 clone() {
        return new float4x4(
                a00, a01, a02, a03,
                a10, a11, a12, a13,
                a20, a21, a22, a23,
                a30, a31, a32, a33
        );
    }

    @Override
    public final Object toJson() {
        return new JsonArray(
                new JsonArray(a00, a01, a02, a03),
                new JsonArray(a10, a11, a12, a13),
                new JsonArray(a20, a21, a22, a23),
                new JsonArray(a30, a31, a32, a33)
        );
    }


    /**
     * Returns the first component of the first row.
     *
     * @return The component (0,0)
     */
    public final float a00() {
        return a00;
    }

    /**
     * Returns the second component of the first row.
     *
     * @return The component (0,1)
     */
    public final float a01() {
        return a01;
    }

    /**
     * Returns the third component of the first row.
     *
     * @return The component (0,2)
     */
    public final float a02() {
        return a02;
    }

    /**
     * Returns the fourth component of the first row.
     *
     * @return The component (0,3)
     */
    public final float a03() {
        return a03;
    }

    /**
     * Returns the first component of the second row.
     *
     * @return The component (1,0)
     */
    public final float a10() {
        return a10;
    }

    /**
     * Returns the second component of the second row.
     *
     * @return The component (1,1)
     */
    public final float a11() {
        return a11;
    }

    /**
     * Returns the third component of the second row.
     *
     * @return The component (1,2)
     */
    public final float a12() {
        return a12;
    }

    /**
     * Returns the fourth component of the second row.
     *
     * @return The component (1,3)
     */
    public final float a13() {
        return a13;
    }

    /**
     * Returns the first component of the third row.
     *
     * @return The component (2,0)
     */
    public final float a20() {
        return a20;
    }

    /**
     * Returns the second component of the third row.
     *
     * @return The component (2,1)
     */
    public final float a21() {
        return a21;
    }

    /**
     * Returns the third component of the third row.
     *
     * @return The component (2,2)
     */
    public final float a22() {
        return a22;
    }

    /**
     * Returns the fourth component of the third row.
     *
     * @return The component (2,3)
     */
    public final float a23() {
        return a23;
    }

    /**
     * Returns the first component of the fourth row.
     *
     * @return The component (3,0)
     */
    public final float a30() {
        return a30;
    }

    /**
     * Returns the second component of the fourth row.
     *
     * @return The component (3,1)
     */
    public final float a31() {
        return a31;
    }

    /**
     * Returns the third component of the fourth row.
     *
     * @return The component (3,2)
     */
    public final float a32() {
        return a32;
    }

    /**
     * Returns the fourth component of the fourth row.
     *
     * @return The component (3,3)
     */
    public final float a33() {
        return a33;
    }

    /**
     * Returns the first row of the matrix.
     *
     * @return The first row
     */
    public final float4 row0() {
        return new float4(a00, a01, a02, a03);
    }

    /**
     * Returns the second row of the matrix.
     *
     * @return The second row
     */
    public final float4 row1() {
        return new float4(a10, a11, a12, a13);
    }

    /**
     * Returns the third row of the matrix.
     *
     * @return The third row
     */
    public final float4 row2() {
        return new float4(a20, a21, a22, a23);
    }

    /**
     * Returns the fourth row of the matrix.
     *
     * @return The fourth row
     */
    public final float4 row3() {
        return new float4(a30, a31, a32, a33);
    }

    /**
     * Returns the first column of the matrix.
     *
     * @return The first column
     */
    public final float4 column0() {
        return new float4(a00, a10, a20, a30);
    }

    /**
     * Returns the second column of the matrix.
     *
     * @return The second column
     */
    public final float4 column1() {
        return new float4(a01, a11, a21, a31);
    }

    /**
     * Returns the third column of the matrix.
     *
     * @return The third column
     */
    public final float4 column2() {
        return new float4(a02, a12, a22, a32);
    }

    /**
     * Returns the fourth column of the matrix.
     *
     * @return The fourth column
     */
    public final float4 column3() {
        return new float4(a03, a13, a23, a33);
    }

    /**
     * Returns the component at the specified index.
     *
     * @param i The row index
     * @param j The column index
     * @return The component at that index
     */
    public final float component(@Range(from = 0, to = 3) int i, @Range(from = 0, to = 3) int j) {
        switch(i) {
            case 0: switch(j) {
                case 0: return a00;
                case 1: return a01;
                case 2: return a02;
                case 3: return a03;
            } break;
            case 1: switch(j) {
                case 0: return a10;
                case 1: return a11;
                case 2: return a12;
                case 3: return a13;
            } break;
            case 2: switch(j) {
                case 0: return a20;
                case 1: return a21;
                case 2: return a22;
                case 3: return a23;
            } break;
            case 3: switch(j) {
                case 0: return a30;
                case 1: return a31;
                case 2: return a32;
                case 3: return a33;
            } break;
        }
        throw new ArgumentOutOfRangeException("Index ("+i+","+j+") for 4x4 matrix");
    }

    /**
     * Returns the row at the specified index.
     *
     * @param i The index of the row
     * @return The row at that index
     */
    public final float4 row(@Range(from = 0, to = 3) int i) {
        switch(i) {
            case 0: return new float4(a00, a01, a02, a03);
            case 1: return new float4(a10, a11, a12, a13);
            case 2: return new float4(a20, a21, a22, a23);
            case 3: return new float4(a30, a31, a32, a33);
            default: throw new ArgumentOutOfRangeException(i, 0, 4);
        }
    }

    /**
     * Returns the column at the specified index.
     *
     * @param j The index of the column
     * @return The column at that index
     */
    public final float4 column(@Range(from = 0, to = 3) int j) {
        switch(j) {
            case 0: return new float4(a00, a10, a20, a30);
            case 1: return new float4(a01, a11, a21, a31);
            case 2: return new float4(a02, a12, a22, a32);
            case 3: return new float4(a03, a13, a23, a33);
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
                a00, a01, a02, a03,
                a10, a11, a12, a13,
                a20, a21, a22, a23,
                a30, a31, a32, a33
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
        arr[offset]    = a00;
        arr[offset+1]  = a01;
        arr[offset+2]  = a02;
        arr[offset+3]  = a03;
        arr[offset+4]  = a10;
        arr[offset+5]  = a11;
        arr[offset+6]  = a12;
        arr[offset+7]  = a13;
        arr[offset+8]  = a20;
        arr[offset+9]  = a21;
        arr[offset+10] = a22;
        arr[offset+11] = a23;
        arr[offset+12] = a30;
        arr[offset+13] = a31;
        arr[offset+14] = a32;
        arr[offset+15] = a33;
        return arr;
    }

    /**
     * Returns the matrix as array of rows.
     *
     * @return The rows of the matrix, as arrays
     */
    public final float[][] to2dArray() {
        return new float[][] {
                new float[] { a00, a01, a02, a03 },
                new float[] { a10, a11, a12, a13 },
                new float[] { a20, a21, a22, a23 },
                new float[] { a30, a31, a32, a33 }
        };
    }


    /**
     * Returns whether all components of this matrix are zero.
     *
     * @return Whether all components are 0
     */
    public final boolean isZero() {
        return a00 == 0 && a01 == 0 && a02 == 0 && a03 == 0 &&
               a10 == 0 && a11 == 0 && a12 == 0 && a13 == 0 &&
               a20 == 0 && a21 == 0 && a22 == 0 && a23 == 0 &&
               a30 == 0 && a31 == 0 && a32 == 0 && a33 == 0;
    }

    /**
     * Returns whether all components of this matrix are approximately zero, using the given
     * maximum permitted component-wise difference.
     *
     * @param ep The maximum permitted component-wise difference from zero
     * @return Whether this matrix is approximately 0
     */
    public final boolean isZero(float ep) {
        return abs(a00) <= ep && abs(a01) <= ep && abs(a02) <= ep && abs(a03) <= ep &&
               abs(a10) <= ep && abs(a11) <= ep && abs(a12) <= ep && abs(a13) <= ep &&
               abs(a20) <= ep && abs(a21) <= ep && abs(a22) <= ep && abs(a23) <= ep &&
               abs(a30) <= ep && abs(a31) <= ep && abs(a32) <= ep && abs(a33) <= ep;
    }

    /**
     * Returns whether all components of the matrix are finite.
     *
     * @return Whether all components are finite
     */
    public final boolean isFinite() {
        return Float.isFinite(a00) && Float.isFinite(a01) && Float.isFinite(a02) && Float.isFinite(a03) &&
               Float.isFinite(a10) && Float.isFinite(a11) && Float.isFinite(a12) && Float.isFinite(a13) &&
               Float.isFinite(a20) && Float.isFinite(a21) && Float.isFinite(a22) && Float.isFinite(a23) &&
               Float.isFinite(a30) && Float.isFinite(a31) && Float.isFinite(a32) && Float.isFinite(a33);
    }

    /**
     * Returns whether any components of the matrix are infinite.
     *
     * @return Whether any components are infinite
     */
    public final boolean isInfinite() {
        return Float.isInfinite(a00) || Float.isInfinite(a01) || Float.isInfinite(a02) || Float.isInfinite(a03) ||
               Float.isInfinite(a10) || Float.isInfinite(a11) || Float.isInfinite(a12) || Float.isInfinite(a13) ||
               Float.isInfinite(a20) || Float.isInfinite(a21) || Float.isInfinite(a22) || Float.isInfinite(a23) ||
               Float.isInfinite(a30) || Float.isInfinite(a31) || Float.isInfinite(a32) || Float.isInfinite(a33);
    }

    /**
     * Returns whether any components of the matrix are {@link Float#NaN}.
     *
     * @return Whether any components are {@link Float#NaN}
     */
    public final boolean isNaN() {
        return Float.isNaN(a00) || Float.isNaN(a01) || Float.isNaN(a02) || Float.isNaN(a03) ||
               Float.isNaN(a10) || Float.isNaN(a11) || Float.isNaN(a12) || Float.isNaN(a13) ||
               Float.isNaN(a20) || Float.isNaN(a21) || Float.isNaN(a22) || Float.isNaN(a23) ||
               Float.isNaN(a30) || Float.isNaN(a31) || Float.isNaN(a32) || Float.isNaN(a33);
    }

    /**
     * Returns whether this matrix is a diagonal matrix.
     *
     * @return Whether this matrix is diagonal
     */
    public final boolean isDiagonal() {
        return a01 == 0 && a02 == 0 && a03 == 0 &&
               a10 == 0 && a12 == 0 && a13 == 0 &&
               a20 == 0 && a21 == 0 && a23 == 0 &&
               a30 == 0 && a31 == 0 && a32 == 0;
    }

    /**
     * Returns whether this matrix is an upper triangular matrix.
     *
     * @return Whether this matrix is upper triangular
     */
    public final boolean isUpperTriangular() {
        return a10 == 0 &&
               a20 == 0 && a21 == 0 &&
               a30 == 0 && a31 == 0 && a32 == 0;
    }

    /**
     * Returns whether this matrix is a lower triangular matrix.
     *
     * @return Whether this matrix is lower triangular
     */
    public final boolean isLowerTriangular() {
        return a01 == 0 && a02 == 0 && a03 == 0 &&
                           a12 == 0 && a13 == 0 &&
                                       a23 == 0;
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
        return a03 * a12 * a21 * a30 - a02 * a13 * a21 * a30 -
               a03 * a11 * a22 * a30 + a01 * a13 * a22 * a30 +
               a02 * a11 * a23 * a30 - a01 * a12 * a23 * a30 -
               a03 * a12 * a20 * a31 + a02 * a13 * a20 * a31 +
               a03 * a10 * a22 * a31 - a00 * a13 * a22 * a31 -
               a02 * a10 * a23 * a31 + a00 * a12 * a23 * a31 +
               a03 * a11 * a20 * a32 - a01 * a13 * a20 * a32 -
               a03 * a10 * a21 * a32 + a00 * a13 * a21 * a32 +
               a01 * a10 * a23 * a32 - a00 * a11 * a23 * a32 -
               a02 * a11 * a20 * a33 + a01 * a12 * a20 * a33 +
               a02 * a10 * a21 * a33 - a00 * a12 * a21 * a33 -
               a01 * a10 * a22 * a33 + a00 * a11 * a22 * a33;
    }

    /**
     * Returns the trace of the matrix
     *
     * @return The trace of the matrix
     */
    public final float trace() {
        return a00 + a11 + a22 + a33;
    }


    /**
     * Returns this matrix negated.
     *
     * @return A new matrix representing this matrix component-wise negated
     */
    public final float4x4 negated() {
        return new float4x4(
                -a00, -a01, -a02, -a03,
                -a10, -a11, -a12, -a13,
                -a20, -a21, -a22, -a23,
                -a30, -a31, -a32, -a33
        );
    }

    /**
     * Returns the transpose of this matrix.
     *
     * @return A new matrix representing the transposition of this matrix
     */
    public final float4x4 transp() {
        return new float4x4(
                a00, a10, a20, a30,
                a01, a11, a21, a31,
                a02, a12, a22, a32,
                a03, a13, a23, a33
        );
    }

    /**
     * Returns the inverse of this matrix, or <code>null</code> if this matrix is singular.
     *
     * @return The inverse of this matrix or <code>null</code>
     */
    public final float4x4 inverse() {
        return inverse(0);
    }

    /**
     * Returns the inverse of this matrix, or <code>null</code> if this matrix is singular.
     *
     * @param ep The maximum difference from 0 of the determinant for the matrix to be considered
     *           singular
     * @return The inverse of this matrix or <code>null</code>
     */
    public final float4x4 inverse(float ep) {
        float4x4 inv = new float4x4();

        inv.a00 = a11  * a22 * a33 -
                a11  * a23 * a32 -
                a21  * a12  * a33 +
                a21  * a13  * a32 +
                a31 * a12  * a23 -
                a31 * a13  * a22;

        inv.a10 = -a10  * a22 * a33 +
                a10  * a23 * a32 +
                a20  * a12  * a33 -
                a20  * a13  * a32 -
                a30 * a12  * a23 +
                a30 * a13  * a22;

        inv.a20 = a10  * a21 * a33 -
                a10  * a23 * a31 -
                a20  * a11 * a33 +
                a20  * a13 * a31 +
                a30 * a11 * a23 -
                a30 * a13 * a21;

        inv.a30 = -a10  * a21 * a32 +
                a10  * a22 * a31 +
                a20  * a11 * a32 -
                a20  * a12 * a31 -
                a30 * a11 * a22 +
                a30 * a12 * a21;

        inv.a01 = -a01  * a22 * a33 +
                a01  * a23 * a32 +
                a21  * a02 * a33 -
                a21  * a03 * a32 -
                a31 * a02 * a23 +
                a31 * a03 * a22;

        inv.a11 = a00  * a22 * a33 -
                a00  * a23 * a32 -
                a20  * a02 * a33 +
                a20  * a03 * a32 +
                a30 * a02 * a23 -
                a30 * a03 * a22;

        inv.a21 = -a00  * a21 * a33 +
                a00  * a23 * a31 +
                a20  * a01 * a33 -
                a20  * a03 * a31 -
                a30 * a01 * a23 +
                a30 * a03 * a21;

        inv.a31 = a00  * a21 * a32 -
                a00  * a22 * a31 -
                a20  * a01 * a32 +
                a20  * a02 * a31 +
                a30 * a01 * a22 -
                a30 * a02 * a21;

        inv.a02 = a01  * a12 * a33 -
                a01  * a13 * a32 -
                a11  * a02 * a33 +
                a11  * a03 * a32 +
                a31 * a02 * a13 -
                a31 * a03 * a12;

        inv.a12 = -a00  * a12 * a33 +
                a00  * a13 * a32 +
                a10  * a02 * a33 -
                a10  * a03 * a32 -
                a30 * a02 * a13 +
                a30 * a03 * a12;

        inv.a22 = a00  * a11 * a33 -
                a00  * a13 * a31 -
                a10  * a01 * a33 +
                a10  * a03 * a31 +
                a30 * a01 * a13 -
                a30 * a03 * a11;

        inv.a32 = -a00  * a11 * a32 +
                a00  * a12 * a31 +
                a10  * a01 * a32 -
                a10  * a02 * a31 -
                a30 * a01 * a12 +
                a30 * a02 * a11;

        inv.a03 = -a01 * a12 * a23 +
                a01 * a13 * a22 +
                a11 * a02 * a23 -
                a11 * a03 * a22 -
                a21 * a02 * a13 +
                a21 * a03 * a12;

        inv.a13 = a00 * a12 * a23 -
                a00 * a13 * a22 -
                a10 * a02 * a23 +
                a10 * a03 * a22 +
                a20 * a02 * a13 -
                a20 * a03 * a12;

        inv.a23 = -a00 * a11 * a23 +
                a00 * a13 * a21 +
                a10 * a01 * a23 -
                a10 * a03 * a21 -
                a20 * a01 * a13 +
                a20 * a03 * a11;

        inv.a33 = a00 * a11 * a22 -
                a00 * a12 * a21 -
                a10 * a01 * a22 +
                a10 * a02 * a21 +
                a20 * a01 * a12 -
                a20 * a02 * a11;

        float det = a00 * inv.a00 + a01 * inv.a10 + a02 * inv.a20 + a03 * inv.a30;

        if(det >= -ep && det <= ep)
            return null;

        return inv.scale(1 / det);
    }

    /**
     * Returns the matrix that gets created by omitting the ith row and jth column of this matrix.
     *
     * @param i The row to omit
     * @param j The column to omit
     * @return The (i,j)th submatrix
     */
    public final float3x3 submatrix(@Range(from = 0, to = 3) int i, @Range(from = 0, to = 3) int j) {
        switch(i) {
            case 0: switch(j) {
                case 0: return new float3x3(a11, a12, a13,
                                            a21, a22, a23,
                                            a31, a32, a33);
                case 1: return new float3x3(a10, a12, a13,
                                            a20, a22, a23,
                                            a30, a32, a33);
                case 2: return new float3x3(a10, a11, a13,
                                            a20, a21, a23,
                                            a30, a31, a33);
                case 3: return new float3x3(a10, a11, a12,
                                            a20, a21, a22,
                                            a30, a31, a32);
            } break;
            case 1: switch(j) {
                case 0: return new float3x3(a01, a02, a03,
                                            a21, a22, a23,
                                            a31, a32, a33);
                case 1: return new float3x3(a00, a02, a03,
                                            a20, a22, a23,
                                            a30, a32, a33);
                case 2: return new float3x3(a00, a01, a03,
                                            a20, a21, a23,
                                            a30, a31, a33);
                case 3: return new float3x3(a00, a01, a02,
                                            a20, a21, a22,
                                            a30, a31, a32);
            } break;
            case 2: switch(j) {
                case 0: return new float3x3(a01, a02, a03,
                                            a11, a12, a13,
                                            a31, a32, a33);
                case 1: return new float3x3(a00, a02, a03,
                                            a10, a12, a13,
                                            a30, a32, a33);
                case 2: return new float3x3(a00, a01, a03,
                                            a10, a11, a13,
                                            a30, a31, a33);
                case 3: return new float3x3(a00, a01, a02,
                                            a10, a11, a12,
                                            a30, a31, a32);
            } break;
            case 3: switch(j) {
                case 0: return new float3x3(a01, a02, a03,
                                            a11, a12, a13,
                                            a21, a22, a23);
                case 1: return new float3x3(a00, a02, a03,
                                            a10, a12, a13,
                                            a20, a22, a23);
                case 2: return new float3x3(a00, a01, a03,
                                            a10, a11, a13,
                                            a20, a21, a23);
                case 3: return new float3x3(a00, a01, a02,
                                            a10, a11, a12,
                                            a20, a21, a22);
            } break;
        }
        throw new ArgumentOutOfRangeException("Index ("+i+","+j+") for 4x4 matrix");
    }

    /**
     * Returns the minor of the given element, that is, the determinant of the submatrix
     * of that element.
     *
     * @param i The row to omit
     * @param j The column to omit
     * @return The (i,j)th minor
     */
    public final float minor(@Range(from = 0, to = 3) int i, @Range(from = 0, to = 3) int j) {
        return submatrix(i,j).det();
    }

    /**
     * Returns the cofactor of the given element, that is, the minor of that element multiplied
     * by <code>(-1)^(i+j)</code>.
     *
     * @param i The row to omit in the submatrix
     * @param j The column to omit in the submatrix
     * @return The (i,j)th cofactor
     */
    public final float cofactor(@Range(from = 0, to = 3) int i, @Range(from = 0, to = 3) int j) {
        return (i+j & 1) == 0 ? minor(i,j) : -minor(i,j);
    }

    /**
     * Returns the adjugate matrix of this matrix, that is, the transpose of its cofactor matrix.
     *
     * @return This matrix's adjugate matrix
     */
    public final float4x4 adjugate() {
        return new float4x4(
                cofactor(0,0), cofactor(1,0), cofactor(2,0), cofactor(3,0),
                cofactor(0,1), cofactor(1,1), cofactor(2,1), cofactor(3,1),
                cofactor(0,2), cofactor(1,2), cofactor(2,2), cofactor(3,2),
                cofactor(0,3), cofactor(1,3), cofactor(2,3), cofactor(3,3)
        );
    }


    /**
     * Adds the given value to all components of this matrix.
     *
     * @param x The value to add to all components
     * @return A new matrix with the value added to all components
     */
    public final float4x4 added(float x) {
        return new float4x4(
                a00+x, a01+x, a02+x, a03+x,
                a10+x, a11+x, a12+x, a13+x,
                a20+x, a21+x, a22+x, a23+x,
                a30+x, a31+x, a32+x, a33+x
        );
    }

    /**
     * Adds the given matrix component-wise to this matrix.
     *
     * @param m The matrix to add
     * @return A new matrix representing the sum of this and the given matrix
     */
    public final float4x4 added(constFloat4x4 m) {
        return new float4x4(
                a00 + m.a00, a01 + m.a01, a02 + m.a02, a03 + m.a03,
                a10 + m.a10, a11 + m.a11, a12 + m.a12, a13 + m.a13,
                a20 + m.a20, a21 + m.a21, a22 + m.a22, a23 + m.a23,
                a30 + m.a30, a31 + m.a31, a32 + m.a32, a33 + m.a33
        );
    }

    /**
     * Subtracts the given value from all components of this matrix.
     *
     * @param x The value to subtract from all components
     * @return A new matrix with the value subtracted from all components
     */
    public final float4x4 subed(float x) {
        return new float4x4(
                a00-x, a01-x, a02-x, a03-x,
                a10-x, a11-x, a12-x, a13-x,
                a20-x, a21-x, a22-x, a23-x,
                a30-x, a31-x, a32-x, a33-x
        );
    }

    /**
     * Subtracts the given matrix component-wise from this matrix.
     *
     * @param m The matrix to subtract
     * @return A new matrix representing the subtraction of the given matrix from this matrix
     */
    public final float4x4 subed(constFloat4x4 m) {
        return new float4x4(
                a00 - m.a00, a01 - m.a01, a02 - m.a02, a03 - m.a03,
                a10 - m.a10, a11 - m.a11, a12 - m.a12, a13 - m.a13,
                a20 - m.a20, a21 - m.a21, a22 - m.a22, a23 - m.a23,
                a30 - m.a30, a31 - m.a31, a32 - m.a32, a33 - m.a33
        );
    }

    /**
     * Multiplies all components of this matrix by the given factor.
     *
     * @param f The factor to multiply all components with
     * @return A new matrix with all components multiplied by the given factor
     */
    public final float4x4 scaled(float f) {
        return new float4x4(
                a00*f, a01*f, a02*f, a03*f,
                a10*f, a11*f, a12*f, a13*f,
                a20*f, a21*f, a22*f, a23*f,
                a30*f, a31*f, a32*f, a33*f
        );
    }

    /**
     * Adds this matrix component-wise by the given matrix.
     *
     * @param m The matrix to multiply by
     * @return A new matrix representing the component-wise product of this and the given matrix
     */
    public final float4x4 scaled(constFloat4x4 m) {
        return new float4x4(
                a00 - m.a00, a01 - m.a01, a02 - m.a02, a03 - m.a03,
                a10 - m.a10, a11 - m.a11, a12 - m.a12, a13 - m.a13,
                a20 - m.a20, a21 - m.a21, a22 - m.a22, a23 - m.a23,
                a30 - m.a30, a31 - m.a31, a32 - m.a32, a33 - m.a33
        );
    }

    /**
     * Multiplies this matrix by the given matrix.
     *
     * @param m The matrix to multiply with
     * @return The product of this and the given matrix
     */
    public final float4x4 mult(constFloat4x4 m) {
        return new float4x4(
                a00 * m.a00 + a01 * m.a10 + a02 * m.a20 + a03 * m.a30,
                a10 * m.a00 + a11 * m.a10 + a12 * m.a20 + a13 * m.a30,
                a20 * m.a00 + a21 * m.a10 + a22 * m.a20 + a23 * m.a30,
                a30 * m.a00 + a31 * m.a10 + a32 * m.a20 + a33 * m.a30,

                a00 * m.a01 + a01 * m.a11 + a02 * m.a21 + a03 * m.a31,
                a10 * m.a01 + a11 * m.a11 + a12 * m.a21 + a13 * m.a31,
                a20 * m.a01 + a21 * m.a11 + a22 * m.a21 + a23 * m.a31,
                a30 * m.a01 + a31 * m.a11 + a32 * m.a21 + a33 * m.a31,

                a00 * m.a02 + a01 * m.a12 + a02 * m.a22 + a03 * m.a32,
                a10 * m.a02 + a11 * m.a12 + a12 * m.a22 + a13 * m.a32,
                a20 * m.a02 + a21 * m.a12 + a22 * m.a22 + a23 * m.a32,
                a30 * m.a02 + a31 * m.a12 + a32 * m.a22 + a33 * m.a32,

                a00 * m.a03 + a01 * m.a13 + a02 * m.a23 + a03 * m.a33,
                a10 * m.a03 + a11 * m.a13 + a12 * m.a23 + a13 * m.a33,
                a20 * m.a03 + a21 * m.a13 + a22 * m.a23 + a23 * m.a33,
                a30 * m.a03 + a31 * m.a13 + a32 * m.a23 + a33 * m.a33
        );
    }

    /**
     * Multiplies this matrix by the given vector.
     *
     * @param v The vector to multiply with
     * @return The product of this matrix and the given vector
     */
    public final float4 mult(constFloat4 v) {
        return new float4(
                a00 * v.x + a01 * v.y + a02 * v.z + a03 * v.w,
                a10 * v.x + a11 * v.y + a12 * v.z + a13 * v.w,
                a20 * v.x + a21 * v.y + a22 * v.z + a23 * v.w,
                a30 * v.x + a31 * v.y + a32 * v.z + a33 * v.w
        );
    }



    /**
     * Creates a new matrix with the given values for the diagonal entries, and
     * all other components set to 0.
     *
     * @param a00 The value for the component in the first row and column
     * @param a11 The value for the component in the second row and column
     * @param a22 The value for the component in the third row and column
     * @param a33 The value for the component in the fourth row and column
     * @return A diagonal matrix with the given component values
     */
    public static constFloat4x4 diag(float a00, float a11, float a22, float a33) {
        return new constFloat4x4(a00, a11, a22, a33);
    }

    /**
     * Creates a new matrix from the given rows.
     *
     * @param r0 The first row of the matrix
     * @param r1 The second row of the matrix
     * @param r2 The third row of the matrix
     * @param r3 The forth row of the matrix
     * @return A new matrix from those rows
     */
    public static constFloat4x4 fromRows(constFloat4 r0, constFloat4 r1, constFloat4 r2, constFloat4 r3) {
        return new constFloat4x4(
                r0.x, r0.y, r0.z, r0.w,
                r1.x, r1.y, r1.z, r1.w,
                r2.x, r2.y, r2.z, r2.w,
                r3.x, r3.y, r3.z, r3.w
        );
    }

    /**
     * Creates a new matrix from the given columns.
     *
     * @param c0 The first column of the matrix
     * @param c1 The second column of the matrix
     * @param c2 The third column of the matrix
     * @param c3 The forth column of the matrix
     * @return A new matrix from those columns
     */
    public static constFloat4x4 fromColumns(constFloat4 c0, constFloat4 c1, constFloat4 c2, constFloat4 c3) {
        return new constFloat4x4(
                c0.x, c1.x, c2.x, c3.x,
                c0.y, c1.y, c2.y, c3.y,
                c0.z, c1.z, c2.z, c3.z,
                c0.w, c1.w, c2.w, c3.w
        );
    }

    /**
     * Creates a new matrix from the given array.
     *
     * @param components The components for the matrix, row by row. Must contain at least
     *                   as many elements as the matrix will have components
     * @return A new matrix with the specified components
     */
    public static constFloat4x4 fromArray(float[] components) {
        return new constFloat4x4(
                components[0], components[1], components[2], components[3],
                components[4], components[5], components[6], components[7],
                components[8], components[9], components[10], components[11],
                components[12], components[13], components[14], components[15]
        );
    }

    /**
     * Creates a new matrix by reading the component values from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the top left component, then row by row
     * @return A new matrix with the read components
     */
    public static constFloat4x4 fromArray(float[] arr, int offset) {
        return new constFloat4x4(
                arr[offset], arr[offset+1], arr[offset+2], arr[offset+3],
                arr[offset+4], arr[offset+5], arr[offset+6], arr[offset+7],
                arr[offset+8], arr[offset+9], arr[offset+10], arr[offset+11],
                arr[offset+12], arr[offset+13], arr[offset+14], arr[offset+15]
        );
    }
}
