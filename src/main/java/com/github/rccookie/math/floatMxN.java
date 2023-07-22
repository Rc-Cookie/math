package com.github.rccookie.math;

import java.util.Arrays;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.Arguments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import static java.lang.Math.abs;

/**
 * A mutable float matrix.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public final class floatMxN extends constFloatMxN {

    static {
        JsonDeserialization.register(floatMxN.class, json -> new floatMxN(json.as(float[][].class)));
    }

    floatMxN(int m, int n) {
        super(m, n);
    }

    floatMxN(float @NotNull [] @NotNull [] ref) {
        super(ref);
    }

    /**
     * Sets this matrix to the given component values.
     *
     * @param rows The components to set, row by row
     * @return This matrix
     */
    public floatMxN setRows(float[]... rows) {
        for(int i=0; i<rows.length; i++)
            System.arraycopy(rows[i], 0, this.rows[i], 0, this.rows.length);
        return this;
    }

    /**
     * Sets this matrix to the given component values.
     *
     * @param columns The components to set, column by column
     * @return This matrix
     */
    public floatMxN setColumns(float[]... columns) {
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[0].length; j++)
                rows[i][j] = columns[j][i];
        return this;
    }

    /**
     * Sets this matrix to the given matrix's component values.
     *
     * @param m The matrix to set this matrix to
     * @return This matrix
     */
    public floatMxN set(constFloatMxN m) {
        check(m);
        for(int i=0; i<rows.length; i++)
            System.arraycopy(m.rows[i], 0, rows[i], 0, rows[i].length);
        return this;
    }

    /**
     * Sets the specified component of this matrix.
     *
     * @param i The row index of the component to set
     * @param j The column index of the component to set
     * @param x The value to set
     * @return This matrix
     */
    public floatMxN setComponent(@Range(from = 0, to = Integer.MAX_VALUE) int i, @Range(from = 0, to = Integer.MAX_VALUE) int j, float x) {
        rows[i][j] = x;
        return this;
    }

    /**
     * Sets the specified row of this matrix.
     *
     * @param i The index of the row to set
     * @param r The row to set
     * @return This matrix
     */
    public floatMxN setRow(@Range(from = 0, to = Integer.MAX_VALUE) int i, floatN r) {
        if(rows[0].length != r.components.length)
            throw new IncompatibleMatrixException("Incorrect row size ("+r.components.length+") for "+m()+"x"+n()+" matrix");
        System.arraycopy(r.components, 0, rows[i], 0, rows[i].length);
        return this;
    }

    /**
     * Sets the specified column of this matrix.
     *
     * @param j The index of the column to set
     * @param c The column to set
     * @return This matrix
     */
    public floatMxN setColumn(@Range(from = 0, to = Integer.MAX_VALUE) int j, floatN c) {
        if(rows.length != c.components.length)
            throw new IncompatibleMatrixException("Incorrect column size ("+c.components.length+") for "+m()+"x"+n()+" matrix");
        for(float[] row : rows)
            row[j] = c.components[j];
        return this;
    }

    /**
     * Swaps two rows of this matrix.
     *
     * @param i1 The index of the first row
     * @param i2 The index of the second row
     * @return This matrix
     */
    public floatMxN swapRows(int i1, int i2) {
        float[] tmp = rows[i1];
        rows[i1] = rows[i2];
        rows[i2] = tmp;
        return this;
    }

    /**
     * Swaps two columns of this matrix.
     *
     * @param j1 The index of the first column
     * @param j2 The index of the second column
     * @return This matrix
     */
    public floatMxN swapColumns(int j1, int j2) {
        float tmp;
        for(float[] row : rows) {
            tmp = row[j1];
            row[j1] = row[j2];
            row[j2] = tmp;
        }
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param components The components to set, row by row. Must contain at least as many
     *                   elements as this matrix components
     * @return This matrix
     */
    public floatMxN set(float[] components) {
        return set(components, 0);
    }

    /**
     * Reads this matrix's components from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the first component, then row by row
     * @return This matrix
     */
    public floatMxN set(float[] arr, int offset) {
        for(int i=0; i<rows.length; i++)
            System.arraycopy(arr, i*rows[0].length + offset, rows[i], 0, rows[i].length);
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param rows The rows to set to
     * @return This matrix
     */
    public floatMxN set(float[][] rows) {
        for(int i=0; i<rows.length; i++)
            System.arraycopy(rows[i], 0, this.rows[i], 0, this.rows[i].length);
        return this;
    }

    /**
     * Sets all components of this matrix to 0.
     *
     * @return This matrix
     */
    public floatMxN setZero() {
        for(float[] row : rows)
            Arrays.fill(row, 0);
        return this;
    }


    /**
     * Adds the given value to all components of this matrix.
     *
     * @param x The value to add to all components
     * @return This matrix
     */
    public floatMxN add(float x) {
        for(float[] row : rows)
            for(int j=0; j<row.length; j++)
                row[j] += x;
        return this;
    }

    /**
     * Adds the given matrix component-wise to this matrix.
     *
     * @param m The matrix to add to this matrix
     * @return This matrix
     */
    public floatMxN add(floatMxN m) {
        check(m);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                rows[i][j] += m.rows[i][j];
        return this;
    }

    /**
     * Subtracts the given value from all components of this matrix.
     *
     * @param x The value to subtract from all components
     * @return This matrix
     */
    public floatMxN sub(float x) {
        for(float[] row : rows)
            for(int j=0; j<row.length; j++)
                row[j] -= x;
        return this;
    }

    /**
     * Subtracts the given matrix component-wise from this matrix.
     *
     * @param m The matrix to subtract from this matrix
     * @return This matrix
     */
    public floatMxN sub(floatMxN m) {
        check(m);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                rows[i][j] -= m.rows[i][j];
        return this;
    }

    /**
     * Multiplies all components of this matrix by the given factor.
     *
     * @param f The factor to multiply all components with
     * @return This matrix
     */
    public floatMxN scale(float f) {
        for(float[] row : rows)
            for(int j=0; j<row.length; j++)
                row[j] *= f;
        return this;
    }

    /**
     * Multiplies this matrix component-wise by the given matrix.
     *
     * @param m The matrix to multiply this matrix with
     * @return This matrix
     */
    public floatMxN scale(floatMxN m) {
        check(m);
        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows[i].length; j++)
                rows[i][j] *= m.rows[i][j];
        return this;
    }


    float toEchelonInplace(float ep, floatMxN transform) {
        int startI = 0;
        boolean neg = false;

        // Clear out (relevant part of) columns from left to right
        for(int j=0; j<rows[0].length; j++) {

            // Move columns with zeros to bottom and find last relevant (non-zero in this column) row
            int endI = rows.length;
            for(int i=startI; i<endI; i++) {
                if(abs(rows[i][j]) <= ep) {
                    if(i != endI-1) {
                        swapRows(i, endI - 1);
                        if(transform != null)
                            transform.swapRows(i, endI - 1);
                        neg = !neg;
                    }
                    i--;
                    endI--;
                }
            }

            // All relevant rows have zeros in this column?
            if(endI == startI) continue;

            // Find row with the greatest coefficient in columns and move to top, for stability purposes
            int max = startI;
            for(int i=startI+1; i<endI; i++)
                if(rows[i][j] > rows[max][j])
                    max = i;
            if(startI != max) {
                swapRows(startI, max);
                if(transform != null)
                    transform.swapRows(startI, max);
                neg = !neg;
            }

            // Subtract selected row from all columns below to produce zeros in this columns
            for(int i=startI+1; i<endI; i++) {
                float factor = rows[i][j] / rows[startI][j];
                rows[i][j] = 0;
                for(int j2=j+1; j2<rows[i].length; j2++)
                    rows[i][j2] -= rows[startI][j2] * factor;
                if(transform != null)
                    for(int j2=0; j2<transform.rows[i].length; j2++)
                        rows[i][j2] -= transform.rows[startI][j2] * factor;
            }

            // The row added to the others is done now
            startI++;
        }
        return neg ? -1 : 1;
    }

    void toReducedEchelonInplace(float ep, floatMxN transform) {
        toEchelonInplace(ep, transform);
        if(isReducedEchelon(ep, false))
            return;

        for(int i=rows.length-1; i>=0; i--) {

            // Find non-zero start of row
            int j = 0;
            while(j < rows[i].length && abs(rows[i][j]) <= ep) j++;

            // Row only has zeros
            if(j == rows[i].length) continue;

            // Subtract row from each row above to eliminate factors in row
            for(int i2=i-1; i2>=0; i2--) {
                rows[i2][j] = 0;
                if(transform != null) {
                    float factor = rows[i2][j] / rows[i][j];
                    for(int j2=0; j2<transform.rows[i].length; j2++)
                        transform.rows[i2][j2] -= transform.rows[i][j2] * factor;
                }
            }

            // Normalize row
            if(transform != null)
                for(int j2=0; j2<transform.rows[i].length; j2++)
                    transform.rows[i][j2] /= rows[i][j];
            rows[i][j] = 1;
        }
    }


    /**
     * Returns a new zero matrix.
     *
     * @param m The number of rows in the matrix
     * @param n The number of columns in the matrix
     * @return A new zero matrix
     */
    public static floatMxN zero(int m, int n) {
        Arguments.checkRange(m, 1, null);
        Arguments.checkRange(n, 1, null);
        return new floatMxN(m,n);
    }

    /**
     * Returns a new square zero matrix.
     *
     * @param n The size of the matrix
     * @return A new square zero matrix
     */
    public static floatMxN zero(int n) {
        Arguments.checkRange(n, 1, null);
        return new floatMxN(n,n);
    }

    /**
     * Returns a new identity matrix.
     *
     * @param n The size of the matrix
     * @return A new identity matrix
     */
    public static floatMxN id(int n) {
        Arguments.checkRange(n, 1, null);
        floatMxN id = new floatMxN(n,n);
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
    public static floatMxN one(int m, int n) {
        Arguments.checkRange(m, 1, null);
        Arguments.checkRange(n, 1, null);
        floatMxN one = new floatMxN(m,n);
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
    public static floatMxN one(int n) {
        return one(n,n);
    }

    /**
     * Creates a new matrix with the given values for the diagonal entries, and
     * all other components set to 0.
     *
     * @param diagComponents The component values for the diagonal entries
     * @return A diagonal matrix with the given component values
     */
    public static floatMxN diag(float... diagComponents) {
        Arguments.checkRange(Arguments.checkNull(diagComponents, "diagComponents").length, 1, null);
        floatMxN m = new floatMxN(diagComponents.length, diagComponents.length);
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
    public static floatMxN fromRows(constFloatN... rows) {
        Arguments.checkRange(Arguments.checkNull(rows, "rows").length, 1, null);
        float[][] rs = new float[rows.length][];
        Arguments.checkRange((rs[0] = rows[0].components.clone()).length, 1, null);

        for(int i=1; i<rows.length; i++)
            if((rs[i] = rows[i].components.clone()).length != rs[0].length)
                throw new IllegalArgumentException("Rows have different sizes");

        return new floatMxN(rs);
    }

    /**
     * Creates a new matrix from the given columns.
     *
     * @param columns The columns for the matrix
     * @return A new matrix from those columns
     */
    public static floatMxN fromColumns(constFloatN... columns) {
        Arguments.checkRange(Arguments.checkNull(columns, "columns").length, 1, null);
        float[][] rows = new float[Arguments.checkRange(columns[0].components.length, 1, null)][columns.length];

        for(int i=1; i<columns.length; i++)
            if(columns[i].components.length != rows.length)
                throw new IllegalArgumentException("Columns have different sizes");

        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows.length; j++)
                rows[i][j] = columns[j].components[i];

        return new floatMxN(rows);
    }

    /**
     * Creates a new matrix from the given rows.
     *
     * @param rows The rows for the matrix
     * @return A new matrix from those rows
     */
    public static floatMxN fromRows(float[]... rows) {
        Arguments.checkRange(Arguments.checkNull(rows, "rows").length, 1, null);
        float[][] rs = new float[rows.length][];
        Arguments.checkRange((rs[0] = rows[0].clone()).length, 1, null);

        for(int i=1; i<rows.length; i++)
            if((rs[i] = rows[i].clone()).length != rs[0].length)
                throw new IllegalArgumentException("Rows have different sizes");

        return new floatMxN(rs);
    }

    /**
     * Creates a new matrix from the given columns.
     *
     * @param columns The columns for the matrix
     * @return A new matrix from those columns
     */
    public static floatMxN fromColumns(float[]... columns) {
        Arguments.checkRange(Arguments.checkNull(columns, "columns").length, 1, null);
        float[][] rows = new float[Arguments.checkRange(columns[0].length, 1, null)][columns.length];

        for(int i=1; i<columns.length; i++)
            if(columns[i].length != rows.length)
                throw new IllegalArgumentException("Columns have different sizes");

        for(int i=0; i<rows.length; i++)
            for(int j=0; j<rows.length; j++)
                rows[i][j] = columns[j][i];

        return new floatMxN(rows);
    }

    /**
     * Creates a new matrix from the given array.
     *
     * @param components The components for the matrix, row by row. Must contain at least
     *                   as many elements as the matrix will have components
     * @return A new matrix with the specified components
     */
    public static floatMxN fromArray(int m, int n, float[] components) {
        return fromArray(m, n, components, 0);
    }

    /**
     * Creates a new matrix by reading the component values from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the top left component, then row by row
     * @return A new matrix with the read components
     */
    public static floatMxN fromArray(int m, int n, float[] arr, int offset) {
        Arguments.checkRange(m, 1, null);
        Arguments.checkRange(n, 1, null);
        float[][] rows = new float[m][n];
        for(int i=0; i<m; i++)
            System.arraycopy(arr, i*n + offset, rows[i], 0, n);
        return new floatMxN(rows);
    }

    /**
     * Creates a new matrix using the given array as internal matrix data.
     *
     * @param rowsRef The array to use for the matrix as data. Modifying either will also modify the other
     * @return A new matrix using the given array
     */
    public static floatMxN ref(float[]... rowsRef) {
        Arguments.checkRange(rowsRef.length, 1, null);
        Arguments.checkRange(rowsRef[0].length, 1, null);
        for(int i=1; i<rowsRef.length; i++)
            if(rowsRef[i].length != rowsRef[0].length)
                throw new IllegalArgumentException("Rows have different sizes");
        return new floatMxN(rowsRef);
    }
}
