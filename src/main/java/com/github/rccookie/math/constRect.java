package com.github.rccookie.math;

import java.util.Objects;

import com.github.rccookie.json.JsonArray;
import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.json.JsonSerializable;
import com.github.rccookie.math.collision.Raycast2D;
import com.github.rccookie.math.collision.Shape2D;
import com.github.rccookie.math.collision.Manifold2D;
import com.github.rccookie.util.Cloneable;

import org.jetbrains.annotations.NotNull;

/**
 * An axis-aligned rectangle with read-only access.
 */
@SuppressWarnings("DuplicatedCode")
public class constRect implements Cloneable<Rect>, JsonSerializable, Shape2D {

    static {
        JsonDeserialization.register(constRect.class, json -> new constRect(json.get(0).as(constFloat2.class), json.get(1).as(constFloat2.class), true));
    }

    /**
     * An empty rectangle at (0,0).
     */
    public static final constRect ZERO = new constRect(constFloat2.zero, constFloat2.zero, true);
    /**
     * A rectangle covering all (positive and negative) space.
     */
    public static final constRect INF = new constRect(constFloat2.negInf, constFloat2.inf, true);

    /**
     * The lower and upper bounds of the rectangle.
     */
    final constFloat2 min, max;

    /**
     * Creates a new rectangle.
     *
     * @param min The lower bounds of the rectangle
     * @param max The upper bounds of the rectangle
     */
    public constRect(constFloat2 min, constFloat2 max) {
        this.min = min.toConst();
        this.max = max.toConst();
    }

    /**
     * Creates a new rectangle.
     *
     * @param min The lower bounds of the rectangle
     * @param max The upper bounds of the rectangle
     */
    public constRect(constFloat2 min, constInt2 max) {
        this.min = min.toConst();
        this.max = max.toConstF();
    }

    /**
     * Creates a new rectangle.
     *
     * @param min The lower bounds of the rectangle
     * @param max The upper bounds of the rectangle
     */
    public constRect(constInt2 min, constFloat2 max) {
        this.min = min.toConstF();
        this.max = max.toConst();
    }

    /**
     * Creates a new rectangle.
     *
     * @param min The lower bounds of the rectangle
     * @param max The upper bounds of the rectangle
     */
    public constRect(constInt2 min, constInt2 max) {
        this.min = min.toConstF();
        this.max = max.toConstF();
    }

    /**
     * Creates a new rectangle.
     *
     * @param minX The lower x bounds of the rectangle
     * @param minY The lower y bounds of the rectangle
     * @param maxX The upper x bounds of the rectangle
     * @param maxY The upper y bounds of the rectangle
     */
    public constRect(float minX, float minY, float maxX, float maxY) {
        min = new constFloat2(minX, minY);
        max = new constFloat2(maxX, maxY);
    }

    constRect(constFloat2 minRef, constFloat2 maxRef, boolean ignored) {
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
        return obj instanceof constRect && ((constRect) obj).min.equals(min) && ((constRect) obj).max.equals(max);
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
    public final @NotNull Rect clone() {
        return new Rect(min, max);
    }

    @Override
    public final Object toJson() {
        return new JsonArray(min, max);
    }

    /**
     * Casts this rectangle component-wise to an int rectangle.
     *
     * @return This rectangle as {@link IRect}
     */
    public final IRect toI() {
        return new IRect(min.toI(), max.toI(), true);
    }

    /**
     * Returns the lower bounds of this rectangle.
     *
     * @return The lower bounds of the rectangle
     */
    public constFloat2 min() {
        return min;
    }

    /**
     * Returns the upper bounds of this rectangle.
     *
     * @return The upper bounds of the rectangle
     */
    public constFloat2 max() {
        return max;
    }

    /**
     * Returns (min.x, max.y). In a mathematical coordinate system this is the
     * top left corner, in common graphics coordinate systems (y-axis flipped) this
     * is the bottom left corner.
     *
     * @return (min.x, max.y)
     */
    public final float2 minMax() {
        return new float2(min.x, max.y);
    }

    /**
     * Returns (max.x, min.y). In a mathematical coordinate system this is the
     * bottom right corner, in common graphics coordinate systems (y-axis flipped) this
     * is the top right corner.
     *
     * @return (max.x, min.y)
     */
    public final float2 maxMin() {
        return new float2(max.x, min.y);
    }

    /**
     * Returns the size of this rectangle.
     *
     * @return The size of the rectangle
     */
    public final float2 size() {
        return float2.between(min, max);
    }

    /**
     * Returns the center of this rectangle.
     *
     * @return The center of the rectangle
     */
    @Override
    public final float2 center() {
        return float2.average(min, max);
    }

    /**
     * Returns the extents (the half size) of this rectangle.
     *
     * @return The extents (the half size) of the rectangle
     */
    public final float2 extents() {
        return size().scale(0.5f);
    }

    /**
     * Returns the area this rectangle covers.
     *
     * @return The rectangle's area
     */
    public final float area() {
        return (max.x - min.x) * (max.y - min.y);
    }

    @Override
    public final constRect bounds() {
        return this;
    }

    /**
     * Returns whether this rectangle contains the given point.
     * Laying on the border does count as contained.
     *
     * @param p The point to test
     * @return Whether the point is within this rectangle
     */
    @Override
    public final boolean contains(constFloat2 p) {
        return min.leq(p) && max.geq(p);
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
     * Returns whether this rectangle fully contains the given rectangle.
     * Laying on the border does count as contained.
     *
     * @param r The rectangle to test
     * @return Whether the rectangle is within this rectangle
     */
    public final boolean contains(constRect r) {
        return min.leq(r.min) && max.geq(r.max);
    }

    @Override
    public final boolean contains(Shape2D s) {
        if(s instanceof constRect)
            return contains((constRect) s);
        throw new UnsupportedOperationException("Containment test of Rect and "+ s.getClass().getSimpleName());
    }

    /**
     * Returns whether this rectangle partially contains the given rectangle.
     * Overlapping with the border does count as overlapping.
     *
     * @param r The rectangle to test
     * @return Whether the rectangle overlaps this rectangle
     */
    @Override
    public final boolean overlaps(constRect r) {
        return r.min.x <= max.x && r.min.y <= min.y &&
               r.max.x >= min.x && r.max.y >= max.y;
    }

    @Override
    public final boolean overlaps(Shape2D s) {
        if(s instanceof constRect)
            return overlaps((constRect) s);
        return s.overlaps(this);
    }

    /**
     * Returns the area where this rectangle overlaps the given rectangle, or <code>null</code>
     * if they don't overlap. Overlapping with the border does count as overlapping.
     *
     * @param r The rectangle to get the overlap with
     * @return The overlapping area, or <code>null</code>
     */
    public final Rect overlap(constRect r) {
        float2 min = float2.max(this.min, r.min);
        float2 max = float2.min(this.max, r.max);
        return min.x <= max.x && min.y <= max.y ? new Rect(min, max, true) : null;
    }

    /**
     * Returns the closest point on the border of this rectangle to the given point. If the point is
     * within the rectangle, the returned point will still lay on the edge of the rectangle (otherwise
     * one may just use {@link constFloat2#clamped(constRect)}).
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
     * Returns the closest point on the border of this rectangle to the given point. If the point is
     * within the rectangle, the returned point will still lay on the edge of the rectangle (otherwise
     * one may just use {@link constInt2#clamped(constRect)}).
     *
     * @param p The point to find the closest point on this rectangle to
     * @return The closest point on this rectangle to the given point
     */
    public final float2 closestPoint(constInt2 p) {
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
    public final float2 closestPoint(constRect r) {
        return new float2(
                Mathf.closestPoint(r.min.x, r.max.x, min.x, max.x),
                Mathf.closestPoint(r.min.y, r.max.y, min.y, max.y)
        );
    }

    @Override
    public final Manifold2D contactWith(Shape2D s) {
        if(s instanceof constRect) {
            constRect r = (constRect) s;
            Rect overlap = overlap(r);

            if(overlap == null)
                return null;
            if(equals(overlap)) {
                float2 pointOnB = r.closestPoint(center());
                float2 pointOnA = center().scale(2).sub(pointOnB).clamp(this);
                return new Manifold2D(this, r, new Manifold2D.Point(pointOnA, pointOnB));
            }
            if(r.equals(overlap)) {
                float2 pointOnA = closestPoint(r.center());
                float2 pointOnB = r.center().scale(2).sub(pointOnA).clamp(r);
                return new Manifold2D(this, r, new Manifold2D.Point(pointOnA, pointOnB));
            }

            constFloat2 inner1 = null, inner2 = null;
            for(float2 v : overlap.vertices()) {
                if(v.x == min.x || v.x == max.x || v.y == min.y || v.y == max.y)
                    continue;
                if(inner1 == null) inner1 = v;
                else {
                    inner2 = v;
                    break;
                }
            }
            if(inner1 == null) { // Rectangles "cross over"
                float4 diff = new float4(max.subed(r.min), r.max.subed(min));
                switch(Mathf.minIndex(diff)) {
                    case 0: return new Manifold2D(this, r,
                            new Manifold2D.Point(overlap.maxMin(), overlap.maxMin().subed(diff.x, 0)),
                            new Manifold2D.Point(overlap.max, overlap.max.subed(diff.x, 0)));
                    case 1: return new Manifold2D(this, r,
                            new Manifold2D.Point(overlap.minMax(), overlap.minMax().subed(0, diff.y)),
                            new Manifold2D.Point(overlap.max, overlap.max.subed(0, diff.y)));
                    case 2: return new Manifold2D(this, r,
                            new Manifold2D.Point(overlap.min, overlap.min.added(diff.z, 0)),
                            new Manifold2D.Point(overlap.minMax(), overlap.minMax().added(diff.z, 0)));
                    case 3: return new Manifold2D(this, r,
                            new Manifold2D.Point(overlap.min, overlap.min.added(0, diff.w)),
                            new Manifold2D.Point(overlap.maxMin(), overlap.maxMin().added(0, diff.w)));
                    default: throw new AssertionError();
                }
            }
            if(inner2 == null)
                return new Manifold2D(this, r, new Manifold2D.Point(overlap.center().sub(inner1), inner1));
            return new Manifold2D(this, r,
                    new Manifold2D.Point(overlap.center().sub(inner1), inner1),
                    new Manifold2D.Point(overlap.center().sub(inner2), inner2));
        }
        return s.contactWith(this);
    }

    @Override
    public final boolean intersects(constRay2 ray, float maxLength) {
        return intersection(ray) <= maxLength;
    }

    /**
     * Calculates the distance along the ray where the ray intersects the rectangle.
     * If the ray does not hit the rectangle, {@link Float#POSITIVE_INFINITY} will be returned.
     * If the ray starts within the rectangle, 0 will be returned.
     *
     * @param ray The ray to test for intersection
     * @return The factor such that <code>ray.origin + intersection(ray) * ray.direction</code> is the intersection point
     */
    @SuppressWarnings("ManualMinMaxCalculation")
    public final float intersection(constRay2 ray) {
        float i1 = (min.x - ray.origin.x) / ray.direction.x;
        float i3 = (min.y - ray.origin.y) / ray.direction.y;
        float i2 = (max.x - ray.origin.x) / ray.direction.x;
        float i4 = (max.y - ray.origin.y) / ray.direction.y;

        float iMin = Mathf.max(i1 < i2 ? i1 : i2, i3 < i4 ? i3 : i4);
        float iMax = Mathf.min(i1 > i2 ? i1 : i2, i3 > i4 ? i3 : i4);

        if(iMin > iMax || iMax < 0) return Float.POSITIVE_INFINITY;

        return iMin > 0 ? iMin : 0;
    }

    @Override
    public Raycast2D raycast(constRay2 ray, float maxLength) {
        // Will be reused later
        float x = max.x - min.x, y = max.y - min.y;
        float xPart = x / (2 * (x + y));

        if(ray.direction.x != 0) {
            if(ray.origin.x <= min.x) {
                //   |
                //   +----+
                // x |    |
                //   +----+
                //   |
                float ri = (min.x - ray.origin.x) / ray.direction.x;
                if(ri < 0) return null; // Points away from rect, can't hit other sides

                y = ray.origin.y + ri * ray.direction.y;
                if(y >= min.y && y <= max.y) {
                    if(ri > maxLength) return null;
                    float i = (y - min.y) / (max.y - min.y);
                    return new Raycast2D(this, ray, ri, i * (0.5f - xPart) + 0.5f + xPart, float2.X.negated());
                }
            } else if(ray.origin.y >= max.x) {
                //      |
                // +----+
                // |    | x
                // +----+
                //      |
                float ri = (max.x - ray.origin.x) / ray.direction.x;
                if(ri < 0) return null; // Points away from rect, can't hit other sides

                y = ray.origin.y + ri * ray.direction.y;
                if(y >= min.y && y <= max.y) {
                    if(ri > maxLength) return null;
                    float i = (y - min.y) / (max.y - min.y);
                    return new Raycast2D(this, ray, ri, i * (0.5f - xPart) + xPart, float2.X);
                }
            }
        }

        if(ray.direction.y != 0) {
            if(ray.origin.y <= min.y) {
                //   +----+
                //   |    |
                // --+----+--
                //      x
                float ri = (min.y - ray.origin.y) / ray.direction.y;
                if(ri < 0) return null;

                x = ray.origin.x + ri * ray.direction.x;
                if(x < min.x || x > max.x || ri > maxLength) return null;

                float i = (x - min.x) / (max.x - min.x);
                return new Raycast2D(this, ray, ri, i * xPart, float2.Y.negated());
            } else if(ray.origin.y >= max.y) {
                //      x
                // --+----+--
                //   |    |
                //   +----+
                float ri = (max.y - ray.origin.y) / ray.direction.y;
                if(ri < 0) return null;

                x = ray.origin.x + ri * ray.direction.x;
                if(x < min.x || x > max.x || ri > maxLength) return null;

                float i = (x - min.x) / (max.x - min.x);
                return new Raycast2D(this, ray, ri, i * xPart + 0.5f, float2.Y);
            }
        }
        // +----+
        // |  x |
        // +----+
        return null;
    }

    @Override
    public final float2 getPoint(float index) {
        float x = max.x - min.x, y = max.y - min.y;
        float xPart = x / (2 * (x + y));

        if(index <= xPart)
            return min.added(x * index / xPart, 0);
        if(index <= 0.5f)
            return maxMin().added(y * (index - xPart) / (0.5f - xPart));
        if(index <= xPart + 0.5f)
            return max.subed(x * (index - 0.5f) / xPart, 0);
        return minMax().subed(0, y * (index - 0.5f - xPart) / (0.5f - xPart));
    }

    @Override
    public final constFloat2 getNormal(float index) {
        float x = max.x - min.x, y = max.y - min.y;
        float xPart = x / (2 * (x + y));

        if(index <= xPart)        return float2.Y.negated();
        if(index <= 0.5f)         return float2.X;
        if(index <= 0.5f + xPart) return float2.Y;
        else                      return float2.X.negated();
    }

    /**
     * Returns the smallest rectangle which contains both this and the given rectangle.
     *
     * @param r The rectangle to include
     * @return A new rectangle containing both this and the given rectangle
     */
    public final Rect containing(constRect r) {
        return new Rect(float2.min(min, r.min), float2.max(max, r.max), true);
    }

    /**
     * Returns the smallest rectangle which contains this rectangle and the given point.
     *
     * @param p The point to include
     * @return A new rectangle including both this rectangle and the given point
     */
    public final Rect containing(constFloat2 p) {
        return new Rect(float2.min(min, p), float2.max(max, p), true);
    }

    /**
     * Returns this rectangle with its size scaled from its center point by the given factor.
     *
     * @param f The factor to scale by
     * @return The scaled rectangle
     */
    public final Rect scaled(float f) {
        float2 e = extents();
        float2 c = min.added(e);
        e.scale(f);
        return new Rect(c.subed(e), c.add(e), true);
    }

    /**
     * Returns this rectangle with its size scaled from its center point by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled rectangle
     */
    public final Rect scaled(constFloat2 f) {
        float2 e = extents();
        float2 c = min.added(e);
        e.scale(f);
        return new Rect(c.subed(e), c.add(e), true);
    }

    /**
     * Returns this rectangle with its size scaled from its center point by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled rectangle
     */
    public final Rect scaled(constInt2 f) {
        float2 e = extents();
        float2 c = min.added(e);
        e.scale(f);
        return new Rect(c.subed(e), c.add(e), true);
    }

    /**
     * Returns this rectangle with its size scaled from its lower bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return The scaled rectangle
     */
    public final Rect scaledFromMin(float f) {
        float2 size = size().scale(f);
        return new Rect(min.clone(), min.added(size), true);
    }

    /**
     * Returns this rectangle with its size scaled from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled rectangle
     */
    public final Rect scaledFromMin(constFloat2 f) {
        float2 size = size().scale(f);
        return new Rect(min.clone(), min.added(size), true);
    }

    /**
     * Returns this rectangle with its size scaled from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled rectangle
     */
    public final Rect scaledFromMin(constInt2 f) {
        float2 size = size().scale(f);
        return new Rect(min.clone(), min.added(size), true);
    }

    /**
     * Returns this rectangle with its size scaled from its upper bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return The scaled rectangle
     */
    public final Rect scaledFromMax(float f) {
        float2 size = size().scale(f);
        return new Rect(max.subed(size), max.clone(), true);
    }

    /**
     * Returns this rectangle with its size scaled from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled rectangle
     */
    public final Rect scaledFromMax(constFloat2 f) {
        float2 size = size().scale(f);
        return new Rect(max.subed(size), max.clone(), true);
    }

    /**
     * Returns this rectangle with its size scaled from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return The scaled rectangle
     */
    public final Rect scaledFromMax(constInt2 f) {
        float2 size = size().scale(f);
        return new Rect(max.subed(size), max.clone(), true);
    }

    /**
     * Returns whether this rectangle is valid. A rectangle is valid its lower bounds are less or equal
     * to its upper bounds (-> its size is non-negative). This especially requires all components to not
     * be {@link Float#NaN}.
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
    public final float2[] vertices() {
        return new float2[] { min.clone(), new float2(max.x, min.y), max.clone(), new float2(min.x, max.y) };
    }
}
