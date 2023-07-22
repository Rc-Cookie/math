package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.ArgumentOutOfRangeException;
import com.github.rccookie.util.Arguments;

import org.jetbrains.annotations.Range;

/**
 * A mutable 3D float vector.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public final class float3 extends constFloat3 {

    static {
        JsonDeserialization.register(float3.class, json -> json.isArray() ? new float3(
                json.get(0).asFloat(),
                json.get(1).asFloat(),
                json.get(2).asFloat()
        ) : new float3(
                json.get("x").asFloat(),
                json.get("y").asFloat(),
                json.get("z").asFloat()
        ));
    }

    float3() { }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param xyz The value for all components
     */
    public float3(float xyz) {
        super(xyz);
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param z The value for the z component
     */
    public float3(constFloat2 xy, float z) {
        super(xy,z);
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param z The value for the z component
     */
    public float3(constInt2 xy, float z) {
        super(xy,z);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yz The values for the y and z component
     */
    public float3(float x, constFloat2 yz) {
        super(x,yz);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yz The values for the y and z component
     */
    public float3(float x, constInt2 yz) {
        super(x,yz);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param y The value for the y component
     * @param z The value for the z component
     */
    public float3(float x, float y, float z) {
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
    public float3 set(float x, float y, float z) {
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
    public float3 set(constFloat3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    /**
     * Sets this vector to the same value as the given vector.
     *
     * @param v The vector to set this vector to
     * @return This vector
     */
    public float3 set(constInt3 v) {
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
    public float3 setX(float x) {
        this.x = x;
        return this;
    }


    /**
     * Sets the y component of this vector to the given value.
     *
     * @param y The new value for the y component
     * @return This vector
     */
    public float3 setY(float y) {
        this.y = y;
        return this;
    }


    /**
     * Sets the z component of this vector to the given value.
     *
     * @param z The new value for the z component
     * @return This vector
     */
    public float3 setZ(float z) {
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
    public float3 setComponent(@Range(from = 0, to = 2) int index, float x) {
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
    public float3 setZero() {
        x = y = z = 0;
        return this;
    }

    /**
     * Scales this vector so that it has the desired length, without changing its direction.
     *
     * @param l The length to set. Negative values will flip the resulting direction
     * @return This vector
     */
    public float3 setLength(float l) {
        return norm().scale(l);
    }

    /**
     * Clamps the length of this vector so that it is within the given range, without changing
     * its direction.
     *
     * @param min The minimum length for the vector
     * @param max The maximum length for the vector
     * @return This vector
     */
    public float3 clampLength(float min, float max) {
        float len2 = length2();
        if(len2 == 0)
            return setX(min);
        if(len2 < min)
            return scale(min / Mathf.sqrt(len2));
        if(len2 > max)
            return scale(max / Mathf.sqrt(len2));
        return this;
    }

    /**
     * Sets this vector to the component-wise minimum of this and the given vector.
     *
     * @param v The vector to take the minimum from
     * @return This vector
     */
    public float3 setMin(constFloat3 v) {
        x = Mathf.min(x, v.x);
        y = Mathf.min(y, v.y);
        z = Mathf.min(z, v.z);
        return this;
    }

    /**
     * Sets this vector to the component-wise minimum of this and the given vector.
     *
     * @param v The vector to take the minimum from
     * @return This vector
     */
    public float3 setMin(constInt3 v) {
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
    public float3 setMax(constFloat3 v) {
        x = Mathf.max(x, v.x);
        y = Mathf.max(y, v.y);
        z = Mathf.max(z, v.z);
        return this;
    }

    /**
     * Sets this vector to the component-wise maximum of this and the given vector.
     *
     * @param v The vector to take the maximum from
     * @return This vector
     */
    public float3 setMax(constInt3 v) {
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
    public float3 neg() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    /**
     * Inverts this vector component-wise.
     *
     * @return This vector
     */
    public float3 invert() {
        x = 1/x;
        y = 1/y;
        z = 1/z;
        return this;
    }

    /**
     * Sets this vector to its component-wise absolute value.
     *
     * @return This vector
     */
    public float3 abs() {
        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);
        return this;
    }

    /**
     * Normalizes this vector, so that it has a length of 1 in the same direction.
     *
     * @return This vector
     */
    public float3 norm() {
        return div(length());
    }

    /**
     * Normalizes this vector, so that it has a length of 1 in the same direction.
     * If this vector is 0, it will be set to the x-axis unit vector.
     *
     * @return This vector
     */
    public float3 safeNorm() {
        float len = length();
        return len == 0 ? setX(1) : dived(len);
    }


    /**
     * Adds the given value onto all components of this vector.
     *
     * @param x The value to add to all components
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public float3 add(float x) {
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
    public float3 add(float x, float y, float z) {
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
    public float3 add(constFloat3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Adds the given vector component-wise to this vector.
     *
     * @param v The vector to add to this vector
     * @return This vector
     */
    public float3 add(constInt3 v) {
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
    public float3 sub(float x) {
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
    public float3 sub(float x, float y, float z) {
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
    public float3 sub(constFloat3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * Subtracts the given vector component-wise from this vector.
     *
     * @param v The vector to subtract from this vector
     * @return This vector
     */
    public float3 sub(constInt3 v) {
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
    public float3 scale(float f) {
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
    public float3 scale(float x, float y, float z) {
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
    public float3 scale(constFloat3 v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    /**
     * Multiplies the given vector component-wise to this vector.
     *
     * @param v The vector to multiply with this vector
     * @return This vector
     */
    public float3 scale(constInt3 v) {
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
    public float3 div(float x) {
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
    public float3 div(float x, float y, float z) {
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
    public float3 div(constFloat3 v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        return this;
    }

    /**
     * Divides this vector component-wise by the given vector.
     *
     * @param v The vector to divide this vector by
     * @return This vector
     */
    public float3 div(constInt3 v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        return this;
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return This vector
     */
    public float3 lerp(constFloat3 t, float a) {
        x += (t.x - x) * a;
        y += (t.y - y) * a;
        z += (t.z - z) * a;
        return this;
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return This vector
     */
    public float3 lerp(constInt3 t, float a) {
        x += (t.x - x) * a;
        y += (t.y - y) * a;
        z += (t.z - z) * a;
        return this;
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return This vector
     */
    public float3 project(constFloat3 onto) {
        float f = (x*onto.x + y*onto.y + z*onto.z) / (onto.x*onto.x + onto.y*onto.y + onto.z*onto.z);
        x = onto.x * f;
        y = onto.y * f;
        z = onto.z * f;
        return this;
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return This vector
     */
    public float3 project(constInt3 onto) {
        float f = (x*onto.x + y*onto.y + z*onto.z) / (onto.x*onto.x + onto.y*onto.y + onto.z*onto.z);
        x = onto.x * f;
        y = onto.y * f;
        z = onto.z * f;
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
    public float3 clamp(constFloat3 min, constFloat3 max) {
        x = Mathf.clamp(x, min.x, max.x);
        y = Mathf.clamp(y, min.y, max.y);
        z = Mathf.clamp(z, min.z, max.z);
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
    public float3 clamp(constInt3 min, constInt3 max) {
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
    public float3 clamp(constBounds area) {
        x = Mathf.clamp(x, area.min.x, area.max.x);
        y = Mathf.clamp(y, area.min.y, area.max.y);
        z = Mathf.clamp(z, area.min.z, area.max.z);
        return this;
    }

    /**
     * Clamps this vector component-wise within the given area.
     *
     * @param area The area to clamp this vector into
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public float3 clamp(constIBounds area) {
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
    public static float3 zero() {
        return new float3();
    }

    /**
     * Returns a new x-axis unit vector.
     *
     * @return A new x-axis unit vector
     */
    public static float3 X() {
        return new float3(1,0,0);
    }

    /**
     * Returns a new y-axis unit vector.
     *
     * @return A new y-axis unit vector
     */
    public static float3 Y() {
        return new float3(0,1,0);
    }

    /**
     * Returns a new z-axis unit vector.
     *
     * @return A new z-axis unit vector
     */
    public static float3 Z() {
        return new float3(0,0,1);
    }

    /**
     * Returns a new vector with all components set to 1.
     *
     * @return A new vector with all components set to 1
     */
    public static float3 one() {
        return new float3(1);
    }

    /**
     * Returns a new vector with all components set to -1.
     *
     * @return A new vector with all components set to -1
     */
    public static float3 minusOne() {
        return new float3(-1);
    }

    /**
     * Returns a new vector with all components set to {@link Float#POSITIVE_INFINITY}.
     *
     * @return A new vector with all components set to {@link Float#POSITIVE_INFINITY}
     */
    public static float3 inf() {
        return new float3(Float.POSITIVE_INFINITY);
    }

    /**
     * Returns a new vector with all components set to {@link Float#NEGATIVE_INFINITY}.
     *
     * @return A new vector with all components set to {@link Float#NEGATIVE_INFINITY}
     */
    public static float3 negInf() {
        return new float3(Float.NEGATIVE_INFINITY);
    }

    /**
     * Returns a new vector with all components set to {@link Float#NaN}.
     *
     * @return A new vector with all components set to {@link Float#NaN}
     */
    public static float3 NaN() {
        return new float3(Float.NaN);
    }

    /**
     * Creates a new vector from the given array.
     *
     * @param arr The array to read the components from. Must be at least of length 3
     * @return A new vector with the components from the array
     */
    public static float3 fromArray(float[] arr) {
        if(Arguments.checkNull(arr, "arr").length < 3)
            throw new IllegalArgumentException("arr.length < 3");
        return new float3(arr[0], arr[1], arr[2]);
    }

    /**
     * Creates a new vector from the given array.
     *
     * @param arr The array to read the components from. Must have at least 3 more elements
     *            starting from <code>offset</code>
     * @param offset The index of the x component in the array
     * @return A new vector with the components from the array
     */
    public static float3 fromArray(float[] arr, int offset) {
        if(Arguments.checkNull(arr, "arr").length < offset + 3)
            throw new IllegalArgumentException("Range out of array bounds");
        return new float3(arr[0], arr[1], arr[2]);
    }


    /**
     * Returns the component-wise minimum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise minimum
     */
    public static float3 min(constFloat3 a, constFloat3 b) {
        return new float3(
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
    public static float3 max(constFloat3 a, constFloat3 b) {
        return new float3(
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
    public static float3 between(constFloat3 a, constFloat3 b) {
        return new float3(b.x - a.x, b.y - a.y, b.z - a.z);
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    public static float3 between(constFloat3 a, constInt3 b) {
        return new float3(b.x - a.x, b.y - a.y, b.z - a.z);
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    public static float3 between(constInt3 a, constFloat3 b) {
        return new float3(b.x - a.x, b.y - a.y, b.z - a.z);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    public static float3 average(constFloat3 a, constFloat3 b) {
        return new float3((b.x + a.x) / 2, (b.y + a.y) / 2, (b.z + a.z) / 2);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    public static float3 average(constFloat3 a, constInt3 b) {
        return new float3((b.x + a.x) / 2, (b.y + a.y) / 2, (b.z + a.z) / 2);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    public static float3 average(constInt3 a, constFloat3 b) {
        return new float3((b.x + a.x) / 2, (b.y + a.y) / 2, (b.z + a.z) / 2);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    public static float3 average(constInt3 a, constInt3 b) {
        return new float3((b.x + a.x) / 2f, (b.y + a.y) / 2f, (b.z + a.z) / 2f);
    }
}
