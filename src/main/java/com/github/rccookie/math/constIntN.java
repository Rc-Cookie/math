package com.github.rccookie.math;

import java.util.Arrays;

import com.github.rccookie.json.Json;
import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.json.JsonSerializable;
import com.github.rccookie.util.Cloneable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import static java.lang.Math.abs;

/**
 * A 4D float vector with read-only access.
 */
@SuppressWarnings({"DuplicatedCode"})
public class constIntN implements Cloneable<intN>, JsonSerializable {

    static {
        JsonDeserialization.register(constIntN.class, json -> new constIntN(json.as(int[].class)));
    }


    /**
     * The components of this vector.
     */
    protected final int[] components;


    /**
     * Creates a vector with default value components.
     *
     * @param size The number of components in the vector
     */
    protected constIntN(int size) {
        if(size <= 0) throw new IllegalVectorSizeException(size);
        components = new int[size];
    }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param size The number of components in the vector
     * @param value The value for all components
     */
    public constIntN(int size, int value) {
        if(size <= 0) throw new IllegalVectorSizeException(size);
        Arrays.fill(components = new int[size], value);
    }

    /**
     * Creates a new vector with the given components.
     *
     * @param components The component values for the vector. No reference is kept to this array
     */
    public constIntN(int[] components) {
        if(components.length == 0) throw new IllegalVectorSizeException(0);
        this.components = components.clone();
    }

    /**
     * Creates a new vector with the given components.
     *
     * @param arr The array to read the components from
     * @param offset The index of the first component
     * @param size The number of components for the vector
     */
    public constIntN(int[] arr, int offset, int size) {
        if(size <= 0) throw new IllegalVectorSizeException(size);
        components = new int[size];
        System.arraycopy(arr, offset, components, 0, size);
    }

    /**
     * Creates a new vector, using the given array as component data.
     *
     * @param componentsRef The array instance to use for storing the components
     */
    protected constIntN(int[] componentsRef, boolean ignored) {
        components = componentsRef;
    }


    /**
     * Returns a string representation of this vector, which is identical to the
     * result of <code>Arrays.toString(v.toArray())</code> for a vector <code>v</code>.
     *
     * @return A string representation of this vector.
     */
    @Override
    public String toString() {
        return Arrays.toString(components);
    }

    /**
     * Returns whether this vector is numerically equal to the given object. That is,
     * if the given object is also a vector of the same size, and is component-wise
     * equal. It does <b>not</b> matter whether one of the vectors is read-only and
     * the other one is not.
     *
     * @param obj The object to test for equality
     * @return Whether this vector is equal to the given object
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof constIntN)
            return equals((constIntN) obj);
        if(obj instanceof constInt2)
            return equals((constInt2) obj);
        if(obj instanceof constInt3)
            return equals((constInt3) obj);
        if(obj instanceof constInt4)
            return equals((constInt4) obj);
        return false;
    }

    /**
     * Returns whether this vector is component-wise equal to the given vector. It does
     * <b>not</b> matter whether one of the vectors is read-only and the other one is not.
     *
     * @param v The vector to test for equality
     * @return Whether this vector is equal to the given vector
     */
    public final boolean equals(constIntN v) {
        return Arrays.equals(components, v.components);
    }

    /**
     * Returns whether this vector is component-wise equal to the given vector. It does
     * <b>not</b> matter whether one of the vectors is read-only and the other one is not.
     *
     * @param v The vector to test for equality
     * @return Whether this vector is equal to the given vector
     */
    public final boolean equals(constInt2 v) {
        return components.length == 2 && components[0] == v.x && components[1] == v.y;
    }

    /**
     * Returns whether this vector is component-wise equal to the given vector. It does
     * <b>not</b> matter whether one of the vectors is read-only and the other one is not.
     *
     * @param v The vector to test for equality
     * @return Whether this vector is equal to the given vector
     */
    public final boolean equals(constInt3 v) {
        return components.length == 3 && components[0] == v.x && components[1] == v.y && components[2] == v.z;
    }

    /**
     * Returns whether this vector is component-wise equal to the given vector. It does
     * <b>not</b> matter whether one of the vectors is read-only and the other one is not.
     *
     * @param v The vector to test for equality
     * @return Whether this vector is equal to the given vector
     */
    public final boolean equals(constInt4 v) {
        return components.length == 4 && components[0] == v.x && components[1] == v.y && components[2] == v.z && components[3] == v.w;
    }

    /**
     * Returns a hash code for this vector.
     *
     * @return A hash code for this vector
     */
    @Override
    public final int hashCode() {
        return Arrays.hashCode(components);
    }

    /**
     * Returns a copy of this vector with identical component values.
     *
     * @return A copy of this vector
     */
    @Override
    public final @NotNull intN clone() {
        return new intN(components);
    }

    /**
     * Returns a json representation of this vector.
     *
     * @return This vector encoded as json
     */
    @Override
    public Object toJson() {
        return Json.extractJson(components);
    }


    /**
     * Returns the number of components in this vector.
     *
     * @return The size of the vector
     */
    public final int size() {
        return components.length;
    }

    /**
     * Returns the component at the given index of this vector, where 0 is the x component.
     *
     * @param index The index of the component to retrieve
     * @return The value of the component at that index
     */
    public final float component(@Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return components[index];
    }

    /**
     * Casts this vector component-wise to a float vector.
     *
     * @return An int vector with each element cast to a float
     */
    public final floatN toF() {
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i];
        return floatN.ref(c);
    }

    /**
     * Casts this vector component-wise to a float vector and returns the
     * result as a {@link constFloatN} (not {@link floatN}) instance.
     *
     * @return An immutable float vector with each component cast to a float
     */
    @Contract(pure = true, value = "->new")
    public final constFloatN toConstF() {
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i];
        return constFloatN.ref(c);
    }

    /**
     * Returns a new vector with the given size based on this one, cutting off additional
     * components or padding with 0s.
     *
     * @param size The size for the new vector
     * @return A new vector with the given size
     */
    public final intN toSize(int size) {
        if(components.length == size) return clone();
        int[] c = new int[size];
        System.arraycopy(components, 0, c, 0, Math.min(components.length, size));
        return intN.ref(c);
    }

    /**
     * Returns a new 2D vector based on this one, cutting off additional components or
     * padding with 0s.
     *
     * @return A new 2D vector based on this vector
     */
    public final int2 to2() {
        return new int2(components[0], components.length != 1 ? components[1] : 0);
    }

    /**
     * Returns a new 3D vector based on this one, cutting off additional components or
     * padding with 0s.
     *
     * @return A new 3D vector based on this vector
     */
    public final int3 to3() {
        if(components.length == 1)
            return new int3(components[0], 0, 0);
        if(components.length == 2)
            return new int3(components[0], components[1], 0);
        return new int3(components[0], components[1], components[2]);
    }

    /**
     * Returns a new 4D vector based on this one, cutting off additional components or
     * padding with 0s.
     *
     * @return A new 4D vector based on this vector
     */
    public final int4 to4() {
        if(components.length == 1)
            return new int4(components[0], 0, 0, 0);
        if(components.length == 2)
            return new int4(components[0], components[1], 0, 0);
        if(components.length == 3)
            return new int4(components[0], components[1], components[2], 0);
        return new int4(components[0], components[1], components[2], components[3]);
    }

    /**
     * Returns an array containing the components of this vector.
     *
     * @return This vector as an array
     */
    public final int[] toArray() {
        return components.clone();
    }

    /**
     * Writes the components of this vector into the given array.
     *
     * @param arr The array to write into
     * @param offset The index where to write the x component
     * @return The supplied array
     */
    public final int[] toArray(int[] arr, int offset) {
        System.arraycopy(components, 0, arr, offset, components.length);
        return arr;
    }

    /**
     * Returns a {@link constIntN} object with identical values (which is <b>not</b> a
     * {@link intN} instance) to this vector. If <code>getClass() == constIntN.class</code> is
     * already true, this vector will be returned itself.
     *
     * @return An unmodifiable vector with the specified values, possibly this itself
     */
    public final constIntN toConst() {
        return getClass() == constIntN.class ? this : new constIntN(components);
    }


    /**
     * Returns a copy of this vector with the specified component set to the given value.
     *
     * @param index The index of the component to set
     * @param value The value to set
     * @return A new vector with the given value as the specified component
     */
    public intN withComponent(int index, int value) {
        int[] c = components.clone();
        c[index] = value;
        return intN.ref(c);
    }


    /**
     * Returns whether all components of this vector are zero.
     *
     * @return Whether this vector is 0
     */
    public final boolean isZero() {
        for(int c : components)
            if(c != 0) return false;
        return true;
    }

    /**
     * Returns whether all components of this vector are approximately zero,
     * with the given allowed absolute difference.
     *
     * @param ep The permitted component-wise difference
     * @return Whether this vector is approximately 0
     */
    public final boolean isZero(float ep) {
        for(int c : components)
            if(abs(c) > ep) return false;
        return true;
    }


    /**
     * Returns the length of this vector.
     *
     * @return The length of this vector
     */
    public final float length() {
        int len2 = length2();
        return len2 == 1 ? 1 : Mathf.sqrt(len2);
    }

    /**
     * Returns the squared length of this vector.
     *
     * @return The squared length of this vector
     */
    public final int length2() {
        int l2 = 0;
        for(int c : components)
            l2 += c*c;
        return l2;
    }

    /**
     * Returns the dot product of this and the given vector.
     *
     * @param v The other vector
     * @return The dot product of the two vectors
     */
    public final int dot(constIntN v) {
        check(v);
        int dot = 0;
        for(int i=0; i<components.length; i++)
            dot += components[i] * v.components[i];
        return dot;
    }

    /**
     * Returns the dot product of this and the given vector.
     *
     * @param v The other vector
     * @return The dot product of the two vectors
     */
    public final float dot(constFloatN v) {
        check(v);
        float dot = 0;
        for(int i=0; i<components.length; i++)
            dot += components[i] * v.components[i];
        return dot;
    }

    /**
     * Returns the sum of the vector's components.
     *
     * @return The sum of the components of the vector
     */
    public final int sum() {
        int sum = 0;
        for(int c : components)
            sum += c;
        return sum;
    }

    /**
     * Returns the product of the vector's components.
     *
     * @return The product of the components of the vector
     */
    public final int product() {
        int product = 1;
        for(int c : components)
            product *= c;
        return product;
    }


    /**
     * Returns the distance between the point represented by this vector and
     * the point represented by the given vector.
     *
     * @param v The other point
     * @return The distance between this and the given vector
     */
    public final float dist(constIntN v) {
        int dist2 = dist2(v);
        return dist2 == 1 ? 1 : Mathf.sqrt(dist2);
    }

    /**
     * Returns the distance between the point represented by this vector and
     * the point represented by the given vector.
     *
     * @param v The other point
     * @return The distance between this and the given vector
     */
    public final float dist(constFloatN v) {
        float dist2 = dist2(v);
        return dist2 == 1 ? 1 : Mathf.sqrt(dist2);
    }

    /**
     * Returns the squared distance between the point represented by this vector
     * and the point represented by the given vector.
     *
     * @param v The other point
     * @return The squared distance between this and the given vector
     */
    public final int dist2(constIntN v) {
        check(v);
        int d2 = 0;
        for(int i=0; i<components.length; i++)
            d2 += (v.components[i] - components[i]) * (v.components[i] * components[i]);
        return d2;
    }

    /**
     * Returns the squared distance between the point represented by this vector
     * and the point represented by the given vector.
     *
     * @param v The other point
     * @return The squared distance between this and the given vector
     */
    public final float dist2(constFloatN v) {
        check(v);
        float d2 = 0;
        for(int i=0; i<components.length; i++)
            d2 += (v.components[i] - components[i]) * (v.components[i] * components[i]);
        return d2;
    }

    /**
     * Returns the angle of this vector to the x-axis.
     *
     * @return The angle to the x-axis, in degrees
     */
    public final float angle() {
        return Mathf.acos(components[0] / length());
    }

    /**
     * Returns the angle between this and the given vector.
     *
     * @param to The vector to compute the angle to
     * @return The angle between the vectors, in degrees
     */
    public final float angle(constIntN to) {
        return Mathf.acos(dot(to) / Mathf.sqrt(length2() * to.length2()));
    }

    /**
     * Returns the angle between this and the given vector.
     *
     * @param to The vector to compute the angle to
     * @return The angle between the vectors, in degrees
     */
    public final float angle(constFloatN to) {
        return Mathf.acos(dot(to) / Mathf.sqrt(length2() * to.length2()));
    }



    /**
     * Returns this vector component-wise negated.
     *
     * @return A new vector representing this vector negate
     */
    public final intN negated() {
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = -components[i];
        return intN.ref(c);
    }

    /**
     * Returns this vector component-wise inverted.
     *
     * @return A new vector representing this vector component-wise inverted
     */
    public final floatN inverse() {
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = 1f / components[i];
        return floatN.ref(c);
    }

    /**
     * Returns the component-wise absolute of this vector.
     *
     * @return A new vector representing the component-wise absolute value
     */
    public final intN absolute() {
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = abs(components[i]);
        return intN.ref(c);
    }

    /**
     * Returns this vector normalized (a vector with the same direction but length 1)
     *
     * @return A new vector representing this vector normalized.
     */
    public final floatN normed() {
        return dived(length());
    }

    /**
     * Returns this vector normalized, or the x-axis vector if this vector is 0.
     *
     * @return A new vector representing this vector normalized, or the x-axis
     */
    public final floatN safeNormed() {
        float len = length();
        if(len != 0)
            return dived(len);
        floatN res = floatN.zero(components.length);
        res.components[0] = 1;
        return res;
    }



    /**
     * Returns the result of adding the given value onto each component of this vector.
     *
     * @param x The value to add
     * @return A new vector representing the component-wise sum of this vector and the given scalar
     */
    public final intN added(int x) {
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] + x;
        return intN.ref(c);
    }

    /**
     * Returns the result of adding the given value onto each component of this vector.
     *
     * @param x The value to add
     * @return A new vector representing the component-wise sum of this vector and the given scalar
     */
    public final floatN added(float x) {
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] + x;
        return floatN.ref(c);
    }

    /**
     * Returns the component-wise sum of this and the given vector.
     *
     * @param v The vector to add
     * @return A new vector representing the sum of this and the given vector
     */
    public final intN added(constIntN v) {
        check(v);
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] + v.components[i];
        return intN.ref(c);
    }

    /**
     * Returns the component-wise sum of this and the given vector.
     *
     * @param v The vector to add
     * @return A new vector representing the sum of this and the given vector
     */
    public final floatN added(constFloatN v) {
        check(v);
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] + v.components[i];
        return floatN.ref(c);
    }

    /**
     * Returns the result of subtracting the given value from each component of this vector.
     *
     * @param x The value to subtract
     * @return A new vector representing the component-wise subtraction of this vector and the given scalar
     */
    public final intN subed(int x) {
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] - x;
        return intN.ref(c);
    }

    /**
     * Returns the result of subtracting the given value from each component of this vector.
     *
     * @param x The value to subtract
     * @return A new vector representing the component-wise subtraction of this vector and the given scalar
     */
    public final floatN subed(float x) {
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] - x;
        return floatN.ref(c);
    }

    /**
     * Returns the component-wise subtraction of this and the given vector.
     *
     * @param v The vector to subtract
     * @return A new vector representing the subtraction of this and the given vector
     */
    public final intN subed(constIntN v) {
        check(v);
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] + v.components[i];
        return intN.ref(c);
    }

    /**
     * Returns the component-wise subtraction of this and the given vector.
     *
     * @param v The vector to subtract
     * @return A new vector representing the subtraction of this and the given vector
     */
    public final floatN subed(constFloatN v) {
        check(v);
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] - v.components[i];
        return floatN.ref(c);
    }

    /**
     * Returns the result of multiplying the given value by each component of this vector.
     *
     * @param f The value to multiply by
     * @return A new vector representing the component-wise multiplication of this vector and the given scalar
     */
    public final intN scaled(int f) {
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] * f;
        return intN.ref(c);
    }

    /**
     * Returns the result of multiplying the given value by each component of this vector.
     *
     * @param f The value to multiply by
     * @return A new vector representing the component-wise multiplication of this vector and the given scalar
     */
    public final floatN scaled(float f) {
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] * f;
        return floatN.ref(c);
    }

    /**
     * Returns the component-wise product of this and the given vector.
     *
     * @param v The vector to multiply by
     * @return A new vector representing the product of this and the given vector
     */
    public final intN scaled(constIntN v) {
        check(v);
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] * v.components[i];
        return intN.ref(c);
    }

    /**
     * Returns the component-wise product of this and the given vector.
     *
     * @param v The vector to multiply by
     * @return A new vector representing the product of this and the given vector
     */
    public final floatN scaled(constFloatN v) {
        check(v);
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] * v.components[i];
        return floatN.ref(c);
    }

    /**
     * Returns the result of dividing each component of this vector by the given value.
     *
     * @param x The value to divide by
     * @return A new vector representing the component-wise division of this vector and the given scalar
     */
    public final intN dived(int x) {
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] / x;
        return intN.ref(c);
    }

    /**
     * Returns the result of dividing each component of this vector by the given value.
     *
     * @param x The value to divide by
     * @return A new vector representing the component-wise division of this vector and the given scalar
     */
    public final floatN dived(float x) {
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] / x;
        return floatN.ref(c);
    }

    /**
     * Returns the component-wise division of this and the given vector.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    public final intN dived(constIntN v) {
        check(v);
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] / v.components[i];
        return intN.ref(c);
    }

    /**
     * Returns the component-wise division of this and the given vector.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    public final floatN dived(constFloatN v) {
        check(v);
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] / v.components[i];
        return floatN.ref(c);
    }

    /**
     * Returns the component-wise division of this and the given vector, using
     * floating-point division.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    public final floatN divedF(constIntN v) {
        check(v);
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] / (float) v.components[i];
        return floatN.ref(c);
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return A new vector representing this vector lerped
     */
    public final floatN lerped(constIntN t, float a) {
        check(t);
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] + (t.components[i] - components[i]) * a;
        return floatN.ref(c);
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return A new vector representing this vector lerped
     */
    public final floatN lerped(constFloatN t, float a) {
        check(t);
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = components[i] + (t.components[i] - components[i]) * a;
        return floatN.ref(c);
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return A new vector representing this vector projected onto the given vector
     */
    public final floatN projected(constIntN onto) {
        return onto.scaled(dot(onto) / (float) onto.length2());
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return A new vector representing this vector projected onto the given vector
     */
    public final floatN projected(constFloatN onto) {
        return onto.scaled(dot(onto) / onto.length2());
    }

    /**
     * Clamps this vector component-wise into the given range.
     *
     * @param min The component-wise lower bound
     * @param max The component-wise upper bound
     * @return A new vector representing this vector clamped
     */
    public final intN clamped(constIntN min, constIntN max) {
        check(min);
        check(max);
        int[] c = new int[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = Mathf.clamp(components[i], min.components[i], max.components[i]);
        return intN.ref(c);
    }

    /**
     * Clamps this vector component-wise into the given range.
     *
     * @param min The component-wise lower bound
     * @param max The component-wise upper bound
     * @return A new vector representing this vector clamped
     */
    public final floatN clamped(constFloatN min, constFloatN max) {
        check(min);
        check(max);
        float[] c = new float[components.length];
        for(int i=0; i<c.length; i++)
            c[i] = Mathf.clamp(components[i], min.components[i], max.components[i]);
        return floatN.ref(c);
    }


    /**
     * Returns a new vector with the same direction as this vector, but with the given length.
     * If this vector is zero, the returned vector will be a multiple of the x-axis unit vector.
     *
     * @param l The length to set
     * @return A new vector pointing in the same direction as this one, but with the given length
     */
    public final floatN withLength(float l) {
        return safeNormed().scale(l);
    }

    /**
     * Returns a new vector with the same direction as this vector, but with this vector's
     * length clamped into the given range. If this vector is zero, the returned vector will be
     * a multiple of the x-axis unit vector.
     *
     * @param min The minimum length for the returned vector
     * @param max The maximum length for the returned vector
     * @return A new vector with this vector clamped to have a length in the given range
     */
    public final floatN withLengthClamped(float min, float max) {
        float len2 = length2();
        if(len2 == 0) {
            floatN res = floatN.zero(components.length);
            res.components[0] = 1;
            return res;
        }
        if(len2 < min)
            return scaled(min / Mathf.sqrt(len2));
        if(len2 > max)
            return scaled(max / Mathf.sqrt(len2));
        return toF();
    }


    /**
     * Throws an exception if the given vector has a different size than this vector.
     *
     * @param v The vector to test
     * @return <code>v</code>
     */
    protected final constIntN check(constIntN v) {
        if(components.length != v.components.length)
            throw new IncompatibleMatrixException(this, v);
        return v;
    }

    /**
     * Throws an exception if the given vector has a different size than this vector.
     *
     * @param v The vector to test
     * @return <code>v</code>
     */
    protected final constFloatN check(constFloatN v) {
        if(components.length != v.components.length)
            throw new IncompatibleMatrixException(this, v);
        return v;
    }





    /**
     * Returns a new vector with all components set to 0.
     *
     ** @param size The number of components in the vector
     ** @return A new vector with all components set to 0.
     */
    public static constIntN zero(int size) {
        return new constIntN(size);
    }

    /**
     * Returns a new vector with all components set to 1.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to 1.
     */
    public static constIntN one(int size) {
        return new constIntN(size, 1);
    }

    /**
     * Returns a new vector with all components set to 0.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to 0.
     */
    public static constIntN minusOne(int size) {
        return new constIntN(size, -1);
    }

    /**
     * Returns a new vector with all components set to {@link Integer#MIN_VALUE}.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to {@link Integer#MIN_VALUE}.
     */
    public static constIntN min(int size) {
        return new constIntN(size, Integer.MIN_VALUE);
    }

    /**
     * Returns a new vector with all components set to {@link Integer#MAX_VALUE}.
     *
     * @param size The number of components in the vector
     * @return A new vector with all components set to {@link Integer#MAX_VALUE}.
     */
    public static constIntN max(int size) {
        return new constIntN(size, Integer.MAX_VALUE);
    }

    /**
     * Returns a new vector with the given components. The given array will be used as
     * component data; modifying it will also modify the vector.
     *
     * @param componentsRef The vector to use as component data
     * @return A new vector with the given components
     */
    public static constIntN ref(int... componentsRef) {
        if(componentsRef.length == 0) throw new IllegalVectorSizeException(0);
        return new constIntN(componentsRef, true);
    }
}
