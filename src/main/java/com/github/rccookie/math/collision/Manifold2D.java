package com.github.rccookie.math.collision;

import java.util.Iterator;

import com.github.rccookie.math.constFloat2;
import com.github.rccookie.math.float2;
import com.github.rccookie.util.Arguments;
import com.github.rccookie.util.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * Approximates the 2D contact surface between two shapes.
 */
public final class Manifold2D implements Iterable<Manifold2D.Point> {

    private Shape2D shapeA;
    private Shape2D shapeB;
    private Point[] points;

    /**
     * Creates a new manifold.
     *
     * @param shapeA The first of the shapes in contact
     * @param shapeB The second of the shapes in contact
     * @param points The contact points defining the manifold
     */
    public Manifold2D(Shape2D shapeA, Shape2D shapeB, Point... points) {
        setShapeA(shapeA);
        setShapeB(shapeB);
        setPoints(points);
    }

    /**
     * Returns one of the shapes in contact.
     *
     * @return The first of the two shapes in contact
     */
    public Shape2D shapeA() {
        return shapeA;
    }

    /**
     * Returns the other of the shapes in contact.
     *
     * @return The second of the two shapes in contact
     */
    public Shape2D shapeB() {
        return shapeB;
    }

    /**
     * Returns the number of contact point defining this manifold, at least 1.
     *
     * @return The number of contact points
     */
    @Range(from = 1, to = Integer.MAX_VALUE)
    public int pointCount() {
        return points.length;
    }

    /**
     * Returns the contact points defining this manifold. The contact points can also
     * be iterated by iterating this manifold object directly.
     *
     * @return The contact points, at least one
     */
    public Point[] points() {
        return points.clone();
    }

    /**
     * Returns an iterator over the contact points.
     *
     * @return An iterator over the contact points
     */
    @NotNull
    @Override
    public Iterator<Point> iterator() {
        return Utils.iterator(points);
    }

    /**
     * Sets one of the shapes in contact
     *
     * @param shapeA The shape to set
     */
    public void setShapeA(Shape2D shapeA) {
        this.shapeA = Arguments.checkNull(shapeA, "objA");
    }

    /**
     * Sets the other of the shapes in contact
     *
     * @param shapeB The shape to set
     */
    public void setShapeB(Shape2D shapeB) {
        this.shapeB = Arguments.checkNull(shapeB, "objB");
    }

    /**
     * Sets the contact points defining this manifold.
     *
     * @param points The points to set. The array will be copied (not the points though)
     */
    public void setPoints(Point... points) {
        if(Arguments.deepCheckNull(points, "points").length == 0)
            throw new IllegalArgumentException("At least one contact point required");
        this.points = points.clone();
    }

    /**
     * A point of contact between two shapes.
     */
    public static final class Point {

        private final constFloat2 pointOnA;
        private final constFloat2 normalOnA;
        private final float distance;

        /**
         * Creates a new contact point.
         *
         * @param pointOnA The contact point on shape A (in world space)
         * @param normalOnA The contact normal at the contact point on A. Must be normalized.
         * @param distance The distance along the normal from the contact point on shape A to
         *                 the contact point on shape B. Negative values result in contact point
         *                 B laying within (or behind) shape A (which should usually be the case),
         *                 positive values mean that the shapes are not actually touching in that
         *                 point
         */
        public Point(constFloat2 pointOnA, constFloat2 normalOnA, float distance) {
            this.pointOnA = pointOnA;
            this.normalOnA = normalOnA;
            this.distance = distance;

            assert normalOnA.length2() == 1;
        }

        /**
         * Creates a new contact point assuming the two shapes actually overlap each other.
         *
         * @param pointOnA The contact point on A (in world space)
         * @param pointOnB The contact point on B (in world space)
         */
        public Point(constFloat2 pointOnA, constFloat2 pointOnB) {
            this.pointOnA = pointOnA;
            this.distance = -pointOnA.dist(pointOnB);
            this.normalOnA = distance == 0 ? float2.X : float2.between(pointOnA, pointOnB).div(-distance);
        }

        /**
         * Returns the contact point on shape A, which must be at the surface of shape A.
         *
         * @return The contact point on shape A
         */
        public float2 pointOnA() {
            return pointOnA.clone();
        }

        /**
         * Returns the contact point on shape B, which must be at the surface of shape B.
         *
         * @return The contact point on shape B
         */
        public float2 pointOnB() {
            return normalOnA.scaled(distance).add(pointOnA);
        }

        /**
         * Returns the approximate point of contact, which is the average of the
         * contact points on each shape.
         *
         * @return The approximate contact point
         */
        public float2 point() {
            return normalOnA.scaled(distance * 0.5f).add(pointOnA);
        }

        /**
         * Returns the surface normal of the contact on shape A (which points away from shape A).
         *
         * @return The contact normal on shape A
         */
        public float2 normalOnA() {
            return normalOnA.clone();
        }

        /**
         * Returns the surface normal of the contact on shape B (which points away from shape B).
         *
         * @return The contact normal on shape B
         */
        public float2 normalOnB() {
            return normalOnA.negated();
        }

        /**
         * Returns the distance between the shapes in this contact point. Negative values mean
         * that the shapes are overlapping by this distance, positive values mean that the shapes
         * are not actually in contact in this point. 0 means the shapes are exactly touching
         * but not penetrating.
         *
         * @return The distance between the shape in this point
         */
        public float distance() {
            return distance;
        }

        /**
         * Creates a new contact point where the two shapes don't actually overlap each other.
         *
         * @param pointOnA The contact point on A (in world space)
         * @param pointOnB The contact point on B (in world space)
         * @return A new contact point, whether the distance is exactly <code>dist(pointOnA, pointOnB)</code>
         */
        public static Point notIntersecting(constFloat2 pointOnA, constFloat2 pointOnB) {
            float distance = pointOnA.dist(pointOnB);
            float2 normalOnA = float2.between(pointOnA, pointOnB).scale(distance);
            return new Point(pointOnA, normalOnA, distance);
        }
    }
}
