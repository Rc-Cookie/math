package com.github.rccookie.math.collision;

import com.github.rccookie.math.constFloat2;
import com.github.rccookie.math.constRay2;
import com.github.rccookie.math.constRect;
import com.github.rccookie.math.float2;

/**
 * A 2d collision shape.
 */
public interface Shape2D {

    /**
     * Returns the bounding rectangle of this shape. The returned rectangle might be
     * a reference and change over time, but this is not necessarily the case.
     *
     * @return The bounds of this shape
     */
    constRect bounds();

    /**
     * Returns the center of the shape. This does not necessarily need to be identical
     * to <code>bounds().center()</code>, it could, for example, also be the center of
     * mass. The returned vector might be a reference and change over time, but this is
     * not necessarily the case.
     *
     * @return The center of the shape
     */
    constFloat2 center();

    /**
     * Returns the location of the point at the specified index of this shape. The index
     * is a value between 0 and 1 which describes the position along the border of the shape.
     *
     * @param index The index along the border of the shape
     * @return The point of the border at that index
     */
    float2 getPoint(float index);

    /**
     * Returns the normal vector of the point at the specified index of this shape. The
     * index is a value between 0 and 1 which describes the position along the border of
     * the shape.
     *
     * @param index The index along the border of the shape
     * @return The normal vector of the border at that index
     */
    constFloat2 getNormal(float index);

    /**
     * Returns whether this shape overlaps the given shape. Touching also counts
     * as overlapping.
     *
     * @param s The shape to test overlapping against
     * @return Whether the shapes overlap
     */
    boolean overlaps(Shape2D s);

    /**
     * Returns whether this shape overlaps the given bounding rectangle. Touching
     * also counts as overlapping.
     *
     * @param r The rectangle to test overlapping against
     * @return Whether this shape overlaps the given rectangle
     */
    boolean overlaps(constRect r);

    /**
     * Returns whether this shape contains the given point. Laying on the border
     * of the shape does count as being contained.
     *
     * @param p The point to test
     * @return Whether this shape contains the given point
     */
    boolean contains(constFloat2 p);

    /**
     * Returns whether this shape fully contains the given shape.
     *
     * @param s The shape to test for containment
     * @return Whether this shape contains the given shape
     */
    boolean contains(Shape2D s);

    /**
     * Returns the contact manifold between this and the given shape, or <code>null</code>
     * if they do not overlap. Touching also counts as overlapping.
     *
     * @param s The shape to test contact against
     * @return The contact manifold, or <code>null</code>
     */
    Manifold2D contactWith(Shape2D s);

    /**
     * Returns whether the given ray intersects this shape. Intersections only count when entering
     * the shape from the outside to the inside.
     *
     * @param ray The ray to test against
     * @return Whether the ray intersects this shape
     */
    default boolean intersects(constRay2 ray) {
        return intersects(ray, Float.POSITIVE_INFINITY);
    }

    /**
     * Returns whether the given ray intersects this shape within the given distance. Intersections
     * only count when entering the shape from the outside to the inside.
     *
     * @param ray The ray to test against
     * @param maxLength The maximum distance of the intersection point from the origin of the ray
     * @return Whether the ray intersects this shape
     */
    boolean intersects(constRay2 ray, float maxLength);

    /**
     * Returns the first intersection of the given ray with this shape. Intersections only count
     * when entering the shape from the outside to the inside.
     *
     * @param ray The ray to find the intersection of
     * @return The ray intersection, or <code>null</code> the ray does not intersect this shape
     */
    default Raycast2D raycast(constRay2 ray) {
        return raycast(ray, Float.POSITIVE_INFINITY);
    }

    /**
     * Returns the first intersection of the given ray with this shape within the given distance.
     * Intersections only count when entering the shape from the outside to the inside.
     *
     * @param ray The ray to find the intersection of
     * @param maxLength The maximum distance of the intersection point from the origin of the ray
     * @return The ray intersection, or <code>null</code> the ray does not intersect this shape
     *         within the given distance
     */
    Raycast2D raycast(constRay2 ray, float maxLength);
}
