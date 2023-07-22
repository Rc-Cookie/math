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
 * A 2D float vector with read-only access.
 */
@SuppressWarnings({"DuplicatedCode"})
public class constFloat2 implements Cloneable<float2>, JsonSerializable {

    static {
        JsonDeserialization.register(constFloat2.class, json -> json.isArray() ? new constFloat2(
                json.get(0).asFloat(),
                json.get(1).asFloat()
        ) : new constFloat2(
                json.get("x").asFloat(),
                json.get("y").asFloat()
        ));
    }

    /**
     * A vector with all components set to 0.
     */
    public static final constFloat2 zero = new constFloat2(0);
    /**
     * A vector representing the x-axis unit vector.
     */
    public static final constFloat2 X = new constFloat2(1,0);
    /**
     * A vector representing the y-axis unit vector.
     */
    public static final constFloat2 Y = new constFloat2(0,1);
    /**
     * A vector with all components set to 1.
     */
    public static final constFloat2 one = new constFloat2(1);
    /**
     * A vector with all components set to 0.
     */
    public static final constFloat2 minusOne = new constFloat2(-1);
    /**
     * A vector with all components set to {@link Float#POSITIVE_INFINITY}.
     */
    public static final constFloat2 inf = new constFloat2(Float.POSITIVE_INFINITY);
    /**
     * A vector with all components set to {@link Float#NEGATIVE_INFINITY}.
     */
    public static final constFloat2 negInf = new constFloat2(Float.NEGATIVE_INFINITY);
    /**
     * A vector with all components set to {@link Float#NaN}.
     */
    public static final constFloat2 NaN = new constFloat2(Float.NaN);


    /**
     * The components of this vector.
     */
    protected float x,y;


    /**
     * Creates a vector with default value components.
     */
    protected constFloat2() { }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param xy The value for each component
     */
    public constFloat2(float xy) {
        x = y = xy;
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param y The value for the y component
     */
    public constFloat2(float x, float y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Returns a string representation of this vector, which is identical to the
     * result of <code>Arrays.toString(v.toArray())</code> for a vector <code>v</code>.
     *
     * @return A string representation of this vector.
     */
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
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
        return (obj instanceof constFloat2 && equals((constFloat2) obj)) || (obj instanceof constFloatN && ((constFloatN) obj).equals(this));
    }

    /**
     * Returns whether this vector is component-wise equal to the given vector. It does
     * <b>not</b> matter whether one of the vectors is read-only and the other one is not.
     *
     * @param v The vector to test for equality
     * @return Whether this vector is equal to the given vector
     */
    public final boolean equals(constFloat2 v) {
        return x == v.x && y == v.y;
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
    public final boolean equals(constFloat2 v, float ep) {
        return abs(v.x - x) <= ep && abs(v.y - y) <= ep;
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
    public final @NotNull float2 clone() {
        return new float2(x,y);
    }

    /**
     * Returns a json representation of this vector.
     *
     * @return This vector encoded as json
     */
    @Override
    @Contract(pure = true)
    public Object toJson() {
        return new JsonObject("x", x, "y", y);
    }

    /**
     * Returns a json array representation of this vector.
     *
     * @return This vector encoded as json array
     */
    public JsonArray toJsonArray() {
        return new JsonArray(x, y);
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
     * Returns the component at the given index of this vector, where 0 is the x component.
     *
     * @param index The index of the component to retrieve
     * @return The value of the component at that index
     */
    public final float component(@Range(from = 0, to = 1) int index) {
        switch(index) {
            case 0: return x;
            case 1: return y;
            default: throw new ArgumentOutOfRangeException(index, 0, 2);
        }
    }

    /**
     * Returns a new vector with x and y component swapped.
     *
     * @return A new vector with x and y swapped
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final float2 yx() {
        return new float2(y,x);
    }

    /**
     * Returns a new 3d vector with the z component set to 0.
     *
     * @return x and y component of this vector, with z set to 0
     */
    public final float3 xy0() {
        return new float3(x,y,0);
    }

    /**
     * Returns a new 4d vector with additional components set to 0.
     *
     * @return x and y component of this vector, padded with 0s
     */
    public final float4 xy00() {
        return new float4(x,y,0,0);
    }

    /**
     * Casts this vector component-wise to an int vector.
     *
     * @return An int vector with each element cast (not rounded) to an int
     */
    public final int2 toI() {
        return new int2((int) x, (int) y);
    }

    /**
     * Casts this vector component-wise to an int vector and returns an {@link constInt2}
     * (not {@link int2}) instance.
     *
     * @return A read-only int vector with each element cast (not rounded) to an int
     */
    @Contract(pure = true, value = "->new")
    public final constInt2 toConstI() {
        return new constInt2((int) x, (int) y);
    }

    /**
     * Returns an array containing the components of this vector.
     *
     * @return This vector as an array
     */
    public final float[] toArray() {
        return new float[] { x,y };
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
        return arr;
    }

    /**
     * Returns a {@link constFloat2} object with identical values (which is <b>not</b> a
     * {@link float2} instance) to this vector. If <code>getClass() == constFloat2.class</code> is
     * already true, this vector will be returned itself.
     *
     * @return An unmodifiable vector with the specified values, possibly this itself
     */
    public final constFloat2 toConst() {
        return getClass() == constFloat2.class ? this : new constFloat2(x,y);
    }


    /**
     * Returns a copy of this vector with the x component set to the given value.
     *
     * @param x The x value to set
     * @return A new vector with the given value as x component
     */
    public final float2 withX(float x) {
        return new float2(x,y);
    }

    /**
     * Returns a copy of this vector with the y component set to the given value.
     *
     * @param y The y value to set
     * @return A new vector with the given value as y component
     */
    public final float2 withY(float y) {
        return new float2(x,y);
    }


    /**
     * Returns whether all components of this vector are zero.
     *
     * @return Whether this vector is 0
     */
    public final boolean isZero() {
        return x == 0 && y == 0;
    }

    /**
     * Returns whether all components of this vector are approximately zero,
     * with the given allowed absolute difference.
     *
     * @param ep The permitted component-wise difference
     * @return Whether this vector is approximately 0
     */
    public final boolean isZero(float ep) {
        return abs(x) <= ep && abs(y) <= ep;
    }

    /**
     * Returns whether all components of this vector are finite.
     *
     * @return Whether this vector is finite
     */
    public final boolean isFinite() {
        return Float.isFinite(x) && Float.isFinite(y);
    }

    /**
     * Returns whether any component of this vector is infinite.
     *
     * @return Whether this vector is infinite
     */
    public final boolean isInfinite() {
        return Float.isInfinite(x) || Float.isInfinite(y);
    }

    /**
     * Returns whether any component of this vector is {@link Float#NaN}.
     *
     * @return Whether this vector contains an NaN
     */
    public final boolean isNaN() {
        return Float.isNaN(x) || Float.isNaN(y);
    }

    /**
     * Returns whether this vector is normalized, meaning its length is equal to 1.
     *
     * @return Whether this vector is normalized
     */
    public final boolean isNormed() {
        return x*x + y*y == 1;
    }

    /**
     * Returns whether this vector is approximately normalized, meaning its length is
     * at most the specified epsilon away from 1.
     *
     * @param ep The maximum allowed difference of the length of this vector from 1
     * @return Whether this vector is approximately normalized
     */
    public final boolean isNormed(float ep) {
        return abs(x*x + y*y - 1) <= ep*ep;
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
        return x*x + y*y;
    }

    /**
     * Returns the dot product of this and the given vector.
     *
     * @param v The other vector
     * @return The dot product of the two vectors
     */
    public final float dot(constFloat2 v) {
        return x*v.x + y*v.y;
    }

    /**
     * Returns the dot product of this and the given vector.
     *
     * @param v The other vector
     * @return The dot product of the two vectors
     */
    public final float dot(constInt2 v) {
        return x*v.x + y*v.y;
    }

    /**
     * Returns the sum of the vector's components.
     *
     * @return The sum of the components of the vector
     */
    public final float sum() {
        return x + y;
    }

    /**
     * Returns the product of the vector's components.
     *
     * @return The product of the components of the vector
     */
    public final float product() {
        return x * y;
    }

    /**
     * Returns the area this vector spans. This is equivalent to
     * {@link #product()}.
     *
     * @return The area defined by this vector
     */
    public final float area() {
        return x * y;
    }

    /**
     * Returns whether this vector is component-wise less than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less than the respective components from the given vector
     */
    public final boolean less(constFloat2 v) {
        return x < v.x && y < v.y;
    }

    /**
     * Returns whether this vector is component-wise less than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less than the respective components from the given vector
     */
    public final boolean less(constInt2 v) {
        return x < v.x && y < v.y;
    }

    /**
     * Returns whether this vector is component-wise less than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less or equal to the respective components from the given vector
     */
    public final boolean leq(constFloat2 v) {
        return x <= v.x && y <= v.y;
    }

    /**
     * Returns whether this vector is component-wise less than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are less or equal to the respective components from the given vector
     */
    public final boolean leq(constInt2 v) {
        return x <= v.x && y <= v.y;
    }

    /**
     * Returns whether this vector is component-wise greater than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater than the respective components from the given vector
     */
    public final boolean greater(constFloat2 v) {
        return x > v.x && y > v.y;
    }

    /**
     * Returns whether this vector is component-wise greater than the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater than the respective components from the given vector
     */
    public final boolean greater(constInt2 v) {
        return x > v.x && y > v.y;
    }

    /**
     * Returns whether this vector is component-wise greater than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater or equal to the respective components from the given vector
     */
    public final boolean geq(constInt2 v) {
        return x >= v.x && y >= v.y;
    }

    /**
     * Returns whether this vector is component-wise greater than or equal to the given vector.
     *
     * @param v The vector to compare to
     * @return True if all components are greater or equal to the respective components from the given vector
     */
    public final boolean geq(constFloat2 v) {
        return x >= v.x && y >= v.y;
    }


    /**
     * Returns the distance between the point represented by this vector and
     * the point represented by the given vector.
     *
     * @param v The other point
     * @return The distance between this and the given vector
     */
    public final float dist(constFloat2 v) {
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
    public final float dist(constInt2 v) {
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
    public final float dist2(constFloat2 v) {
        return (v.x-x)*(v.x-x) + (v.y-y)*(v.y-y);
    }

    /**
     * Returns the squared distance between the point represented by this vector
     * and the point represented by the given vector.
     *
     * @param v The other point
     * @return The squared distance between this and the given vector
     */
    public final float dist2(constInt2 v) {
        return (v.x-x)*(v.x-x) + (v.y-y)*(v.y-y);
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
    public final float angle(constFloat2 to) {
        return Mathf.acos(dot(to) / Mathf.sqrt(length2() * to.length2()));
    }

    /**
     * Returns the angle between this and the given vector.
     *
     * @param to The vector to compute the angle to
     * @return The angle between the vectors, in degrees
     */
    public final float angle(constInt2 to) {
        return Mathf.acos(dot(to) / Mathf.sqrt(length2() * to.length2()));
    }

    /**
     * Returns the signed angle between this vector and the x-axis, where
     * rotating counter-clockwise increases the angle.
     *
     * @return The signed angle between this vector and the x-axis
     */
    public final float signedAngle() {
        return Mathf.atan2(y,x);
    }

    /**
     * Returns the signed angle between this and the given vector, where
     * rotating counter-clockwise increases the angle.
     *
     * @return The signed angle between this and the given vector
     */
    public final float signedAngle(constFloat2 to) {
        float a = signedAngle() - to.signedAngle();
        if(a > 180) return a - 360;
        if(a <= -180) return a + 360;
        return a;
    }

    /**
     * Returns the signed angle between this and the given vector, where
     * rotating counter-clockwise increases the angle.
     *
     * @return The signed angle between this and the given vector
     */
    public final float signedAngle(constInt2 to) {
        float a = signedAngle() - to.signedAngle();
        if(a > 180) return a - 360;
        if(a <= -180) return a + 360;
        return a;
    }





    /**
     * Returns this vector component-wise negated.
     *
     * @return A new vector representing this vector negate
     */
    public final float2 negated() {
        return new float2(-x, -y);
    }

    /**
     * Returns this vector component-wise inverted.
     *
     * @return A new vector representing this vector component-wise inverted
     */
    public final float2 inverse() {
        return new float2(1/x, 1/y);
    }

    /**
     * Returns the component-wise absolute of this vector.
     *
     * @return A new vector representing the component-wise absolute value
     */
    public final float2 absolute() {
        return new float2(abs(x), abs(y));
    }

    /**
     * Returns this vector normalized (a vector with the same direction but length 1)
     *
     * @return A new vector representing this vector normalized.
     */
    public final float2 normed() {
        return dived(length());
    }

    /**
     * Returns this vector normalized, or the x-axis vector if this vector is 0.
     *
     * @return A new vector representing this vector normalized, or the x-axis
     */
    public final float2 safeNormed() {
        float len = length();
        return len == 0 ? float2.X() : dived(len);
    }



    /**
     * Returns the result of adding the given value onto each component of this vector.
     *
     * @param x The value to add
     * @return A new vector representing the component-wise sum of this vector and the given scalar
     */
    public final float2 added(float x) {
        return new float2(this.x + x, y+x);
    }

    /**
     * Returns the component-wise sum of this vector and the given components.
     *
     * @param x The value to add to the x component
     * @param y The value to add to the y component
     * @return A new vector representing the sum of this vector and the given components
     */
    public final float2 added(float x, float y) {
        return new float2(this.x + x, this.y + y);
    }

    /**
     * Returns the component-wise sum of this and the given vector.
     *
     * @param v The vector to add
     * @return A new vector representing the sum of this and the given vector
     */
    public final float2 added(constFloat2 v) {
        return new float2(x + v.x, y + v.y);
    }

    /**
     * Returns the component-wise sum of this and the given vector.
     *
     * @param v The vector to add
     * @return A new vector representing the sum of this and the given vector
     */
    public final float2 added(constInt2 v) {
        return new float2(x + v.x, y + v.y);
    }

    /**
     * Returns the result of subtracting the given value from each component of this vector.
     *
     * @param x The value to subtract
     * @return A new vector representing the component-wise subtraction of this vector and the given scalar
     */
    public final float2 subed(float x) {
        return new float2(this.x - x, y-x);
    }

    /**
     * Returns the component-wise subtraction of this vector and the given components.
     *
     * @param x The value to subtract from the x component
     * @param y The value to subtract from the y component
     * @return A new vector representing the subtraction of this vector and the given components
     */
    public final float2 subed(float x, float y) {
        return new float2(this.x - x, this.y - y);
    }

    /**
     * Returns the component-wise subtraction of this and the given vector.
     *
     * @param v The vector to subtract
     * @return A new vector representing the subtraction of this and the given vector
     */
    public final float2 subed(constFloat2 v) {
        return new float2(x - v.x, y - v.y);
    }

    /**
     * Returns the component-wise subtraction of this and the given vector.
     *
     * @param v The vector to subtract
     * @return A new vector representing the subtraction of this and the given vector
     */
    public final float2 subed(constInt2 v) {
        return new float2(x - v.x, y - v.y);
    }

    /**
     * Returns the result of multiplying the given value by each component of this vector.
     *
     * @param f The value to multiply by
     * @return A new vector representing the component-wise multiplication of this vector and the given scalar
     */
    public final float2 scaled(float f) {
        return new float2(x*f, y*f);
    }

    /**
     * Returns the component-wise product of this vector and the given components.
     *
     * @param x The value to multiply the x component by
     * @param y The value to multiply the y component by
     * @return A new vector representing the product of this vector and the given components
     */
    public final float2 scaled(float x, float y) {
        return new float2(this.x * x, this.y * y);
    }

    /**
     * Returns the component-wise product of this and the given vector.
     *
     * @param v The vector to multiply by
     * @return A new vector representing the product of this and the given vector
     */
    public final float2 scaled(constFloat2 v) {
        return new float2(x * v.x, y * v.y);
    }

    /**
     * Returns the component-wise product of this and the given vector.
     *
     * @param v The vector to multiply by
     * @return A new vector representing the product of this and the given vector
     */
    public final float2 scaled(constInt2 v) {
        return new float2(x * v.x, y * v.y);
    }

    /**
     * Returns the result of dividing each component of this vector by the given value.
     *
     * @param x The value to divide by
     * @return A new vector representing the component-wise division of this vector and the given scalar
     */
    public final float2 dived(float x) {
        return new float2(this.x / x, y/x);
    }

    /**
     * Returns the component-wise division of this vector and the given components.
     *
     * @param x The value to divide the x component by
     * @param y The value to divide the y component by
     * @return A new vector representing the division of this vector and the given components
     */
    public final float2 dived(float x, float y) {
        return new float2(this.x / x, this.y / y);
    }

    /**
     * Returns the component-wise division of this and the given vector.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    public final float2 dived(constFloat2 v) {
        return new float2(x / v.x, y / v.y);
    }

    /**
     * Returns the component-wise division of this and the given vector.
     *
     * @param v The vector to divide by
     * @return A new vector representing the division of this and the given vector
     */
    public final float2 dived(constInt2 v) {
        return new float2(x / v.x, y / v.y);
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return A new vector representing this vector lerped
     */
    public final float2 lerped(constFloat2 t, float a) {
        return new float2(
                x + (t.x - x) * a,
                y + (t.y - y) * a
        );
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return A new vector representing this vector lerped
     */
    public final float2 lerped(constInt2 t, float a) {
        return new float2(
                x + (t.x - x) * a,
                y + (t.y - y) * a
        );
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return A new vector representing this vector projected onto the given vector
     */
    public final float2 projected(constFloat2 onto) {
        float f = (x*onto.x + y*onto.y) / (onto.x*onto.x + onto.y*onto.y);
        return new float2(onto.x * f, onto.y * f);
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return A new vector representing this vector projected onto the given vector
     */
    public final float2 projected(constInt2 onto) {
        float f = (x*onto.x + y*onto.y) / (onto.x*onto.x + onto.y*onto.y);
        return new float2(onto.x * f, onto.y * f);
    }

    /**
     * Clamps this vector component-wise into the given range.
     *
     * @param min The component-wise lower bound
     * @param max The component-wise upper bound
     * @return A new vector representing this vector clamped
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final float2 clamped(constFloat2 min, constFloat2 max) {
        return new float2(
                Mathf.clamp(x, min.x, max.x),
                Mathf.clamp(y, min.y, max.y)
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
    public final float2 clamped(constInt2 min, constInt2 max) {
        return new float2(
                Mathf.clamp(x, min.x, max.x),
                Mathf.clamp(y, min.y, max.y)
        );
    }

    /**
     * Clamps this vector component-wise within the given area.
     *
     * @param area The area to clamp this vector into
     * @return A new vector representing this vector clamped
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final float2 clamped(constRect area) {
        return new float2(
                Mathf.clamp(x, area.min.x, area.max.x),
                Mathf.clamp(y, area.min.y, area.max.y)
        );
    }

    /**
     * Clamps this vector component-wise within the given area.
     *
     * @param area The area to clamp this vector into
     * @return A new vector representing this vector clamped
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public final float2 clamped(constIRect area) {
        return new float2(
                Mathf.clamp(x, area.min.x, area.max.x),
                Mathf.clamp(y, area.min.y, area.max.y)
        );
    }


    /**
     * Returns a new vector with the same direction as this vector, but with the given length.
     * If this vector is zero, the returned vector will be a multiple of the x-axis unit vector.
     *
     * @param l The length to set
     * @return A new vector pointing in the same direction as this one, but with the given length
     */
    public final float2 withLength(float l) {
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
    public final float2 withLengthClamped(float min, float max) {
        float len2 = length2();
        if(len2 == 0)
            return new float2(min, 0);
        if(len2 < min)
            return scaled(min / Mathf.sqrt(len2));
        if(len2 > max)
            return scaled(max / Mathf.sqrt(len2));
        return new float2(x,y);
    }

    /**
     * Returns a copy of this vector rotated by the given angle in
     * degrees.
     *
     * @param a The angle to rotate the vector by, in degrees
     * @return The rotated vector
     */
    public float2 rotated(float a) {
        float sin = Mathf.sin(a), cos = Mathf.cos(a);
        return new float2(x * cos - y * sin, x * sin + y * cos);
    }

    /**
     * Returns a copy of this vector rotated by the given angle in degrees
     * around the specified center point.
     *
     * @param c The point to rotate around
     * @param a The angle to rotate the vector by, in degrees
     * @return The rotated vector
     */
    public float2 rotatedAround(float2 c, float a) {
        float sin = Mathf.sin(a), cos = Mathf.cos(a);

        float2 v = new float2(x - c.x, y - c.y);

        float oldX = v.x;
        v.x = v.x * cos - v.y * sin;
        v.y = oldX * sin + v.y * cos;

        v.x += c.x;
        v.y += c.y;

        return v;
    }
}
