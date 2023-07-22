package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.ArgumentOutOfRangeException;
import com.github.rccookie.util.Arguments;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Range;

/**
 * A mutable 4d float vector.
 */
@SuppressWarnings({"NewClassNamingConvention", "DuplicatedCode"})
public final class float4 extends constFloat4 {

    static {
        JsonDeserialization.register(float4.class, json -> new float4(
                json.get(0).asFloat(),
                json.get(1).asFloat(),
                json.get(2).asFloat(),
                json.get(3).asFloat()
        ));
    }

    @Contract(pure = true)
    float4() { }

    /**
     * Creates a new vector with all components set to the given value.
     *
     * @param xyzw The value for all components
     */
    @Contract(pure = true)
    public float4(float xyzw) {
        super(xyzw);
    }

    /**
     * Creates a new vector.
     *
     * @param xyz The values for the x, y and z components
     * @param w The value for the w component
     */
    @Contract(pure = true)
    public float4(constFloat3 xyz, float w) {
        super(xyz,w);
    }

    /**
     * Creates a new vector.
     *
     * @param xyz The values for the x, y and z components
     * @param w The value for the w component
     */
    @Contract(pure = true)
    public float4(constInt3 xyz, float w) {
        super(xyz,w);
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param zw The values for the z and w component
     */
    @Contract(pure = true)
    public float4(constFloat2 xy, constFloat2 zw) {
        super(xy,zw);
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param zw The values for the z and w component
     */
    @Contract(pure = true)
    public float4(constInt2 xy, constFloat2 zw) {
        super(xy,zw);
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param zw The values for the z and w component
     */
    @Contract(pure = true)
    public float4(constFloat2 xy, constInt2 zw) {
        super(xy,zw);
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param zw The values for the z and w component
     */
    @Contract(pure = true)
    public float4(constInt2 xy, constInt2 zw) {
        super(xy,zw);
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param z The value for the z component
     * @param w The value for the w component
     */
    @Contract(pure = true)
    public float4(constFloat2 xy, float z, float w) {
        super(xy,z,w);
    }

    /**
     * Creates a new vector.
     *
     * @param xy The values for the x and y component
     * @param z The value for the z component
     * @param w The value for the w component
     */
    @Contract(pure = true)
    public float4(constInt2 xy, float z, float w) {
        super(xy,z,w);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yzw The values for the y, z and w components
     */
    @Contract(pure = true)
    public float4(float x, constFloat3 yzw) {
        super(x,yzw);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yzw The values for the y, z and w components
     */
    @Contract(pure = true)
    public float4(float x, constInt3 yzw) {
        super(x,yzw);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yz The values for the y and z component
     * @param w The value for the w component
     */
    @Contract(pure = true)
    public float4(float x, constFloat2 yz, float w) {
        super(x,yz,w);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param yz The values for the y and z component
     * @param w The value for the w component
     */
    @Contract(pure = true)
    public float4(float x, constInt2 yz, float w) {
        super(x,yz,w);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param y The value for the y component
     * @param zw The values for the z and w component
     */
    @Contract(pure = true)
    public float4(float x, float y, constFloat2 zw) {
        super(x,y,zw);
    }

    /**
     * Creates a new vector.
     *
     * @param x The value for the x component
     * @param y The value for the y component
     * @param zw The values for the z and w component
     */
    @Contract(pure = true)
    public float4(float x, float y, constInt2 zw) {
        super(x,y,zw);
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
    public float4(float x, float y, float z, float w) {
        super(x,y,z,w);
    }


    /**
     * Sets this vector to the given values.
     *
     * @param x The value to set the x component to
     * @param y The value to set the y component to
     * @param z The value to set the z component to
     * @param w The value to set the w component to
     * @return This vector
     */
    @Contract(value = "_,_,_,_->this", mutates = "this")
    public float4 set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Sets this vector to the same value as the given vector.
     *
     * @param v The vector to set this vector to
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 set(constFloat4 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = v.w;
        return this;
    }

    /**
     * Sets this vector to the same value as the given vector.
     *
     * @param v The vector to set this vector to
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 set(constInt4 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = v.w;
        return this;
    }

    /**
     * Sets the x component of this vector to the given value.
     *
     * @param x The new value for the x component
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 setX(float x) {
        this.x = x;
        return this;
    }


    /**
     * Sets the y component of this vector to the given value.
     *
     * @param y The new value for the y component
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 setY(float y) {
        this.y = y;
        return this;
    }


    /**
     * Sets the z component of this vector to the given value.
     *
     * @param z The new value for the z component
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 setZ(float z) {
        this.z = z;
        return this;
    }


    /**
     * Sets the w component of this vector to the given value.
     *
     * @param w The new value for the w component
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 setW(float w) {
        this.w = w;
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
    @Contract(value = "_,_->this", mutates = "this")
    @SuppressWarnings("SuspiciousNameCombination")
    public float4 setComponent(@Range(from = 0, to = 3) int index, float x) {
        switch(index) {
            case 0: this.x = x; return this;
            case 1: y = x; return this;
            case 2: z = x; return this;
            case 3: w = x; return this;
            default: throw new ArgumentOutOfRangeException(index, 0, 4);
        }
    }

    /**
     * Sets all components of this vector to 0.
     *
     * @return This vector
     */
    @Contract(value = "->this", mutates = "this")
    public float4 setZero() {
        x = y = z = w = 0;
        return this;
    }

    /**
     * Scales this vector so that it has the desired length, without changing its direction.
     *
     * @param l The length to set. Negative values will flip the resulting direction
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 setLength(float l) {
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
    @Contract(value = "_,_->this", mutates = "this")
    public float4 clampLength(float min, float max) {
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
    @Contract(value = "_->this", mutates = "this")
    public float4 setMin(constFloat4 v) {
        x = Mathf.min(x, v.x);
        y = Mathf.min(y, v.y);
        z = Mathf.min(z, v.z);
        w = Mathf.min(w, v.w);
        return this;
    }

    /**
     * Sets this vector to the component-wise minimum of this and the given vector.
     *
     * @param v The vector to take the minimum from
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 setMin(constInt4 v) {
        x = Mathf.min(x, v.x);
        y = Mathf.min(y, v.y);
        z = Mathf.min(z, v.z);
        w = Mathf.min(w, v.w);
        return this;
    }

    /**
     * Sets this vector to the component-wise maximum of this and the given vector.
     *
     * @param v The vector to take the maximum from
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 setMax(constFloat4 v) {
        x = Mathf.max(x, v.x);
        y = Mathf.max(y, v.y);
        z = Mathf.max(z, v.z);
        w = Mathf.max(w, v.w);
        return this;
    }

    /**
     * Sets this vector to the component-wise maximum of this and the given vector.
     *
     * @param v The vector to take the maximum from
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 setMax(constInt4 v) {
        x = Mathf.max(x, v.x);
        y = Mathf.max(y, v.y);
        z = Mathf.max(z, v.z);
        w = Mathf.max(w, v.w);
        return this;
    }



    /**
     * Negates this vector component-wise.
     *
     * @return This vector
     */
    @Contract(value = "->this", mutates = "this")
    public float4 neg() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    /**
     * Inverts this vector component-wise.
     *
     * @return This vector
     */
    @Contract(value = "->this", mutates = "this")
    public float4 invert() {
        x = 1/x;
        y = 1/y;
        z = 1/z;
        w = 1/w;
        return this;
    }

    /**
     * Sets this vector to its component-wise absolute value.
     *
     * @return This vector
     */
    @Contract(value = "->this", mutates = "this")
    public float4 abs() {
        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);
        w = Math.abs(w);
        return this;
    }

    /**
     * Normalizes this vector, so that it has a length of 1 in the same direction.
     *
     * @return This vector
     */
    @Contract(value = "->this", mutates = "this")
    public float4 norm() {
        return div(length());
    }

    /**
     * Normalizes this vector, so that it has a length of 1 in the same direction.
     * If this vector is 0, it will be set to the x-axis unit vector.
     *
     * @return This vector
     */
    @Contract(value = "->this", mutates = "this")
    public float4 safeNorm() {
        float len = length();
        return len == 0 ? setX(1) : dived(len);
    }


    /**
     * Adds the given value onto all components of this vector.
     *
     * @param x The value to add to all components
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    @SuppressWarnings("SuspiciousNameCombination")
    public float4 add(float x) {
        this.x += x;
        y += x;
        z += x;
        w += x;
        return this;
    }

    /**
     * Adds the given values to the respective components of this vector.
     *
     * @param x The value to add to the x component
     * @param y The value to add to the y component
     * @param z The value to add to the z component
     * @param w The value to add to the w component
     * @return This vector
     */
    @Contract(value = "_,_,_,_->this", mutates = "this")
    public float4 add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    /**
     * Adds the given vector component-wise to this vector.
     *
     * @param v The vector to add to this vector
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 add(constFloat4 v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    /**
     * Adds the given vector component-wise to this vector.
     *
     * @param v The vector to add to this vector
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 add(constInt4 v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    /**
     * Subtracts the given value from all components of this vector.
     *
     * @param x The value to subtract from all components
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    @SuppressWarnings("SuspiciousNameCombination")
    public float4 sub(float x) {
        this.x -= x;
        y -= x;
        z -= x;
        w -= x;
        return this;
    }

    /**
     * Subtracts the given values from the respective components of this vector.
     *
     * @param x The value to subtract from the x component
     * @param y The value to subtract from the y component
     * @param z The value to subtract from the z component
     * @param w The value to subtract from the w component
     * @return This vector
     */
    @Contract(value = "_,_,_,_->this", mutates = "this")
    public float4 sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    /**
     * Subtracts the given vector component-wise from this vector.
     *
     * @param v The vector to subtract from this vector
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 sub(constFloat4 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }

    /**
     * Subtracts the given vector component-wise from this vector.
     *
     * @param v The vector to subtract from this vector
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 sub(constInt4 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }

    /**
     * Multiplies all components of this vector by the given value.
     *
     * @param f The value to multiply all components with
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 scale(float f) {
        x *= f;
        y *= f;
        z *= f;
        w *= f;
        return this;
    }

    /**
     * Multiplies all components of this vector by the respective values.
     *
     * @param x The value to multiply the x component by
     * @param y The value to multiply the y component by
     * @param z The value to multiply the z component by
     * @param w The value to multiply the w component by
     * @return This vector
     */
    @Contract(value = "_,_,_,_->this", mutates = "this")
    public float4 scale(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
    }

    /**
     * Multiplies the given vector component-wise to this vector.
     *
     * @param v The vector to multiply with this vector
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 scale(constFloat4 v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        w *= v.w;
        return this;
    }

    /**
     * Multiplies the given vector component-wise to this vector.
     *
     * @param v The vector to multiply with this vector
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 scale(constInt4 v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        w *= v.w;
        return this;
    }

    /**
     * Divides all components of this vector by the given value.
     *
     * @param x The value to divide all components by
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    @SuppressWarnings("SuspiciousNameCombination")
    public float4 div(float x) {
        this.x /= x;
        y /= x;
        z /= x;
        w /= x;
        return this;
    }

    /**
     * Divides all components of this vector by the respective values.
     *
     * @param x The value to divide the x component by
     * @param y The value to divide the y component by
     * @param z The value to divide the z component by
     * @param w The value to divide the w component by
     * @return This vector
     */
    @Contract(value = "_,_,_,_->this", mutates = "this")
    public float4 div(float x, float y, float z, float w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
        return this;
    }

    /**
     * Divides this vector component-wise by the given vector.
     *
     * @param v The vector to divide this vector by
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 div(constFloat4 v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        w /= v.w;
        return this;
    }

    /**
     * Divides this vector component-wise by the given vector.
     *
     * @param v The vector to divide this vector by
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 div(constInt4 v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        w /= v.w;
        return this;
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return This vector
     */
    @Contract(value = "_,_->this", mutates = "this")
    public float4 lerp(constFloat4 t, float a) {
        x += (t.x - x) * a;
        y += (t.y - y) * a;
        z += (t.z - z) * a;
        w += (t.w - w) * a;
        return this;
    }

    /**
     * Lerps this vector by the given amount towards the given target vector.
     *
     * @param t The target vector to lerp towards
     * @param a The amount to lerp. 0 means this vector, 1 means the target vector, 0.5 the average of them
     * @return This vector
     */
    @Contract(value = "_,_->this", mutates = "this")
    public float4 lerp(constInt4 t, float a) {
        x += (t.x - x) * a;
        y += (t.y - y) * a;
        z += (t.z - z) * a;
        w += (t.w - w) * a;
        return this;
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 project(constFloat4 onto) {
        float f = (x*onto.x + y*onto.y + z*onto.z + w*onto.w) / (onto.x*onto.x + onto.y*onto.y + onto.z*onto.z + onto.w*onto.w);
        x = onto.x * f;
        y = onto.y * f;
        z = onto.z * f;
        w = onto.w * f;
        return this;
    }

    /**
     * Projects this vector onto the given vector.
     *
     * @param onto The vector to project onto
     * @return This vector
     */
    @Contract(value = "_->this", mutates = "this")
    public float4 project(constInt4 onto) {
        float f = (x*onto.x + y*onto.y + z*onto.z + w*onto.w) / (onto.x*onto.x + onto.y*onto.y + onto.z*onto.z + onto.w*onto.w);
        x = onto.x * f;
        y = onto.y * f;
        z = onto.z * f;
        w = onto.w * f;
        return this;
    }

    /**
     * Clamps this vector component-wise within the given range.
     *
     * @param min The component-wise minimum value
     * @param max The component-wise maximum value
     * @return This vector
     */
    @Contract(value = "_,_->this", mutates = "this")
    @SuppressWarnings("SuspiciousNameCombination")
    public float4 clamp(constFloat4 min, constFloat4 max) {
        x = Mathf.clamp(x, min.x, max.x);
        y = Mathf.clamp(y, min.y, max.y);
        z = Mathf.clamp(z, min.z, max.z);
        w = Mathf.clamp(w, min.w, max.w);
        return this;
    }

    /**
     * Clamps this vector component-wise within the given range.
     *
     * @param min The component-wise minimum value
     * @param max The component-wise maximum value
     * @return This vector
     */
    @Contract(value = "_,_->this", mutates = "this")
    @SuppressWarnings("SuspiciousNameCombination")
    public float4 clamp(constInt4 min, constInt4 max) {
        x = Mathf.clamp(x, min.x, max.x);
        y = Mathf.clamp(y, min.y, max.y);
        z = Mathf.clamp(z, min.z, max.z);
        w = Mathf.clamp(w, min.w, max.w);
        return this;
    }


    /**
     * Returns a new zero vector.
     *
     * @return A new zero vector
     */
    @Contract(pure = true, value = "->new")
    public static float4 zero() {
        return new float4();
    }

    /**
     * Returns a new x-axis unit vector.
     *
     * @return A new x-axis unit vector
     */
    @Contract(pure = true, value = "->new")
    public static float4 X() {
        return new float4(1,0,0,0);
    }

    /**
     * Returns a new y-axis unit vector.
     *
     * @return A new y-axis unit vector
     */
    @Contract(pure = true, value = "->new")
    public static float4 Y() {
        return new float4(0,1,0,0);
    }

    /**
     * Returns a new z-axis unit vector.
     *
     * @return A new z-axis unit vector
     */
    @Contract(pure = true, value = "->new")
    public static float4 Z() {
        return new float4(0,0,1,0);
    }

    /**
     * Returns a new w-axis unit vector.
     *
     * @return A new w-axis unit vector
     */
    @Contract(pure = true, value = "->new")
    public static float4 W() {
        return new float4(0,0,0,1);
    }

    /**
     * Returns a new vector with all components set to 1.
     *
     * @return A new vector with all components set to 1
     */
    @Contract(pure = true, value = "->new")
    public static float4 one() {
        return new float4(1);
    }

    /**
     * Returns a new vector with all components set to -1.
     *
     * @return A new vector with all components set to -1
     */
    @Contract(pure = true, value = "->new")
    public static float4 minusOne() {
        return new float4(-1);
    }

    /**
     * Returns a new vector with all components set to {@link Float#POSITIVE_INFINITY}.
     *
     * @return A new vector with all components set to {@link Float#POSITIVE_INFINITY}
     */
    @Contract(pure = true, value = "->new")
    public static float4 inf() {
        return new float4(Float.POSITIVE_INFINITY);
    }

    /**
     * Returns a new vector with all components set to {@link Float#NEGATIVE_INFINITY}.
     *
     * @return A new vector with all components set to {@link Float#NEGATIVE_INFINITY}
     */
    @Contract(pure = true, value = "->new")
    public static float4 negInf() {
        return new float4(Float.NEGATIVE_INFINITY);
    }

    /**
     * Returns a new vector with all components set to {@link Float#NaN}.
     *
     * @return A new vector with all components set to {@link Float#NaN}
     */
    @Contract(pure = true, value = "->new")
    public static float4 NaN() {
        return new float4(Float.NaN);
    }

    /**
     * Creates a new vector from the given array.
     *
     * @param arr The array to read the components from. Must be at least of length 4
     * @return A new vector with the components from the array
     */
    @Contract(pure = true, value = "_->new")
    public static float4 fromArray(float[] arr) {
        if(Arguments.checkNull(arr, "arr").length < 4)
            throw new IllegalArgumentException("arr.length < 4");
        return new float4(arr[0], arr[1], arr[2], arr[3]);
    }

    /**
     * Creates a new vector from the given array.
     *
     * @param arr The array to read the components from. Must have at east 4 more elements
     *            starting from <code>offset</code>
     * @param offset The index of the x component in the array
     * @return A new vector with the components from the array
     */
    @Contract(pure = true, value = "_,_->new")
    public static float4 fromArray(float[] arr, int offset) {
        if(Arguments.checkNull(arr, "arr").length < offset + 4)
            throw new IllegalArgumentException("Range out of array bounds");
        return new float4(arr[0], arr[1], arr[2], arr[3]);
    }


    /**
     * Returns the component-wise minimum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise minimum
     */
    @Contract(pure = true, value = "_,_->new")
    public static float4 min(constFloat4 a, constFloat4 b) {
        return new float4(
                Math.min(a.x, b.x),
                Math.min(a.y, b.y),
                Math.min(a.z, b.z),
                Math.min(a.w, b.w)
        );
    }

    /**
     * Returns the component-wise maximum of the two vectors.
     *
     * @param a The first vector
     * @param b The second vector
     * @return A new vector with the component-wise maximum
     */
    @Contract(pure = true, value = "_,_->new")
    public static float4 max(constFloat4 a, constFloat4 b) {
        return new float4(
                Math.max(a.x, b.x),
                Math.max(a.y, b.y),
                Math.max(a.z, b.z),
                Math.max(a.w, b.w)
        );
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    @Contract(pure = true, value = "_,_->new")
    public static float4 between(constFloat4 a, constFloat4 b) {
        return new float4(b.x - a.x, b.y - a.y, b.z - a.z, b.w - a.w);
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    @Contract(pure = true, value = "_,_->new")
    public static float4 between(constFloat4 a, constInt4 b) {
        return new float4(b.x - a.x, b.y - a.y, b.z - a.z, b.w - a.w);
    }

    /**
     * Returns a new vector which represents the path from a to b.
     *
     * @param a The starting point
     * @param b The end point
     * @return <code>b - a</code>
     */
    @Contract(pure = true, value = "_,_->new")
    public static float4 between(constInt4 a, constFloat4 b) {
        return new float4(b.x - a.x, b.y - a.y, b.z - a.z, b.w - a.w);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    @Contract(pure = true, value = "_,_->new")
    public static float4 average(constFloat4 a, constFloat4 b) {
        return new float4((b.x + a.x) / 2, (b.y + a.y) / 2, (b.z + a.z) / 2, (b.w + a.w) / 2);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    @Contract(pure = true, value = "_,_->new")
    public static float4 average(constFloat4 a, constInt4 b) {
        return new float4((b.x + a.x) / 2, (b.y + a.y) / 2, (b.z + a.z) / 2, (b.w + a.w) / 2);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    @Contract(pure = true, value = "_,_->new")
    public static float4 average(constInt4 a, constFloat4 b) {
        return new float4((b.x + a.x) / 2, (b.y + a.y) / 2, (b.z + a.z) / 2, (b.w + a.w) / 2);
    }

    /**
     * Returns a new vector which represents the center between a to b.
     *
     * @param a The first point
     * @param b The second point
     * @return <code>(a+b) / 2</code>
     */
    @Contract(pure = true, value = "_,_->new")
    public static float4 average(constInt4 a, constInt4 b) {
        return new float4((b.x + a.x) / 2f, (b.y + a.y) / 2f, (b.z + a.z) / 2f, (b.w + a.w) / 2f);
    }
}
