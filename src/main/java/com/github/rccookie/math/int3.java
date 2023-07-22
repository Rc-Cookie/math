package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.ArgumentOutOfRangeException;
import com.github.rccookie.util.Arguments;

import org.jetbrains.annotations.Range;

/**
 * A mutable 3D int vector.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public final class int3 extends constInt3 {

    static {
        JsonDeserialization.register(int3.class, json -> new int3(
                json.get(0).asInt(),
                json.get(1).asInt(),
                json.get(2).asInt()
        ));
    }

    int3() { }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param xyz The value for all components
     */
    public int3(int xyz) {
        super(xyz);
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param z The value for the z component
     */
    public int3(constInt2 xy, int z) {
        super(xy,z);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yz The values for the y and z component
     */
    public int3(int x, constInt2 yz) {
        super(x,yz);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param y The value for the y component
     * @param z The value for the z component
     */
    public int3(int x, int y, int z) {
        super(x,y,z);
    }


    /**
     * Sets this vector to the given values.
     *
     * @param x The value to set the x component to
     * @param y The value to set the y component to
     * @param z The value to set the z component to
     * @return This vector
     */
    public int3 set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Sets this vector to the same value as the given vector.
     *
     * @param v The vector to set this vector to
     * @return This vector
     */
    public int3 set(constInt3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    /**
     * Sets the x component of this vector to the given value.
     *
     * @param x The new value for the x component
     * @return This vector
     */
    public int3 setX(int x) {
        this.x = x;
        return this;
    }


    /**
     * Sets the y component of this vector to the given value.
     *
     * @param y The new value for the y component
     * @return This vector
     */
    public int3 setY(int y) {
        this.y = y;
        return this;
    }


    /**
     * Sets the z component of this vector to the given value.
     *
     * @param z The new value for the z component
     * @return This vector
     */
    public int3 setZ(int z) {
        this.z = z;
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
    public int3 setComponent(@Range(from = 0, to = 2) int index, int x) {
        switch(index) {
            case 0: this.x = x; return this;
            case 1: y = x; return this;
            case 2: z = x; return this;
            default: throw new ArgumentOutOfRangeException(index, 0, 3);
        }
    }

    /**
     * Sets all components of this vector to 0.
     *
     * @return This vector
     */
    public int3 setZero() {
        x = y = z = 0;
        return this;
    }

    /**
     * Sets this vector to the component-wise minimum of this and the given vector.
     *
     * @param v The vector to take the minimum from
     * @return This vector
     */
    public int3 setMin(constInt3 v) {
        x = Mathf.min(x, v.x);
        y = Mathf.min(y, v.y);
        z = Mathf.min(z, v.z);
        return this;
    }

    /**
     * Sets this vector to the component-wise maximum of this and the given vector.
     *
     * @param v The vector to take the maximum from
     * @return This vector
     */
    public int3 setMax(constInt3 v) {
        x = Mathf.max(x, v.x);
        y = Mathf.max(y, v.y);
        z = Mathf.max(z, v.z);
        return this;
    }



    /**
     * Negates this vector component-wise.
     *
     * @return This vector
     */
    public int3 neg() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    /**
     * Sets this vector to its component-wise absolute value.
     *
     * @return This vector
     */
    public int3 abs() {
        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);
        return this;
    }


    /**
     * Adds the given value onto all components of this vector.
     *
     * @param x The value to add to all components
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public int3 add(int x) {
        this.x += x;
        y += x;
        z += x;
        return this;
    }

    /**
     * Adds the given values to the respective components of this vector.
     *
     * @param x The value to add to the x component
     * @param y The value to add to the y component
     * @param z The value to add to the z component
     * @return This vector
     */
    public int3 add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Adds the given vector component-wise to this vector.
     *
     * @param v The vector to add to this vector
     * @return This vector
     */
    public int3 add(constInt3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Subtracts the given value from all components of this vector.
     *
     * @param x The value to subtract from all components
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public int3 sub(int x) {
        this.x -= x;
        y -= x;
        z -= x;
        return this;
    }

    /**
     * Subtracts the given values from the respective components of this vector.
     *
     * @param x The value to subtract from the x component
     * @param y The value to subtract from the y component
     * @param z The value to subtract from the z component
     * @return This vector
     */
    public int3 sub(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Subtracts the given vector component-wise from this vector.
     *
     * @param v The vector to subtract from this vector
     * @return This vector
     */
    public int3 sub(constInt3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * Multiplies all components of this vector by the given value.
     *
     * @param f The value to multiply all components with
     * @return This vector
     */
    public int3 scale(int f) {
        x *= f;
        y *= f;
        z *= f;
        return this;
    }

    /**
     * Multiplies all components of this vector by the respective values.
     *
     * @param x The value to multiply the x component by
     * @param y The value to multiply the y component by
     * @param z The value to multiply the z component by
     * @return This vector
     */
    public int3 scale(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    /**
     * Multiplies the given vector component-wise to this vector.
     *
     * @param v The vector to multiply with this vector
     * @return This vector
     */
    public int3 scale(constInt3 v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    /**
     * Divides all components of this vector by the given value.
     *
     * @param x The value to divide all components by
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public int3 div(int x) {
        this.x /= x;
        y /= x;
        z /= x;
        return this;
    }

    /**
     * Divides all components of this vector by the respective values.
     *
     * @param x The value to divide the x component by
     * @param y The value to divide the y component by
     * @param z The value to divide the z component by
     * @return This vector
     */
    public int3 div(int x, int y, int z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    /**
     * Divides this vector component-wise by the given vector.
     *
     * @param v The vector to divide this vector by
     * @return This vector
     */
    public int3 div(constInt3 v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
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
    public int3 clamp(constInt3 min, constInt3 max) {
        x = Mathf.clamp(x, min.x, max.x);
        y = Mathf.clamp(y, min.y, max.y);
        z = Mathf.clamp(z, min.z, max.z);
        return this;
    }

    /**
     * Clamps this vector component-wise within the given area.
     *
     * @param area The area to clamp this vector into
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public int3 clamp(constIBounds area) {
        x = Mathf.clamp(x, area.min.x, area.max.x);
        y = Mathf.clamp(y, area.min.y, area.max.y);
        z = Mathf.clamp(z, area.min.z, area.max.z);
        return this;
    }


    /**
     * Returns a new zero vector.
     *
     * @return A new zero vector
     */
    public static int3 zero() {
        return new int3();
    }

    /**
     * Returns a new x-axis unit vector.
     *
     * @return A new x-axis unit vector
     */
    public static int3 X() {
        return new int3(1,0,0);
    }

    /**
     * Returns a new y-axis unit vector.
     *
     * @return A new y-axis unit vector
     */
    public static int3 Y() {
        return new int3(0,1,0);
    }

    /**
     * Returns a new z-axis unit vector.
     *
     * @return A new z-axis unit vector
     */
    public static int3 Z() {
        return new int3(0,0,1);
    }

    /**
     * Returns a new vector with all components set to 1.
     *
     * @return A new vector with all components set to 1
     */
    public static int3 one() {
        return new int3(1);
    }

    /**
     * Returns a new vector with all components set to -1.
     *
     * @return A new vector with all components set to -1
     */
    public static int3 minusOne() {
        return new int3(-1);
    }

    /**
     * Returns a new vector with all components set to {@link Integer#MIN_VALUE}.
     *
     * @return A new vector with all components set to {@link Integer#MIN_VALUE}
     */
    public static int3 min() {
        return new int3(Integer.MIN_VALUE);
    }

    /**
     * Returns a new vector with all components set to {@link Integer#MAX_VALUE}.
     *
     * @return A new vector with all components set to {@link Integer#MAX_VALUE}
     */
    public static int3 max() {
        return new int3(Integer.MAX_VALUE);
    }

    /**
     * Creates a new vector from the given array.
     *
     * @param arr The array to read the components from. Must be at least of length 3
     * @return A new vector with the components from the array
     */
    public static int3 fromArray(int[] arr) {
        if(Arguments.checkNull(arr, "arr").length < 3)
            throw new IllegalArgumentException("arr.length < 3");
        return new int3(arr[0], arr[1], arr[2]);
    }

    /**
     * Creates a new vector from the given array.
     *
     * @param arr The array to read the components from. Must have at least 3 more elements
     *            starting from <code>offset</code>
     * @param offset The index of the x component in the array
     * @return A new vector with the components from the array
     */
    public static int3 fromArray(int[] arr, int offset) {
        if(Arguments.checkNull(arr, "arr").length < offset + 3)
            throw new IllegalArgumentException("Range out of array bounds");
        return new int3(arr[0], arr[1], arr[2]);
    }


    /**
     * Returns the component-wise minimum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise minimum
     */
    public static int3 min(constInt3 a, constInt3 b) {
        return new int3(
                Math.min(a.x, b.x),
                Math.min(a.y, b.y),
                Math.min(a.z, b.z)
        );
    }

    /**
     * Returns the component-wise maximum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise maximum
     */
    public static int3 max(constInt3 a, constInt3 b) {
        return new int3(
                Math.max(a.x, b.x),
                Math.max(a.y, b.y),
                Math.max(a.z, b.z)
        );
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    public static int3 between(constInt3 a, constInt3 b) {
        return new int3(b.x - a.x, b.y - a.y, b.z - a.z);
    }
}
