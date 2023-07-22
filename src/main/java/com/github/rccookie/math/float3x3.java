package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.ArgumentOutOfRangeException;

import org.jetbrains.annotations.Range;

/**
 * A mutable 3x3 float matrix.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public final class float3x3 extends constFloat3x3 {

    static {
        JsonDeserialization.register(float3x3.class, json -> {
            if(json.isArray()) {
                if(json.get(0).isArray())
                    return fromRows(json.get(0).as(float3.class), json.get(1).as(float3.class), json.get(2).as(float3.class));
                return fromArray(json.as(float[].class));
            }
            if(json.contains("a"))
                return new float3x3(json.get("a").asFloat(), json.get("b").asFloat(), json.get("c").asFloat(),
                        json.get("d").asFloat(), json.get("e").asFloat(), json.get("f").asFloat(),
                        json.get("g").asFloat(), json.get("h").asFloat(), json.get("i").asFloat());
            if(json.contains("a00"))
                return new float3x3(json.get("a00").asFloat(), json.get("a01").asFloat(), json.get("a02").asFloat(),
                        json.get("a10").asFloat(), json.get("a11").asFloat(), json.get("a12").asFloat(),
                        json.get("a20").asFloat(), json.get("a21").asFloat(), json.get("a22").asFloat());
            if(json.contains("m00"))
                return new float3x3(json.get("m00").asFloat(), json.get("m01").asFloat(), json.get("m02").asFloat(),
                        json.get("m10").asFloat(), json.get("m11").asFloat(), json.get("m12").asFloat(),
                        json.get("m20").asFloat(), json.get("m21").asFloat(), json.get("m22").asFloat());
            if(json.contains("a11"))
                return new float3x3(json.get("a11").asFloat(), json.get("a12").asFloat(), json.get("a13").asFloat(),
                        json.get("a21").asFloat(), json.get("a22").asFloat(), json.get("a23").asFloat(),
                        json.get("a31").asFloat(), json.get("a32").asFloat(), json.get("a33").asFloat());
            return new float3x3(json.get("m11").asFloat(), json.get("m12").asFloat(), json.get("m13").asFloat(),
                    json.get("m21").asFloat(), json.get("m22").asFloat(), json.get("m23").asFloat(),
                    json.get("m31").asFloat(), json.get("m32").asFloat(), json.get("m33").asFloat());
        });
    }

    float3x3() { }

    float3x3(float a00, float a11, float a22) {
        super(a00, a11, a22);
    }

    /**
     * Creates a new matrix.
     */
    public float3x3(float a00, float a01, float a02,
                    float a10, float a11, float a12,
                    float a20, float a21, float a22) {
        super(a00, a01, a02,
              a10, a11, a12,
              a20, a21, a22);
    }



    /**
     * Sets this matrix to the given component values.
     *
     * @return This matrix
     */
    public float3x3 set(float a00, float a01, float a02,
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
        return this;
    }

    /**
     * Sets this matrix to the given matrix's component values.
     *
     * @param m The matrix to set this matrix to
     * @return This matrix
     */
    public float3x3 set(constFloat3x3 m) {
        a00 = m.a00;
        a01 = m.a01;
        a02 = m.a02;
        a10 = m.a10;
        a11 = m.a11;
        a12 = m.a12;
        a20 = m.a20;
        a21 = m.a21;
        a22 = m.a22;
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param row0 The component values for the first row
     * @param row1 The component values for the second row
     * @param row2 The component values for the third row
     * @return This matrix
     */
    public float3x3 setRows(constFloat3 row0, constFloat3 row1, constFloat3 row2) {
        this.a00 = row0.x;
        this.a01 = row0.y;
        this.a02 = row0.z;
        this.a10 = row1.x;
        this.a11 = row1.y;
        this.a12 = row1.z;
        this.a20 = row2.x;
        this.a21 = row2.y;
        this.a22 = row2.z;
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param column0 The component values for the first column
     * @param column1 The component values for the second column
     * @param column2 The component values for the third column
     * @return This matrix
     */
    public float3x3 setColumns(constFloat3 column0, constFloat3 column1, constFloat3 column2) {
        this.a00 = column0.x;
        this.a10 = column0.y;
        this.a20 = column0.z;
        this.a01 = column1.x;
        this.a11 = column1.y;
        this.a21 = column1.z;
        this.a02 = column2.x;
        this.a12 = column2.y;
        this.a22 = column2.z;
        return this;
    }

    /**
     * Sets the top left component of this matrix.
     *
     * @param a00 The value to set
     * @return This matrix
     */
    public float3x3 set00(float a00) {
        this.a00 = a00;
        return this;
    }

    /**
     * Sets the top center component of this matrix.
     *
     * @param a01 The value to set
     * @return This matrix
     */
    public float3x3 set01(float a01) {
        this.a01 = a01;
        return this;
    }

    /**
     * Sets the top right component of this matrix.
     *
     * @param a02 The value to set
     * @return This matrix
     */
    public float3x3 set02(float a02) {
        this.a02 = a02;
        return this;
    }

    /**
     * Sets the center left component of this matrix.
     *
     * @param a10 The value to set
     * @return This matrix
     */
    public float3x3 set10(float a10) {
        this.a10 = a10;
        return this;
    }

    /**
     * Sets the center component of this matrix.
     *
     * @param a11 The value to set
     * @return This matrix
     */
    public float3x3 set11(float a11) {
        this.a11 = a11;
        return this;
    }

    /**
     * Sets the center right component of this matrix.
     *
     * @param a12 The value to set
     * @return This matrix
     */
    public float3x3 set12(float a12) {
        this.a12 = a12;
        return this;
    }

    /**
     * Sets the bottom left component of this matrix.
     *
     * @param a20 The value to set
     * @return This matrix
     */
    public float3x3 set20(float a20) {
        this.a20 = a20;
        return this;
    }

    /**
     * Sets the bottom center component of this matrix.
     *
     * @param a21 The value to set
     * @return This matrix
     */
    public float3x3 set21(float a21) {
        this.a21 = a21;
        return this;
    }

    /**
     * Sets the bottom right component of this matrix.
     *
     * @param a22 The value to set
     * @return This matrix
     */
    public float3x3 set22(float a22) {
        this.a22 = a22;
        return this;
    }

    /**
     * Sets the top row of this matrix.
     *
     * @param r The row to set
     * @return This matrix
     */
    public float3x3 setRow0(constFloat3 r) {
        a00 = r.x;
        a01 = r.y;
        a02 = r.z;
        return this;
    }

    /**
     * Sets the center row of this matrix.
     *
     * @param r The row to set
     * @return This matrix
     */
    public float3x3 setRow1(constFloat3 r) {
        a10 = r.x;
        a11 = r.y;
        a12 = r.z;
        return this;
    }

    /**
     * Sets the bottom row of this matrix.
     *
     * @param r The row to set
     * @return This matrix
     */
    public float3x3 setRow2(constFloat3 r) {
        a20 = r.x;
        a21 = r.y;
        a22 = r.z;
        return this;
    }

    /**
     * Sets the left column of this matrix.
     *
     * @param c The column to set
     * @return This matrix
     */
    public float3x3 setColumn0(constFloat3 c) {
        a00 = c.x;
        a10 = c.y;
        a20 = c.z;
        return this;
    }

    /**
     * Sets the center column of this matrix.
     *
     * @param c The column to set
     * @return This matrix
     */
    public float3x3 setColumn1(constFloat3 c) {
        a01 = c.x;
        a11 = c.y;
        a21 = c.z;
        return this;
    }

    /**
     * Sets the right column of this matrix.
     *
     * @param c The column to set
     * @return This matrix
     */
    public float3x3 setColumn2(constFloat3 c) {
        a02 = c.x;
        a12 = c.y;
        a22 = c.z;
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
    public float3x3 setComponent(@Range(from = 0, to = 2) int i, @Range(from = 0, to = 2) int j, float x) {
        switch(i) {
            case 0: switch(j) {
                case 0: a00 = x; return this;
                case 1: a01 = x; return this;
                case 2: a02 = x; return this;
            } break;
            case 1: switch(j) {
                case 0: a10 = x; return this;
                case 1: a11 = x; return this;
                case 2: a12 = x; return this;
            } break;
            case 2: switch(j) {
                case 0: a20 = x; return this;
                case 1: a21 = x; return this;
                case 2: a22 = x; return this;
            } break;
        }
        throw new ArgumentOutOfRangeException("Index ("+i+","+j+") for 3x3 matrix");
    }

    /**
     * Sets the specified row of this matrix.
     *
     * @param i The index of the row to set
     * @param r The row to set
     * @return This matrix
     */
    public float3x3 setRow(@Range(from = 0, to = 2) int i, float4 r) {
        switch(i) {
            case 0: a00 = r.x; a01 = r.y; a02 = r.z; return this;
            case 1: a10 = r.x; a11 = r.y; a12 = r.z; return this;
            case 2: a20 = r.x; a21 = r.y; a22 = r.z; return this;
            default: throw new ArgumentOutOfRangeException(i, 0, 3);
        }
    }

    /**
     * Sets the specified column of this matrix.
     *
     * @param j The index of the column to set
     * @param c The column to set
     * @return This matrix
     */
    public float3x3 setColumn(@Range(from = 0, to = 2) int j, float4 c) {
        switch(j) {
            case 0: a00 = c.x; a10 = c.y; a20 = c.z; return this;
            case 1: a01 = c.x; a11 = c.y; a21 = c.z; return this;
            case 2: a02 = c.x; a12 = c.y; a22 = c.z; return this;
            default: throw new ArgumentOutOfRangeException(j, 0, 3);
        }
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param components The components to set, row by row. Must contain at least as many
     *                   elements as this matrix components
     * @return This matrix
     */
    public float3x3 set(float[] components) {
        a00 = components[0];
        a01 = components[1];
        a02 = components[2];
        a10 = components[3];
        a11 = components[4];
        a12 = components[5];
        a20 = components[6];
        a21 = components[7];
        a22 = components[8];
        return this;
    }

    /**
     * Reads this matrix's components from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the first component, then row by row
     * @return This matrix
     */
    public float3x3 set(float[] arr, int offset) {
        a00 = arr[offset];
        a01 = arr[offset+1];
        a02 = arr[offset+2];
        a10 = arr[offset+3];
        a11 = arr[offset+4];
        a12 = arr[offset+5];
        a20 = arr[offset+6];
        a21 = arr[offset+7];
        a22 = arr[offset+8];
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param rows The rows to set to
     * @return This matrix
     */
    public float3x3 set(float[][] rows) {
        a00 = rows[0][0];
        a01 = rows[0][1];
        a02 = rows[0][2];
        a10 = rows[1][0];
        a11 = rows[1][1];
        a12 = rows[1][2];
        a20 = rows[2][0];
        a21 = rows[2][1];
        a22 = rows[2][2];
        return this;
    }

    /**
     * Sets all components of this matrix to 0.
     *
     * @return This matrix
     */
    public float3x3 setZero() {
        a00 = a01 = a02 = 0;
        a10 = a11 = a12 = 0;
        a20 = a21 = a22 = 0;
        return this;
    }


    /**
     * Adds the given value to all components of this matrix.
     *
     * @param x The value to add to all components
     * @return This matrix
     */
    public float3x3 add(float x) {
        a00 += x;
        a01 += x;
        a02 += x;
        a10 += x;
        a11 += x;
        a12 += x;
        a20 += x;
        a21 += x;
        a22 += x;
        return this;
    }

    /**
     * Adds the given matrix component-wise to this matrix.
     *
     * @param m The matrix to add to this matrix
     * @return This matrix
     */
    public float3x3 add(float3x3 m) {
        a00 += m.a00;
        a01 += m.a01;
        a02 += m.a02;
        a10 += m.a10;
        a11 += m.a11;
        a12 += m.a12;
        a20 += m.a20;
        a21 += m.a21;
        a22 += m.a22;
        return this;
    }

    /**
     * Subtracts the given value from all components of this matrix.
     *
     * @param x The value to subtract from all components
     * @return This matrix
     */
    public float3x3 sub(float x) {
        a00 -= x;
        a01 -= x;
        a02 -= x;
        a10 -= x;
        a11 -= x;
        a12 -= x;
        a20 -= x;
        a21 -= x;
        a22 -= x;
        return this;
    }

    /**
     * Subtracts the given matrix component-wise from this matrix.
     *
     * @param m The matrix to subtract from this matrix
     * @return This matrix
     */
    public float3x3 sub(float3x3 m) {
        a00 -= m.a00;
        a01 -= m.a01;
        a02 -= m.a02;
        a10 -= m.a10;
        a11 -= m.a11;
        a12 -= m.a12;
        a20 -= m.a20;
        a21 -= m.a21;
        a22 -= m.a22;
        return this;
    }

    /**
     * Multiplies all components of this matrix by the given factor.
     *
     * @param f The factor to multiply all components with
     * @return This matrix
     */
    public float3x3 scale(float f) {
        a00 *= f;
        a01 *= f;
        a02 *= f;
        a10 *= f;
        a11 *= f;
        a12 *= f;
        a20 *= f;
        a21 *= f;
        a22 *= f;
        return this;
    }

    /**
     * Multiplies this matrix component-wise by the given matrix.
     *
     * @param m The matrix to multiply this matrix with
     * @return This matrix
     */
    public float3x3 scale(float3x3 m) {
        a00 *= m.a00;
        a01 *= m.a01;
        a02 *= m.a02;
        a10 *= m.a10;
        a11 *= m.a11;
        a12 *= m.a12;
        a20 *= m.a20;
        a21 *= m.a21;
        a22 *= m.a22;
        return this;
    }


    /**
     * Returns a new zero matrix.
     *
     * @return A new zero matrix
     */
    public static float3x3 zero() {
        return new float3x3();
    }

    /**
     * Returns a new identity matrix.
     *
     * @return A new identity matrix
     */
    public static float3x3 id() {
        return new float3x3(1,1,1);
    }

    /**
     * Returns a new matrix with all components set to 1.
     *
     * @return A new matrix with all components set to 1
     */
    public static float3x3 one() {
        return new float3x3(1,1,1,
                            1,1,1,
                            1,1,1);
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
    public static float3x3 diag(float a00, float a11, float a22) {
        return new float3x3(a00, a11, a22);
    }

    /**
     * Creates a new matrix from the given rows.
     *
     * @param r0 The first row of the matrix
     * @param r1 The second row of the matrix
     * @param r2 The third row of the matrix
     * @return A new matrix from those rows
     */
    public static float3x3 fromRows(constFloat3 r0, constFloat3 r1, constFloat3 r2) {
        return new float3x3(
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
    public static float3x3 fromColumns(constFloat3 c0, constFloat3 c1, constFloat3 c2) {
        return new float3x3(
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
    public static float3x3 fromArray(float[] components) {
        return new float3x3(
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
    public static float3x3 fromArray(float[] arr, int offset) {
        return new float3x3(
                arr[offset], arr[offset+1], arr[offset+2],
                arr[offset+3], arr[offset+4], arr[offset+5],
                arr[offset+6], arr[offset+7], arr[offset+8]
        );
    }
}
