package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.Arguments;

/**
 * A mutable, axis-aligned integer bounding box.
 */
public final class IBounds extends constIBounds {

    static {
        JsonDeserialization.register(IBounds.class, json -> new IBounds(json.get(0).as(int3.class), json.get(1).as(int3.class), true));
    }

    /**
     * Creates a new bounding box.
     *
     * @param min The lower bounds of the bounding box
     * @param max The upper bounds of the bounding box
     */
    public IBounds(constInt3 min, constInt3 max) {
        super(min, max);
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
    public IBounds(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        super(minX, minY, minZ, maxX, maxY, maxZ);
    }

    IBounds(int3 minRef, int3 maxRef, boolean ignored) {
        super(minRef, maxRef, ignored);
    }

    /**
     * Returns a mutable reference to the bounding box's lower bounds. Modifying this also
     * modifies the bounding box.
     *
     * @return A reference to the bounding box's lower bounds
     */
    @Override
    public int3 min() {
        return (int3) min;
    }

    /**
     * Returns a mutable reference to the bounding box's upper bounds. Modifying this also
     * modifies the bounding box.
     *
     * @return A reference to the bounding box's upper bounds
     */
    @Override
    public int3 max() {
        return (int3) max;
    }

    /**
     * Sets this bounding box to the same values as the given bounding box.
     *
     * @param r The bounding box to set this bounding box to
     * @return This bounding box
     */
    public IBounds set(constIBounds r) {
        min().set(r.min);
        max().set(r.max);
        return this;
    }

    /**
     * Sets the size of this bounding box by scaling it from its lower bounds.
     *
     * @param size The size to set
     * @return This bounding box
     */
    public IBounds setSizeFromMin(constInt3 size) {
        max().set(min).add(size);
        return this;
    }

    /**
     * Sets the size of this bounding box by scaling it from its upper bounds.
     *
     * @param size The size to set
     * @return This bounding box
     */
    public IBounds setSizeFromMax(constInt3 size) {
        min().set(max).sub(size);
        return this;
    }

    /**
     * Moves this bounding box the specified translation.
     *
     * @param translation The translation to apply to both lower and upper bounds
     * @return This bounding box
     */
    public IBounds move(constInt3 translation) {
        min().add(translation);
        max().add(translation);
        return this;
    }

    /**
     * Scales the size of this bounding box from its lower bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return This bounding box
     */
    public IBounds scaleFromMin(int f) {
        int3 size = size().scale(f);
        max().set(min).add(size);
        return this;
    }

    /**
     * Scales the size of this bounding box from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This bounding box
     */
    public IBounds scaleFromMin(constInt3 f) {
        int3 size = size().scale(f);
        max().set(min).add(size);
        return this;
    }

    /**
     * Scales the size of this bounding box from its upper bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return This bounding box
     */
    public IBounds scaleFromMax(int f) {
        int3 size = size().scale(f);
        min().set(max).sub(size);
        return this;
    }

    /**
     * Scales the size of this bounding box from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This bounding box
     */
    public IBounds scaleFromMax(constInt3 f) {
        int3 size = size().scale(f);
        min().set(max).sub(size);
        return this;
    }

    /**
     * Grows this bounding box if necessary to contain the given point.
     *
     * @param p The point to contain
     * @return This bounding box
     */
    public IBounds contain(constInt3 p) {
        min().setMin(p);
        max().setMax(p);
        return this;
    }

    /**
     * Grows this bounding box if necessary to fully contain the given bounding box.
     *
     * @param r The bounding box to contain
     * @return This bounding box
     */
    public IBounds contain(constIBounds r) {
        min().setMin(r.min);
        max().setMax(r.max);
        return this;
    }

    /**
     * Sets this bounding box to the smallest bounding box that contains both given rectangles.
     * The original shape of this bounding box is discarded.
     *
     * @param a The first bounding box to be contained
     * @param b The second bounding box to be contained
     * @return This bounding box
     */
    public IBounds setContaining(constIBounds a, constIBounds b) {
        min().set(a.min).setMin(b.min);
        max().set(a.max).setMax(b.max);
        return this;
    }


    /**
     * Returns a new, empty bounding box at (0,0).
     *
     * @return A new zero bounding box
     */
    public static IBounds zero() {
        return new IBounds(new int3(), new int3(), true);
    }

    /**
     * Returns a new, empty bounding box at the specified position.
     *
     * @param center The position of the bounding box
     * @return A new zero bounding box
     */
    public static IBounds zero(constInt3 center) {
        return new IBounds(Arguments.checkNull(center, "center"), center);
    }

    /**
     * Returns a new bounding box with its lower bounds at (0,0).
     *
     * @param size The size of the bounding box (equivalently: its upper bounds)
     * @return A new bounding box with the specified size
     */
    public static IBounds fromZero(constInt3 size) {
        return new IBounds(int3.zero, Arguments.checkNull(size, "size"));
    }

    /**
     * Returns a new bounding box with the specified extents from the specified position.
     *
     * @param center The center of the bounding box
     * @param extents The extents (half size) of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static IBounds extending(constInt3 center, constInt3 extents) {
        return new IBounds(center.subed(extents), center.added(extents), true);
    }

    /**
     * Returns a new bounding box with the specified size starting at the specified lower bounds.
     *
     * @param min The lower bounds of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static IBounds withSizeFromMin(constInt3 min, constInt3 size) {
        return new IBounds(min.clone(), min.added(size), true);
    }

    /**
     * Returns a new bounding box with the specified size up to the specified upper bounds.
     *
     * @param max The upper bounds of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static IBounds withSizeFromMax(constInt3 max, constInt3 size) {
        return new IBounds(max.subed(size), max.clone(), true);
    }

    /**
     * Returns a new bounding box with the specified vectors as lower and upper bounds references.
     * Modifying the vectors also modifies the bounding box, and vice versa.
     *
     * @param minRef The vector to use as lower bounds of the bounding box
     * @param maxRef The vector to use as upper bounds of the bounding box
     * @return A new bounding box using the specified vector instances
     */
    public static IBounds ref(int3 minRef, int3 maxRef) {
        return new IBounds(Arguments.checkNull(minRef, "minRef"), Arguments.checkNull(maxRef, "maxRef"), true);
    }

    /**
     * Returns the smallest bounding box which contains both specified rectangles.
     *
     * @param a The first bounding box to be contained
     * @param b The second bounding box to be contained
     * @return A bounding box containing both specified rectangles
     */
    public static IBounds combining(constIBounds a, constIBounds b) {
        return new IBounds(int3.min(a.min, b.min), int3.max(a.max, b.max), true);
    }
}
