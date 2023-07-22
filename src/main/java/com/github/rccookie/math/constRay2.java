package com.github.rccookie.math;

import java.util.Objects;

import com.github.rccookie.json.JsonObject;
import com.github.rccookie.json.JsonSerializable;
import com.github.rccookie.util.Cloneable;

import org.jetbrains.annotations.NotNull;

/**
 * A 2D ray (an infinite line starting at a point) with read-only access.
 */
public class constRay2 implements Cloneable<Ray2>, JsonSerializable {

    /**
     * The x-axis ray.
     */
    public static final constRay2 X = new constRay2(float2.zero, float2.X, true);
    /**
     * The y-axis ray.
     */
    public static final constRay2 Y = new constRay2(float2.zero, float2.Y, true);

    final constFloat2 origin;
    final constFloat2 direction;

    /**
     * Creates a new ray.
     *
     * @param origin The origin for the ray
     * @param direction The direction for the ray, will be normalized
     */
    public constRay2(constFloat2 origin, constFloat2 direction) {
        this.origin = origin.toConst();
        this.direction = direction.normed();
    }

    constRay2(constFloat2 originRef, constFloat2 directionRef, boolean ignored) {
        origin = originRef;
        direction = directionRef;
    }

    /**
     * Returns a string representation of this ray.
     *
     * @return This ray as string
     */
    @Override
    public String toString() {
        return "Ray{ " + origin + " -> " + direction + " }";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof constRay2 && origin.equals(((constRay2) obj).origin) && direction.equals(((constRay2) obj).direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, direction);
    }

    /**
     * Returns a copy of this ray.
     *
     * @return A new ray with identical values
     */
    @Override
    public @NotNull Ray2 clone() {
        return new Ray2(origin, direction);
    }

    @Override
    public Object toJson() {
        return new JsonObject("origin", origin, "direction", direction);
    }

    /**
     * Returns the origin of the ray.
     *
     * @return The origin of the ray
     */
    public constFloat2 origin() {
        return origin;
    }

    /**
     * Returns the direction vector of the ray, which should usually be normalized.
     *
     * @return The direction vector of the ray
     */
    public constFloat2 direction() {
        return direction;
    }

    /**
     * Returns the point at the specified position along the ray. If the direction vector
     * is normalized, the position is the distance from the origin along the ray direction
     * of the point to receive.
     *
     * @param position The position along the ray to get the point for, if negative
     *                 the result will not be on the ray
     * @return The point at that position along the ray
     */
    public float2 getPoint(float position) {
        return direction.scaled(position).add(origin);
    }

    /**
     * Returns whether the specified point is laying on this ray.
     *
     * @param p The point to test for containment
     * @return Whether the point is laying on this ray
     */
    public boolean contains(constFloat2 p) {
        if(direction.x == 0) return p.x == origin.x;
        if(direction.y == 0) return p.y == origin.y;
        float2 index = p.subed(origin).div(direction);
        return index.x == index.y && index.x >= 0;
    }

    /**
     * Returns a copy of this ray where the direction vector is normalized.
     *
     * @return A new ray with a normalized direction vector in the same direction as this direction vector
     */
    public Ray2 normed() {
        return new Ray2(origin, direction);
    }
}
