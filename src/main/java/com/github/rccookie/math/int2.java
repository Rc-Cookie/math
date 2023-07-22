package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.ArgumentOutOfRangeException;
import com.github.rccookie.util.Arguments;

import org.jetbrains.annotations.Range;

/**
 * A mutable 2D int vector.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public class int2 extends constInt2 {

    static {
        JsonDeserialization.register(int2.class, json -> new int2(
                json.get(0).asInt(),
                json.get(1).asInt()
        ));
    }

    int2() { }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param xy The value for all components
     */
    public int2(int xy) {
        super(xy);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param y The value for the y component
     */
    public int2(int x, int y) {
        super(x,y);
    }


    /**
     * Sets this vector to the given values.
     *
     * @param x The value to set the x component to
     * @param y The value to set the y component to
     * @return This vector
     */
    public final int2 set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Sets this vector to the same value as the given vector.
     *
     * @param v The vector to set this vector to
     * @return This vector
     */
    public final int2 set(constInt2 v) {
        x = v.x;
        y = v.y;
        return this;
    }

    /**
     * Sets the x component of this vector to the given value.
     *
     * @param x The new value for the x component
     * @return This vector
     */
    public final int2 setX(int x) {
        this.x = x;
        return this;
    }


    /**
     * Sets the y component of this vector to the given value.
     *
     * @param y The new value for the y component
     * @return This vector
     */
    public final int2 setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Sets the component at the specified index of this vector to the given
     * value, where 0 is the index of the x component.
     *
     * @param index The index of the component to set
     * @param x The new value for that component
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final int2 setComponent(@Range(from = 0, to = 1) int index, int x) {
        switch(index) {
            case 0: this.x = x; return this;
            case 1: y = x; return this;
            default: throw new ArgumentOutOfRangeException(index, 0, 2);
        }
    }

    /**
     * Sets all components of this vector to 0.
     *
     * @return This vector
     */
    public final int2 setZero() {
        x = y = 0;
        return this;
    }

    /**
     * Sets this vector to the component-wise minimum of this and the given vector.
     *
     * @param v The vector to take the minimum from
     * @return This vector
     */
    public int2 setMin(constInt2 v) {
        x = Mathf.min(x, v.x);
        y = Mathf.min(y, v.y);
        return this;
    }

    /**
     * Sets this vector to the component-wise maximum of this and the given vector.
     *
     * @param v The vector to take the maximum from
     * @return This vector
     */
    public int2 setMax(constInt2 v) {
        x = Mathf.max(x, v.x);
        y = Mathf.max(y, v.y);
        return this;
    }



    /**
     * Negates this vector component-wise.
     *
     * @return This vector
     */
    public final int2 neg() {
        x = -x;
        y = -y;
        return this;
    }

    /**
     * Sets this vector to its component-wise absolute value.
     *
     * @return This vector
     */
    public final int2 abs() {
        x = Math.abs(x);
        y = Math.abs(y);
        return this;
    }


    /**
     * Adds the given value onto all components of this vector.
     *
     * @param x The value to add to all components
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final int2 add(int x) {
        this.x += x;
        y += x;
        return this;
    }

    /**
     * Adds the given values to the respective components of this vector.
     *
     * @param x The value to add to the x component
     * @param y The value to add to the y component
     * @return This vector
     */
    public final int2 add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Adds the given vector component-wise to this vector.
     *
     * @param v The vector to add to this vector
     * @return This vector
     */
    public final int2 add(constInt2 v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /**
     * Subtracts the given value from all components of this vector.
     *
     * @param x The value to subtract from all components
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final int2 sub(int x) {
        this.x -= x;
        y -= x;
        return this;
    }

    /**
     * Subtracts the given values from the respective components of this vector.
     *
     * @param x The value to subtract from the x component
     * @param y The value to subtract from the y component
     * @return This vector
     */
    public final int2 sub(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * Subtracts the given vector component-wise from this vector.
     *
     * @param v The vector to subtract from this vector
     * @return This vector
     */
    public final int2 sub(constInt2 v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /**
     * Multiplies all components of this vector by the given value.
     *
     * @param f The value to multiply all components with
     * @return This vector
     */
    public final int2 scale(int f) {
        x *= f;
        y *= f;
        return this;
    }

    /**
     * Multiplies all components of this vector by the respective values.
     *
     * @param x The value to multiply the x component by
     * @param y The value to multiply the y component by
     * @return This vector
     */
    public final int2 scale(int x, int y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    /**
     * Multiplies the given vector component-wise to this vector.
     *
     * @param v The vector to multiply with this vector
     * @return This vector
     */
    public final int2 scale(constInt2 v) {
        x *= v.x;
        y *= v.y;
        return this;
    }

    /**
     * Divides all components of this vector by the given value.
     *
     * @param x The value to divide all components by
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final int2 div(int x) {
        this.x /= x;
        y /= x;
        return this;
    }

    /**
     * Divides all components of this vector by the respective values.
     *
     * @param x The value to divide the x component by
     * @param y The value to divide the y component by
     * @return This vector
     */
    public final int2 div(int x, int y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    /**
     * Divides this vector component-wise by the given vector.
     *
     * @param v The vector to divide this vector by
     * @return This vector
     */
    public final int2 div(constInt2 v) {
        x /= v.x;
        y /= v.y;
        return this;
    }

    /**
     * Clamps this vector component-wise within the given range.
     *
     * @param min The component-wise minimum value
     * @param max The component-wise maximum value
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final int2 clamp(constInt2 min, constInt2 max) {
        x = Mathf.clamp(x, min.x, max.x);
        y = Mathf.clamp(y, min.y, max.y);
        return this;
    }

    /**
     * Clamps this vector component-wise within the given area.
     *
     * @param area The area to clamp this vector into
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public int2 clamp(constIRect area) {
        x = Mathf.clamp(x, area.min.x, area.max.x);
        y = Mathf.clamp(y, area.min.y, area.max.y);
        return this;
    }


    /**
     * Returns a new zero vector.
     *
     * @return A new zero vector
     */
    public static int2 zero() {
        return new int2();
    }

    /**
     * Returns a new x-axis unit vector.
     *
     * @return A new x-axis unit vector
     */
    public static int2 X() {
        return new int2(1,0);
    }

    /**
     * Returns a new y-axis unit vector.
     *
     * @return A new y-axis unit vector
     */
    public static int2 Y() {
        return new int2(0,1);
    }

    /**
     * Returns a new vector with all components set to 1.
     *
     * @return A new vector with all components set to 1
     */
    public static int2 one() {
        return new int2(1);
    }

    /**
     * Returns a new vector with all components set to -1.
     *
     * @return A new vector with all components set to -1
     */
    public static int2 minusOne() {
        return new int2(-1);
    }

    /**
     * Returns a new vector with all components set to {@link Integer#MIN_VALUE}.
     *
     * @return A new vector with all components set to {@link Integer#MIN_VALUE}
     */
    public static int2 min() {
        return new int2(Integer.MIN_VALUE);
    }

    /**
     * Returns a new vector with all components set to {@link Integer#MAX_VALUE}.
     *
     * @return A new vector with all components set to {@link Integer#MAX_VALUE}
     */
    public static int2 max() {
        return new int2(Integer.MAX_VALUE);
    }

    /**
     * Creates a new vector from the given array.
     *
     * @param arr The array to read the components from. Must be at least of length 2
     * @return A new vector with the components from the array
     */
    public static int2 fromArray(int[] arr) {
        if(Arguments.checkNull(arr, "arr").length < 2)
            throw new IllegalArgumentException("arr.length < 2");
        return new int2(arr[0], arr[1]);
    }

    /**
     * Creates a new vector from the given array.
     *
     * @param arr The array to read the components from. Must have at least 2 more elements
     *            starting from <code>offset</code>
     * @param offset The index of the x component in the array
     * @return A new vector with the components from the array
     */
    public static int2 fromArray(int[] arr, int offset) {
        if(Arguments.checkNull(arr, "arr").length < offset + 2)
            throw new IllegalArgumentException("Range out of array bounds");
        return new int2(arr[0], arr[1]);
    }


    /**
     * Returns the component-wise minimum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise minimum
     */
    public static int2 min(constInt2 a, constInt2 b) {
        return new int2(
                Math.min(a.x, b.x),
                Math.min(a.y, b.y)
        );
    }

    /**
     * Returns the component-wise maximum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise maximum
     */
    public static int2 max(constInt2 a, constInt2 b) {
        return new int2(
                Math.max(a.x, b.x),
                Math.max(a.y, b.y)
        );
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    public static int2 between(constInt2 a, constInt2 b) {
        return new int2(b.x - a.x, b.y - a.y);
    }
}
