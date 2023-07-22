package com.github.rccookie.math;

import java.util.Arrays;

import com.github.rccookie.json.JsonDeserialization;

import org.jetbrains.annotations.Range;

/**
 * A mutable generic-size float vector. Vectors must have at least one component.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public final class floatN extends constFloatN {

    static {
        JsonDeserialization.register(floatN.class, json -> new floatN(json.as(float[].class), true));
    }

    floatN(int size) {
        super(size);
    }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param size The number of components in the vector
     * @param value The value for all components
     */
    public floatN(int size, float value) {
        super(size, value);
    }

    /**
     * Creates a new vector.
     *
     * @param components The component values for the vector
     */
    public floatN(float[] components) {
        super(components);
    }

    /**
     * Creates a new vector by reading the components from the given array.
     *
     * @param arr The array to read the component values from
     * @param offset The index of the first component
     * @param size The number of components for the vector
     */
    public floatN(float[] arr, int offset, int size) {
        super(arr, offset, size);
    }

    private floatN(float[] componentsRef, boolean ignored) {
        super(componentsRef, ignored);
    }


    /**
     * Sets this vector to the given values.
     *
     * @param components The array containing the components to set, must have at least as many
     *                   entries as this vector has components
     * @return This vector
     */
    public floatN set(float... components) {
        return set(components, 0);
    }

    /**
     * Sets this vector to the components read from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the x component
     * @return This vector
     */
    public floatN set(float[] arr, int offset) {
        System.arraycopy(components, offset, this.components, 0, this.components.length);
        return this;
    }

    /**
     * Sets this vector to the same value as the given vector.
     *
     * @param v The vector to set this vector to
     * @return This vector
     */
    public floatN set(constFloatN v) {
        return set(check(v).components);
    }

    /**
     * Sets this vector to the same value as the given vector.
     *
     * @param v The vector to set this vector to
     * @return This vector
     */
    public floatN set(constIntN v) {
        check(v);
        for(int i=0; i<components.length; i++)
            components[i] = v.components[i];
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
    public floatN setComponent(@Range(from = 0, to = Integer.MAX_VALUE) int index, float x) {
        components[index] = x;
        return this;
    }

    /**
     * Sets all components of this vector to 0.
     *
     * @return This vector
     */
    public floatN setZero() {
        Arrays.fill(components, 0);
        return this;
    }

    /**
     * Scales this vector so that it has the desired length, without changing its direction.
     *
     * @param l The length to set. Negative values will flip the resulting direction
     * @return This vector
     */
    public floatN setLength(float l) {
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
    public floatN clampLength(float min, float max) {
        float len2 = length2();
        if(len2 == 0)
            return setComponent(0, min);
        if(len2 < min)
            return scale(min / Mathf.sqrt(len2));
        if(len2 > max)
            return scale(max / Mathf.sqrt(len2));
        return this;
    }



    /**
     * Negates this vector component-wise.
     *
     * @return This vector
     */
    public floatN neg() {
        for(int i=0; i<components.length; i++)
            components[i] = -components[i];
        return this;
    }

    /**
     * Inverts this vector component-wise.
     *
     * @return This vector
     */
    public floatN invert() {
        for(int i=0; i<components.length; i++)
            components[i] = 1 / components[i];
        return this;
    }

    /**
     * Sets this vector to its component-wise absolute value.
     *
     * @return This vector
     */
    public floatN abs() {
        for(int i=0; i<components.length; i++)
            components[i] = Math.abs(components[i]);
        return this;
    }

    /**
     * Normalizes this vector, so that it has a length of 1 in the same direction.
     *
     * @return This vector
     */
    public floatN norm() {
        return div(length());
    }

    /**
     * Normalizes this vector, so that it has a length of 1 in the same direction.
     * If this vector is 0, it will be set to the x-axis unit vector.
     *
     * @return This vector
     */
    public floatN safeNorm() {
        float len = length();
        return len == 0 ? setComponent(0,1) : dived(len);
    }


    /**
     * Adds the given value onto all components of this vector.
     *
     * @param x The value to add to all components
     * @return This vector
     */
    public floatN add(float x) {
        for(int i=0; i<components.length; i++)
            components[i] += x;
        return this;
    }

    /**
     * Adds the given vector component-wise to this vector.
     *
     * @param v The vector to add to this vector
     * @return This vector
     */
    public floatN add(constFloatN v) {
        for(int i=0; i<components.length; i++)
            components[i] += v.components[i];
        return this;
    }

    /**
     * Adds the given vector component-wise to this vector.
     *
     * @param v The vector to add to this vector
     * @return This vector
     */
    public floatN add(constIntN v) {
        for(int i=0; i<components.length; i++)
            components[i] += v.components[i];
        return this;
    }

    /**
     * Subtracts the given value from all components of this vector.
     *
     * @param x The value to subtract from all components
     * @return This vector
     */
    public floatN sub(float x) {
        for(int i=0; i<components.length; i++)
            components[i] -= x;
        return this;
    }

    /**
     * Subtracts the given vector component-wise from this vector.
     *
     * @param v The vector to subtract from this vector
     * @return This vector
     */
    public floatN sub(constFloatN v) {
        for(int i=0; i<components.length; i++)
            components[i] -= v.components[i];
        return this;
    }

    /**
     * Subtracts the given vector component-wise from this vector.
     *
     * @param v The vector to subtract from this vector
     * @return This vector
     */
    public floatN sub(constIntN v) {
        for(int i=0; i<components.length; i++)
            components[i] -= v.components[i];
        return this;
    }

    /**
     * Multiplies all components of this vector by the given value.
     *
     * @param f The value to multiply all components with
     * @return This vector
     */
    public floatN scale(float f) {
        for(int i=0; i<components.length; i++)
            components[i] *= f;
        return this;
    }

    /**
     * Multiplies the given vector component-wise to this vector.
     *
     * @param v The vector to multiply with this vector
     * @return This vector
     */
    public floatN scale(constFloatN v) {
        for(int i=0; i<components.length; i++)
            components[i] *= v.components[i];
        return this;
    }

    /**
     * Multiplies the given vector component-wise to this vector.
     *
     * @param v The vector to multiply with this vector
     * @return This vector
     */
    public floatN scale(constIntN v) {
        for(int i=0; i<components.length; i++)
            components[i] *= v.components[i];
        return this;
    }

    /**
     * Divides all components of this vector by the given value.
     *
     * @param x The value to divide all components by
     * @return This vector
     */
    public floatN div(float x) {
        for(int i=0; i<components.length; i++)
            components[i] /= x;
        return this;
    }

    /**
     * Divides this vector component-wise by the given vector.
     *
     * @param v The vector to divide this vector by
     * @return This vector
     */
    public floatN div(constFloatN v) {
        for(int i=0; i<components.length; i++)
            components[i] /= v.components[i];
        return this;
    }

    /**
     * Divides this vector component-wise by the given vector.
     *
     * @param v The vector to divide this vector by
     * @return This vector
     */
    public floatN div(constIntN v) {
        for(int i=0; i<components.length; i++)
            components[i] /= v.components[i];
        return this;
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return This vector
     */
    public floatN lerp(constFloatN t, float a) {
        for(int i=0; i<components.length; i++)
            components[i] += (t.components[i] - components[i]) * a;
        return this;
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return This vector
     */
    public floatN lerp(constIntN t, float a) {
        for(int i=0; i<components.length; i++)
            components[i] += (t.components[i] - components[i]) * a;
        return this;
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return This vector
     */
    public floatN project(constFloatN onto) {
        return scale(dot(onto) / onto.length2());
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return This vector
     */
    public floatN project(constIntN onto) {
        return scale(dot(onto) / onto.length2());
    }

    /**
     * Clamps this vector component-wise within the given range.
     *
     * @param min The component-wise minimum value
     * @param max The component-wise maximum value
     * @return This vector
     */
    public floatN clamp(constFloatN min, constFloatN max) {
        for(int i=0; i<components.length; i++)
            components[i] = Mathf.clamp(components[i], min.components[i], max.components[i]);
        return this;
    }

    /**
     * Clamps this vector component-wise within the given range.
     *
     * @param min The component-wise minimum value
     * @param max The component-wise maximum value
     * @return This vector
     */
    public floatN clamp(constIntN min, constIntN max) {
        for(int i=0; i<components.length; i++)
            components[i] = Mathf.clamp(components[i], min.components[i], max.components[i]);
        return this;
    }



    /**
     * Returns a new vector with all components set to 0.
     *
     ** @param size The number of components in the vector
     ** @return A new vector with all components set to 0.
     */
    public static floatN zero(int size) {
        return new floatN(size);
    }

    /**
     * Returns a new vector with all components set to 1.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to 1.
     */
    public static floatN one(int size) {
        return new floatN(size, 1);
    }

    /**
     * Returns a new vector with all components set to 0.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to 0.
     */
    public static floatN minusOne(int size) {
        return new floatN(size, -1);
    }

    /**
     * Returns a new vector with all components set to {@link Float#POSITIVE_INFINITY}.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to {@link Float#POSITIVE_INFINITY}.
     */
    public static floatN inf(int size) {
        return new floatN(size, Float.POSITIVE_INFINITY);
    }

    /**
     * Returns a new vector with all components set to {@link Float#NEGATIVE_INFINITY}.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to {@link Float#NEGATIVE_INFINITY}.
     */
    public static floatN negInf(int size) {
        return new floatN(size, Float.NEGATIVE_INFINITY);
    }

    /**
     * Returns a new vector with all components set to {@link Float#NaN}.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to {@link Float#NaN}.
     */
    public static floatN NaN(int size) {
        return new floatN(size, Float.NaN);
    }

    /**
     * Returns a new vector with the given components. The given array will be used as
     * component data; modifying it will also modify the vector.
     *
     * @param componentsRef The vector to use as component data
     * @return A new vector with the given components
     */
    public static floatN ref(float... componentsRef) {
        if(componentsRef.length == 0) throw new IllegalVectorSizeException(0);
        return new floatN(componentsRef, true);
    }


    /**
     * Returns the component-wise minimum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise minimum
     */
    public static floatN min(constFloatN a, constFloatN b) {
        a.check(b);
        floatN min = new floatN(a.components);
        for(int i=0; i<a.components.length; i++)
            min.components[i] = Math.min(a.components[i], b.components[i]);
        return min;
    }

    /**
     * Returns the component-wise maximum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise maximum
     */
    public static floatN max(constFloatN a, constFloatN b) {
        a.check(b);
        floatN max = new floatN(a.components);
        for(int i=0; i<a.components.length; i++)
            max.components[i] = Math.max(a.components[i], b.components[i]);
        return max;
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    public static floatN between(constFloatN a, constFloatN b) {
        return b.subed(a);
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    public static floatN between(constFloatN a, constIntN b) {
        return b.subed(a);
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    public static floatN between(constIntN a, constFloatN b) {
        return b.subed(a);
    }
}
