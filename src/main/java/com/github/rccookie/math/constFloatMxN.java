package com.github.rccookie.math;

import java.util.Arrays;

import com.github.rccookie.json.Json;
import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.json.JsonSerializable;
import com.github.rccookie.util.Arguments;
import com.github.rccookie.util.Cloneable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import static java.lang.Math.abs;

/**
 * A float matrix with read-only access.
 */
@SuppressWarnings({"DuplicatedCode"})
public class constFloatMxN implements Cloneable<floatMxN>, JsonSerializable {

    static {
        JsonDeserialization.register(constFloatMxN.class, json -> new constFloatMxN(json.as(float[][].class)));
    }

    /**
     * The components of the matrix, rows by row.
     */
    protected final float @NotNull [] @NotNull [] rows;

    protected constFloatMxN(int m, int n) {
        rows = new float[m][n];
    }

    protected constFloatMxN(float @NotNull [] @NotNull [] ref) {
        this.rows = ref;
    }


    /**
     * Returns a string representation of this matrix. Rows are separated by commas, columns
     * by double spaces.
     *
     * @return A string representation of this matrix
     */
    @Override
    public final String toString() {
        StringBuilder str = new StringBuilder("[");
        for(float[] row : rows) {
            for(float c : row)
                str.append(c).append(' ').append(' ');
            str.setCharAt(str.length()-2, ',');
        }
        str.deleteCharAt(str.length() - 1);
        str.setCharAt(str.length() - 1, ']');
        return str.toString();
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
        return (obj instanceof constFloatMxN && equals((constFloatMxN) obj));
    }

    /**
     * Returns whether the given matrix is equal to this matrix.
     *
     * @param m The matrix to test
     * @return Whether the matrix is equal to this matrix
     */
    public final boolean equals(constFloatMxN m) {
        return Arrays.deepEquals(rows, m.rows);
    }

    /**
     * Returns whether the given matrix is equal to this matrix.
     *
     * @param m The matrix to test
     * @return Whether the matrix is equal to this matrix
     */
    public final boolean equals(constFloat2x2 m) {
        return rows.length == 2 && rows[0].length == 2 &&
                rows[0][0] == m.a && rows[0][1] == m.b && rows[1][0] == m.c && rows[1][1] == m.d;
    }

    /**
     * Returns whether the given matrix is equal to this matrix.
     *
     * @param m The matrix to test
     * @return Whether the matrix is equal to this matrix
     */
    public final boolean equals(constFloat3x3 m) {
        return rows.length == 3 && rows[0].length == 3 &&
                rows[0][0] == m.a00 && rows[0][1] == m.a01 && rows[0][2] == m.a02 &&
                rows[1][0] == m.a10 && rows[1][1] == m.a11 && rows[1][2] == m.a12 &&
                rows[2][0] == m.a20 && rows[2][1] == m.a21 && rows[2][2] == m.a22;
    }

    /**
     * Returns whether the given matrix is equal to this matrix.
     *
     * @param m The matrix to test
     * @return Whether the matrix is equal to this matrix
     */
    public final boolean equals(constFloat4x4 m) {
        return rows.length == 4 && rows[0].length == 4 &&
                rows[0][0] == m.a00 && rows[0][1] == m.a01 && rows[0][2] == m.a02 && rows[0][3] == m.a03 &&
                rows[1][0] == m.a10 && rows[1][1] == m.a11 && rows[1][2] == m.a12 && rows[1][3] == m.a13 &&
                rows[2][0] == m.a20 && rows[2][1] == m.a21 && rows[2][2] == m.a22 && rows[2][3] == m.a23 &&
                rows[3][0] == m.a30 && rows[3][1] == m.a31 && rows[3][2] == m.a32 && rows[3][3] == m.a33;
    }

    /**
     * Returns whether the given matrix is approximately equal to this matrix, using the given
     * maximum permitted component-wise difference.
     *
     * @param m The matrix to test
     * @param ep The maximum permitted component-wise difference
     * @return Whether the matrix is approximately equal to this matrix
     */
    public final boolean equals(constFloatMxN m, float ep) {
        if(rows.length != m.rows.length || rows[0].length != m.rows[0].length)
            return false;
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                if(abs(m.rows[i][j] - rows[i][j]) > ep)
                    return false;
        return true;
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
        return rows.length == 2 && rows[0].length == 2 &&
                abs(m.a - rows[0][0]) <= ep && abs(m.b - rows[0][1]) <= ep && abs(m.c - rows[1][0]) <= ep && abs(m.d - rows[1][1]) <= ep;
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
        return rows.length == 3 && rows[0].length == 3 &&
                abs(m.a00 - rows[0][0]) <= ep && abs(m.a01 - rows[0][1]) <= ep && abs(m.a02 - rows[0][2]) <= ep &&
                abs(m.a10 - rows[1][0]) <= ep && abs(m.a11 - rows[1][1]) <= ep && abs(m.a12 - rows[1][2]) <= ep &&
                abs(m.a20 - rows[2][0]) <= ep && abs(m.a21 - rows[2][1]) <= ep && abs(m.a22 - rows[2][2]) <= ep;
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
        return rows.length == 4 && rows[0].length == 4 &&
                abs(m.a00 - rows[0][0]) <= ep && abs(m.a01 - rows[0][1]) <= ep && abs(m.a02 - rows[0][2]) <= ep && abs(m.a03 - rows[0][3]) <= ep &&
                abs(m.a10 - rows[1][0]) <= ep && abs(m.a11 - rows[1][1]) <= ep && abs(m.a12 - rows[1][2]) <= ep && abs(m.a13 - rows[1][3]) <= ep &&
                abs(m.a20 - rows[2][0]) <= ep && abs(m.a21 - rows[2][1]) <= ep && abs(m.a22 - rows[2][2]) <= ep && abs(m.a23 - rows[2][3]) <= ep &&
                abs(m.a30 - rows[3][0]) <= ep && abs(m.a31 - rows[3][1]) <= ep && abs(m.a32 - rows[3][2]) <= ep && abs(m.a33 - rows[3][3]) <= ep;
    }

    /**
     * Returns a hash code for this matrix.
     *
     * @return A hash code for this matrix
     */
    @Override
    public final int hashCode() {
        return Arrays.deepHashCode(rows);
    }

    /**
     * Returns a copy of this matrix.
     *
     * @return A copy of this matrix
     */
    @Override
    public final @NotNull floatMxN clone() {
        return new floatMxN(to2dArray());
    }

    @Override
    public final Object toJson() {
        return Json.extractJson(rows);
    }


    /**
     * Returns the number of rows in this matrix
     *
     * @return The number of rows in this matrix
     */
    public final int m() {
        return rows.length;
    }

    /**
     * Returns the number of columns in this matrix
     *
     * @return The number of columns in this matrix
     */
    public final int n() {
        return rows[0].length;
    }

    /**
     * Returns the number of rows in this matrix
     *
     * @return The number of rows in this matrix
     */
    public final int height() {
        return rows.length;
    }

    /**
     * Returns the number of columns in this matrix
     *
     * @return The number of columns in this matrix
     */
    public final int width() {
        return rows[0].length;
    }

    /**
     * Returns the size of this matrix, where x is the number of rows and
     * y the number of columns.
     *
     * @return The size of this matrix
     */
    public final int2 size() {
        return new int2(rows.length, rows[0].length);
    }

    /**
     * Returns the component at the specified index.
     *
     * @param i The row index
     * @param j The column index
     * @return The component at that index
     */
    public final float component(@Range(from = 0, to = Integer.MAX_VALUE) int i, @Range(from = 0, to = Integer.MAX_VALUE) int j) {
        return rows[i][j];
    }

    /**
     * Returns the row at the specified index.
     *
     * @param i The index of the row
     * @return The row at that index
     */
    public final constFloatN row(@Range(from = 0, to = Integer.MAX_VALUE) int i) {
        return constFloatN.ref(rows[i]);
    }

    /**
     * Returns the column at the specified index.
     *
     * @param j The index of the column
     * @return The column at that index
     */
    public final floatN column(@Range(from = 0, to = Integer.MAX_VALUE) int j) {
        floatN c = new floatN(rows.length);
        for(int i=0; i<rows.length; i++)
            c.components[i] = rows[i][j];
        return c;
    }

    /**
     * Converts this matrix to an array, writing components row by row.
     *
     * @return This matrix as an array
     */
    public final float[] toArray() {
        return toArray(new float[rows.length * rows[0].length], 0);
    }

    /**
     * Writes the contents of this matrix row by row into the given array.
     *
     * @param arr The array to write into
     * @param offset The index of the first component
     * @return The supplied array
     */
    public final float[] toArray(float[] arr, int offset) {
        for(int i=0; i<rows.length; i++)
            System.arraycopy(rows[i], 0, arr, i * rows[0].length + offset, rows[i].length);
        return arr;
    }

    /**
     * Returns the matrix as array of rows.
     *
     * @return The rows of the matrix, as arrays
     */
    public final float[][] to2dArray() {
        float[][] arr = new float[rows.length][];
        for(int i=0; i<rows.length; i++)
            arr[i] = rows[i].clone();
        return arr;
    }

    /**
     * Converts this matrix into a 2x2 matrix, cutting off excess rows / columns or padding with 0s.
     *
     * @return A new 2x2 matrix based on this matrix
     */
    public final float2x2 to2x2() {
        float2x2 m = new float2x2();
        for(int i=0; i<Math.min(rows.length, 2); i++)
            for(int j=0; j<Math.min(rows[0].length, 2); j++)
                m.setComponent(i, j, rows[i][j]);
        return m;
    }

    /**
     * Converts this matrix into a 3x3 matrix, cutting off excess rows / columns or padding with 0s.
     *
     * @return A new 3x3 matrix based on this matrix
     */
    public final float3x3 to3x3() {
        float3x3 m = new float3x3();
        for(int i=0; i<Math.min(rows.length, 3); i++)
            for(int j=0; j<Math.min(rows[0].length, 3); j++)
                m.setComponent(i, j, rows[i][j]);
        return m;
    }

    /**
     * Converts this matrix into a 4x4 matrix, cutting off excess rows / columns or padding with 0s.
     *
     * @return A new 4x4 matrix based on this matrix
     */
    public final float4x4 to4x4() {
        float4x4 m = new float4x4();
        for(int i=0; i<Math.min(rows.length, 4); i++)
            for(int j=0; j<Math.min(rows[0].length, 4); j++)
                m.setComponent(i, j, rows[i][j]);
        return m;
    }

    /**
     * Returns a new matrix with the given size based on this matrix, cutting off excess rows / columns
     * or padding with 0s.
     *
     * @param m The number of rows for the new matrix
     * @param n The number of column for the new matrix
     * @return A new matrix with the given size and the same values in the overlapping area
     */
    public final floatMxN resize(int m, int n) {
        Arguments.checkRange(m, 1, null);
        Arguments.checkRange(n, 1, null);

        floatMxN res = new floatMxN(m,n);
        for(int i=0; i<Math.min(m, rows.length); i++)
            System.arraycopy(rows[i], 0, res.rows[i], 0, Math.min(n, rows[i].length));
        return res;
    }


    /**
     * Returns whether all components of this matrix are zero.
     *
     * @return Whether all components are 0
     */
    public final boolean isZero() {
        for(float[] row : rows)
            for(float c : row)
                if(c != 0) return false;
        return true;
    }

    /**
     * Returns whether all components of this matrix are approximately zero, using the given
     * maximum permitted component-wise difference.
     *
     * @param ep The maximum permitted component-wise difference from zero
     * @return Whether this matrix is approximately 0
     */
    public final boolean isZero(float ep) {
        for(float[] row : rows)
            for(float c : row)
                if(abs(c) > ep) return false;
        return true;
    }

    /**
     * Returns whether all components of the matrix are finite.
     *
     * @return Whether all components are finite
     */
    public final boolean isFinite() {
        for(float[] row : rows)
            for(float c : row)
                if(!Float.isFinite(c)) return false;
        return true;
    }

    /**
     * Returns whether any components of the matrix are infinite.
     *
     * @return Whether any components are infinite
     */
    public final boolean isInfinite() {
        for(float[] row : rows)
            for(float c : row)
                if(Float.isInfinite(c)) return true;
        return false;
    }

    /**
     * Returns whether any components of the matrix are {@link Float#NaN}.
     *
     * @return Whether any components are {@link Float#NaN}
     */
    public final boolean isNaN() {
        for(float[] row : rows)
            for(float c : row)
                if(Float.isNaN(c)) return true;
        return false;
    }

    /**
     * Returns whether this matrix has the same number of rows as columns.
     *
     * @return Whether this matrix is a square matrix
     */
    public final boolean isSquare() {
        return rows.length == rows[0].length;
    }

    /**
     * Returns whether this matrix is a diagonal matrix.
     *
     * @return Whether this matrix is diagonal
     */
    public final boolean isDiagonal() {
        if(rows.length != rows[0].length)
            throw new ArithmeticException("isDiagonal(): Not a square matrix");
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows.length; j++)
                if(i != j && rows[i][j] != 0)
                    return false;
        return true;
    }

    /**
     * Returns whether this matrix is an upper triangular matrix.
     *
     * @return Whether this matrix is upper triangular
     */
    public final boolean isUpperTriangular() {
        if(rows.length != rows[0].length)
            throw new ArithmeticException("isUpperTriangular(): Not a square matrix");
        for(int i=1; i<rows.length; i++)
            for(int j=0; j<i; j++)
                if(rows[i][j] != 0)
                    return false;
        return true;
    }

    /**
     * Returns whether this matrix is a lower triangular matrix.
     *
     * @return Whether this matrix is lower triangular
     */
    public final boolean isLowerTriangular() {
        if(rows.length != rows[0].length)
            throw new ArithmeticException("isLowerTriangular(): Not a square matrix");
        for(int i=0; i<rows.length-1; i++)
            for(int j=i+1; j<rows[i].length; j++)
                if(rows[i][j] != 0)
                    return false;
        return true;
    }

    /**
     * Returns whether this matrix is a triangular matrix.
     *
     * @return Whether this matrix is upper or lower triangular
     */
    public final boolean isTriangular() {
        return isUpperTriangular() || isLowerTriangular();
    }

    public final boolean isEchelon() {
        return isEchelon(0);
    }

    public final boolean isEchelon(float ep) {
        int left = -1;
        for(float[] row : rows) {
            int j = 0;
            for(int stop = Math.min(left, row.length-1); j<=stop; j++)
                if(abs(row[j]) > ep) return false;
            while(j < row.length && abs(row[j]) <= ep) j++;
            left = j;
        }
        return true;
    }

    public final boolean isReducedEchelon() {
        return isReducedEchelon(0, true);
    }

    public final boolean isReducedEchelon(float ep) {
        return isReducedEchelon(ep, true);
    }

    final boolean isReducedEchelon(float ep, boolean testEchelon) {
        if(testEchelon && !isEchelon())
            return false;
        for(int i=0; i<rows.length; i++) {
            int j = 0;
            while(j < rows[i].length && abs(rows[i][j]) <= ep) j++;
            if(j == rows[i].length) break;
            if(abs(rows[i][j] - 1) > ep) return false;
            for(int i2=0; i2<rows.length; i2++)
                if(i != i2 && abs(rows[i2][j]) > ep) return false;
        }
        return true;
    }

    public final floatMxN toEchelon() {
        return toEchelon(0, null);
    }

    public final floatMxN toEchelon(float ep, @Nullable floatMxN transformIdentically) {
        floatMxN m = clone();
        if(!isEchelon(ep))
            m.toEchelonInplace(ep, transformIdentically);
        return m;
    }

    public final floatMxN toReducedEchelon() {
        return toReducedEchelon(0, null);
    }

    public final floatMxN toReducedEchelon(float ep, @Nullable floatMxN transformIdentically) {
        floatMxN m = clone();
        if(isReducedEchelon(ep))
            m.toReducedEchelonInplace(ep, transformIdentically);
        return m;
    }

    /**
     * Returns the determinant of the matrix.
     *
     * @return The determinant of the matrix
     */
    public final float det() {
        if(rows.length != rows[0].length)
            throw new ArithmeticException("det(): Not a square matrix");
        if(rows.length == 1)
            return rows[0][0];
        if(rows.length == 2)
            return rows[0][0] * rows[1][1] - rows[0][1] * rows[1][0];
        if(rows.length == 3) {
            return rows[0][0] * rows[1][1] * rows[2][2]
                 - rows[0][0] * rows[1][2] * rows[2][1]
                 + rows[0][1] * rows[1][2] * rows[2][0]
                 - rows[0][1] * rows[1][0] * rows[2][2]
                 + rows[0][2] * rows[1][0] * rows[2][1]
                 - rows[0][1] * rows[1][1] * rows[2][0];
        }

        constFloatMxN trig = this;
        float det = 1;
        if(!isTriangular()) {
            trig = clone();
            det = ((floatMxN) trig).toEchelonInplace(0, null);
        }

        for(int i=0; i<rows.length; i++)
            det *= trig.rows[i][i];
        return det;
    }

    /**
     * Returns the trace of the matrix
     *
     * @return The trace of the matrix
     */
    public final float trace() {
        if(rows.length != rows[0].length)
            throw new ArithmeticException("trace(): Not a square matrix");
        float trace = 0;
        for(int i=0; i<rows.length; i++)
            trace += rows[i][i];
        return trace;
    }


    /**
     * Returns this matrix negated.
     *
     * @return A new matrix representing this matrix component-wise negated
     */
    public final floatMxN negated() {
        floatMxN neg = new floatMxN(rows.length, rows[0].length);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                neg.rows[i][j] = -rows[i][j];
        return neg;
    }

    /**
     * Returns the transpose of this matrix.
     *
     * @return A new matrix representing the transposition of this matrix
     */
    public final floatMxN transp() {
        return floatMxN.fromColumns(rows);
    }

    /**
     * Returns the inverse of this matrix, or <code>null</code> if this matrix is singular.
     *
     * @return The inverse of this matrix or <code>null</code>
     */
    public final floatMxN inverse() {
        return inverse(0);
    }

    /**
     * Returns the inverse of this matrix, or <code>null</code> if this matrix is singular.
     *
     * @param ep The maximum difference from 0 of the determinant for the matrix to be considered
     *           singular
     * @return The inverse of this matrix or <code>null</code>
     */
    public final floatMxN inverse(float ep) {
        if(rows.length != rows[0].length)
            throw new ArithmeticException("inverse(): Not a square matrix");
        if(rows.length == 1)
            return clone();

        floatMxN inv = floatMxN.id(rows.length);
        constFloatMxN echelon = toReducedEchelon(ep, inv);
        return abs(echelon.rows[rows.length - 1][rows.length - 1]) <= ep ? null : inv;
    }

    /**
     * Returns the matrix that gets created by omitting the ith row and jth column of this matrix.
     *
     * @param i The row to omit
     * @param j The column to omit
     * @return The (i,j)th submatrix
     */
    public final floatMxN submatrix(@Range(from = 0, to = Integer.MAX_VALUE) int i, @Range(from = 0, to = Integer.MAX_VALUE) int j) {
        if(rows.length == 1 || rows[0].length == 1)
            throw new ArithmeticException("Submatrix of vector");
        floatMxN sub = new floatMxN(rows.length-1, rows[0].length-1);
        for(int i2=0; i2<i; i2++) {
            System.arraycopy(rows[i2], 0, sub.rows[i2], 0, j);
            System.arraycopy(rows[i2], j+1, sub.rows[i2], j, rows[0].length - j - 1);
        }
        for(int i2=i+1; i2<rows.length; i2++) {
            System.arraycopy(rows[i2], 0, sub.rows[i2-1], 0, j);
            System.arraycopy(rows[i2], j+1, sub.rows[i2-1], j, rows[0].length - j - 1);
        }
        return sub;
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
        if(rows.length != rows[0].length)
            throw new ArithmeticException("minor(): Not a square matrix");
        if(rows.length == 1)
            throw new ArithmeticException("Minor of vector");
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
        if(rows.length != rows[0].length)
            throw new ArithmeticException("cofactor(): Not a square matrix");
        if(rows.length == 1)
            throw new ArithmeticException("Cofactor of vector");
        return (i+j & 1) == 0 ? minor(i,j) : -minor(i,j);
    }

    /**
     * Returns the adjugate matrix of this matrix, that is, the transpose of its cofactor matrix.
     *
     * @return This matrix's adjugate matrix
     */
    public final floatMxN adjugate() {
        if(rows.length != rows[0].length)
            throw new ArithmeticException("adjugate(): Not a square matrix");
        if(rows.length == 1)
            throw new ArithmeticException("Adjugate of vector");

        floatMxN adj = new floatMxN(rows.length, rows[0].length);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows.length; j++)
                adj.rows[i][j] = cofactor(j,i);
        return adj;
    }


    /**
     * Adds the given value to all components of this matrix.
     *
     * @param x The value to add to all components
     * @return A new matrix with the value added to all components
     */
    public final floatMxN added(float x) {
        floatMxN m = new floatMxN(rows.length, rows[0].length);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                m.rows[i][j] = rows[i][j] + x;
        return m;
    }

    /**
     * Adds the given matrix component-wise to this matrix.
     *
     * @param m The matrix to add
     * @return A new matrix representing the sum of this and the given matrix
     */
    public final floatMxN added(constFloatMxN m) {
        check(m);
        floatMxN res = new floatMxN(rows.length, rows[0].length);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                res.rows[i][j] = rows[i][j] + m.rows[i][j];
        return res;
    }

    /**
     * Subtracts the given value from all components of this matrix.
     *
     * @param x The value to subtract from all components
     * @return A new matrix with the value subtracted from all components
     */
    public final floatMxN subed(float x) {
        floatMxN m = new floatMxN(rows.length, rows[0].length);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                m.rows[i][j] = rows[i][j] - x;
        return m;
    }

    /**
     * Subtracts the given matrix component-wise from this matrix.
     *
     * @param m The matrix to subtract
     * @return A new matrix representing the subtraction of the given matrix from this matrix
     */
    public final floatMxN subed(constFloatMxN m) {
        check(m);
        floatMxN res = new floatMxN(rows.length, rows[0].length);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                res.rows[i][j] = rows[i][j] - m.rows[i][j];
        return res;
    }

    /**
     * Multiplies all components of this matrix by the given factor.
     *
     * @param f The factor to multiply all components with
     * @return A new matrix with all components multiplied by the given factor
     */
    public final floatMxN scaled(float f) {
        floatMxN m = new floatMxN(rows.length, rows[0].length);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                m.rows[i][j] = rows[i][j] * f;
        return m;
    }

    /**
     * Adds this matrix component-wise by the given matrix.
     *
     * @param m The matrix to multiply by
     * @return A new matrix representing the component-wise product of this and the given matrix
     */
    public final floatMxN scaled(constFloatMxN m) {
        check(m);
        floatMxN res = new floatMxN(rows.length, rows[0].length);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                res.rows[i][j] = rows[i][j] * m.rows[i][j];
        return res;
    }

    /**
     * Multiplies this matrix by the given matrix.
     *
     * @param m The matrix to multiply with
     * @return The product of this and the given matrix
     */
    public final floatMxN mult(constFloatMxN m) {
        if(rows[0].length != m.rows.length)
            throw new IncompatibleMatrixException("Trying to multiply "+m()+"x"+n()+" matrix by "+m.m()+"x"+m.n()+" matrix");

        floatMxN res = new floatMxN(rows.length, m.rows[0].length);
        for(int i=0; i<res.rows.length; i++)
            for(int j=0; j<res.rows[i].length; j++)
                for(int k=0; k<rows[0].length; k++)
                    res.rows[i][j] += rows[i][k] * m.rows[k][j];
        return res;
    }

    /**
     * Multiplies this matrix by the given vector.
     *
     * @param v The vector to multiply with
     * @return The product of this matrix and the given vector
     */
    public final floatN mult(constFloatN v) {
        if(rows[0].length != v.components.length)
            throw new IncompatibleMatrixException("Trying to multiply "+m()+"x"+n()+" matrix by "+v.components.length+"d vector");

        floatN res = new floatN(rows.length);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[0].length; j++)
                res.components[i] += rows[i][j] * v.components[j];
        return res;
    }

    /**
     * Multiplies this matrix by the given vector.
     *
     * @param v The vector to multiply with
     * @return The product of this matrix and the given vector
     */
    public final floatN mult(constFloat4 v) {
        if(rows[0].length != 4)
            throw new IncompatibleMatrixException("Trying to multiply "+m()+"x"+n()+" matrix by 4d vector");

        floatN res = new floatN(rows.length);
        for(int i=0; i<rows.length; i++)
            res.components[i] += rows[i][0] * v.x + rows[i][1] * v.y + rows[i][2] * v.z + rows[i][3] * v.w;
        return res;
    }

    /**
     * Multiplies this matrix by the given vector.
     *
     * @param v The vector to multiply with
     * @return The product of this matrix and the given vector
     */
    public final floatN mult(constFloat3 v) {
        if(rows[0].length != 3)
            throw new IncompatibleMatrixException("Trying to multiply "+m()+"x"+n()+" matrix by 3d vector");

        floatN res = new floatN(rows.length);
        for(int i=0; i<rows.length; i++)
            res.components[i] += rows[i][0] * v.x + rows[i][1] * v.y + rows[i][2] * v.z;
        return res;
    }

    /**
     * Multiplies this matrix by the given vector.
     *
     * @param v The vector to multiply with
     * @return The product of this matrix and the given vector
     */
    public final floatN mult(constFloat2 v) {
        if(rows[0].length != 2)
            throw new IncompatibleMatrixException("Trying to multiply "+m()+"x"+n()+" matrix by 2d vector");

        floatN res = new floatN(rows.length);
        for(int i=0; i<rows.length; i++)
            res.components[i] += rows[i][0] * v.x + rows[i][1] * v.y;
        return res;
    }


    /**
     * Throws an exception if the given matrix has a different size than this matrix.
     *
     * @param m The matrix to test
     * @return <code>v</code>
     */
    protected final constFloatMxN check(constFloatMxN m) {
        if(rows.length != m.rows.length || rows[0].length != m.rows[0].length)
            throw new IncompatibleMatrixException(this, m);
        return m;
    }


    /**
     * Returns a new zero matrix.
     *
     * @param m The number of rows in the matrix
     * @param n The number of columns in the matrix
     * @return A new zero matrix
     */
    public static constFloatMxN zero(int m, int n) {
        Arguments.checkRange(m, 1, null);
        Arguments.checkRange(n, 1, null);
        return new constFloatMxN(m,n);
    }

    /**
     * Returns a new square zero matrix.
     *
     * @param n The size of the matrix
     * @return A new square zero matrix
     */
    public static constFloatMxN zero(int n) {
        Arguments.checkRange(n, 1, null);
        return new constFloatMxN(n,n);
    }

    /**
     * Returns a new identity matrix.
     *
     * @param n The size of the matrix
     * @return A new identity matrix
     */
    public static constFloatMxN id(int n) {
        Arguments.checkRange(n, 1, null);
        constFloatMxN id = new constFloatMxN(n,n);
        for(int i=0; i<n; i++)
            id.rows[i][i] = 1;
        return id;
    }

    /**
     * Returns a new matrix with all components set to 1.
     *
     * @param m The number of rows in the matrix
     * @param n The number of columns in the matrix
     * @return A new matrix with all components set to 1
     */
    public static constFloatMxN one(int m, int n) {
        Arguments.checkRange(m, 1, null);
        Arguments.checkRange(n, 1, null);
        constFloatMxN one = new constFloatMxN(m,n);
        for(float[] row : one.rows)
            Arrays.fill(row, 1);
        return one;
    }

    /**
     * Returns a new matrix with all components set to 1.
     *
     * @param n The size of the matrix
     * @return A new matrix with all components set to 1
     */
    public static constFloatMxN one(int n) {
        return one(n,n);
    }

    /**
     * Creates a new matrix with the given values for the diagonal entries, and
     * all other components set to 0.
     *
     * @param diagComponents The component values for the diagonal entries
     * @return A diagonal matrix with the given component values
     */
    public static constFloatMxN diag(float... diagComponents) {
        Arguments.checkRange(Arguments.checkNull(diagComponents, "diagComponents").length, 1, null);
        constFloatMxN m = new constFloatMxN(diagComponents.length, diagComponents.length);
        for(int i=0; i<diagComponents.length; i++)
            m.rows[i][i] = diagComponents[i];
        return m;
    }

    /**
     * Creates a new matrix from the given rows.
     *
     * @param rows The rows for the matrix
     * @return A new matrix from those rows
     */
    public static constFloatMxN fromRows(constFloatN... rows) {
        Arguments.checkRange(Arguments.checkNull(rows, "rows").length, 1, null);
        float[][] rs = new float[rows.length][];
        Arguments.checkRange((rs[0] = rows[0].components.clone()).length, 1, null);

        for(int i=1; i<rows.length; i++)
            if((rs[i] = rows[i].components.clone()).length != rs[0].length)
                throw new IllegalArgumentException("Rows have different sizes");

        return new constFloatMxN(rs);
    }

    /**
     * Creates a new matrix from the given columns.
     *
     * @param columns The columns for the matrix
     * @return A new matrix from those columns
     */
    public static constFloatMxN fromColumns(constFloatN... columns) {
        Arguments.checkRange(Arguments.checkNull(columns, "columns").length, 1, null);
        float[][] rows = new float[Arguments.checkRange(columns[0].components.length, 1, null)][columns.length];

        for(int i=1; i<columns.length; i++)
            if(columns[i].components.length != rows.length)
                throw new IllegalArgumentException("Columns have different sizes");

        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows.length; j++)
                rows[i][j] = columns[j].components[i];

        return new constFloatMxN(rows);
    }

    /**
     * Creates a new matrix from the given rows.
     *
     * @param rows The rows for the matrix
     * @return A new matrix from those rows
     */
    public static constFloatMxN fromRows(float[]... rows) {
        Arguments.checkRange(Arguments.checkNull(rows, "rows").length, 1, null);
        float[][] rs = new float[rows.length][];
        Arguments.checkRange((rs[0] = rows[0].clone()).length, 1, null);

        for(int i=1; i<rows.length; i++)
            if((rs[i] = rows[i].clone()).length != rs[0].length)
                throw new IllegalArgumentException("Rows have different sizes");

        return new constFloatMxN(rs);
    }

    /**
     * Creates a new matrix from the given columns.
     *
     * @param columns The columns for the matrix
     * @return A new matrix from those columns
     */
    public static constFloatMxN fromColumns(float[]... columns) {
        Arguments.checkRange(Arguments.checkNull(columns, "columns").length, 1, null);
        float[][] rows = new float[Arguments.checkRange(columns[0].length, 1, null)][columns.length];

        for(int i=1; i<columns.length; i++)
            if(columns[i].length != rows.length)
                throw new IllegalArgumentException("Columns have different sizes");

        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows.length; j++)
                rows[i][j] = columns[j][i];

        return new constFloatMxN(rows);
    }

    /**
     * Creates a new matrix from the given array.
     *
     * @param components The components for the matrix, row by row. Must contain at least
     *                   as many elements as the matrix will have components
     * @return A new matrix with the specified components
     */
    public static constFloatMxN fromArray(int m, int n, float[] components) {
        return fromArray(m, n, components, 0);
    }

    /**
     * Creates a new matrix by reading the component values from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the top left component, then row by row
     * @return A new matrix with the read components
     */
    public static constFloatMxN fromArray(int m, int n, float[] arr, int offset) {
        Arguments.checkRange(m, 1, null);
        Arguments.checkRange(n, 1, null);
        float[][] rows = new float[m][n];
        for(int i=0; i<m; i++)
            System.arraycopy(arr, i*n + offset, rows[i], 0, n);
        return new constFloatMxN(rows);
    }

    /**
     * Creates a new matrix using the given array as internal matrix data.
     *
     * @param rowsRef The array to use for the matrix as data. Modifying either will also modify the other
     * @return A new matrix using the given array
     */
    public static constFloatMxN ref(float[]... rowsRef) {
        Arguments.checkRange(rowsRef.length, 1, null);
        Arguments.checkRange(rowsRef[0].length, 1, null);
        for(int i=1; i<rowsRef.length; i++)
            if(rowsRef[i].length != rowsRef[0].length)
                throw new IllegalArgumentException("Rows have different sizes");
        return new constFloatMxN(rowsRef);
    }
}
