package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.ArgumentOutOfRangeException;

import org.jetbrains.annotations.Range;

/**
 * A mutable 4x4 float matrix.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public final class float4x4 extends constFloat4x4 {

    static {
        JsonDeserialization.register(float4x4.class, json -> {
            if(json.isArray()) {
                if(json.get(0).isArray())
                    return fromRows(json.get(0).as(float4.class), json.get(1).as(float4.class), json.get(2).as(float4.class), json.get(3).as(float4.class));
                return fromArray(json.as(float[].class));
            }
            if(json.contains("a"))
                return new float4x4(json.get("a").asFloat(), json.get("b").asFloat(), json.get("c").asFloat(), json.get("d").asFloat(),
                        json.get("e").asFloat(), json.get("f").asFloat(), json.get("g").asFloat(), json.get("h").asFloat(),
                        json.get("i").asFloat(), json.get("j").asFloat(), json.get("k").asFloat(), json.get("l").asFloat(),
                        json.get("m").asFloat(), json.get("n").asFloat(), json.get("o").asFloat(), json.get("p").asFloat());
            if(json.contains("a00"))
                return new float4x4(json.get("a00").asFloat(), json.get("a01").asFloat(), json.get("a02").asFloat(), json.get("a03").asFloat(),
                        json.get("a10").asFloat(), json.get("a11").asFloat(), json.get("a12").asFloat(), json.get("a13").asFloat(),
                        json.get("a20").asFloat(), json.get("a21").asFloat(), json.get("a22").asFloat(), json.get("a23").asFloat(),
                        json.get("a30").asFloat(), json.get("a31").asFloat(), json.get("a32").asFloat(), json.get("a33").asFloat());
            if(json.contains("m00"))
                return new float4x4(json.get("m00").asFloat(), json.get("m01").asFloat(), json.get("m02").asFloat(), json.get("m03").asFloat(),
                        json.get("m10").asFloat(), json.get("m11").asFloat(), json.get("m12").asFloat(), json.get("m13").asFloat(),
                        json.get("m20").asFloat(), json.get("m21").asFloat(), json.get("m22").asFloat(), json.get("m23").asFloat(),
                        json.get("m30").asFloat(), json.get("m31").asFloat(), json.get("m32").asFloat(), json.get("m33").asFloat());
            if(json.contains("a11"))
                return new float4x4(json.get("a11").asFloat(), json.get("a12").asFloat(), json.get("a13").asFloat(), json.get("a14").asFloat(),
                        json.get("a21").asFloat(), json.get("a22").asFloat(), json.get("a23").asFloat(), json.get("a24").asFloat(),
                        json.get("a31").asFloat(), json.get("a32").asFloat(), json.get("a33").asFloat(), json.get("a34").asFloat(),
                        json.get("a41").asFloat(), json.get("a42").asFloat(), json.get("a43").asFloat(), json.get("a44").asFloat());
            return new float4x4(json.get("m11").asFloat(), json.get("m12").asFloat(), json.get("m13").asFloat(), json.get("m14").asFloat(),
                    json.get("m21").asFloat(), json.get("m22").asFloat(), json.get("m23").asFloat(), json.get("m24").asFloat(),
                    json.get("m31").asFloat(), json.get("m32").asFloat(), json.get("m33").asFloat(), json.get("m34").asFloat(),
                    json.get("m41").asFloat(), json.get("m42").asFloat(), json.get("m43").asFloat(), json.get("m44").asFloat());
        });
    }

    float4x4() { }

    float4x4(float a00, float a11, float a22, float a33) {
        super(a00, a11, a22, a33);
    }

    /**
     * Creates a new matrix.
     */
    public float4x4(float a00, float a01, float a02, float a03,
                    float a10, float a11, float a12, float a13,
                    float a20, float a21, float a22, float a23,
                    float a30, float a31, float a32, float a33) {
        super(a00, a01, a02, a03,
              a10, a11, a12, a13,
              a20, a21, a22, a23,
              a30, a31, a32, a33);
    }



    /**
     * Sets this matrix to the given component values.
     *
     * @return This matrix
     */
    public float4x4 set(float a00, float a01, float a02, float a03,
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
        return this;
    }

    /**
     * Sets this matrix to the given matrix's component values.
     *
     * @param m The matrix to set this matrix to
     * @return This matrix
     */
    public float4x4 set(constFloat4x4 m) {
        a00 = m.a00;
        a01 = m.a01;
        a02 = m.a02;
        a03 = m.a03;
        a10 = m.a10;
        a11 = m.a11;
        a12 = m.a12;
        a13 = m.a13;
        a20 = m.a20;
        a21 = m.a21;
        a22 = m.a22;
        a23 = m.a23;
        a30 = m.a30;
        a31 = m.a31;
        a32 = m.a32;
        a33 = m.a33;
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param row0 The component values for the first row
     * @param row1 The component values for the second row
     * @param row2 The component values for the third row
     * @param row3 The component values for the fourth row
     * @return This matrix
     */
    public float4x4 setRows(constFloat4 row0, constFloat4 row1, constFloat4 row2, constFloat4 row3) {
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
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param column0 The component values for the first column
     * @param column1 The component values for the second column
     * @param column2 The component values for the third column
     * @param column3 The component values for the fourth column
     * @return This matrix
     */
    public float4x4 setColumns(constFloat4 column0, constFloat4 column1, constFloat4 column2, constFloat4 column3) {
        this.a00 = column0.x;
        this.a10 = column0.y;
        this.a20 = column0.z;
        this.a30 = column0.w;
        this.a01 = column1.x;
        this.a11 = column1.y;
        this.a21 = column1.z;
        this.a31 = column1.w;
        this.a02 = column2.x;
        this.a12 = column2.y;
        this.a22 = column2.z;
        this.a32 = column2.w;
        this.a03 = column3.x;
        this.a13 = column3.y;
        this.a23 = column3.z;
        this.a33 = column3.w;
        return this;
    }

    /**
     * Sets the first component in the first row of this matrix.
     *
     * @param a00 The value to set
     * @return This matrix
     */
    public float4x4 set00(float a00) {
        this.a00 = a00;
        return this;
    }

    /**
     * Sets the second component in the first row of this matrix.
     *
     * @param a01 The value to set
     * @return This matrix
     */
    public float4x4 set01(float a01) {
        this.a01 = a01;
        return this;
    }

    /**
     * Sets the third component in the first row of this matrix.
     *
     * @param a02 The value to set
     * @return This matrix
     */
    public float4x4 set02(float a02) {
        this.a02 = a02;
        return this;
    }

    /**
     * Sets the fourth component in the first row of this matrix.
     *
     * @param a03 The value to set
     * @return This matrix
     */
    public float4x4 set03(float a03) {
        this.a03 = a03;
        return this;
    }

    /**
     * Sets the first component in the second row of this matrix.
     *
     * @param a10 The value to set
     * @return This matrix
     */
    public float4x4 set10(float a10) {
        this.a10 = a10;
        return this;
    }

    /**
     * Sets the second component in the second row of this matrix.
     *
     * @param a11 The value to set
     * @return This matrix
     */
    public float4x4 set11(float a11) {
        this.a11 = a11;
        return this;
    }

    /**
     * Sets the third component in the second row of this matrix.
     *
     * @param a12 The value to set
     * @return This matrix
     */
    public float4x4 set12(float a12) {
        this.a12 = a12;
        return this;
    }

    /**
     * Sets the fourth component in the second row of this matrix.
     *
     * @param a13 The value to set
     * @return This matrix
     */
    public float4x4 set13(float a13) {
        this.a13 = a13;
        return this;
    }

    /**
     * Sets the first component in the third row of this matrix.
     *
     * @param a20 The value to set
     * @return This matrix
     */
    public float4x4 set20(float a20) {
        this.a20 = a20;
        return this;
    }

    /**
     * Sets the second component in the third row of this matrix.
     *
     * @param a21 The value to set
     * @return This matrix
     */
    public float4x4 set21(float a21) {
        this.a21 = a21;
        return this;
    }

    /**
     * Sets the third component in the third row of this matrix.
     *
     * @param a22 The value to set
     * @return This matrix
     */
    public float4x4 set22(float a22) {
        this.a22 = a22;
        return this;
    }

    /**
     * Sets the fourth component in the third row of this matrix.
     *
     * @param a23 The value to set
     * @return This matrix
     */
    public float4x4 set23(float a23) {
        this.a23 = a23;
        return this;
    }

    /**
     * Sets the first component in the fourth row of this matrix.
     *
     * @param a30 The value to set
     * @return This matrix
     */
    public float4x4 set30(float a30) {
        this.a30 = a30;
        return this;
    }

    /**
     * Sets the second component in the fourth row of this matrix.
     *
     * @param a31 The value to set
     * @return This matrix
     */
    public float4x4 set31(float a31) {
        this.a31 = a31;
        return this;
    }

    /**
     * Sets the third component in the fourth row of this matrix.
     *
     * @param a32 The value to set
     * @return This matrix
     */
    public float4x4 set32(float a32) {
        this.a32 = a32;
        return this;
    }

    /**
     * Sets the fourth component in the fourth row of this matrix.
     *
     * @param a33 The value to set
     * @return This matrix
     */
    public float4x4 set33(float a33) {
        this.a33 = a33;
        return this;
    }

    /**
     * Sets the first row of this matrix.
     *
     * @param r The row to set
     * @return This matrix
     */
    public float4x4 setRow0(constFloat4 r) {
        a00 = r.x;
        a01 = r.y;
        a02 = r.z;
        a03 = r.w;
        return this;
    }

    /**
     * Sets the second row of this matrix.
     *
     * @param r The row to set
     * @return This matrix
     */
    public float4x4 setRow1(constFloat4 r) {
        a10 = r.x;
        a11 = r.y;
        a12 = r.z;
        a13 = r.w;
        return this;
    }

    /**
     * Sets the third row of this matrix.
     *
     * @param r The row to set
     * @return This matrix
     */
    public float4x4 setRow2(constFloat4 r) {
        a20 = r.x;
        a21 = r.y;
        a22 = r.z;
        a23 = r.w;
        return this;
    }

    /**
     * Sets the fourth row of this matrix.
     *
     * @param r The row to set
     * @return This matrix
     */
    public float4x4 setRow3(constFloat4 r) {
        a30 = r.x;
        a31 = r.y;
        a32 = r.z;
        a33 = r.w;
        return this;
    }

    /**
     * Sets the first column of this matrix.
     *
     * @param c The column to set
     * @return This matrix
     */
    public float4x4 setColumn0(constFloat4 c) {
        a00 = c.x;
        a10 = c.y;
        a20 = c.z;
        a30 = c.w;
        return this;
    }

    /**
     * Sets the second column of this matrix.
     *
     * @param c The column to set
     * @return This matrix
     */
    public float4x4 setColumn1(constFloat4 c) {
        a01 = c.x;
        a11 = c.y;
        a21 = c.z;
        a31 = c.w;
        return this;
    }

    /**
     * Sets the third column of this matrix.
     *
     * @param c The column to set
     * @return This matrix
     */
    public float4x4 setColumn2(constFloat4 c) {
        a02 = c.x;
        a12 = c.y;
        a22 = c.z;
        a32 = c.w;
        return this;
    }

    /**
     * Sets the fourth column of this matrix.
     *
     * @param c The column to set
     * @return This matrix
     */
    public float4x4 setColumn3(constFloat4 c) {
        a03 = c.x;
        a13 = c.y;
        a23 = c.z;
        a33 = c.w;
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
    public float4x4 setComponent(@Range(from = 0, to = 3) int i, @Range(from = 0, to = 3) int j, float x) {
        switch(i) {
            case 0: switch(j) {
                case 0: a00 = x; return this;
                case 1: a01 = x; return this;
                case 2: a02 = x; return this;
                case 3: a03 = x; return this;
            } break;
            case 1: switch(j) {
                case 0: a10 = x; return this;
                case 1: a11 = x; return this;
                case 2: a12 = x; return this;
                case 3: a13 = x; return this;
            } break;
            case 2: switch(j) {
                case 0: a20 = x; return this;
                case 1: a21 = x; return this;
                case 2: a22 = x; return this;
                case 3: a23 = x; return this;
            } break;
            case 3: switch(j) {
                case 0: a30 = x; return this;
                case 1: a31 = x; return this;
                case 2: a32 = x; return this;
                case 3: a33 = x; return this;
            } break;
        }
        throw new ArgumentOutOfRangeException("Index ("+i+","+j+") for 4x4 matrix");
    }

    /**
     * Sets the specified row of this matrix.
     *
     * @param i The index of the row to set
     * @param r The row to set
     * @return This matrix
     */
    public float4x4 setRow(@Range(from = 0, to = 3) int i, float4 r) {
        switch(i) {
            case 0: a00 = r.x; a01 = r.y; a02 = r.z; a03 = r.w; return this;
            case 1: a10 = r.x; a11 = r.y; a12 = r.z; a13 = r.w; return this;
            case 2: a20 = r.x; a21 = r.y; a22 = r.z; a23 = r.w; return this;
            case 3: a30 = r.x; a31 = r.y; a32 = r.z; a33 = r.w; return this;
            default: throw new ArgumentOutOfRangeException(i, 0, 4);
        }
    }

    /**
     * Sets the specified column of this matrix.
     *
     * @param j The index of the column to set
     * @param c The column to set
     * @return This matrix
     */
    public float4x4 setColumn(@Range(from = 0, to = 3) int j, float4 c) {
        switch(j) {
            case 0: a00 = c.x; a10 = c.y; a20 = c.z; a30 = c.w; return this;
            case 1: a01 = c.x; a11 = c.y; a21 = c.z; a31 = c.w; return this;
            case 2: a02 = c.x; a12 = c.y; a22 = c.z; a32 = c.w; return this;
            case 3: a03 = c.x; a13 = c.y; a23 = c.z; a33 = c.w; return this;
            default: throw new ArgumentOutOfRangeException(j, 0, 4);
        }
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param components The components to set, row by row. Must contain at least as many
     *                   elements as this matrix components
     * @return This matrix
     */
    public float4x4 set(float[] components) {
        a00 = components[0];
        a01 = components[1];
        a02 = components[2];
        a03 = components[3];
        a10 = components[4];
        a11 = components[5];
        a12 = components[6];
        a13 = components[7];
        a20 = components[8];
        a21 = components[9];
        a22 = components[10];
        a23 = components[11];
        a30 = components[12];
        a31 = components[13];
        a32 = components[14];
        a33 = components[15];
        return this;
    }

    /**
     * Reads this matrix's components from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the first component, then row by row
     * @return This matrix
     */
    public float4x4 set(float[] arr, int offset) {
        a00 = arr[offset];
        a01 = arr[offset+1];
        a02 = arr[offset+2];
        a03 = arr[offset+3];
        a10 = arr[offset+4];
        a11 = arr[offset+5];
        a12 = arr[offset+6];
        a13 = arr[offset+7];
        a20 = arr[offset+8];
        a21 = arr[offset+9];
        a22 = arr[offset+10];
        a23 = arr[offset+11];
        a30 = arr[offset+12];
        a31 = arr[offset+13];
        a32 = arr[offset+14];
        a33 = arr[offset+15];
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param rows The rows to set to
     * @return This matrix
     */
    public float4x4 set(float[][] rows) {
        a00 = rows[0][0];
        a01 = rows[0][1];
        a02 = rows[0][2];
        a03 = rows[0][3];
        a10 = rows[1][0];
        a11 = rows[1][1];
        a12 = rows[1][2];
        a13 = rows[1][3];
        a20 = rows[2][0];
        a21 = rows[2][1];
        a22 = rows[2][2];
        a23 = rows[2][3];
        a30 = rows[3][0];
        a31 = rows[3][1];
        a32 = rows[3][2];
        a33 = rows[3][3];
        return this;
    }

    /**
     * Sets all components of this matrix to 0.
     *
     * @return This matrix
     */
    public float4x4 setZero() {
        a00 = a01 = a02 = a03 = 0;
        a10 = a11 = a12 = a13 = 0;
        a20 = a21 = a22 = a23 = 0;
        a30 = a31 = a32 = a33 = 0;
        return this;
    }


    /**
     * Adds the given value to all components of this matrix.
     *
     * @param x The value to add to all components
     * @return This matrix
     */
    public float4x4 add(float x) {
        a00 += x;
        a01 += x;
        a02 += x;
        a03 += x;
        a10 += x;
        a11 += x;
        a12 += x;
        a13 += x;
        a20 += x;
        a21 += x;
        a22 += x;
        a23 += x;
        a30 += x;
        a31 += x;
        a32 += x;
        a33 += x;
        return this;
    }

    /**
     * Adds the given matrix component-wise to this matrix.
     *
     * @param m The matrix to add to this matrix
     * @return This matrix
     */
    public float4x4 add(float4x4 m) {
        a00 += m.a00;
        a01 += m.a01;
        a02 += m.a02;
        a03 += m.a03;
        a10 += m.a10;
        a11 += m.a11;
        a12 += m.a12;
        a13 += m.a13;
        a20 += m.a20;
        a21 += m.a21;
        a22 += m.a22;
        a23 += m.a23;
        a30 += m.a30;
        a31 += m.a31;
        a32 += m.a32;
        a33 += m.a33;
        return this;
    }

    /**
     * Subtracts the given value from all components of this matrix.
     *
     * @param x The value to subtract from all components
     * @return This matrix
     */
    public float4x4 sub(float x) {
        a00 -= x;
        a01 -= x;
        a02 -= x;
        a03 -= x;
        a10 -= x;
        a11 -= x;
        a12 -= x;
        a13 -= x;
        a20 -= x;
        a21 -= x;
        a22 -= x;
        a23 -= x;
        a30 -= x;
        a31 -= x;
        a32 -= x;
        a33 -= x;
        return this;
    }

    /**
     * Subtracts the given matrix component-wise from this matrix.
     *
     * @param m The matrix to subtract from this matrix
     * @return This matrix
     */
    public float4x4 sub(float4x4 m) {
        a00 -= m.a00;
        a01 -= m.a01;
        a02 -= m.a02;
        a03 -= m.a03;
        a10 -= m.a10;
        a11 -= m.a11;
        a12 -= m.a12;
        a13 -= m.a13;
        a20 -= m.a20;
        a21 -= m.a21;
        a22 -= m.a22;
        a23 -= m.a23;
        a30 -= m.a30;
        a31 -= m.a31;
        a32 -= m.a32;
        a33 -= m.a33;
        return this;
    }

    /**
     * Multiplies all components of this matrix by the given factor.
     *
     * @param f The factor to multiply all components with
     * @return This matrix
     */
    public float4x4 scale(float f) {
        a00 *= f;
        a01 *= f;
        a02 *= f;
        a03 *= f;
        a10 *= f;
        a11 *= f;
        a12 *= f;
        a13 *= f;
        a20 *= f;
        a21 *= f;
        a22 *= f;
        a23 *= f;
        a30 *= f;
        a31 *= f;
        a32 *= f;
        a33 *= f;
        return this;
    }

    /**
     * Multiplies this matrix component-wise by the given matrix.
     *
     * @param m The matrix to multiply this matrix with
     * @return This matrix
     */
    public float4x4 scale(float4x4 m) {
        a00 *= m.a00;
        a01 *= m.a01;
        a02 *= m.a02;
        a03 *= m.a03;
        a10 *= m.a10;
        a11 *= m.a11;
        a12 *= m.a12;
        a13 *= m.a13;
        a20 *= m.a20;
        a21 *= m.a21;
        a22 *= m.a22;
        a23 *= m.a23;
        a30 *= m.a30;
        a31 *= m.a31;
        a32 *= m.a32;
        a33 *= m.a33;
        return this;
    }


    /**
     * Returns a new zero matrix.
     *
     * @return A new zero matrix
     */
    public static float4x4 zero() {
        return new float4x4();
    }

    /**
     * Returns a new identity matrix.
     *
     * @return A new identity matrix
     */
    public static float4x4 id() {
        return new float4x4(1,1,1,1);
    }

    /**
     * Returns a new matrix with all components set to 1.
     *
     * @return A new matrix with all components set to 1
     */
    public static float4x4 one() {
        return new float4x4(1,1,1,1,
                            1,1,1,1,
                            1,1,1,1,
                            1,1,1,1);
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
    public static float4x4 diag(float a00, float a11, float a22, float a33) {
        return new float4x4(a00, a11, a22, a33);
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
    public static float4x4 fromRows(constFloat4 r0, constFloat4 r1, constFloat4 r2, constFloat4 r3) {
        return new float4x4(
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
    public static float4x4 fromColumns(constFloat4 c0, constFloat4 c1, constFloat4 c2, constFloat4 c3) {
        return new float4x4(
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
    public static float4x4 fromArray(float[] components) {
        return new float4x4(
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
    public static float4x4 fromArray(float[] arr, int offset) {
        return new float4x4(
                arr[offset], arr[offset+1], arr[offset+2], arr[offset+3],
                arr[offset+4], arr[offset+5], arr[offset+6], arr[offset+7],
                arr[offset+8], arr[offset+9], arr[offset+10], arr[offset+11],
                arr[offset+12], arr[offset+13], arr[offset+14], arr[offset+15]
        );
    }
}
