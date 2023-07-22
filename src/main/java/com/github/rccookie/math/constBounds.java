package com.github.rccookie.math;

import java.util.Objects;

import com.github.rccookie.json.JsonArray;
import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.json.JsonSerializable;
import com.github.rccookie.util.Cloneable;

import org.jetbrains.annotations.NotNull;

/**
 * An axis-aligned bounding box with read-only access.
 */
@SuppressWarnings("DuplicatedCode")
public class constBounds implements Cloneable<Bounds>, JsonSerializable {

    static {
        JsonDeserialization.register(constBounds.class, json -> new constBounds(json.get(0).as(constFloat3.class), json.get(1).as(constFloat3.class), true));
    }

    /**
     * An empty bounding box at (0,0,0).
     */
    public static final constBounds ZERO = new constBounds(constFloat3.zero, constFloat3.zero, true);
    /**
     * A bounding box covering all (positive and negative) space.
     */
    public static final constBounds INF = new constBounds(constFloat3.negInf, constFloat3.inf, true);

    /**
     * The lower and upper bounds of the bounding box.
     */
    final constFloat3 min, max;

    /**
     * Creates a new bounding box.
     *
     * @param min The lower bounds of the bounding box
     * @param max The upper bounds of the bounding box
     */
    public constBounds(constFloat3 min, constFloat3 max) {
        this.min = min.toConst();
        this.max = max.toConst();
    }

    /**
     * Creates a new bounding box.
     *
     * @param min The lower bounds of the bounding box
     * @param max The upper bounds of the bounding box
     */
    public constBounds(constFloat3 min, constInt3 max) {
        this.min = min.toConst();
        this.max = max.toConstF();
    }

    /**
     * Creates a new bounding box.
     *
     * @param min The lower bounds of the bounding box
     * @param max The upper bounds of the bounding box
     */
    public constBounds(constInt3 min, constFloat3 max) {
        this.min = min.toConstF();
        this.max = max.toConst();
    }

    /**
     * Creates a new bounding box.
     *
     * @param min The lower bounds of the bounding box
     * @param max The upper bounds of the bounding box
     */
    public constBounds(constInt3 min, constInt3 max) {
        this.min = min.toConstF();
        this.max = max.toConstF();
    }

    /**
     * Creates a new bounding box.
     *
     * @param minX The lower x bounds of the bounding box
     * @param minY The lower y bounds of the bounding box
     * @param minZ The lower z bounds of the bounding box
     * @param maxX The upper x bounds of the bounding box
     * @param maxY The upper y bounds of the bounding box
     * @param maxZ The upper z bounds of the bounding box
     */
    public constBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        min = new constFloat3(minX, minY, minZ);
        max = new constFloat3(maxX, maxY, maxZ);
    }

    constBounds(constFloat3 minRef, constFloat3 maxRef, boolean ignored) {
        min = minRef;
        max = maxRef;
    }

    @Override
    public final String toString() {
        if(min.equals(max))
            return min.isZero() ? "[0]" : "[0 @ "+min+"]";
        return "["+min+" - "+max+"]";
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof constBounds && ((constBounds) obj).min.equals(min) && ((constBounds) obj).max.equals(max);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(min, max);
    }

    /**
     * Returns a (deep) copy of this bounding box.
     *
     * @return A new identical bounding box
     */
    @Override
    public final @NotNull Bounds clone() {
        return new Bounds(min, max);
    }

    @Override
    public final Object toJson() {
        return new JsonArray(min, max);
    }

    /**
     * Casts this bounding box component-wise to an int bounding box.
     *
     * @return This bounding box as {@link IBounds}
     */
    public final IBounds toI() {
        return new IBounds(min.toI(), max.toI(), true);
    }

    /**
     * Returns the lower bounds of this bounding box.
     *
     * @return The lower bounds of the bounding box
     */
    public constFloat3 min() {
        return min;
    }

    /**
     * Returns the upper bounds of this bounding box.
     *
     * @return The upper bounds of the bounding box
     */
    public constFloat3 max() {
        return max;
    }

    /**
     * Returns the size of this bounding box.
     *
     * @return The size of the bounding box
     */
    public final float3 size() {
        return max.subed(min);
    }

    /**
     * Returns the center of this bounding box.
     *
     * @return The center of the bounding box
     */
    public final float3 center() {
        return float3.average(min, max);
    }

    /**
     * Returns the extents (the half size) of this bounding box.
     *
     * @return The extents (the half size) of the bounding box
     */
    public final float3 extents() {
        return size().scale(0.5f);
    }

    /**
     * Returns the area this bounding box covers.
     *
     * @return The bounding box's area
     */
    public final float area() {
        return (max.x - min.x) * (max.y - min.y);
    }

    /**
     * Returns whether this bounding box contains the given point.
     * Laying on the border does count as contained.
     *
     * @param p The point to test
     * @return Whether the point is within this bounding box
     */
    public final boolean contains(constFloat3 p) {
        return min.leq(p) && max.geq(p);
    }

    /**
     * Returns whether this bounding box contains the given point.
     * Laying on the border does count as contained.
     *
     * @param p The point to test
     * @return Whether the point is within this bounding box
     */
    public final boolean contains(constInt3 p) {
        return min.leq(p) && max.geq(p);
    }

    /**
     * Returns whether this bounding box fully contains the given bounding box.
     * Laying on the border does count as contained.
     *
     * @param r The bounding box to test
     * @return Whether the bounding box is within this bounding box
     */
    public final boolean contains(constBounds r) {
        return min.leq(r.min) && max.geq(r.max);
    }

    /**
     * Returns whether this bounding box partially contains the given bounding box.
     * Overlapping with the border does count as overlapping.
     *
     * @param r The bounding box to test
     * @return Whether the bounding box overlaps this bounding box
     */
    public final boolean overlaps(constBounds r) {
        return r.min.x <= max.x && r.min.y <= min.y &&
               r.max.x >= min.x && r.max.y >= max.y;
    }

    /**
     * Returns the area where this bounding box overlaps the given bounding box, or <code>null</code>
     * if they don't overlap. Overlapping with the border does count as overlapping.
     *
     * @param r The bounding box to get the overlap with
     * @return The overlapping area, or <code>null</code>
     */
    public final Bounds overlap(constBounds r) {
        float3 min = float3.max(this.min, r.min);
        float3 max = float3.min(this.max, r.max);
        return min.x <= max.x && min.y <= max.y ? new Bounds(min, max, true) : null;
    }

    /**
     * Returns the closest point on the border of this bounding box to the given point. If the point is
     * within the bounding box, the returned point will still lay on the edge of the bounding box (otherwise
     * one may just use {@link constFloat3#clamped(constBounds)}).
     *
     * @param p The point to find the closest point on this bounding box to
     * @return The closest point on this bounding box to the given point
     */
    public final float3 closestPoint(constFloat3 p) {
        if(min.geq(p) || max.leq(p)) // === !contains(p) || p on border
            return p.clamped(this);
        return new float3(
                p.x - min.x < max.x - p.x ? min.x : max.x,
                p.y - min.y < max.y - p.y ? min.y : max.y,
                p.z - min.z < max.z - p.z ? min.z : max.z
        );
    }

    /**
     * Returns the closest point on the border of this bounding box to the given point. If the point is
     * within the bounding box, the returned point will still lay on the edge of the bounding box (otherwise
     * one may just use {@link constInt3#clamped(constBounds)}).
     *
     * @param p The point to find the closest point on this bounding box to
     * @return The closest point on this bounding box to the given point
     */
    public final float3 closestPoint(constInt3 p) {
        if(min.geq(p) || max.leq(p)) // === !contains(p) || p on border
            return p.clamped(this);
        return new float3(
                p.x - min.x < max.x - p.x ? min.x : max.x,
                p.y - min.y < max.y - p.y ? min.y : max.y,
                p.z - min.z < max.z - p.z ? min.z : max.z
        );
    }

    /**
     * Returns the closest point on the border of this bounding box to the given bounding box. If the
     * bounding box is within this bounding box, the returned point will still lay on the edge of the
     * bounding box.
     *
     * @param r The bounding box to find the closest point on this bounding box to
     * @return The closest point on this bounding box to the given bounding box
     */
    public final float3 closestPoint(constBounds r) {
        return new float3(
                Mathf.closestPoint(r.min.x, r.max.x, min.x, max.x),
                Mathf.closestPoint(r.min.y, r.max.y, min.y, max.y),
                Mathf.closestPoint(r.min.z, r.max.z, min.z, max.z)
        );
    }

    /**
     * Returns the smallest bounding box which contains both this and the given bounding box.
     *
     * @param r The bounding box to include
     * @return A new bounding box containing both this and the given bounding box
     */
    public final Bounds containing(constBounds r) {
        return new Bounds(float3.min(min, r.min), float3.max(max, r.max), true);
    }

    /**
     * Returns the smallest bounding box which contains this bounding box and the given point.
     *
     * @param p The point to include
     * @return A new bounding box including both this bounding box and the given point
     */
    public final Bounds containing(constFloat3 p) {
        return new Bounds(float3.min(min, p), float3.max(max, p), true);
    }

    /**
     * Returns this bounding box with its size scaled from its center point by the given factor.
     *
     * @param f The factor to scale by
     * @return The scaled bounding box
     */
    public final Bounds scaled(float f) {
        float3 e = extents();
        float3 c = min.added(e);
        e.scale(f);
        return new Bounds(c.subed(e), c.add(e), true);
    }

    /**
     * Returns this bounding box with its size scaled from its center point by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled bounding box
     */
    public final Bounds scaled(constFloat3 f) {
        float3 e = extents();
        float3 c = min.added(e);
        e.scale(f);
        return new Bounds(c.subed(e), c.add(e), true);
    }

    /**
     * Returns this bounding box with its size scaled from its center point by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled bounding box
     */
    public final Bounds scaled(constInt3 f) {
        float3 e = extents();
        float3 c = min.added(e);
        e.scale(f);
        return new Bounds(c.subed(e), c.add(e), true);
    }

    /**
     * Returns this bounding box with its size scaled from its lower bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return The scaled bounding box
     */
    public final Bounds scaledFromMin(float f) {
        float3 size = size().scale(f);
        return new Bounds(min.clone(), min.added(size), true);
    }

    /**
     * Returns this bounding box with its size scaled from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled bounding box
     */
    public final Bounds scaledFromMin(constFloat3 f) {
        float3 size = size().scale(f);
        return new Bounds(min.clone(), min.added(size), true);
    }

    /**
     * Returns this bounding box with its size scaled from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled bounding box
     */
    public final Bounds scaledFromMin(constInt3 f) {
        float3 size = size().scale(f);
        return new Bounds(min.clone(), min.added(size), true);
    }

    /**
     * Returns this bounding box with its size scaled from its upper bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return The scaled bounding box
     */
    public final Bounds scaledFromMax(float f) {
        float3 size = size().scale(f);
        return new Bounds(max.subed(size), max.clone(), true);
    }

    /**
     * Returns this bounding box with its size scaled from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled bounding box
     */
    public final Bounds scaledFromMax(constFloat3 f) {
        float3 size = size().scale(f);
        return new Bounds(max.subed(size), max.clone(), true);
    }

    /**
     * Returns this bounding box with its size scaled from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled bounding box
     */
    public final Bounds scaledFromMax(constInt3 f) {
        float3 size = size().scale(f);
        return new Bounds(max.subed(size), max.clone(), true);
    }

    /**
     * Returns whether this bounding box is valid. A bounding box is valid its lower bounds are less or equal
     * to its upper bounds (-> its size is non-negative). This especially requires all components to not
     * be {@link Float#NaN}.
     *
     * @return Whether this bounding box is valid
     */
    public final boolean isValid() {
        return min.leq(max);
    }

    /**
     * Returns the corners of this bounding box.
     *
     * @return The vertices of this bounding box
     */
    public final float3[] vertices() {
        return new float3[] {
                min.clone(), new float3(max.x, min.y, min.z), new float3(max.x, max.y, min.z), new float3(min.x, max.y, min.z),
                new float3(min.x, min.y, max.z), new float3(max.x, min.y, max.z), max.clone(), new float3(min.x, max.y, max.z)
        };
    }
}
