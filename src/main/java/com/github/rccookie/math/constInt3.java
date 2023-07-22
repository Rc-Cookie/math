package com.github.rccookie.math;

import java.util.Arrays;

import com.github.rccookie.json.JsonArray;
import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.json.JsonObject;
import com.github.rccookie.json.JsonSerializable;
import com.github.rccookie.util.ArgumentOutOfRangeException;
import com.github.rccookie.util.Cloneable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import static java.lang.Math.abs;

/**
 * A 3D int vector with read-only access.
 */
@SuppressWarnings({"DuplicatedCode"})
public class constInt3 implements Cloneable<int3>, JsonSerializable {

    static {
        JsonDeserialization.register(constInt3.class, json -> json.isArray() ? new constInt3(
                json.get(0).asInt(),
                json.get(1).asInt(),
                json.get(2).asInt()
        ) : new constInt3(
                json.get("x").asInt(),
                json.get("y").asInt(),
                json.get("z").asInt()
        ));
    }

    /**
     * A vector with all components set to 0.
     */
    public static final constInt3 zero = new constInt3(0);
    /**
     * A vector representing the x-axis unit vector.
     */
    public static final constInt3 X = new constInt3(1,0,0);
    /**
     * A vector representing the y-axis unit vector.
     */
    public static final constInt3 Y = new constInt3(0,1,0);
    /**
     * A vector representing the z-axis unit vector.
     */
    public static final constInt3 Z = new constInt3(0,0,1);
    /**
     * A vector with all components set to 1.
     */
    public static final constInt3 one = new constInt3(1);
    /**
     * A vector with all components set to 0.
     */
    public static final constInt3 minusOne = new constInt3(-1);
    /**
     * A vector with all components set to {@link Integer#MIN_VALUE}.
     */
    public static final constInt3 min = new constInt3(Integer.MIN_VALUE);
    /**
     * A vector with all components set to {@link Integer#MAX_VALUE}.
     */
    public static final constInt3 max = new constInt3(Integer.MAX_VALUE);


    /**
     * The components of this vector.
     */
    protected int x,y,z;


    /**
     * Creates a vector with default value components.
     */
    protected constInt3() { }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param xyz The value for each component
     */
    public constInt3(int xyz) {
        x = y = z = xyz;
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param z The value for the z component
     */
    public constInt3(constInt2 xy, int z) {
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
    public constInt3(int x, constInt2 yz) {
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
    public constInt3(int x, int y, int z) {
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
        return obj instanceof constInt3 && equals((constInt3) obj);
    }

    /**
     * Returns whether this vector is component-wise equal to the given vector. It does
     * <b>not</b> matter whether one of the vectors is read-only and the other one is not.
     *
     * @param v The vector to test for equality
     * @return Whether this vector is equal to the given vector
     */
    public final boolean equals(constInt3 v) {
        return x == v.x && y == v.y && z == v.z;
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
    public final @NotNull int3 clone() {
        return new int3(x,y,z);
    }

    /**
     * Returns a json representation of this vector.
     *
     * @return This vector encoded as json
     */
    @Override
    @Contract(pure = true)
    public Object toJson() {
        return new JsonObject("x", x, "y", y, "z", z);
    }

    /**
     * Returns a json array representation of this vector.
     *
     * @return This vector encoded as json array
     */
    public JsonArray toJsonArray() {
        return new JsonArray(x, y, z);
    }


    /**
     * Returns the x component of this vector.
     *
     * @return The x component
     */
    public final int x() {
        return x;
    }

    /**
     * Returns the y component of this vector.
     *
     * @return The y component
     */
    public final int y() {
        return y;
    }

    /**
     * Returns the z component of this vector.
     *
     * @return The z component
     */
    public final int z() {
        return z;
    }

    /**
     * Returns the component at the given index of this vector, where 0 is the x component.
     *
     * @param index The index of the component to retrieve
     * @return The value of the component at that index
     */
    public final int component(@Range(from = 0, to = 2) int index) {
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
    public final int2 xy() {
        return new int2(x,y);
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
    public final int4 xyz0() {
        return new int4(x,y,z,0);
    }

    /**
     * Casts this vector component-wise to a float vector.
     *
     * @return An int vector with each element cast to a float
     */
    @Contract(pure = true, value = "->new")
    public final float3 toF() {
        return new float3(x,y,z);
    }

    /**
     * Casts this vector component-wise to a float vector and returns the
     * result as a {@link constFloat3} (not {@link float3}) instance.
     *
     * @return An immutable float vector with each component cast to a float
     */
    @Contract(pure = true, value = "->new")
    public final constFloat3 toConstF() {
        return new constFloat3(x,y,z);
    }

    /**
     * Returns an array containing the components of this vector.
     *
     * @return This vector as an array
     */
    public final int[] toArray() {
        return new int[] { x,y,z };
    }

    /**
     * Writes the components of this vector into the given array.
     *
     * @param arr The array to write into
     * @param offset The index where to write the x component
     * @return The supplied array
     */
    public final int[] toArray(int[] arr, int offset) {
        arr[offset] = x;
        arr[offset+1] = y;
        arr[offset+2] = z;
        return arr;
    }

    /**
     * Returns a {@link constInt3} object with identical values (which is <b>not</b> a
     * {@link int3} instance) to this vector. If <code>getClass() == constInt3.class</code> is
     * already true, this vector will be returned itself.
     *
     * @return An unmodifiable vector with the specified values, possibly this itself
     */
    public final constInt3 toConst() {
        return getClass() == constInt3.class ? this : new constInt3(x,y,z);
    }


    /**
     * Returns a copy of this vector with the x component set to the given value.
     *
     * @param x The x value to set
     * @return A new vector with the given value as x component
     */
    public final int3 withX(int x) {
        return new int3(x,y,z);
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
    public final int3 withY(int y) {
        return new int3(x,y,z);
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
    public final int3 withZ(int z) {
        return new int3(x,y,z);
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
    public final int length2() {
        return x*x + y*y + z*z;
    }

    /**
     * Returns the dot product of this and the given vector.
     *
     * @param v The other vector
     * @return The dot product of the two vectors
     */
    public final int dot(constInt3 v) {
        return x*v.x + y*v.y + z*v.z;
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
     * Returns the sum of the vector's components.
     *
     * @return The sum of the components of the vector
     */
    public final int sum() {
        return x + y + z;
    }

    /**
     * Returns the product of the vector's components.
     *
     * @return The product of the components of the vector
     */
    public final int product() {
        return x * y * z;
    }

    /**
     * Returns the volume this vector spans. This is equivalent to
     * {@link #product()}.
     *
     * @return The volume defined by this vector
     */
    public final int volume() {
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
    public final float dist(constInt3 v) {
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
    public final float dist(constFloat3 v) {
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
    public final int dist2(constInt3 v) {
        return (v.x-x)*(v.x-x) + (v.y-y)*(v.y-y) + (v.z-z)*(v.z-z);
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
    public final float angle(constInt3 to) {
        return Mathf.acos(dot(to) / Mathf.sqrt(length2() * to.length2()));
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
     * Returns this vector component-wise negated.
     *
     * @return A new vector representing this vector negate
     */
    public final int3 negated() {
        return new int3(-x, -y, -z);
    }

    /**
     * Returns this vector component-wise inverted.
     *
     * @return A new vector representing this vector component-wise inverted
     */
    public final float3 inverse() {
        return new float3(1f/x, 1f/y, 1f/z);
    }

    /**
     * Returns the component-wise absolute of this vector.
     *
     * @return A new vector representing the component-wise absolute value
     */
    public final int3 absolute() {
        return new int3(abs(x), abs(y), abs(z));
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
    public final int3 added(int x) {
        return new int3(this.x + x, y+x, z+x);
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
    public final int3 added(int x, int y, int z) {
        return new int3(this.x + x, this.y + y, this.z + z);
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
    public final int3 added(constInt3 v) {
        return new int3(x + v.x, y + v.y, z + v.z);
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
     * Returns the result of subtracting the given value from each component of this vector.
     *
     * @param x The value to subtract
     * @return A new vector representing the component-wise subtraction of this vector and the given scalar
     */
    public final int3 subed(int x) {
        return new int3(this.x - x, y-x, z-x);
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
    public final int3 subed(int x, int y, int z) {
        return new int3(this.x - x, this.y - y, this.z - z);
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
    public final int3 subed(constInt3 v) {
        return new int3(x - v.x, y - v.y, z - v.z);
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
     * Returns the result of multiplying the given value by each component of this vector.
     *
     * @param f The value to multiply by
     * @return A new vector representing the component-wise multiplication of this vector and the given scalar
     */
    public final int3 scaled(int f) {
        return new int3(x*f, y*f, z*f);
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
    public final int3 scaled(int x, int y, int z) {
        return new int3(this.x * x, this.y * y, this.z * z);
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
    public final int3 scaled(constInt3 v) {
        return new int3(x * v.x, y * v.y, z * v.z);
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
     * Returns the result of dividing each component of this vector by the given value.
     *
     * @param x The value to divide by
     * @return A new vector representing the component-wise division of this vector and the given scalar
     */
    public final int3 dived(int x) {
        return new int3(this.x / x, y/x, z/x);
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
    public final int3 dived(int x, int y, int z) {
        return new int3(this.x / x, this.y / y, this.z / z);
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
    public final int3 dived(constInt3 v) {
        return new int3(x / v.x, y / v.y, z / v.z);
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
     * Returns the float-based component-wise division of this and the given vector.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    public final float3 divedF(constInt3 v) {
        return new float3((float) x / v.x, (float) y / v.y, (float) z / v.z);
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
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return A new vector representing this vector projected onto the given vector
     */
    public final float3 projected(constInt3 onto) {
        float f = (x*onto.x + y*onto.y + z*onto.z) / (float) (onto.x*onto.x + onto.y*onto.y + onto.z*onto.z);
        return new float3(onto.x * f, onto.y * f, onto.z * f);
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
     * Returns the cross product of this and the given vector.
     *
     * @param v The vector to compute the cross product with
     * @return A new vector representing the cross product of this and the given vector
     */
    public final int3 cross(constInt3 v) {
        return new int3(
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
    public final float3 cross(constFloat3 v) {
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
    public final int3 clamped(constInt3 min, constInt3 max) {
        return new int3(
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
    public final float3 clamped(constFloat3 min, constFloat3 max) {
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
    public final int3 clamped(constIBounds area) {
        return new int3(
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
    public final float3 clamped(constBounds area) {
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
