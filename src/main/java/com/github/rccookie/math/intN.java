package com.github.rccookie.math;

import java.util.Arrays;

import com.github.rccookie.json.JsonDeserialization;

import org.jetbrains.annotations.Range;

/**
 * A mutable generic-size float vector. Vectors must have at least one component.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public final class intN extends constIntN {

    static {
        JsonDeserialization.register(intN.class, json -> new intN(json.as(int[].class)));
    }

    private intN(int size) {
        super(size);
    }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param size The number of components in the vector
     * @param value The value for all components
     */
    public intN(int size, int value) {
        super(size, value);
    }

    /**
     * Creates a new vector.
     *
     * @param components The component values for the vector
     */
    public intN(int[] components) {
        super(components);
    }

    /**
     * Creates a new vector by reading the components from the given array.
     *
     * @param arr The array to read the component values from
     * @param offset The index of the first component
     * @param size The number of components for the vector
     */
    public intN(int[] arr, int offset, int size) {
        super(arr, offset, size);
    }

    private intN(int[] componentsRef, boolean ignored) {
        super(componentsRef, ignored);
    }


    /**
     * Sets this vector to the given values.
     *
     * @param components The array containing the components to set, must have at least as many
     *                   entries as this vector has components
     * @return This vector
     */
    public intN set(int... components) {
        return set(components, 0);
    }

    /**
     * Sets this vector to the components read from the given array.
     *
     * @param arr The array to read the components from
     * @param offset The index of the x component
     * @return This vector
     */
    public intN set(int[] arr, int offset) {
        System.arraycopy(components, offset, this.components, 0, this.components.length);
        return this;
    }

    /**
     * Sets this vector to the same value as the given vector.
     *
     * @param v The vector to set this vector to
     * @return This vector
     */
    public intN set(constIntN v) {
        check(v);
        System.arraycopy(v.components, 0, components, 0, components.length);
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
    public intN setComponent(@Range(from = 0, to = Integer.MAX_VALUE) int index, int x) {
        components[index] = x;
        return this;
    }

    /**
     * Sets all components of this vector to 0.
     *
     * @return This vector
     */
    public intN setZero() {
        Arrays.fill(components, 0);
        return this;
    }



    /**
     * Negates this vector component-wise.
     *
     * @return This vector
     */
    public intN neg() {
        for(int i=0; i<components.length; i++)
            components[i] = -components[i];
        return this;
    }

    /**
     * Sets this vector to its component-wise absolute value.
     *
     * @return This vector
     */
    public intN abs() {
        for(int i=0; i<components.length; i++)
            components[i] = Math.abs(components[i]);
        return this;
    }


    /**
     * Adds the given value onto all components of this vector.
     *
     * @param x The value to add to all components
     * @return This vector
     */
    public intN add(int x) {
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
    public intN add(constIntN v) {
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
    public intN sub(int x) {
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
    public intN sub(constIntN v) {
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
    public intN scale(int f) {
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
    public intN scale(constIntN v) {
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
    public intN div(int x) {
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
    public intN div(constIntN v) {
        for(int i=0; i<components.length; i++)
            components[i] /= v.components[i];
        return this;
    }

    /**
     * Clamps this vector component-wise within the given range.
     *
     * @param min The component-wise minimum value
     * @param max The component-wise maximum value
     * @return This vector
     */
    public intN clamp(constIntN min, constIntN max) {
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
    public static intN zero(int size) {
        return new intN(size);
    }

    /**
     * Returns a new vector with all components set to 1.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to 1.
     */
    public static intN one(int size) {
        return new intN(size, 1);
    }

    /**
     * Returns a new vector with all components set to 0.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to 0.
     */
    public static intN minusOne(int size) {
        return new intN(size, -1);
    }

    /**
     * Returns a new vector with all components set to {@link Integer#MIN_VALUE}.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to {@link Integer#MIN_VALUE}.
     */
    public static intN min(int size) {
        return new intN(size, Integer.MIN_VALUE);
    }

    /**
     * Returns a new vector with all components set to {@link Integer#MAX_VALUE}.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to {@link Integer#MAX_VALUE}.
     */
    public static intN max(int size) {
        return new intN(size, Integer.MAX_VALUE);
    }

    /**
     * Returns a new vector with the given components. The given array will be used as
     * component data; modifying it will also modify the vector.
     *
     * @param componentsRef The vector to use as component data
     * @return A new vector with the given components
     */
    public static intN ref(int... componentsRef) {
        if(componentsRef.length == 0) throw new IllegalVectorSizeException(0);
        return new intN(componentsRef, true);
    }


    /**
     * Returns the component-wise minimum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise minimum
     */
    public static intN min(constIntN a, constIntN b) {
        a.check(b);
        intN min = new intN(a.components);
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
    public static intN max(constIntN a, constIntN b) {
        a.check(b);
        intN max = new intN(a.components);
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
    public static intN between(constIntN a, constIntN b) {
        return b.subed(a);
    }
}
