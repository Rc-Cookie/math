package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.Arguments;

/**
 * A mutable, axis-aligned rectangle.
 */
public final class Rect extends constRect {

    static {
        JsonDeserialization.register(Rect.class, json -> new Rect(json.get(0).as(float2.class), json.get(1).as(float2.class), true));
    }

    /**
     * Creates a new rectangle.
     *
     * @param min The lower bounds of the rectangle
     * @param max The upper bounds of the rectangle
     */
    public Rect(constFloat2 min, constFloat2 max) {
        super(min.clone(), max.clone(), true);
    }

    /**
     * Creates a new rectangle.
     *
     * @param min The lower bounds of the rectangle
     * @param max The upper bounds of the rectangle
     */
    public Rect(constFloat2 min, constInt2 max) {
        super(min.clone(), max.toF(), true);
    }

    /**
     * Creates a new rectangle.
     *
     * @param min The lower bounds of the rectangle
     * @param max The upper bounds of the rectangle
     */
    public Rect(constInt2 min, constFloat2 max) {
        super(min.toF(), max.clone(), true);
    }

    /**
     * Creates a new rectangle.
     *
     * @param min The lower bounds of the rectangle
     * @param max The upper bounds of the rectangle
     */
    public Rect(constInt2 min, constInt2 max) {
        super(min.toF(), max.toF(), true);
    }

    /**
     * Creates a new rectangle.
     *
     * @param minX The lower x bounds of the rectangle
     * @param minY The lower y bounds of the rectangle
     * @param maxX The upper x bounds of the rectangle
     * @param maxY The upper y bounds of the rectangle
     */
    public Rect(float minX, float minY, float maxX, float maxY) {
        super(new float2(minX, minY), new float2(maxX, maxY), true);
    }

    Rect(float2 minRef, float2 maxRef, boolean ignored) {
        super(minRef, maxRef, ignored);
    }

    /**
     * Returns a mutable reference to the rectangle's lower bounds. Modifying this also
     * modifies the rectangle.
     *
     * @return A reference to the rectangle's lower bounds
     */
    @Override
    public float2 min() {
        return (float2) min;
    }

    /**
     * Returns a mutable reference to the rectangle's upper bounds. Modifying this also
     * modifies the rectangle.
     *
     * @return A reference to the rectangle's upper bounds
     */
    @Override
    public float2 max() {
        return (float2) max;
    }

    /**
     * Sets this rectangle to the same values as the given rectangle.
     *
     * @param r The rectangle to set this rectangle to
     * @return This rectangle
     */
    public Rect set(constRect r) {
        min().set(r.min);
        max().set(r.max);
        return this;
    }

    /**
     * Sets the size of this rectangle by scaling it around its center point.
     *
     * @param size The size to set
     * @return This rectangle
     */
    public Rect setSize(constFloat2 size) {
        float2 diff = size().neg().add(size).scale(0.5f);
        min().sub(diff);
        max().add(diff);
        return this;
    }

    /**
     * Sets the size of this rectangle by scaling it around its center point.
     *
     * @param size The size to set
     * @return This rectangle
     */
    public Rect setSize(constInt2 size) {
        float2 diff = size().neg().add(size).scale(0.5f);
        min().sub(diff);
        max().add(diff);
        return this;
    }

    /**
     * Sets the size of this rectangle by scaling it from its lower bounds.
     *
     * @param size The size to set
     * @return This rectangle
     */
    public Rect setSizeFromMin(constFloat2 size) {
        max().set(min).add(size);
        return this;
    }

    /**
     * Sets the size of this rectangle by scaling it from its lower bounds.
     *
     * @param size The size to set
     * @return This rectangle
     */
    public Rect setSizeFromMin(constInt2 size) {
        max().set(min).add(size);
        return this;
    }

    /**
     * Sets the size of this rectangle by scaling it from its upper bounds.
     *
     * @param size The size to set
     * @return This rectangle
     */
    public Rect setSizeFromMax(constFloat2 size) {
        min().set(max).sub(size);
        return this;
    }

    /**
     * Sets the size of this rectangle by scaling it from its upper bounds.
     *
     * @param size The size to set
     * @return This rectangle
     */
    public Rect setSizeFromMax(constInt2 size) {
        min().set(max).sub(size);
        return this;
    }

    /**
     * Moves the center of this rectangle to the specified position.
     *
     * @param center The new center for this rectangle
     * @return This rectangle
     */
    public Rect setCenter(constFloat2 center) {
        float2 off = center().sub(center);
        min().sub(off);
        max().sub(off);
        return this;
    }

    /**
     * Moves the center of this rectangle to the specified position.
     *
     * @param center The new center for this rectangle
     * @return This rectangle
     */
    public Rect setCenter(constInt2 center) {
        float2 off = center().sub(center);
        min().sub(off);
        max().sub(off);
        return this;
    }

    /**
     * Sets the extents (the half size) of this rectangle from its center point.
     *
     * @param extents The extents to set
     * @return This rectangle
     */
    public Rect setExtents(constFloat2 extents) {
        float2 negDiff = extents().sub(extents);
        min().add(negDiff);
        max().sub(negDiff);
        return this;
    }

    /**
     * Sets the extents (the half size) of this rectangle from its center point.
     *
     * @param extents The extents to set
     * @return This rectangle
     */
    public Rect setExtents(constInt2 extents) {
        float2 negDiff = extents().sub(extents);
        min().add(negDiff);
        max().sub(negDiff);
        return this;
    }

    /**
     * Moves this rectangle the specified translation.
     *
     * @param translation The translation to apply to both lower and upper bounds
     * @return This rectangle
     */
    public Rect move(constFloat2 translation) {
        min().add(translation);
        max().add(translation);
        return this;
    }

    /**
     * Moves this rectangle the specified translation.
     *
     * @param translation The translation to apply to both lower and upper bounds
     * @return This rectangle
     */
    public Rect move(constInt2 translation) {
        min().add(translation);
        max().add(translation);
        return this;
    }

    /**
     * Scales the size of this rectangle from its center point by the given factor.
     *
     * @param f The factor to scale by
     * @return This rectangle
     */
    public Rect scale(float f) {
        float2 e = extents();
        float2 c = min.added(e);
        e.scale(f);
        min().set(c).sub(e);
        max().set(c).add(e);
        return this;
    }

    /**
     * Scales the size of this rectangle from its center point by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This rectangle
     */
    public Rect scale(constFloat2 f) {
        float2 e = extents();
        float2 c = min.added(e);
        e.scale(f);
        min().set(c).sub(e);
        max().set(c).add(e);
        return this;
    }

    /**
     * Scales the size of this rectangle from its center point by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This rectangle
     */
    public Rect scale(constInt2 f) {
        float2 e = extents();
        float2 c = min.added(e);
        e.scale(f);
        min().set(c).sub(e);
        max().set(c).add(e);
        return this;
    }

    /**
     * Scales the size of this rectangle from its lower bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return This rectangle
     */
    public Rect scaleFromMin(float f) {
        float2 size = size().scale(f);
        max().set(min).add(size);
        return this;
    }

    /**
     * Scales the size of this rectangle from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This rectangle
     */
    public Rect scaleFromMin(constFloat2 f) {
        float2 size = size().scale(f);
        max().set(min).add(size);
        return this;
    }

    /**
     * Scales the size of this rectangle from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This rectangle
     */
    public Rect scaleFromMin(constInt2 f) {
        float2 size = size().scale(f);
        max().set(min).add(size);
        return this;
    }

    /**
     * Scales the size of this rectangle from its upper bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return This rectangle
     */
    public Rect scaleFromMax(float f) {
        float2 size = size().scale(f);
        min().set(max).sub(size);
        return this;
    }

    /**
     * Scales the size of this rectangle from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This rectangle
     */
    public Rect scaleFromMax(constFloat2 f) {
        float2 size = size().scale(f);
        min().set(max).sub(size);
        return this;
    }

    /**
     * Scales the size of this rectangle from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This rectangle
     */
    public Rect scaleFromMax(constInt2 f) {
        float2 size = size().scale(f);
        min().set(max).sub(size);
        return this;
    }

    /**
     * Grows this rectangle if necessary to contain the given point.
     *
     * @param p The point to contain
     * @return This rectangle
     */
    public Rect contain(constFloat2 p) {
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
    public Rect contain(constRect r) {
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
    public Rect setContaining(constRect a, constRect b) {
        min().set(a.min).setMin(b.min);
        max().set(a.max).setMax(b.max);
        return this;
    }


    /**
     * Returns a new, empty rectangle at (0,0).
     *
     * @return A new zero rectangle
     */
    public static Rect zero() {
        return new Rect(new float2(), new float2(), true);
    }

    /**
     * Returns a new, empty rectangle at the specified position.
     *
     * @param center The position of the rectangle
     * @return A new zero rectangle
     */
    public static Rect zero(constFloat2 center) {
        return new Rect(Arguments.checkNull(center, "center"), center);
    }

    /**
     * Returns a new, empty rectangle at the specified position.
     *
     * @param center The position of the rectangle
     * @return A new zero rectangle
     */
    public static Rect zero(constInt2 center) {
        return new Rect(Arguments.checkNull(center, "center"), center);
    }

    /**
     * Returns a new rectangle with its lower bounds at (0,0).
     *
     * @param size The size of the rectangle (equivalently: its upper bounds)
     * @return A new rectangle with the specified size
     */
    public static Rect fromZero(constFloat2 size) {
        return new Rect(float2.zero, Arguments.checkNull(size, "size"));
    }

    /**
     * Returns a new rectangle with its lower bounds at (0,0).
     *
     * @param size The size of the rectangle (equivalently: its upper bounds)
     * @return A new rectangle with the specified size
     */
    public static Rect fromZero(constInt2 size) {
        return new Rect(float2.zero, Arguments.checkNull(size, "size"));
    }

    /**
     * Returns a new rectangle with the specified extents from the specified position.
     *
     * @param center The center of the rectangle
     * @param extents The extents (half size) of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect extending(constFloat2 center, constFloat2 extents) {
        return new Rect(center.subed(extents), center.added(extents), true);
    }

    /**
     * Returns a new rectangle with the specified extents from the specified position.
     *
     * @param center The center of the rectangle
     * @param extents The extents (half size) of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect extending(constFloat2 center, constInt2 extents) {
        return new Rect(center.subed(extents), center.added(extents), true);
    }

    /**
     * Returns a new rectangle with the specified extents from the specified position.
     *
     * @param center The center of the rectangle
     * @param extents The extents (half size) of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect extending(constInt2 center, constFloat2 extents) {
        return new Rect(center.subed(extents), center.added(extents), true);
    }

    /**
     * Returns a new rectangle with the specified extents from the specified position.
     *
     * @param center The center of the rectangle
     * @param extents The extents (half size) of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect extending(constInt2 center, constInt2 extents) {
        float2 centerF = center.toF();
        return new Rect(centerF.subed(extents), centerF.added(extents), true);
    }

    /**
     * Returns a new rectangle with the specified size centered at the specified position.
     *
     * @param center The center of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSize(constFloat2 center, constFloat2 size) {
        float2 extents = size.scaled(0.5f);
        return new Rect(center.subed(extents), extents.add(size), true);
    }

    /**
     * Returns a new rectangle with the specified size centered at the specified position.
     *
     * @param center The center of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSize(constFloat2 center, constInt2 size) {
        float2 extents = size.scaled(0.5f);
        return new Rect(center.subed(extents), extents.add(size), true);
    }

    /**
     * Returns a new rectangle with the specified size centered at the specified position.
     *
     * @param center The center of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSize(constInt2 center, constFloat2 size) {
        float2 extents = size.scaled(0.5f);
        return new Rect(center.subed(extents), extents.add(size), true);
    }

    /**
     * Returns a new rectangle with the specified size centered at the specified position.
     *
     * @param center The center of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSize(constInt2 center, constInt2 size) {
        float2 extents = size.scaled(0.5f);
        return new Rect(center.subed(extents), extents.add(size), true);
    }

    /**
     * Returns a new rectangle with the specified size starting at the specified lower bounds.
     *
     * @param min The lower bounds of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSizeFromMin(constFloat2 min, constFloat2 size) {
        return new Rect(min.clone(), min.added(size), true);
    }

    /**
     * Returns a new rectangle with the specified size starting at the specified lower bounds.
     *
     * @param min The lower bounds of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSizeFromMin(constFloat2 min, constInt2 size) {
        return new Rect(min.clone(), min.added(size), true);
    }

    /**
     * Returns a new rectangle with the specified size starting at the specified lower bounds.
     *
     * @param min The lower bounds of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSizeFromMin(constInt2 min, constFloat2 size) {
        return new Rect(min.toF(), min.added(size), true);
    }

    /**
     * Returns a new rectangle with the specified size starting at the specified lower bounds.
     *
     * @param min The lower bounds of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSizeFromMin(constInt2 min, constInt2 size) {
        return new Rect(min.toF(), min.toF().add(size), true);
    }

    /**
     * Returns a new rectangle with the specified size up to the specified upper bounds.
     *
     * @param max The upper bounds of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSizeFromMax(constFloat2 max, constFloat2 size) {
        return new Rect(max.subed(size), max.clone(), true);
    }

    /**
     * Returns a new rectangle with the specified size up to the specified upper bounds.
     *
     * @param max The upper bounds of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSizeFromMax(constFloat2 max, constInt2 size) {
        return new Rect(max.subed(size), max.clone(), true);
    }

    /**
     * Returns a new rectangle with the specified size up to the specified upper bounds.
     *
     * @param max The upper bounds of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSizeFromMax(constInt2 max, constFloat2 size) {
        return new Rect(max.subed(size), max.toF(), true);
    }

    /**
     * Returns a new rectangle with the specified size up to the specified upper bounds.
     *
     * @param max The upper bounds of the rectangle
     * @param size The size of the rectangle
     * @return A new rectangle with the specified position and size
     */
    public static Rect withSizeFromMax(constInt2 max, constInt2 size) {
        return new Rect(max.toF().subed(size), max.toF(), true);
    }

    /**
     * Returns a new rectangle with the specified vectors as lower and upper bounds references.
     * Modifying the vectors also modifies the rectangle, and vice versa.
     *
     * @param minRef The vector to use as lower bounds of the rectangle
     * @param maxRef The vector to use as upper bounds of the rectangle
     * @return A new rectangle using the specified vector instances
     */
    public static Rect ref(float2 minRef, float2 maxRef) {
        return new Rect(Arguments.checkNull(minRef, "minRef"), Arguments.checkNull(maxRef, "maxRef"), true);
    }

    /**
     * Returns the smallest rectangle which contains both specified rectangles.
     *
     * @param a The first rectangle to be contained
     * @param b The second rectangle to be contained
     * @return A rectangle containing both specified rectangles
     */
    public static Rect combining(constRect a, constRect b) {
        return new Rect(float2.min(a.min, b.min), float2.max(a.max, b.max), true);
    }
}
