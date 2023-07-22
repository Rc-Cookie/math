package com.github.rccookie.math;

import java.util.Arrays;

import com.github.rccookie.json.JsonArray;
import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.json.JsonSerializable;
import com.github.rccookie.util.ArgumentOutOfRangeException;
import com.github.rccookie.util.Cloneable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import static java.lang.Math.abs;

/**
 * A 3D float vector with read-only access.
 */
@SuppressWarnings({"DuplicatedCode"})
public class constFloat3 implements Cloneable<float3>, JsonSerializable {

    static {
        JsonDeserialization.register(constFloat3.class, json -> new constFloat3(
                json.get(0).asFloat(),
                json.get(1).asFloat(),
                json.get(2).asFloat()
        ));
    }

    /**
     * A vector with all components set to 0.
     */
    public static final constFloat3 zero = new constFloat3(0);
    /**
     * A vector representing the x-axis unit vector.
     */
    public static final constFloat3 X = new constFloat3(1,0,0);
    /**
     * A vector representing the y-axis unit vector.
     */
    public static final constFloat3 Y = new constFloat3(0,1,0);
    /**
     * A vector representing the z-axis unit vector.
     */
    public static final constFloat3 Z = new constFloat3(0,0,1);
    /**
     * A vector with all components set to 1.
     */
    public static final constFloat3 one = new constFloat3(1);
    /**
     * A vector with all components set to 0.
     */
    public static final constFloat3 minusOne = new constFloat3(-1);
    /**
     * A vector with all components set to {@link Float#POSITIVE_INFINITY}.
     */
    public static final constFloat3 inf = new constFloat3(Float.POSITIVE_INFINITY);
    /**
     * A vector with all components set to {@link Float#NEGATIVE_INFINITY}.
     */
    public static final constFloat3 negInf = new constFloat3(Float.NEGATIVE_INFINITY);
    /**
     * A vector with all components set to {@link Float#NaN}.
     */
    public static final constFloat3 NaN = new constFloat3(Float.NaN);


    /**
     * The components of this vector.
     */
    protected float x,y,z;


    /**
     * Creates a vector with default value components.
     */
    protected constFloat3() { }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param xyz The value for each component
     */
    public constFloat3(float xyz) {
        x = y = z = xyz;
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param z The value for the z component
     */
    public constFloat3(constFloat2 xy, float z) {
        this.x = xy.x;
        this.y = xy.y;
        this.z = z;
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param z The value for the z component
     */
    public constFloat3(constInt2 xy, float z) {
        this.x = xy.x;
        this.y = xy.y;
        this.z = z;
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yz The values for the y and z component
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public constFloat3(float x, constFloat2 yz) {
        this.x = x;
        this.y = yz.x;
        this.z = yz.y;
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yz The values for the y and z component
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public constFloat3(float x, constInt2 yz) {
        this.x = x;
        this.y = yz.x;
        this.z = yz.y;
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param y The value for the y component
     * @param z The value for the z component
     */
    public constFloat3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * Returns a string representation of this vector, which is identical to the
     * result of <code>Arrays.toString(v.toArray())</code> for a vector <code>v</code>.
     *
     * @return A string representation of this vector.
     */
    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
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
        return (obj instanceof constFloat3 && equals((constFloat3) obj)) || (obj instanceof constFloatN && ((constFloatN) obj).equals(this));
    }

    /**
     * Returns whether this vector is component-wise equal to the given vector. It does
     * <b>not</b> matter whether one of the vectors is read-only and the other one is not.
     *
     * @param v The vector to test for equality
     * @return Whether this vector is equal to the given vector
     */
    public final boolean equals(constFloat3 v) {
        return x == v.x && y == v.y && z == v.z;
    }

    /**
     * Returns whether this vector is approximately component-wise equal to the given
     * vector, that is, the absolute component-wise difference is less or equal to the
     * given epsilon value. It does <b>not</b> matter whether one of the vectors is read-only
     * and the other one is not.
     *
     * @param v The vector to test for equality
     * @param ep The maximum permitted difference per component
     * @return Whether this vector is approximately equal to the given vector
     */
    public final boolean equals(constFloat3 v, float ep) {
        return abs(v.x - x) <= ep && abs(v.y - y) <= ep && abs(v.z - z) <= ep;
    }

    /**
     * Returns a hash code for this vector.
     *
     * @return A hash code for this vector
     */
    @Override
    public final int hashCode() {
        return Arrays.hashCode(toArray());
    }

    /**
     * Returns a copy of this vector with identical component values.
     *
     * @return A copy of this vector
     */
    @Override
    public final @NotNull float3 clone() {
        return new float3(x,y,z);
    }

    /**
     * Returns a json representation of this vector.
     *
     * @return This vector encoded as json
     */
    @Override
    public Object toJson() {
        return new JsonArray(x,y,z);
    }


    /**
     * Returns the x component of this vector.
     *
     * @return The x component
     */
    public final float x() {
        return x;
    }

    /**
     * Returns the y component of this vector.
     *
     * @return The y component
     */
    public final float y() {
        return y;
    }

    /**
     * Returns the z component of this vector.
     *
     * @return The z component
     */
    public final float z() {
        return z;
    }

    /**
     * Returns the component at the given index of this vector, where 0 is the x component.
     *
     * @param index The index of the component to retrieve
     * @return The value of the component at that index
     */
    public final float component(@Range(from = 0, to = 2) int index) {
        switch(index) {
            case 0: return x;
            case 1: return y;
            case 2: return z;
            default: throw new ArgumentOutOfRangeException(index, 0, 3);
        }
    }

    /**
     * Returns x and y component of this vector.
     *
     * @return x and y component of this vector as a float2
     */
    public final float2 xy() {
        return new float2(x,y);
    }

    /**
     * Returns a new vector where the y and z component are swapped.
     *
     * @return A new vector with y and z swapped
     */
    public final float3 xzy() {
        return new float3(x,z,y);
    }

    /**
     * Returns a new 4d vector with the w component set to 0.
     *
     * @return x, y and z component of this vector, with w set to 0
     */
    public final float4 xyz0() {
        return new float4(x,y,z,0);
    }

    /**
     * Casts this vector component-wise to an int vector.
     *
     * @return An int vector with each element cast (not rounded) to an int
     */
    public final int3 toI() {
        return new int3((int) x, (int) y, (int) z);
    }

    /**
     * Casts this vector component-wise to an int vector and returns an {@link constInt3}
     * (not {@link int3}) instance.
     *
     * @return A read-only int vector with each element cast (not rounded) to an int
     */
    @Contract(pure = true, value = "->new")
    public final constInt3 toConstI() {
        return new constInt3((int) x, (int) y, (int) z);
    }

    /**
     * Returns an array containing the components of this vector.
     *
     * @return This vector as an array
     */
    public final float[] toArray() {
        return new float[] { x,y,z };
    }

    /**
     * Writes the components of this vector into the given array.
     *
     * @param arr The array to write into
     * @param offset The index where to write the x component
     * @return The supplied array
     */
    public final float[] toArray(float[] arr, int offset) {
        arr[offset] = x;
        arr[offset+1] = y;
        arr[offset+2] = z;
        return arr;
    }

    /**
     * Returns a {@link constFloat3} object with identical values (which is <b>not</b> a
     * {@link float3} instance) to this vector. If <code>getClass() == constFloat3.class</code> is
     * already true, this vector will be returned itself.
     *
     * @return An unmodifiable vector with the specified values, possibly this itself
     */
    public final constFloat3 toConst() {
        return getClass() == constFloat3.class ? this : new constFloat3(x,y,z);
    }


    /**
     * Returns a copy of this vector with the x component set to the given value.
     *
     * @param x The x value to set
     * @return A new vector with the given value as x component
     */
    public final float3 withX(float x) {
        return new float3(x,y,z);
    }

    /**
     * Returns a copy of this vector with the y component set to the given value.
     *
     * @param y The y value to set
     * @return A new vector with the given value as y component
     */
    public final float3 withY(float y) {
        return new float3(x,y,z);
    }

    /**
     * Returns a copy of this vector with the z component set to the given value.
     *
     * @param z The z value to set
     * @return A new vector with the given value as z component
     */
    public final float3 withZ(float z) {
        return new float3(x,y,z);
    }


    /**
     * Returns whether all components of this vector are zero.
     *
     * @return Whether this vector is 0
     */
    public final boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    /**
     * Returns whether all components of this vector are approximately zero,
     * with the given allowed absolute difference.
     *
     * @param ep The permitted component-wise difference
     * @return Whether this vector is approximately 0
     */
    public final boolean isZero(float ep) {
        return abs(x) <= ep && abs(y) <= ep && abs(z) <= ep;
    }

    /**
     * Returns whether all components of this vector are finite.
     *
     * @return Whether this vector is finite
     */
    public final boolean isFinite() {
        return Float.isFinite(x) && Float.isFinite(y) && Float.isFinite(z);
    }

    /**
     * Returns whether any component of this vector is infinite.
     *
     * @return Whether this vector is infinite
     */
    public final boolean isInfinite() {
        return Float.isInfinite(x) || Float.isInfinite(y) || Float.isInfinite(z);
    }

    /**
     * Returns whether any component of this vector is {@link Float#NaN}.
     *
     * @return Whether this vector contains an NaN
     */
    public final boolean isNaN() {
        return Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z);
    }

    /**
     * Returns whether this vector is normalized, meaning its length is equal to 1.
     *
     * @return Whether this vector is normalized
     */
    public final boolean isNormed() {
        return x*x + y*y + z*z == 1;
    }

    /**
     * Returns whether this vector is approximately normalized, meaning its length is
     * at most the specified epsilon away from 1.
     *
     * @param ep The maximum allowed difference of the length of this vector from 1
     * @return Whether this vector is approximately normalized
     */
    public final boolean isNormed(float ep) {
        return abs(x*x + y*y + z*z - 1) <= ep*ep;
    }


    /**
     * Returns the length of this vector.
     *
     * @return The length of this vector
     */
    public final float length() {
        float len2 = length2();
        return len2 == 1 ? 1 : Mathf.sqrt(len2);
    }

    /**
     * Returns the squared length of this vector.
     *
     * @return The squared length of this vector
     */
    public final float length2() {
        return x*x + y*y + z*z;
    }

    /**
     * Returns the dot product of this and the given vector.
     *
     * @param v The other vector
     * @return The dot product of the two vectors
     */
    public final float dot(constFloat3 v) {
        return x*v.x + y*v.y + z*v.z;
    }

    /**
     * Returns the dot product of this and the given vector.
     *
     * @param v The other vector
     * @return The dot product of the two vectors
     */
    public final float dot(constInt3 v) {
        return x*v.x + y*v.y + z*v.z;
    }

    /**
     * Returns the sum of the vector's components.
     *
     * @return The sum of the components of the vector
     */
    public final float sum() {
        return x + y + z;
    }

    /**
     * Returns the product of the vector's components.
     *
     * @return The product of the components of the vector
     */
    public final float product() {
        return x * y * z;
    }

    /**
     * Returns the volume this vector spans. This is equivalent to
     * {@link #product()}.
     *
     * @return The volume defined by this vector
     */
    public final float volume() {
        return x * y * z;
    }

    /**
     * Returns whether this vector is component-wise less than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less than the respective components from the given vector
     */
    public final boolean less(constFloat3 v) {
        return x < v.x && y < v.y && z < v.z;
    }

    /**
     * Returns whether this vector is component-wise less than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less than the respective components from the given vector
     */
    public final boolean less(constInt3 v) {
        return x < v.x && y < v.y && z < v.z;
    }

    /**
     * Returns whether this vector is component-wise less than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less or equal to the respective components from the given vector
     */
    public final boolean leq(constFloat3 v) {
        return x <= v.x && y <= v.y && z <= v.z;
    }

    /**
     * Returns whether this vector is component-wise less than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less or equal to the respective components from the given vector
     */
    public final boolean leq(constInt3 v) {
        return x <= v.x && y <= v.y && z <= v.z;
    }

    /**
     * Returns whether this vector is component-wise greater than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater than the respective components from the given vector
     */
    public final boolean greater(constFloat3 v) {
        return x > v.x && y > v.y && z > v.z;
    }

    /**
     * Returns whether this vector is component-wise greater than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater than the respective components from the given vector
     */
    public final boolean greater(constInt3 v) {
        return x > v.x && y > v.y && z > v.z;
    }

    /**
     * Returns whether this vector is component-wise greater than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater or equal to the respective components from the given vector
     */
    public final boolean geq(constInt3 v) {
        return x >= v.x && y >= v.y && z >= v.z;
    }

    /**
     * Returns whether this vector is component-wise greater than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater or equal to the respective components from the given vector
     */
    public final boolean geq(constFloat3 v) {
        return x >= v.x && y >= v.y && z >= v.z;
    }


    /**
     * Returns the distance between the point represented by this vector and
     * the point represented by the given vector.
     *
     * @param v The other point
     * @return The distance between this and the given vector
     */
    public final float dist(constFloat3 v) {
        float dist2 = dist2(v);
        return dist2 == 1 ? 1 : Mathf.sqrt(dist2);
    }

    /**
     * Returns the distance between the point represented by this vector and
     * the point represented by the given vector.
     *
     * @param v The other point
     * @return The distance between this and the given vector
     */
    public final float dist(constInt3 v) {
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
    public final float dist2(constFloat3 v) {
        return (v.x-x)*(v.x-x) + (v.y-y)*(v.y-y) + (v.z-z)*(v.z-z);
    }

    /**
     * Returns the squared distance between the point represented by this vector
     * and the point represented by the given vector.
     *
     * @param v The other point
     * @return The squared distance between this and the given vector
     */
    public final float dist2(constInt3 v) {
        return (v.x-x)*(v.x-x) + (v.y-y)*(v.y-y) + (v.z-z)*(v.z-z);
    }

    /**
     * Returns the angle of this vector to the x-axis.
     *
     * @return The angle to the x-axis, in degrees
     */
    public final float angle() {
        return Mathf.acos(x / length());
    }

    /**
     * Returns the angle between this and the given vector.
     *
     * @param to The vector to compute the angle to
     * @return The angle between the vectors, in degrees
     */
    public final float angle(constFloat3 to) {
        return Mathf.acos(dot(to) / Mathf.sqrt(length2() * to.length2()));
    }

    /**
     * Returns the angle between this and the given vector.
     *
     * @param to The vector to compute the angle to
     * @return The angle between the vectors, in degrees
     */
    public final float angle(constInt3 to) {
        return Mathf.acos(dot(to) / Mathf.sqrt(length2() * to.length2()));
    }



    /**
     * Returns this vector component-wise negated.
     *
     * @return A new vector representing this vector negate
     */
    public final float3 negated() {
        return new float3(-x, -y, -z);
    }

    /**
     * Returns this vector component-wise inverted.
     *
     * @return A new vector representing this vector component-wise inverted
     */
    public final float3 inverse() {
        return new float3(1/x, 1/y, 1/z);
    }

    /**
     * Returns the component-wise absolute of this vector.
     *
     * @return A new vector representing the component-wise absolute value
     */
    public final float3 absolute() {
        return new float3(abs(x), abs(y), abs(z));
    }

    /**
     * Returns this vector normalized (a vector with the same direction but length 1)
     *
     * @return A new vector representing this vector normalized.
     */
    public final float3 normed() {
        return dived(length());
    }

    /**
     * Returns this vector normalized, or the x-axis vector if this vector is 0.
     *
     * @return A new vector representing this vector normalized, or the x-axis
     */
    public final float3 safeNormed() {
        float len = length();
        return len == 0 ? float3.X() : dived(len);
    }



    /**
     * Returns the result of adding the given value onto each component of this vector.
     *
     * @param x The value to add
     * @return A new vector representing the component-wise sum of this vector and the given scalar
     */
    public final float3 added(float x) {
        return new float3(this.x + x, y+x, z+x);
    }

    /**
     * Returns the component-wise sum of this vector and the given components.
     *
     * @param x The value to add to the x component
     * @param y The value to add to the y component
     * @param z The value to add to the z component
     * @return A new vector representing the sum of this vector and the given components
     */
    public final float3 added(float x, float y, float z) {
        return new float3(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Returns the component-wise sum of this and the given vector.
     *
     * @param v The vector to add
     * @return A new vector representing the sum of this and the given vector
     */
    public final float3 added(constFloat3 v) {
        return new float3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Returns the component-wise sum of this and the given vector.
     *
     * @param v The vector to add
     * @return A new vector representing the sum of this and the given vector
     */
    public final float3 added(constInt3 v) {
        return new float3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Returns the result of subtracting the given value from each component of this vector.
     *
     * @param x The value to subtract
     * @return A new vector representing the component-wise subtraction of this vector and the given scalar
     */
    public final float3 subed(float x) {
        return new float3(this.x - x, y-x, z-x);
    }

    /**
     * Returns the component-wise subtraction of this vector and the given components.
     *
     * @param x The value to subtract from the x component
     * @param y The value to subtract from the y component
     * @param z The value to subtract from the z component
     * @return A new vector representing the subtraction of this vector and the given components
     */
    public final float3 subed(float x, float y, float z) {
        return new float3(this.x - x, this.y - y, this.z - z);
    }

    /**
     * Returns the component-wise subtraction of this and the given vector.
     *
     * @param v The vector to subtract
     * @return A new vector representing the subtraction of this and the given vector
     */
    public final float3 subed(constFloat3 v) {
        return new float3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Returns the component-wise subtraction of this and the given vector.
     *
     * @param v The vector to subtract
     * @return A new vector representing the subtraction of this and the given vector
     */
    public final float3 subed(constInt3 v) {
        return new float3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Returns the result of multiplying the given value by each component of this vector.
     *
     * @param f The value to multiply by
     * @return A new vector representing the component-wise multiplication of this vector and the given scalar
     */
    public final float3 scaled(float f) {
        return new float3(x*f, y*f, z*f);
    }

    /**
     * Returns the component-wise product of this vector and the given components.
     *
     * @param x The value to multiply the x component by
     * @param y The value to multiply the y component by
     * @param z The value to multiply the z component by
     * @return A new vector representing the product of this vector and the given components
     */
    public final float3 scaled(float x, float y, float z) {
        return new float3(this.x * x, this.y * y, this.z * z);
    }

    /**
     * Returns the component-wise product of this and the given vector.
     *
     * @param v The vector to multiply by
     * @return A new vector representing the product of this and the given vector
     */
    public final float3 scaled(constFloat3 v) {
        return new float3(x * v.x, y * v.y, z * v.z);
    }

    /**
     * Returns the component-wise product of this and the given vector.
     *
     * @param v The vector to multiply by
     * @return A new vector representing the product of this and the given vector
     */
    public final float3 scaled(constInt3 v) {
        return new float3(x * v.x, y * v.y, z * v.z);
    }

    /**
     * Returns the result of dividing each component of this vector by the given value.
     *
     * @param x The value to divide by
     * @return A new vector representing the component-wise division of this vector and the given scalar
     */
    public final float3 dived(float x) {
        return new float3(this.x / x, y/x, z/x);
    }

    /**
     * Returns the component-wise division of this vector and the given components.
     *
     * @param x The value to divide the x component by
     * @param y The value to divide the y component by
     * @param z The value to divide the z component by
     * @return A new vector representing the division of this vector and the given components
     */
    public final float3 dived(float x, float y, float z) {
        return new float3(this.x / x, this.y / y, this.z / z);
    }

    /**
     * Returns the component-wise division of this and the given vector.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    public final float3 dived(constFloat3 v) {
        return new float3(x / v.x, y / v.y, z / v.z);
    }

    /**
     * Returns the component-wise division of this and the given vector.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    public final float3 dived(constInt3 v) {
        return new float3(x / v.x, y / v.y, z / v.z);
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return A new vector representing this vector lerped
     */
    public final float3 lerped(constFloat3 t, float a) {
        return new float3(
                x + (t.x - x) * a,
                y + (t.y - y) * a,
                z + (t.z - z) * a
        );
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return A new vector representing this vector lerped
     */
    public final float3 lerped(constInt3 t, float a) {
        return new float3(
                x + (t.x - x) * a,
                y + (t.y - y) * a,
                z + (t.z - z) * a
        );
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return A new vector representing this vector projected onto the given vector
     */
    public final float3 projected(constFloat3 onto) {
        float f = (x*onto.x + y*onto.y + z*onto.z) / (onto.x*onto.x + onto.y*onto.y + onto.z*onto.z);
        return new float3(onto.x * f, onto.y * f, onto.z * f);
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return A new vector representing this vector projected onto the given vector
     */
    public final float3 projected(constInt3 onto) {
        float f = (x*onto.x + y*onto.y + z*onto.z) / (onto.x*onto.x + onto.y*onto.y + onto.z*onto.z);
        return new float3(onto.x * f, onto.y * f, onto.z * f);
    }

    /**
     * Returns the cross product of this and the given vector.
     *
     * @param v The vector to compute the cross product with
     * @return A new vector representing the cross product of this and the given vector
     */
    public final float3 cross(constFloat3 v) {
        return new float3(
                y*v.z - z*v.y,
                z*v.x - x*v.z,
                x*v.y - y*v.x
        );
    }

    /**
     * Returns the cross product of this and the given vector.
     *
     * @param v The vector to compute the cross product with
     * @return A new vector representing the cross product of this and the given vector
     */
    public final float3 cross(constInt3 v) {
        return new float3(
                y*v.z - z*v.y,
                z*v.x - x*v.z,
                x*v.y - y*v.x
        );
    }

    /**
     * Clamps this vector component-wise into the given range.
     *
     * @param min The component-wise lower bound
     * @param max The component-wise upper bound
     * @return A new vector representing this vector clamped
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final float3 clamped(constFloat3 min, constFloat3 max) {
        return new float3(
                Mathf.clamp(x, min.x, max.x),
                Mathf.clamp(y, min.y, max.y),
                Mathf.clamp(z, min.z, max.z)
        );
    }

    /**
     * Clamps this vector component-wise into the given range.
     *
     * @param min The component-wise lower bound
     * @param max The component-wise upper bound
     * @return A new vector representing this vector clamped
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final float3 clamped(constInt3 min, constInt3 max) {
        return new float3(
                Mathf.clamp(x, min.x, max.x),
                Mathf.clamp(y, min.y, max.y),
                Mathf.clamp(z, min.z, max.z)
        );
    }

    /**
     * Clamps this vector component-wise within the given area.
     *
     * @param area The area to clamp this vector into
     * @return A new vector representing this vector clamped
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final float3 clamped(constBounds area) {
        return new float3(
                Mathf.clamp(x, area.min.x, area.max.x),
                Mathf.clamp(y, area.min.y, area.max.y),
                Mathf.clamp(z, area.min.z, area.max.z)
        );
    }

    /**
     * Clamps this vector component-wise within the given area.
     *
     * @param area The area to clamp this vector into
     * @return A new vector representing this vector clamped
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final float3 clamped(constIBounds area) {
        return new float3(
                Mathf.clamp(x, area.min.x, area.max.x),
                Mathf.clamp(y, area.min.y, area.max.y),
                Mathf.clamp(z, area.min.z, area.max.z)
        );
    }


    /**
     * Returns a new vector with the same direction as this vector, but with the given length.
     * If this vector is zero, the returned vector will be a multiple of the x-axis unit vector.
     *
     * @param l The length to set
     * @return A new vector pointing in the same direction as this one, but with the given length
     */
    public final float3 withLength(float l) {
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
    public final float3 withLengthClamped(float min, float max) {
        float len2 = length2();
        if(len2 == 0)
            return new float3(min, 0, 0);
        if(len2 < min)
            return scaled(min / Mathf.sqrt(len2));
        if(len2 > max)
            return scaled(max / Mathf.sqrt(len2));
        return new float3(x,y,z);
    }
}
