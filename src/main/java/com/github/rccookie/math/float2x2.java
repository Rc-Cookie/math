package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.ArgumentOutOfRangeException;

import org.jetbrains.annotations.Range;

/**
 * A mutable 2x2 float matrix.
 */
@SuppressWarnings("NewClassNamingConvention")
public final class float2x2 extends constFloat2x2 {

    static {
        JsonDeserialization.register(float2x2.class, json -> {
            if(json.isArray()) {
                if(json.get(0).isArray())
                    return fromRows(json.get(0).as(float2.class), json.get(1).as(float2.class));
                return fromArray(json.as(float[].class));
            }
            if(json.contains("a"))
                return new float2x2(json.get("a").asFloat(), json.get("b").asFloat(),
                        json.get("c").asFloat(), json.get("d").asFloat());
            if(json.contains("a00"))
                return new float2x2(json.get("a00").asFloat(), json.get("a01").asFloat(),
                        json.get("a10").asFloat(), json.get("a11").asFloat());
            if(json.contains("m00"))
                return new float2x2(json.get("m00").asFloat(), json.get("m01").asFloat(),
                        json.get("m10").asFloat(), json.get("m11").asFloat());
            if(json.contains("a11"))
                return new float2x2(json.get("a11").asFloat(), json.get("a12").asFloat(),
                        json.get("a21").asFloat(), json.get("a22").asFloat());
            return new float2x2(json.get("m11").asFloat(), json.get("m12").asFloat(),
                    json.get("m21").asFloat(), json.get("m22").asFloat());
        });
    }

    float2x2() { }

    /**
     * Creates a new matrix.
     *
     * @param a The top left component value
     * @param b The top right component value
     * @param c The bottom left component value
     * @param d The bottom right component value
     */
    public float2x2(float a, float b, float c, float d) {
        super(a, b, c, d);
    }


    /**
     * Sets this matrix to the given component values.
     *
     * @param a The top left component value
     * @param b The top right component value
     * @param c The bottom left component value
     * @param d The bottom right component value
     * @return This matrix
     */
    public float2x2 set(float a, float b, float c, float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        return this;
    }

    /**
     * Sets this matrix to the given matrix's component values.
     *
     * @param m The matrix to set this matrix to
     * @return This matrix
     */
    public float2x2 set(constFloat2x2 m) {
        a = m.a;
        b = m.b;
        c = m.c;
        d = m.d;
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param ab The component values for the first row
     * @param cd The component values for the second row
     * @return This matrix
     */
    public float2x2 setRows(constFloat2 ab, constFloat2 cd) {
        a = ab.x;
        b = ab.y;
        c = cd.x;
        d = cd.y;
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param ac The component values for the first column
     * @param bd The component values for the second column
     * @return This matrix
     */
    public float2x2 setColumns(constFloat2 ac, constFloat2 bd) {
        a = ac.x;
        b = bd.x;
        c = ac.y;
        d = bd.y;
        return this;
    }

    /**
     * Sets the top left component of this matrix.
     *
     * @param a The value to set
     * @return This matrix
     */
    public float2x2 setA(float a) {
        this.a = a;
        return this;
    }

    /**
     * Sets the top right component of this matrix.
     *
     * @param b The value to set
     * @return This matrix
     */
    public float2x2 setB(float b) {
        this.b = b;
        return this;
    }

    /**
     * Sets the bottom left component of this matrix.
     *
     * @param c The value to set
     * @return This matrix
     */
    public float2x2 setC(float c) {
        this.c = c;
        return this;
    }

    /**
     * Sets the bottom right component of this matrix.
     *
     * @param d The value to set
     * @return This matrix
     */
    public float2x2 setD(float d) {
        this.d = d;
        return this;
    }

    /**
     * Sets the top left component of this matrix.
     *
     * @param a00 The value to set
     * @return This matrix
     */
    public float2x2 set00(float a00) {
        a = a00;
        return this;
    }

    /**
     * Sets the top right component of this matrix.
     *
     * @param a01 The value to set
     * @return This matrix
     */
    public float2x2 set01(float a01) {
        b = a01;
        return this;
    }

    /**
     * Sets the bottom left component of this matrix.
     *
     * @param a10 The value to set
     * @return This matrix
     */
    public float2x2 set10(float a10) {
        c = a10;
        return this;
    }

    /**
     * Sets the bottom right component of this matrix.
     *
     * @param a11 The value to set
     * @return This matrix
     */
    public float2x2 set11(float a11) {
        d = a11;
        return this;
    }

    /**
     * Sets the top row of this matrix.
     *
     * @param r The row to set
     * @return This matrix
     */
    public float2x2 setRow0(constFloat2 r) {
        a = r.x;
        b = r.y;
        return this;
    }

    /**
     * Sets the bottom row of this matrix.
     *
     * @param r The row to set
     * @return This matrix
     */
    public float2x2 setRow1(constFloat2 r) {
        c = r.x;
        d = r.y;
        return this;
    }

    /**
     * Sets the left column of this matrix.
     *
     * @param c The column to set
     * @return This matrix
     */
    public float2x2 setColumn0(constFloat2 c) {
        a = c.x;
        this.c = c.y;
        return this;
    }

    /**
     * Sets the right column of this matrix.
     *
     * @param c The column to set
     * @return This matrix
     */
    public float2x2 setColumn1(constFloat2 c) {
        b = c.x;
        d = c.y;
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
    @SuppressWarnings("ConstantValue")
    public float2x2 setComponent(@Range(from = 0, to = 1) int i, @Range(from = 0, to = 1) int j, float x) {
        if(i == 0) {
            if(j == 0) {
                a = x;
                return this;
            }
            if(j == 1) {
                b = x;
                return this;
            }
        }
        else if(i == 1) {
            if(j == 0) {
                c = x;
                return this;
            }
            if(j == 1) {
                d = x;
                return this;
            }
        }
        throw new ArgumentOutOfRangeException("Index ("+i+","+j+") for 2x2 matrix");
    }

    /**
     * Sets the specified row of this matrix.
     *
     * @param i The index of the row to set
     * @param r The row to set
     * @return This matrix
     */
    @SuppressWarnings("ConstantValue")
    public float2x2 setRow(@Range(from = 0, to = 1) int i, float2 r) {
        if(i == 0) {
            a = r.x;
            b = r.y;
            return this;
        }
        if(i == 1) {
            c = r.x;
            d = r.y;
            return this;
        }
        throw new ArgumentOutOfRangeException(i, 0, 2);
    }

    /**
     * Sets the specified column of this matrix.
     *
     * @param j The index of the column to set
     * @param c The column to set
     * @return This matrix
     */
    @SuppressWarnings("ConstantValue")
    public float2x2 setColumn(@Range(from = 0, to = 1) int j, float2 c) {
        if(j == 0) {
            a = c.x;
            this.c = c.y;
            return this;
        }
        if(j == 1) {
            b = c.x;
            d = c.y;
            return this;
        }
        throw new ArgumentOutOfRangeException(j, 0, 2);
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param components The components to set, row by row. Must contain at least as many
     *                   elements as this matrix components
     * @return This matrix
     */
    public float2x2 set(float[] components) {
        a = components[0];
        b = components[1];
        c = components[2];
        d = components[3];
        return this;
    }

    /**
     * Reads this matrix's components from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the first component, then row by row
     * @return This matrix
     */
    public float2x2 set(float[] arr, int offset) {
        a = arr[offset];
        b = arr[offset+1];
        c = arr[offset+2];
        d = arr[offset+3];
        return this;
    }

    /**
     * Sets this matrix to the specified values.
     *
     * @param rows The rows to set to
     * @return This matrix
     */
    public float2x2 set(float[][] rows) {
        a = rows[0][0];
        b = rows[0][1];
        c = rows[1][0];
        d = rows[1][1];
        return this;
    }

    /**
     * Sets all components of this matrix to 0.
     *
     * @return This matrix
     */
    public float2x2 setZero() {
        a = b = c = d = 0;
        return this;
    }


    /**
     * Adds the given value to all components of this matrix.
     *
     * @param x The value to add to all components
     * @return This matrix
     */
    public float2x2 add(float x) {
        a += x;
        b += x;
        c += x;
        d += x;
        return this;
    }

    /**
     * Adds the given matrix component-wise to this matrix.
     *
     * @param m The matrix to add to this matrix
     * @return This matrix
     */
    public float2x2 add(float2x2 m) {
        a += m.a;
        b += m.b;
        c += m.c;
        d += m.d;
        return this;
    }

    /**
     * Subtracts the given value from all components of this matrix.
     *
     * @param x The value to subtract from all components
     * @return This matrix
     */
    public float2x2 sub(float x) {
        a -= x;
        b -= x;
        c -= x;
        d -= x;
        return this;
    }

    /**
     * Subtracts the given matrix component-wise from this matrix.
     *
     * @param m The matrix to subtract from this matrix
     * @return This matrix
     */
    public float2x2 sub(float2x2 m) {
        a -= m.a;
        b -= m.b;
        c -= m.c;
        d -= m.d;
        return this;
    }

    /**
     * Multiplies all components of this matrix by the given factor.
     *
     * @param f The factor to multiply all components with
     * @return This matrix
     */
    public float2x2 scale(float f) {
        a *= f;
        b *= f;
        c *= f;
        d *= f;
        return this;
    }

    /**
     * Multiplies this matrix component-wise by the given matrix.
     *
     * @param m The matrix to multiply this matrix with
     * @return This matrix
     */
    public float2x2 scale(float2x2 m) {
        a *= m.a;
        b *= m.b;
        c *= m.c;
        d *= m.d;
        return this;
    }


    /**
     * Returns a new zero matrix.
     *
     * @return A new zero matrix
     */
    public static float2x2 zero() {
        return new float2x2();
    }

    /**
     * Returns a new identity matrix.
     *
     * @return A new identity matrix
     */
    public static float2x2 id() {
        return new float2x2(1,0,0,1);
    }

    /**
     * Returns a new matrix with all components set to 1.
     *
     * @return A new matrix with all components set to 1
     */
    public static float2x2 one() {
        return new float2x2(1,1,1,1);
    }

    /**
     * Creates a new matrix with the given values for the diagonal entries, and
     * all other components set to 0.
     *
     * @param a The top left component value
     * @param d The bottom right component value
     * @return A diagonal matrix with the given component values
     */
    public static float2x2 diag(float a, float d) {
        return new float2x2(a,0,0,d);
    }

    /**
     * Creates a new matrix from the given rows.
     *
     * @param t The top row
     * @param b The bottom row
     * @return A new matrix from those rows
     */
    public static float2x2 fromRows(constFloat2 t, constFloat2 b) {
        return new float2x2(t.x, t.y, b.x, b.y);
    }

    /**
     * Creates a new matrix from the given columns.
     *
     * @param l The left column
     * @param r The right column
     * @return A new matrix from those columns
     */
    public static float2x2 fromColumns(constFloat2 l, constFloat2 r) {
        return new float2x2(l.x, r.x, r.y, r.y);
    }

    /**
     * Creates a new matrix from the given array.
     *
     * @param components The components for the matrix, row by row. Must contain at least
     *                   as many elements as the matrix will have components
     * @return A new matrix with the specified components
     */
    public static float2x2 fromArray(float[] components) {
        return new float2x2(components[0], components[1], components[2], components[3]);
    }

    /**
     * Creates a new matrix by reading the component values from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the top left component, then row by row
     * @return A new matrix with the read components
     */
    public static float2x2 fromArray(float[] arr, int offset) {
        return new float2x2(arr[offset], arr[offset+1], arr[offset+2], arr[offset+3]);
    }
}
