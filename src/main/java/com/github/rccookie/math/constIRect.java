package com.github.rccookie.math;

import java.util.Objects;

import com.github.rccookie.json.JsonArray;
import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.json.JsonSerializable;
import com.github.rccookie.util.Cloneable;

import org.jetbrains.annotations.NotNull;

/**
 * An axis-aligned integer rectangle with read-only access.
 */
@SuppressWarnings("DuplicatedCode")
public class constIRect implements Cloneable<IRect>, JsonSerializable {

    static {
        JsonDeserialization.register(constIRect.class, json -> new constIRect(json.get(0).as(constInt2.class), json.get(1).as(constInt2.class), true));
    }

    /**
     * An empty rectangle at (0,0).
     */
    public static final constIRect ZERO = new constIRect(constInt2.zero, constInt2.zero, true);
    /**
     * A rectangle covering all (positive and negative) space.
     */
    public static final constIRect MAX = new constIRect(constInt2.min, constInt2.max, true);

    /**
     * The lower and upper bounds of the rectangle.
     */
    final constInt2 min, max;

    /**
     * Creates a new rectangle.
     *
     * @param min The lower bounds of the rectangle
     * @param max The upper bounds of the rectangle
     */
    public constIRect(constInt2 min, constInt2 max) {
        this.min = min.clone();
        this.max = max.clone();
    }

    /**
     * Creates a new rectangle.
     *
     * @param minX The lower x bounds of the rectangle
     * @param minY The lower y bounds of the rectangle
     * @param maxX The upper x bounds of the rectangle
     * @param maxY The upper y bounds of the rectangle
     */
    public constIRect(int minX, int minY, int maxX, int maxY) {
        min = new constInt2(minX, minY);
        max = new constInt2(maxX, maxY);
    }

    constIRect(constInt2 minRef, constInt2 maxRef, boolean ignored) {
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
        return obj instanceof constIRect && ((constIRect) obj).min.equals(min) && ((constIRect) obj).max.equals(max);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(min, max);
    }

    /**
     * Returns a (deep) copy of this rectangle.
     *
     * @return A new identical rectangle
     */
    @Override
    public final @NotNull IRect clone() {
        return new IRect(min, max);
    }

    @Override
    public final Object toJson() {
        return new JsonArray(min, max);
    }

    /**
     * Returns this rectangle as a float rectangle.
     *
     * @return This rectangle as a {@link Rect}
     */
    public final Rect toF() {
        return new Rect(min.toF(), max.toF(), true);
    }

    /**
     * Returns the lower bounds of this rectangle.
     *
     * @return The lower bounds of the rectangle
     */
    public constInt2 min() {
        return min;
    }

    /**
     * Returns the upper bounds of this rectangle.
     *
     * @return The upper bounds of the rectangle
     */
    public constInt2 max() {
        return max;
    }

    /**
     * Returns (min.x, max.y). In a mathematical coordinate system this is the
     * top left corner, in common graphics coordinate systems (y-axis flipped) this
     * is the bottom left corner.
     *
     * @return (min.x, max.y)
     */
    public final int2 minMax() {
        return new int2(min.x, max.y);
    }

    /**
     * Returns (max.x, min.y). In a mathematical coordinate system this is the
     * bottom right corner, in common graphics coordinate systems (y-axis flipped) this
     * is the top right corner.
     *
     * @return (max.x, min.y)
     */
    public final int2 maxMin() {
        return new int2(max.x, min.y);
    }

    /**
     * Returns the size of this rectangle.
     *
     * @return The size of the rectangle
     */
    public final int2 size() {
        return max.subed(min);
    }

    /**
     * Returns the center of this rectangle.
     *
     * @return The center of the rectangle
     */
    public final float2 center() {
        return float2.average(min, max);
    }

    /**
     * Returns the extents (the half size) of this rectangle.
     *
     * @return The extents (the half size) of the rectangle
     */
    public final float2 extents() {
        return max.toF().sub(min).scale(0.5f);
    }

    /**
     * Returns the area this rectangle covers.
     *
     * @return The rectangle's area
     */
    public final int area() {
        return (max.x - min.x) * (max.y - min.y);
    }

    /**
     * Returns whether this rectangle contains the given point.
     * Laying on the border does count as contained.
     *
     * @param p The point to test
     * @return Whether the point is within this rectangle
     */
    public final boolean contains(constInt2 p) {
        return min.leq(p) && max.geq(p);
    }

    /**
     * Returns whether this rectangle contains the given point.
     * Laying on the border does count as contained.
     *
     * @param p The point to test
     * @return Whether the point is within this rectangle
     */
    public final boolean contains(constFloat2 p) {
        return min.leq(p) && max.geq(p);
    }

    /**
     * Returns whether this rectangle fully contains the given rectangle.
     * Laying on the border does count as contained.
     *
     * @param r The rectangle to test
     * @return Whether the rectangle is within this rectangle
     */
    public final boolean contains(constIRect r) {
        return min.leq(r.min) && max.geq(r.max);
    }

    /**
     * Returns whether this rectangle partially contains the given rectangle.
     * Overlapping with the border does count as overlapping.
     *
     * @param r The rectangle to test
     * @return Whether the rectangle overlaps this rectangle
     */
    public final boolean overlaps(constIRect r) {
        return r.min.x <= max.x && r.min.y <= min.y &&
               r.max.x >= min.x && r.max.y >= max.y;
    }

    /**
     * Returns the area where this rectangle overlaps the given rectangle, or <code>null</code>
     * if they don't overlap. Overlapping with the border does count as overlapping.
     *
     * @param r The rectangle to get the overlap with
     * @return The overlapping area, or <code>null</code>
     */
    public final IRect overlap(constIRect r) {
        int2 min = int2.max(this.min, r.min);
        int2 max = int2.min(this.max, r.max);
        return min.x <= max.x && min.y <= max.y ? new IRect(min, max, true) : null;
    }

    /**
     * Returns the closest point on the border of this rectangle to the given point. If the point is
     * within the rectangle, the returned point will still lay on the edge of the rectangle (otherwise
     * one may just use {@link constInt2#clamped(constIRect)}).
     *
     * @param p The point to find the closest point on this rectangle to
     * @return The closest point on this rectangle to the given point
     */
    public final int2 closestPoint(constInt2 p) {
        if(min.geq(p) || max.leq(p)) // === !contains(p) || p on border
            return p.clamped(this);
        return new int2(
                p.x - min.x < max.x - p.x ? min.x : max.x,
                p.y - min.y < max.y - p.y ? min.y : max.y
        );
    }

    /**
     * Returns the closest point on the border of this rectangle to the given point. If the point is
     * within the rectangle, the returned point will still lay on the edge of the rectangle (otherwise
     * one may just use {@link constFloat2#clamped(constIRect)}).
     *
     * @param p The point to find the closest point on this rectangle to
     * @return The closest point on this rectangle to the given point
     */
    public final float2 closestPoint(constFloat2 p) {
        if(min.geq(p) || max.leq(p)) // === !contains(p) || p on border
            return p.clamped(this);
        return new float2(
                p.x - min.x < max.x - p.x ? min.x : max.x,
                p.y - min.y < max.y - p.y ? min.y : max.y
        );
    }

    /**
     * Returns the closest point on the border of this rectangle to the given rectangle. If the
     * rectangle is within this rectangle, the returned point will still lay on the edge of the
     * rectangle.
     *
     * @param r The rectangle to find the closest point on this rectangle to
     * @return The closest point on this rectangle to the given rectangle
     */
    public final int2 closestPoint(constIRect r) {
        return new int2(
                Mathf.closestPoint(r.min.x, r.max.x, min.x, max.x),
                Mathf.closestPoint(r.min.y, r.max.y, min.y, max.y)
        );
    }

    /**
     * Returns the smallest rectangle which contains both this and the given rectangle.
     *
     * @param r The rectangle to include
     * @return A new rectangle containing both this and the given rectangle
     */
    public final IRect containing(constIRect r) {
        return new IRect(int2.min(min, r.min), int2.max(max, r.max), true);
    }

    /**
     * Returns the smallest rectangle which contains this rectangle and the given point.
     *
     * @param p The point to include
     * @return A new rectangle including both this rectangle and the given point
     */
    public final IRect containing(constInt2 p) {
        return new IRect(int2.min(min, p), int2.max(max, p), true);
    }

    /**
     * Returns this rectangle with its size scaled from its lower bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return The scaled rectangle
     */
    public final IRect scaledFromMin(int f) {
        int2 size = size().scale(f);
        return new IRect(min.clone(), min.added(size), true);
    }

    /**
     * Returns this rectangle with its size scaled from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled rectangle
     */
    public final IRect scaledFromMin(constInt2 f) {
        int2 size = size().scale(f);
        return new IRect(min.clone(), min.added(size), true);
    }

    /**
     * Returns this rectangle with its size scaled from its upper bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return The scaled rectangle
     */
    public final IRect scaledFromMax(int f) {
        int2 size = size().scale(f);
        return new IRect(max.subed(size), max.clone(), true);
    }

    /**
     * Returns this rectangle with its size scaled from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled rectangle
     */
    public final IRect scaledFromMax(constInt2 f) {
        int2 size = size().scale(f);
        return new IRect(max.subed(size), max.clone(), true);
    }

    /**
     * Returns whether this rectangle is valid. A rectangle is valid its lower bounds are less or equal
     * to its upper bounds (-> its size is non-negative).
     *
     * @return Whether this rectangle is valid
     */
    public final boolean isValid() {
        return min.leq(max);
    }

    /**
     * Returns the length of the border of this rectangle.
     *
     * @return The rectangle's border length
     */
    public final float borderLength() {
        return 2 * (max.x - min.x + max.y - min.y);
    }

    /**
     * Returns the corners of this rectangle.
     *
     * @return The vertices of this rectangle
     */
    public final int2[] vertices() {
        return new int2[] { min.clone(), new int2(max.x, min.y), max.clone(), new int2(min.x, max.y) };
    }
}
