package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.Arguments;

/**
 * A mutable, axis-aligned integer rectangle.
 */
public final class IRect extends constIRect {

    static {
        JsonDeserialization.register(IRect.class, json -> new IRect(json.get(0).as(int2.class), json.get(1).as(int2.class), true));
    }

    /**
     * Creates a new rectangle.
     *
     * @param min The lower bounds of the rectangle
     * @param max The upper bounds of the rectangle
     */
    public IRect(constInt2 min, constInt2 max) {
        super(min, max);
    }

    /**
     * Creates a new rectangle.
     *
     * @param minX The lower x bounds of the rectangle
     * @param minY The lower y bounds of the rectangle
     * @param maxX The upper x bounds of the rectangle
     * @param maxY The upper y bounds of the rectangle
     */
    public IRect(int minX, int minY, int maxX, int maxY) {
        super(minX, minY, maxX, maxY);
    }

    IRect(int2 minRef, int2 maxRef, boolean ignored) {
        super(minRef, maxRef, ignored);
    }

    /**
     * Returns a mutable reference to the rectangle's lower bounds. Modifying this also
     * modifies the rectangle.
     *
     * @return A reference to the rectangle's lower bounds
     */
    @Override
    public int2 min() {
        return (int2) min;
    }

    /**
     * Returns a mutable reference to the rectangle's upper bounds. Modifying this also
     * modifies the rectangle.
     *
     * @return A reference to the rectangle's upper bounds
     */
    @Override
    public int2 max() {
        return (int2) max;
    }

    /**
     * Sets this rectangle to the same values as the given rectangle.
     *
     * @param r The rectangle to set this rectangle to
     * @return This rectangle
     */
    public IRect set(constIRect r) {
        min().set(r.min);
        max().set(r.max);
        return this;
    }

    /**
     * Sets the size of this rectangle by scaling it from its lower bounds.
     *
     * @param size The size to set
     * @return This rectangle
     */
    public IRect setSizeFromMin(constInt2 size) {
        max().set(min).add(size);
        return this;
    }

    /**
     * Sets the size of this rectangle by scaling it from its upper bounds.
     *
     * @param size The size to set
     * @return This rectangle
     */
    public IRect setSizeFromMax(constInt2 size) {
        min().set(max).sub(size);
        return this;
    }

    /**
     * Moves this rectangle the specified translation.
     *
     * @param translation The translation to apply to both lower and upper bounds
     * @return This rectangle
     */
    public IRect move(constInt2 translation) {
        min().add(translation);
        max().add(translation);
        return this;
    }

    /**
     * Scales the size of this rectangle from its lower bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return This rectangle
     */
    public IRect scaleFromMin(int f) {
        int2 size = size().scale(f);
        max().set(min).add(size);
        return this;
    }

    /**
     * Scales the size of this rectangle from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This rectangle
     */
    public IRect scaleFromMin(constInt2 f) {
        int2 size = size().scale(f);
        max().set(min).add(size);
        return this;
    }

    /**
     * Scales the size of this rectangle from its upper bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return This rectangle
     */
    public IRect scaleFromMax(int f) {
        int2 size = size().scale(f);
        min().set(max).sub(size);
        return this;
    }

    /**
     * Scales the size of this rectangle from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This rectangle
     */
    public IRect scaleFromMax(constInt2 f) {
        int2 size = size().scale(f);
        min().set(max).sub(size);
        return this;
    }

    /**
     * Grows this rectangle if necessary to contain the given point.
     *
     * @param p The point to contain
     * @return This rectangle
     */
    public IRect contain(constInt2 p) {
        min().setMin(p);
        max().setMax(p);
        return this;
    }

    /**
     * Grows this rectangle if necessary to fully contain the given rectangle.
     *
     * @param r The rectangle to contain
     * @return This rectangle
     */
    public IRect contain(constIRect r) {
        min().setMin(r.min);
        max().setMax(r.max);
        return this;
    }

    /**
     * Sets this rectangle to the smallest rectangle that contains both given rectangles.
     * The original shape of this rectangle is discarded.
     *
     * @param a The first rectangle to be contained
     * @param b The second rectangle to be contained
     * @return This rectangle
     */
    public IRect setContaining(constIRect a, constIRect b) {
        min().set(a.min).setMin(b.min);
        max().set(a.max).setMax(b.max);
        return this;
    }


    /**
     * Returns a new, empty rectangle at (0,0).
     *
     * @return A new zero rectangle
     */
    public static IRect zero() {
        return new IRect(new int2(), new int2(), true);
    }

    /**
     * Returns a new, empty rectangle at the specified position.
     *
     * @param center The position of the rectangle
     * @return A new zero rectangle
     */
    public static IRect zero(constInt2 center) {
        return new IRect(Arguments.checkNull(center, "center"), center);
    }

    /**
     * Returns a new rectangle with its lower bounds at (0,0).
     *
     * @param size The size of the rectangle (equivalently: its upper bounds)
     * @return A new rectangle with the specified size
     */
    public static IRect fromZero(constInt2 size) {
        return new IRect(int2.zero, Arguments.checkNull(size, "size"));
    }

    /**
     * Returns a new rectangle with the specified extents from the specified position.
     *
     * @param center The center of the rectangle
     * @param extents The extents (half size) of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static IRect extending(constInt2 center, constInt2 extents) {
        return new IRect(center.subed(extents), center.added(extents), true);
    }

    /**
     * Returns a new rectangle with the specified size starting at the specified lower bounds.
     *
     * @param min The lower bounds of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static IRect withSizeFromMin(constInt2 min, constInt2 size) {
        return new IRect(min.clone(), min.added(size), true);
    }

    /**
     * Returns a new rectangle with the specified size up to the specified upper bounds.
     *
     * @param max The upper bounds of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static IRect withSizeFromMax(constInt2 max, constInt2 size) {
        return new IRect(max.subed(size), max.clone(), true);
    }

    /**
     * Returns a new rectangle with the specified vectors as lower and upper bounds references.
     * Modifying the vectors also modifies the rectangle, and vice versa.
     *
     * @param minRef The vector to use as lower bounds of the rectangle
     * @param maxRef The vector to use as upper bounds of the rectangle
     * @return A new rectangle using the specified vector instances
     */
    public static IRect ref(int2 minRef, int2 maxRef) {
        return new IRect(Arguments.checkNull(minRef, "minRef"), Arguments.checkNull(maxRef, "maxRef"), true);
    }

    /**
     * Returns the smallest rectangle which contains both specified rectangles.
     *
     * @param a The first rectangle to be contained
     * @param b The second rectangle to be contained
     * @return A rectangle containing both specified rectangles
     */
    public static IRect combining(constIRect a, constIRect b) {
        return new IRect(int2.min(a.min, b.min), int2.max(a.max, b.max), true);
    }
}
