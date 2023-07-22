package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.ArgumentOutOfRangeException;
import com.github.rccookie.util.Arguments;

import org.jetbrains.annotations.Range;

/**
 * A mutable 2D float vector.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public final class float2 extends constFloat2 {

    static {
        JsonDeserialization.register(float2.class, json -> json.isArray() ? new float2(
                json.get(0).asFloat(),
                json.get(1).asFloat()
        ) : new float2(
                json.get("x").asFloat(),
                json.get("y").asFloat()
        ));
    }

    float2() { }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param xy The value for all components
     */
    public float2(float xy) {
        super(xy);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param y The value for the y component1
     */
    public float2(float x, float y) {
        super(x,y);
    }


    /**
     * Sets this vector to the given values.
     *
     * @param x The value to set the x component to
     * @param y The value to set the y component to
     * @return This vector
     */
    public float2 set(float x, float y) {
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
    public float2 set(constFloat2 v) {
        x = v.x;
        y = v.y;
        return this;
    }

    /**
     * Sets this vector to the same value as the given vector.
     *
     * @param v The vector to set this vector to
     * @return This vector
     */
    public float2 set(constInt2 v) {
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
    public float2 setX(float x) {
        this.x = x;
        return this;
    }


    /**
     * Sets the y component of this vector to the given value.
     *
     * @param y The new value for the y component
     * @return This vector
     */
    public float2 setY(float y) {
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
    public float2 setComponent(@Range(from = 0, to = 1) int index, float x) {
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
    public float2 setZero() {
        x = y = 0;
        return this;
    }

    /**
     * Scales this vector so that it has the desired length, without changing its direction.
     *
     * @param l The length to set. Negative values will flip the resulting direction
     * @return This vector
     */
    public float2 setLength(float l) {
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
    public float2 clampLength(float min, float max) {
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
    public float2 setMin(constFloat2 v) {
        x = Mathf.min(x, v.x);
        y = Mathf.min(y, v.y);
        return this;
    }

    /**
     * Sets this vector to the component-wise minimum of this and the given vector.
     *
     * @param v The vector to take the minimum from
     * @return This vector
     */
    public float2 setMin(constInt2 v) {
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
    public float2 setMax(constFloat2 v) {
        x = Mathf.max(x, v.x);
        y = Mathf.max(y, v.y);
        return this;
    }

    /**
     * Sets this vector to the component-wise maximum of this and the given vector.
     *
     * @param v The vector to take the maximum from
     * @return This vector
     */
    public float2 setMax(constInt2 v) {
        x = Mathf.max(x, v.x);
        y = Mathf.max(y, v.y);
        return this;
    }



    /**
     * Negates this vector component-wise.
     *
     * @return This vector
     */
    public float2 neg() {
        x = -x;
        y = -y;
        return this;
    }

    /**
     * Inverts this vector component-wise.
     *
     * @return This vector
     */
    public float2 invert() {
        x = 1/x;
        y = 1/y;
        return this;
    }

    /**
     * Sets this vector to its component-wise absolute value.
     *
     * @return This vector
     */
    public float2 abs() {
        x = Math.abs(x);
        y = Math.abs(y);
        return this;
    }

    /**
     * Normalizes this vector, so that it has a length of 1 in the same direction.
     *
     * @return This vector
     */
    public float2 norm() {
        return div(length());
    }

    /**
     * Normalizes this vector, so that it has a length of 1 in the same direction.
     * If this vector is 0, it will be set to the x-axis unit vector.
     *
     * @return This vector
     */
    public float2 safeNorm() {
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
    public float2 add(float x) {
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
    public float2 add(float x, float y) {
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
    public float2 add(constFloat2 v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /**
     * Adds the given vector component-wise to this vector.
     *
     * @param v The vector to add to this vector
     * @return This vector
     */
    public float2 add(constInt2 v) {
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
    public float2 sub(float x) {
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
    public float2 sub(float x, float y) {
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
    public float2 sub(constFloat2 v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /**
     * Subtracts the given vector component-wise from this vector.
     *
     * @param v The vector to subtract from this vector
     * @return This vector
     */
    public float2 sub(constInt2 v) {
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
    public float2 scale(float f) {
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
    public float2 scale(float x, float y) {
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
    public float2 scale(constFloat2 v) {
        x *= v.x;
        y *= v.y;
        return this;
    }

    /**
     * Multiplies the given vector component-wise to this vector.
     *
     * @param v The vector to multiply with this vector
     * @return This vector
     */
    public float2 scale(constInt2 v) {
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
    public float2 div(float x) {
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
    public float2 div(float x, float y) {
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
    public float2 div(constFloat2 v) {
        x /= v.x;
        y /= v.y;
        return this;
    }

    /**
     * Divides this vector component-wise by the given vector.
     *
     * @param v The vector to divide this vector by
     * @return This vector
     */
    public float2 div(constInt2 v) {
        x /= v.x;
        y /= v.y;
        return this;
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return This vector
     */
    public float2 lerp(constFloat2 t, float a) {
        x += (t.x - x) * a;
        y += (t.y - y) * a;
        return this;
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return This vector
     */
    public float2 lerp(constInt2 t, float a) {
        x += (t.x - x) * a;
        y += (t.y - y) * a;
        return this;
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return This vector
     */
    public float2 project(constFloat2 onto) {
        float f = (x*onto.x + y*onto.y) / (onto.x*onto.x + onto.y*onto.y);
        x = onto.x * f;
        y = onto.y * f;
        return this;
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return This vector
     */
    public float2 project(constInt2 onto) {
        float f = (x*onto.x + y*onto.y) / (onto.x*onto.x + onto.y*onto.y);
        x = onto.x * f;
        y = onto.y * f;
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
    public float2 clamp(constFloat2 min, constFloat2 max) {
        x = Mathf.clamp(x, min.x, max.x);
        y = Mathf.clamp(y, min.y, max.y);
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
    public float2 clamp(constInt2 min, constInt2 max) {
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
    public float2 clamp(constRect area) {
        x = Mathf.clamp(x, area.min.x, area.max.x);
        y = Mathf.clamp(y, area.min.y, area.max.y);
        return this;
    }

    /**
     * Clamps this vector component-wise within the given area.
     *
     * @param area The area to clamp this vector into
     * @return This vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public float2 clamp(constIRect area) {
        x = Mathf.clamp(x, area.min.x, area.max.x);
        y = Mathf.clamp(y, area.min.y, area.max.y);
        return this;
    }

    /**
     * Rotates the vector by the given angle in degrees.
     *
     * @param a The angle to rotate the vector by, in degrees
     * @return This vector
     */
    public float2 rotate(float a) {
        if(a == 0) return this;
        float sin = Mathf.sin(a), cos = Mathf.cos(a);
        float oldX = x;
        x = x * cos - y * sin;
        y = oldX * sin + y * cos;
        return this;
    }

    /**
     * Rotates the vector by the given angle in degrees around the specified
     * center point.
     *
     * @param c The point to rotate around
     * @param a The angle to rotate the vector by, in degrees
     * @return This vector
     */
    public float2 rotateAround(float2 c, float a) {
        if(a == 0) return this;
        float sin = Mathf.sin(a), cos = Mathf.cos(a);

        x -= c.x;
        y -= c.y;

        float oldX = x;
        x = x * cos - y * sin;
        y = oldX * sin + y * cos;

        x += c.x;
        y += c.y;

        return this;
    }


    /**
     * Returns a new zero vector.
     *
     * @return A new zero vector
     */
    public static float2 zero() {
        return new float2();
    }

    /**
     * Returns a new x-axis unit vector.
     *
     * @return A new x-axis unit vector
     */
    public static float2 X() {
        return new float2(1,0);
    }

    /**
     * Returns a new y-axis unit vector.
     *
     * @return A new y-axis unit vector
     */
    public static float2 Y() {
        return new float2(0,1);
    }

    /**
     * Returns a new vector with all components set to 1.
     *
     * @return A new vector with all components set to 1
     */
    public static float2 one() {
        return new float2(1);
    }

    /**
     * Returns a new vector with all components set to -1.
     *
     * @return A new vector with all components set to -1
     */
    public static float2 minusOne() {
        return new float2(-1);
    }

    /**
     * Returns a new vector with all components set to {@link Float#POSITIVE_INFINITY}.
     *
     * @return A new vector with all components set to {@link Float#POSITIVE_INFINITY}
     */
    public static float2 inf() {
        return new float2(Float.POSITIVE_INFINITY);
    }

    /**
     * Returns a new vector with all components set to {@link Float#NEGATIVE_INFINITY}.
     *
     * @return A new vector with all components set to {@link Float#NEGATIVE_INFINITY}
     */
    public static float2 negInf() {
        return new float2(Float.NEGATIVE_INFINITY);
    }

    /**
     * Returns a new vector with all components set to {@link Float#NaN}.
     *
     * @return A new vector with all components set to {@link Float#NaN}
     */
    public static float2 NaN() {
        return new float2(Float.NaN);
    }

    /**
     * Creates a new vector from the given array.
     *
     * @param arr The array to read the components from. Must be at least of length 2
     * @return A new vector with the components from the array
     */
    public static float2 fromArray(float[] arr) {
        if(Arguments.checkNull(arr, "arr").length < 2)
            throw new IllegalArgumentException("arr.length < 2");
        return new float2(arr[0], arr[1]);
    }

    /**
     * Creates a new vector from the given array.
     *
     * @param arr The array to read the components from. Must have at least 2 more elements
     *            starting from <code>offset</code>
     * @param offset The index of the x component in the array
     * @return A new vector with the components from the array
     */
    public static float2 fromArray(float[] arr, int offset) {
        if(Arguments.checkNull(arr, "arr").length < offset + 2)
            throw new IllegalArgumentException("Range out of array bounds");
        return new float2(arr[0], arr[1]);
    }


    /**
     * Returns the component-wise minimum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise minimum
     */
    public static float2 min(constFloat2 a, constFloat2 b) {
        return new float2(
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
    public static float2 max(constFloat2 a, constFloat2 b) {
        return new float2(
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
    public static float2 between(constFloat2 a, constFloat2 b) {
        return new float2(b.x - a.x, b.y - a.y);
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    public static float2 between(constFloat2 a, constInt2 b) {
        return new float2(b.x - a.x, b.y - a.y);
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    public static float2 between(constInt2 a, constFloat2 b) {
        return new float2(b.x - a.x, b.y - a.y);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    public static float2 average(constFloat2 a, constFloat2 b) {
        return new float2((b.x + a.x) / 2, (b.y + a.y) / 2);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    public static float2 average(constFloat2 a, constInt2 b) {
        return new float2((b.x + a.x) / 2, (b.y + a.y) / 2);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    public static float2 average(constInt2 a, constFloat2 b) {
        return new float2((b.x + a.x) / 2, (b.y + a.y) / 2);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    public static float2 average(constInt2 a, constInt2 b) {
        return new float2((b.x + a.x) / 2f, (b.y + a.y) / 2f);
    }

    /**
     * Returns a new vector with the given (signed) angle to the x-axis and a length of 1.
     *
     * @param angle The angle for the vector, in degrees
     * @return A new normed vector with that angle
     */
    public static float2 angled(float angle) {
        return new float2(Mathf.cos(angle), Mathf.sin(angle));
    }

    /**
     * Returns a new vector with the given (signed) angle to the x-axis and the given length.
     *
     * @param angle The angle for the vector, in degrees
     * @param length The length for the vector
     * @return A new vector with that angle and length
     */
    public static float2 angled(float angle, float length) {
        return new float2(Mathf.cos(angle) * length, Mathf.sin(angle) * length);
    }
}
