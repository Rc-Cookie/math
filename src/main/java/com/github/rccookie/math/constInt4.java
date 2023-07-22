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
 * A 4D int vector with read-only access.
 */
@SuppressWarnings({"DuplicatedCode"})
public class constInt4 implements Cloneable<int4>, JsonSerializable {

    static {
        JsonDeserialization.register(constInt4.class, json -> json.isArray() ? new constInt4(
                json.get(0).asInt(),
                json.get(1).asInt(),
                json.get(2).asInt(),
                json.get(3).asInt()
        ) : new constInt4(
                json.get("x").asInt(),
                json.get("y").asInt(),
                json.get("z").asInt(),
                json.get("w").asInt()
        ));
    }

    /**
     * A vector with all components set to 0.
     */
    public static final constInt4 zero = new constInt4(0);
    /**
     * A vector representing the x-axis unit vector.
     */
    public static final constInt4 X = new constInt4(1,0,0,0);
    /**
     * A vector representing the y-axis unit vector.
     */
    public static final constInt4 Y = new constInt4(0,1,0,0);
    /**
     * A vector representing the z-axis unit vector.
     */
    public static final constInt4 Z = new constInt4(0,0,1,0);
    /**
     * A vector representing the w-axis unit vector.
     */
    public static final constInt4 W = new constInt4(0,0,0,1);
    /**
     * A vector with all components set to 1.
     */
    public static final constInt4 one = new constInt4(1);
    /**
     * A vector with all components set to 0.
     */
    public static final constInt4 minusOne = new constInt4(-1);
    /**
     * A vector with all components set to {@link Integer#MIN_VALUE}.
     */
    public static final constInt4 min = new constInt4(Integer.MIN_VALUE);
    /**
     * A vector with all components set to {@link Integer#MAX_VALUE}.
     */
    public static final constInt4 max = new constInt4(Integer.MAX_VALUE);


    /**
     * The components of this vector.
     */
    protected int x,y,z,w;


    /**
     * Creates a vector with default value components.
     */
    @Contract(pure = true)
    protected constInt4() { }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param xyzw The value for each component
     */
    @Contract(pure = true)
    public constInt4(int xyzw) {
        x = y = z = w = xyzw;
    }

    /**
     * Creates a new vector.
     *
     * @param xyz The values for the x, y and z components
     * @param w The value for the w component
     */
    @Contract(pure = true)
    public constInt4(constInt3 xyz, int w) {
        this.x = xyz.x;
        this.y = xyz.y;
        this.z = xyz.z;
        this.w = w;
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param zw The values for the z and w component
     */
    @Contract(pure = true)
    public constInt4(constInt2 xy, constInt2 zw) {
        this.x = xy.x;
        this.y = xy.y;
        this.z = zw.x;
        this.w = zw.y;
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param z The value for the z component
     * @param w The value for the w component
     */
    @Contract(pure = true)
    public constInt4(constInt2 xy, int z, int w) {
        this.x = xy.x;
        this.y = xy.y;
        this.z = z;
        this.w = w;
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yzw The values for the y, z and w components
     */
    @Contract(pure = true)
    @SuppressWarnings("SuspiciousNameCombination")
    public constInt4(int x, constInt3 yzw) {
        this.x = x;
        this.y = yzw.x;
        this.z = yzw.y;
        this.w = yzw.z;
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yz The values for the y and z component
     * @param w The value for the w component
     */
    @Contract(pure = true)
    @SuppressWarnings("SuspiciousNameCombination")
    public constInt4(int x, constInt2 yz, int w) {
        this.x = x;
        this.y = yz.x;
        this.z = yz.y;
        this.w = w;
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param y The value for the y component
     * @param zw The values for the z and w component
     */
    @Contract(pure = true)
    public constInt4(int x, int y, constInt2 zw) {
        this.x = x;
        this.y = y;
        this.z = zw.x;
        this.w = zw.y;
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param y The value for the y component
     * @param z The value for the z component
     * @param w The value for the w component
     */
    @Contract(pure = true)
    public constInt4(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


    /**
     * Returns a string representation of this vector, which is identical to the
     * result of <code>Arrays.toString(v.toArray())</code> for a vector <code>v</code>.
     *
     * @return A string representation of this vector.
     */
    @Override
    @Contract(pure = true)
    public String toString() {
        return "[" + x + ", " + y + ", " + z + ", " + w + "]";
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
    @Contract(pure = true)
    public boolean equals(Object obj) {
        return obj instanceof constInt4 && equals((constInt4) obj);
    }

    /**
     * Returns whether this vector is component-wise equal to the given vector. It does
     * <b>not</b> matter whether one of the vectors is read-only and the other one is not.
     *
     * @param v The vector to test for equality
     * @return Whether this vector is equal to the given vector
     */
    @Contract(pure = true)
    public final boolean equals(constInt4 v) {
        return x == v.x && y == v.y && z == v.z && w == v.w;
    }

    /**
     * Returns a hash code for this vector.
     *
     * @return A hash code for this vector
     */
    @Override
    @Contract(pure = true)
    public final int hashCode() {
        return Arrays.hashCode(toArray());
    }

    /**
     * Returns a copy of this vector with identical component values.
     *
     * @return A copy of this vector
     */
    @Override
    @Contract(pure = true, value = "->new")
    public final @NotNull int4 clone() {
        return new int4(x,y,z,w);
    }

    /**
     * Returns a json representation of this vector.
     *
     * @return This vector encoded as json
     */
    @Override
    @Contract(pure = true)
    public Object toJson() {
        return new JsonObject("x", x, "y", y, "z", z, "w", w);
    }

    /**
     * Returns a json array representation of this vector.
     *
     * @return This vector encoded as json array
     */
    public JsonArray toJsonArray() {
        return new JsonArray(x, y, z, w);
    }


    /**
     * Returns the x component of this vector.
     *
     * @return The x component
     */
    @Contract(pure = true)
    public final int x() {
        return x;
    }

    /**
     * Returns the y component of this vector.
     *
     * @return The y component
     */
    @Contract(pure = true)
    public final int y() {
        return y;
    }

    /**
     * Returns the z component of this vector.
     *
     * @return The z component
     */
    @Contract(pure = true)
    public final int z() {
        return z;
    }

    /**
     * Returns the w component of this vector.
     *
     * @return The w component
     */
    @Contract(pure = true)
    public final int w() {
        return w;
    }

    /**
     * Returns the component at the given index of this vector, where 0 is the x component.
     *
     * @param index The index of the component to retrieve
     * @return The value of the component at that index
     */
    @Contract(pure = true)
    public final int component(@Range(from = 0, to = 3) int index) {
        switch(index) {
            case 0: return x;
            case 1: return y;
            case 2: return z;
            case 3: return w;
            default: throw new ArgumentOutOfRangeException(index, 0, 4);
        }
    }

    /**
     * Returns x and y component of this vector.
     *
     * @return x and y component of this vector as a float2
     */
    @Contract(pure = true, value = "->new")
    public final int2 xy() {
        return new int2(x,y);
    }

    /**
     * Returns x, y and z component of this vector.
     *
     * @return x, y and z component of this vector as float3
     */
    @Contract(pure = true, value = "->new")
    public final int3 xyz() {
        return new int3(x,y,z);
    }

    /**
     * Casts this vector component-wise to a float vector.
     *
     * @return An int vector with each element cast to a float
     */
    @Contract(pure = true, value = "->new")
    public final float4 toF() {
        return new float4(x,y,z,w);
    }

    /**
     * Casts this vector component-wise to a float vector and returns the
     * result as a {@link constFloat4} (not {@link float4}) instance.
     *
     * @return An immutable float vector with each component cast to a float
     */
    @Contract(pure = true, value = "->new")
    public final constFloat4 toConstF() {
        return new constFloat4(x,y,z,w);
    }

    /**
     * Returns an array containing the components of this vector.
     *
     * @return This vector as an array
     */
    @Contract(pure = true, value = "->new")
    public final int[] toArray() {
        return new int[] { x,y,z,w };
    }

    /**
     * Writes the components of this vector into the given array.
     *
     * @param arr The array to write into
     * @param offset The index where to write the x component
     * @return The supplied array
     */
    @Contract(value = "_,_->param1", mutates = "param1")
    public final int[] toArray(int[] arr, int offset) {
        arr[offset] = x;
        arr[offset+1] = y;
        arr[offset+2] = z;
        arr[offset+3] = w;
        return arr;
    }

    /**
     * Returns a {@link constInt4} object with identical values (which is <b>not</b> a
     * {@link int4} instance) to this vector. If <code>getClass() == constInt4.class</code> is
     * already true, this vector will be returned itself.
     *
     * @return An unmodifiable vector with the specified values, possibly this itself
     */
    public final constInt4 toConst() {
        return getClass() == constInt4.class ? this : new constInt4(x,y,z,w);
    }


    /**
     * Returns a copy of this vector with the x component set to the given value.
     *
     * @param x The x value to set
     * @return A new vector with the given value as x component
     */
    @Contract(pure = true, value = "_->new")
    public final int4 withX(int x) {
        return new int4(x,y,z,w);
    }

    /**
     * Returns a copy of this vector with the x component set to the given value.
     *
     * @param x The x value to set
     * @return A new vector with the given value as x component
     */
    @Contract(pure = true, value = "_->new")
    public final float4 withX(float x) {
        return new float4(x,y,z,w);
    }

    /**
     * Returns a copy of this vector with the y component set to the given value.
     *
     * @param y The y value to set
     * @return A new vector with the given value as y component
     */
    @Contract(pure = true, value = "_->new")
    public final int4 withY(int y) {
        return new int4(x,y,z,w);
    }

    /**
     * Returns a copy of this vector with the y component set to the given value.
     *
     * @param y The y value to set
     * @return A new vector with the given value as y component
     */
    @Contract(pure = true, value = "_->new")
    public final float4 withY(float y) {
        return new float4(x,y,z,w);
    }

    /**
     * Returns a copy of this vector with the z component set to the given value.
     *
     * @param z The z value to set
     * @return A new vector with the given value as z component
     */
    @Contract(pure = true, value = "_->new")
    public final int4 withZ(int z) {
        return new int4(x,y,z,w);
    }

    /**
     * Returns a copy of this vector with the z component set to the given value.
     *
     * @param z The z value to set
     * @return A new vector with the given value as z component
     */
    @Contract(pure = true, value = "_->new")
    public final float4 withZ(float z) {
        return new float4(x,y,z,w);
    }

    /**
     * Returns a copy of this vector with the w component set to the given value.
     *
     * @param w The w value to set
     * @return A new vector with the given value as w component
     */
    @Contract(pure = true, value = "_->new")
    public final int4 withW(int w) {
        return new int4(x,y,z,w);
    }

    /**
     * Returns a copy of this vector with the w component set to the given value.
     *
     * @param w The w value to set
     * @return A new vector with the given value as w component
     */
    @Contract(pure = true, value = "_->new")
    public final float4 withW(float w) {
        return new float4(x,y,z,w);
    }


    /**
     * Returns whether all components of this vector are zero.
     *
     * @return Whether this vector is 0
     */
    @Contract(pure = true)
    public final boolean isZero() {
        return x == 0 && y == 0 && z == 0 && w == 0;
    }


    /**
     * Returns the length of this vector.
     *
     * @return The length of this vector
     */
    @Contract(pure = true)
    public final float length() {
        float len2 = length2();
        return len2 == 1 ? 1 : Mathf.sqrt(len2);
    }

    /**
     * Returns the squared length of this vector.
     *
     * @return The squared length of this vector
     */
    @Contract(pure = true)
    public final int length2() {
        return x*x + y*y + z*z + w*w;
    }

    /**
     * Returns the dot product of this and the given vector.
     *
     * @param v The other vector
     * @return The dot product of the two vectors
     */
    @Contract(pure = true)
    public final int dot(constInt4 v) {
        return x*v.x + y*v.y + z*v.z + w*v.w;
    }

    /**
     * Returns the dot product of this and the given vector.
     *
     * @param v The other vector
     * @return The dot product of the two vectors
     */
    @Contract(pure = true)
    public final float dot(constFloat4 v) {
        return x*v.x + y*v.y + z*v.z + w*v.w;
    }

    /**
     * Returns the sum of the vector's components.
     *
     * @return The sum of the components of the vector
     */
    @Contract(pure = true)
    public final int sum() {
        return x + y + z + w;
    }

    /**
     * Returns the product of the vector's components.
     *
     * @return The product of the components of the vector
     */
    @Contract(pure = true)
    public final int product() {
        return x * y * z * w;
    }

    /**
     * Returns the hyper-volume this vector spans. This is equivalent to
     * {@link #product()}.
     *
     * @return The hyper-volume defined by this vector
     */
    @Contract(pure = true)
    public final int hyperVol() {
        return x * y * z * w;
    }

    /**
     * Returns whether this vector is component-wise less than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less than the respective components from the given vector
     */
    @Contract(pure = true)
    public final boolean less(constFloat4 v) {
        return x < v.x && y < v.y && z < v.z && w < v.w;
    }

    /**
     * Returns whether this vector is component-wise less than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less than the respective components from the given vector
     */
    @Contract(pure = true)
    public final boolean less(constInt4 v) {
        return x < v.x && y < v.y && z < v.z && w < v.w;
    }

    /**
     * Returns whether this vector is component-wise less than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less or equal to the respective components from the given vector
     */
    @Contract(pure = true)
    public final boolean leq(constFloat4 v) {
        return x <= v.x && y <= v.y && z <= v.z && w <= v.w;
    }

    /**
     * Returns whether this vector is component-wise less than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less or equal to the respective components from the given vector
     */
    @Contract(pure = true)
    public final boolean leq(constInt4 v) {
        return x <= v.x && y <= v.y && z <= v.z && w <= v.w;
    }

    /**
     * Returns whether this vector is component-wise greater than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater than the respective components from the given vector
     */
    @Contract(pure = true)
    public final boolean greater(constFloat4 v) {
        return x > v.x && y > v.y && z > v.z && w > v.w;
    }

    /**
     * Returns whether this vector is component-wise greater than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater than the respective components from the given vector
     */
    @Contract(pure = true)
    public final boolean greater(constInt4 v) {
        return x > v.x && y > v.y && z > v.z && w > v.w;
    }

    /**
     * Returns whether this vector is component-wise greater than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater or equal to the respective components from the given vector
     */
    @Contract(pure = true)
    public final boolean geq(constInt4 v) {
        return x >= v.x && y >= v.y && z >= v.z && w >= v.w;
    }

    /**
     * Returns whether this vector is component-wise greater than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater or equal to the respective components from the given vector
     */
    @Contract(pure = true)
    public final boolean geq(constFloat4 v) {
        return x >= v.x && y >= v.y && z >= v.z && w >= v.w;
    }


    /**
     * Returns the distance between the point represented by this vector and
     * the point represented by the given vector.
     *
     * @param v The other point
     * @return The distance between this and the given vector
     */
    @Contract(pure = true)
    public final float dist(constInt4 v) {
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
    @Contract(pure = true)
    public final float dist(constFloat4 v) {
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
    @Contract(pure = true)
    public final int dist2(constInt4 v) {
        return (v.x-x)*(v.x-x) + (v.y-y)*(v.y-y) + (v.z-z)*(v.z-z) + (v.w-w)*(v.w-w);
    }

    /**
     * Returns the squared distance between the point represented by this vector
     * and the point represented by the given vector.
     *
     * @param v The other point
     * @return The squared distance between this and the given vector
     */
    @Contract(pure = true)
    public final float dist2(constFloat4 v) {
        return (v.x-x)*(v.x-x) + (v.y-y)*(v.y-y) + (v.z-z)*(v.z-z) + (v.w-w)*(v.w-w);
    }

    /**
     * Returns the angle of this vector to the x-axis.
     *
     * @return The angle to the x-axis, in degrees
     */
    @Contract(pure = true)
    public final float angle() {
        return Mathf.acos(x / length());
    }

    /**
     * Returns the angle between this and the given vector.
     *
     * @param to The vector to compute the angle to
     * @return The angle between the vectors, in degrees
     */
    @Contract(pure = true)
    public final float angle(constInt4 to) {
        return Mathf.acos(dot(to) / Mathf.sqrt(length2() * to.length2()));
    }

    /**
     * Returns the angle between this and the given vector.
     *
     * @param to The vector to compute the angle to
     * @return The angle between the vectors, in degrees
     */
    @Contract(pure = true)
    public final float angle(constFloat4 to) {
        return Mathf.acos(dot(to) / Mathf.sqrt(length2() * to.length2()));
    }



    /**
     * Returns this vector component-wise negated.
     *
     * @return A new vector representing this vector negate
     */
    @Contract(pure = true, value = "->new")
    public final int4 negated() {
        return new int4(-x, -y, -z, -w);
    }

    /**
     * Returns this vector component-wise inverted.
     *
     * @return A new vector representing this vector component-wise inverted
     */
    @Contract(pure = true, value = "->new")
    public final float4 inverse() {
        return new float4(1f/x, 1f/y, 1f/z, 1f/w);
    }

    /**
     * Returns the component-wise absolute of this vector.
     *
     * @return A new vector representing the component-wise absolute value
     */
    @Contract(pure = true, value = "->new")
    public final int4 absolute() {
        return new int4(abs(x), abs(y), abs(z), abs(w));
    }

    /**
     * Returns this vector normalized (a vector with the same direction but length 1)
     *
     * @return A new vector representing this vector normalized.
     */
    @Contract(pure = true, value = "->new")
    public final float4 normed() {
        return dived(length());
    }

    /**
     * Returns this vector normalized, or the x-axis vector if this vector is 0.
     *
     * @return A new vector representing this vector normalized, or the x-axis
     */
    @Contract(pure = true, value = "->new")
    public final float4 safeNormed() {
        float len = length();
        return len == 0 ? float4.X() : dived(len);
    }



    /**
     * Returns the result of adding the given value onto each component of this vector.
     *
     * @param x The value to add
     * @return A new vector representing the component-wise sum of this vector and the given scalar
     */
    @Contract(pure = true, value = "_->new")
    public final int4 added(int x) {
        return new int4(this.x + x, y+x, z+x, w+x);
    }

    /**
     * Returns the result of adding the given value onto each component of this vector.
     *
     * @param x The value to add
     * @return A new vector representing the component-wise sum of this vector and the given scalar
     */
    @Contract(pure = true, value = "_->new")
    public final float4 added(float x) {
        return new float4(this.x + x, y+x, z+x, w+x);
    }

    /**
     * Returns the component-wise sum of this vector and the given components.
     *
     * @param x The value to add to the x component
     * @param y The value to add to the y component
     * @param z The value to add to the z component
     * @param w The value to add to the w component
     * @return A new vector representing the sum of this vector and the given components
     */
    @Contract(pure = true, value = "_,_,_,_->new")
    public final int4 added(int x, int y, int z, int w) {
        return new int4(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    /**
     * Returns the component-wise sum of this vector and the given components.
     *
     * @param x The value to add to the x component
     * @param y The value to add to the y component
     * @param z The value to add to the z component
     * @param w The value to add to the w component
     * @return A new vector representing the sum of this vector and the given components
     */
    @Contract(pure = true, value = "_,_,_,_->new")
    public final float4 added(float x, float y, float z, float w) {
        return new float4(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    /**
     * Returns the component-wise sum of this and the given vector.
     *
     * @param v The vector to add
     * @return A new vector representing the sum of this and the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final int4 added(constInt4 v) {
        return new int4(x + v.x, y + v.y, z + v.z, w + v.w);
    }

    /**
     * Returns the component-wise sum of this and the given vector.
     *
     * @param v The vector to add
     * @return A new vector representing the sum of this and the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final float4 added(constFloat4 v) {
        return new float4(x + v.x, y + v.y, z + v.z, w + v.w);
    }

    /**
     * Returns the result of subtracting the given value from each component of this vector.
     *
     * @param x The value to subtract
     * @return A new vector representing the component-wise subtraction of this vector and the given scalar
     */
    @Contract(pure = true, value = "_->new")
    public final int4 subed(int x) {
        return new int4(this.x - x, y-x, z-x, w-x);
    }

    /**
     * Returns the result of subtracting the given value from each component of this vector.
     *
     * @param x The value to subtract
     * @return A new vector representing the component-wise subtraction of this vector and the given scalar
     */
    @Contract(pure = true, value = "_->new")
    public final float4 subed(float x) {
        return new float4(this.x - x, y-x, z-x, w-x);
    }

    /**
     * Returns the component-wise subtraction of this vector and the given components.
     *
     * @param x The value to subtract from the x component
     * @param y The value to subtract from the y component
     * @param z The value to subtract from the z component
     * @param w The value to subtract from the w component
     * @return A new vector representing the subtraction of this vector and the given components
     */
    @Contract(pure = true, value = "_,_,_,_->new")
    public final int4 subed(int x, int y, int z, int w) {
        return new int4(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    /**
     * Returns the component-wise subtraction of this vector and the given components.
     *
     * @param x The value to subtract from the x component
     * @param y The value to subtract from the y component
     * @param z The value to subtract from the z component
     * @param w The value to subtract from the w component
     * @return A new vector representing the subtraction of this vector and the given components
     */
    @Contract(pure = true, value = "_,_,_,_->new")
    public final float4 subed(float x, float y, float z, float w) {
        return new float4(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    /**
     * Returns the component-wise subtraction of this and the given vector.
     *
     * @param v The vector to subtract
     * @return A new vector representing the subtraction of this and the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final int4 subed(constInt4 v) {
        return new int4(x - v.x, y - v.y, z - v.z, w - v.w);
    }

    /**
     * Returns the component-wise subtraction of this and the given vector.
     *
     * @param v The vector to subtract
     * @return A new vector representing the subtraction of this and the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final float4 subed(constFloat4 v) {
        return new float4(x - v.x, y - v.y, z - v.z, w - v.w);
    }

    /**
     * Returns the result of multiplying the given value by each component of this vector.
     *
     * @param f The value to multiply by
     * @return A new vector representing the component-wise multiplication of this vector and the given scalar
     */
    @Contract(pure = true, value = "_->new")
    public final int4 scaled(int f) {
        return new int4(x*f, y*f, z*f, w*f);
    }

    /**
     * Returns the result of multiplying the given value by each component of this vector.
     *
     * @param f The value to multiply by
     * @return A new vector representing the component-wise multiplication of this vector and the given scalar
     */
    @Contract(pure = true, value = "_->new")
    public final float4 scaled(float f) {
        return new float4(x*f, y*f, z*f, w*f);
    }

    /**
     * Returns the component-wise product of this vector and the given components.
     *
     * @param x The value to multiply the x component by
     * @param y The value to multiply the y component by
     * @param z The value to multiply the z component by
     * @param w The value to multiply the w component by
     * @return A new vector representing the product of this vector and the given components
     */
    @Contract(pure = true, value = "_,_,_,_->new")
    public final int4 scaled(int x, int y, int z, int w) {
        return new int4(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    /**
     * Returns the component-wise product of this vector and the given components.
     *
     * @param x The value to multiply the x component by
     * @param y The value to multiply the y component by
     * @param z The value to multiply the z component by
     * @param w The value to multiply the w component by
     * @return A new vector representing the product of this vector and the given components
     */
    @Contract(pure = true, value = "_,_,_,_->new")
    public final float4 scaled(float x, float y, float z, float w) {
        return new float4(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    /**
     * Returns the component-wise product of this and the given vector.
     *
     * @param v The vector to multiply by
     * @return A new vector representing the product of this and the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final int4 scaled(constInt4 v) {
        return new int4(x * v.x, y * v.y, z * v.z, w * v.w);
    }

    /**
     * Returns the component-wise product of this and the given vector.
     *
     * @param v The vector to multiply by
     * @return A new vector representing the product of this and the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final float4 scaled(constFloat4 v) {
        return new float4(x * v.x, y * v.y, z * v.z, w * v.w);
    }

    /**
     * Returns the result of dividing each component of this vector by the given value.
     *
     * @param x The value to divide by
     * @return A new vector representing the component-wise division of this vector and the given scalar
     */
    @Contract(pure = true, value = "_->new")
    public final int4 dived(int x) {
        return new int4(this.x / x, y/x, z/x, w/x);
    }

    /**
     * Returns the result of dividing each component of this vector by the given value.
     *
     * @param x The value to divide by
     * @return A new vector representing the component-wise division of this vector and the given scalar
     */
    @Contract(pure = true, value = "_->new")
    public final float4 dived(float x) {
        return new float4(this.x / x, y/x, z/x, w/x);
    }

    /**
     * Returns the component-wise division of this vector and the given components.
     *
     * @param x The value to divide the x component by
     * @param y The value to divide the y component by
     * @param z The value to divide the z component by
     * @param w The value to divide the w component by
     * @return A new vector representing the division of this vector and the given components
     */
    @Contract(pure = true, value = "_,_,_,_->new")
    public final int4 dived(int x, int y, int z, int w) {
        return new int4(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    /**
     * Returns the component-wise division of this vector and the given components.
     *
     * @param x The value to divide the x component by
     * @param y The value to divide the y component by
     * @param z The value to divide the z component by
     * @param w The value to divide the w component by
     * @return A new vector representing the division of this vector and the given components
     */
    @Contract(pure = true, value = "_,_,_,_->new")
    public final float4 dived(float x, float y, float z, float w) {
        return new float4(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    /**
     * Returns the component-wise division of this and the given vector.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final int4 dived(constInt4 v) {
        return new int4(x / v.x, y / v.y, z / v.z, w / v.w);
    }

    /**
     * Returns the component-wise division of this and the given vector.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final float4 dived(constFloat4 v) {
        return new float4(x / v.x, y / v.y, z / v.z, w / v.w);
    }

    /**
     * Returns the float-based component-wise division of this and the given vector.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final float4 divedF(constInt4 v) {
        return new float4((float) x / v.x, (float) y / v.y, (float) z / v.z, (float) w / v.w);
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return A new vector representing this vector lerped
     */
    @Contract(pure = true, value = "_,_->new")
    public final float4 lerped(constInt4 t, float a) {
        return new float4(
                x + (t.x - x) * a,
                y + (t.y - y) * a,
                z + (t.z - z) * a,
                w + (t.w - z) * a
        );
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return A new vector representing this vector lerped
     */
    @Contract(pure = true, value = "_,_->new")
    public final float4 lerped(constFloat4 t, float a) {
        return new float4(
                x + (t.x - x) * a,
                y + (t.y - y) * a,
                z + (t.z - z) * a,
                w + (t.w - z) * a
        );
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return A new vector representing this vector projected onto the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final float4 projected(constInt4 onto) {
        float f = (x*onto.x + y*onto.y + z*onto.z + w*onto.w) / (float) (onto.x*onto.x + onto.y*onto.y + onto.z*onto.z + onto.w*onto.w);
        return new float4(onto.x * f, onto.y * f, onto.z * f, onto.w * f);
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return A new vector representing this vector projected onto the given vector
     */
    @Contract(pure = true, value = "_->new")
    public final float4 projected(constFloat4 onto) {
        float f = (x*onto.x + y*onto.y + z*onto.z + w*onto.w) / (onto.x*onto.x + onto.y*onto.y + onto.z*onto.z + onto.w*onto.w);
        return new float4(onto.x * f, onto.y * f, onto.z * f, onto.w * f);
    }

    /**
     * Clamps this vector component-wise into the given range.
     *
     * @param min The component-wise lower bound
     * @param max The component-wise upper bound
     * @return A new vector representing this vector clamped
     */
    @Contract(pure = true, value = "_,_->new")
    @SuppressWarnings("SuspiciousNameCombination")
    public final int4 clamped(constInt4 min, constInt4 max) {
        return new int4(
                Mathf.clamp(x, min.x, max.x),
                Mathf.clamp(y, min.y, max.y),
                Mathf.clamp(z, min.z, max.z),
                Mathf.clamp(w, min.w, max.w)
        );
    }

    /**
     * Clamps this vector component-wise into the given range.
     *
     * @param min The component-wise lower bound
     * @param max The component-wise upper bound
     * @return A new vector representing this vector clamped
     */
    @Contract(pure = true, value = "_,_->new")
    @SuppressWarnings("SuspiciousNameCombination")
    public final float4 clamped(constFloat4 min, constFloat4 max) {
        return new float4(
                Mathf.clamp(x, min.x, max.x),
                Mathf.clamp(y, min.y, max.y),
                Mathf.clamp(z, min.z, max.z),
                Mathf.clamp(w, min.w, max.w)
        );
    }


    /**
     * Returns a new vector with the same direction as this vector, but with the given length.
     * If this vector is zero, the returned vector will be a multiple of the x-axis unit vector.
     *
     * @param l The length to set
     * @return A new vector pointing in the same direction as this one, but with the given length
     */
    @Contract(pure = true, value = "_->new")
    public final float4 withLength(float l) {
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
    @Contract(pure = true, value = "_,_->new")
    public final float4 withLengthClamped(float min, float max) {
        float len2 = length2();
        if(len2 == 0)
            return new float4(min, 0, 0, 0);
        if(len2 < min)
            return scaled(min / Mathf.sqrt(len2));
        if(len2 > max)
            return scaled(max / Mathf.sqrt(len2));
        return new float4(x,y,z,w);
    }
}
