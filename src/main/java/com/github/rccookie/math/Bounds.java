package com.github.rccookie.math;

import com.github.rccookie.json.JsonDeserialization;
import com.github.rccookie.util.Arguments;

/**
 * A mutable, axis-aligned bounding box.
 */
public final class Bounds extends constBounds {

    static {
        JsonDeserialization.register(Bounds.class, json -> new Bounds(json.get(0).as(float3.class), json.get(1).as(float3.class), true));
    }

    /**
     * Creates a new bounding box.
     *
     * @param min The lower bounds of the bounding box
     * @param max The upper bounds of the bounding box
     */
    public Bounds(constFloat3 min, constFloat3 max) {
        super(min.clone(), max.clone(), true);
    }

    /**
     * Creates a new bounding box.
     *
     * @param min The lower bounds of the bounding box
     * @param max The upper bounds of the bounding box
     */
    public Bounds(constFloat3 min, constInt3 max) {
        super(min.clone(), max.toConstF(), true);
    }

    /**
     * Creates a new bounding box.
     *
     * @param min The lower bounds of the bounding box
     * @param max The upper bounds of the bounding box
     */
    public Bounds(constInt3 min, constFloat3 max) {
        super(min.toConstF(), max.clone(), true);
    }

    /**
     * Creates a new bounding box.
     *
     * @param min The lower bounds of the bounding box
     * @param max The upper bounds of the bounding box
     */
    public Bounds(constInt3 min, constInt3 max) {
        super(min.toConstF(), max.toConstF(), true);
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
    public Bounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        super(new float3(minX, minY, minZ), new float3(maxX, maxY, maxZ), true);
    }

    Bounds(float3 minRef, float3 maxRef, boolean ignored) {
        super(minRef, maxRef, ignored);
    }

    /**
     * Returns a mutable reference to the bounding box's lower bounds. Modifying this also
     * modifies the bounding box.
     *
     * @return A reference to the bounding box's lower bounds
     */
    @Override
    public float3 min() {
        return (float3) min;
    }

    /**
     * Returns a mutable reference to the bounding box's upper bounds. Modifying this also
     * modifies the bounding box.
     *
     * @return A reference to the bounding box's upper bounds
     */
    @Override
    public float3 max() {
        return (float3) max;
    }

    /**
     * Sets this bounding box to the same values as the given bounding box.
     *
     * @param r The bounding box to set this bounding box to
     * @return This bounding box
     */
    public Bounds set(constBounds r) {
        min().set(r.min);
        max().set(r.max);
        return this;
    }

    /**
     * Sets the size of this bounding box by scaling it around its center point.
     *
     * @param size The size to set
     * @return This bounding box
     */
    public Bounds setSize(constFloat3 size) {
        float3 diff = size().neg().add(size).scale(0.5f);
        min().sub(diff);
        max().add(diff);
        return this;
    }

    /**
     * Sets the size of this bounding box by scaling it around its center point.
     *
     * @param size The size to set
     * @return This bounding box
     */
    public Bounds setSize(constInt3 size) {
        float3 diff = size().neg().add(size).scale(0.5f);
        min().sub(diff);
        max().add(diff);
        return this;
    }

    /**
     * Sets the size of this bounding box by scaling it from its lower bounds.
     *
     * @param size The size to set
     * @return This bounding box
     */
    public Bounds setSizeFromMin(constFloat3 size) {
        max().set(min).add(size);
        return this;
    }

    /**
     * Sets the size of this bounding box by scaling it from its lower bounds.
     *
     * @param size The size to set
     * @return This bounding box
     */
    public Bounds setSizeFromMin(constInt3 size) {
        max().set(min).add(size);
        return this;
    }

    /**
     * Sets the size of this bounding box by scaling it from its upper bounds.
     *
     * @param size The size to set
     * @return This bounding box
     */
    public Bounds setSizeFromMax(constFloat3 size) {
        min().set(max).sub(size);
        return this;
    }

    /**
     * Sets the size of this bounding box by scaling it from its upper bounds.
     *
     * @param size The size to set
     * @return This bounding box
     */
    public Bounds setSizeFromMax(constInt3 size) {
        min().set(max).sub(size);
        return this;
    }

    /**
     * Moves the center of this bounding box to the specified position.
     *
     * @param center The new center for this bounding box
     * @return This bounding box
     */
    public Bounds setCenter(constFloat3 center) {
        float3 off = center().sub(center);
        min().sub(off);
        max().sub(off);
        return this;
    }

    /**
     * Moves the center of this bounding box to the specified position.
     *
     * @param center The new center for this bounding box
     * @return This bounding box
     */
    public Bounds setCenter(constInt3 center) {
        float3 off = center().sub(center);
        min().sub(off);
        max().sub(off);
        return this;
    }

    /**
     * Sets the extents (the half size) of this bounding box from its center point.
     *
     * @param extents The extents to set
     * @return This bounding box
     */
    public Bounds setExtents(constFloat3 extents) {
        float3 negDiff = extents().sub(extents);
        min().add(negDiff);
        max().sub(negDiff);
        return this;
    }

    /**
     * Sets the extents (the half size) of this bounding box from its center point.
     *
     * @param extents The extents to set
     * @return This bounding box
     */
    public Bounds setExtents(constInt3 extents) {
        float3 negDiff = extents().sub(extents);
        min().add(negDiff);
        max().sub(negDiff);
        return this;
    }

    /**
     * Moves this bounding box the specified translation.
     *
     * @param translation The translation to apply to both lower and upper bounds
     * @return This bounding box
     */
    public Bounds move(constFloat3 translation) {
        min().add(translation);
        max().add(translation);
        return this;
    }

    /**
     * Moves this bounding box the specified translation.
     *
     * @param translation The translation to apply to both lower and upper bounds
     * @return This bounding box
     */
    public Bounds move(constInt3 translation) {
        min().add(translation);
        max().add(translation);
        return this;
    }

    /**
     * Scales the size of this bounding box from its center point by the given factor.
     *
     * @param f The factor to scale by
     * @return This bounding box
     */
    public Bounds scale(float f) {
        float3 e = extents();
        float3 c = min.added(e);
        e.scale(f);
        min().set(c).sub(e);
        max().set(c).add(e);
        return this;
    }

    /**
     * Scales the size of this bounding box from its center point by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This bounding box
     */
    public Bounds scale(constFloat3 f) {
        float3 e = extents();
        float3 c = min.added(e);
        e.scale(f);
        min().set(c).sub(e);
        max().set(c).add(e);
        return this;
    }

    /**
     * Scales the size of this bounding box from its center point by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This bounding box
     */
    public Bounds scale(constInt3 f) {
        float3 e = extents();
        float3 c = min.added(e);
        e.scale(f);
        min().set(c).sub(e);
        max().set(c).add(e);
        return this;
    }

    /**
     * Scales the size of this bounding box from its lower bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return This bounding box
     */
    public Bounds scaleFromMin(float f) {
        float3 size = size().scale(f);
        max().set(min).add(size);
        return this;
    }

    /**
     * Scales the size of this bounding box from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This bounding box
     */
    public Bounds scaleFromMin(constFloat3 f) {
        float3 size = size().scale(f);
        max().set(min).add(size);
        return this;
    }

    /**
     * Scales the size of this bounding box from its lower bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This bounding box
     */
    public Bounds scaleFromMin(constInt3 f) {
        float3 size = size().scale(f);
        max().set(min).add(size);
        return this;
    }

    /**
     * Scales the size of this bounding box from its upper bounds by the given factor.
     *
     * @param f The factor to scale by
     * @return This bounding box
     */
    public Bounds scaleFromMax(float f) {
        float3 size = size().scale(f);
        min().set(max).sub(size);
        return this;
    }

    /**
     * Scales the size of this bounding box from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This bounding box
     */
    public Bounds scaleFromMax(constFloat3 f) {
        float3 size = size().scale(f);
        min().set(max).sub(size);
        return this;
    }

    /**
     * Scales the size of this bounding box from its upper bounds by the given factor.
     *
     * @param f The factor to scale by component-wise
     * @return This bounding box
     */
    public Bounds scaleFromMax(constInt3 f) {
        float3 size = size().scale(f);
        min().set(max).sub(size);
        return this;
    }

    /**
     * Grows this bounding box if necessary to contain the given point.
     *
     * @param p The point to contain
     * @return This bounding box
     */
    public Bounds contain(constFloat3 p) {
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
    public Bounds contain(constBounds r) {
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
    public Bounds setContaining(constBounds a, constBounds b) {
        min().set(a.min).setMin(b.min);
        max().set(a.max).setMax(b.max);
        return this;
    }


    /**
     * Returns a new, empty bounding box at (0,0,0).
     *
     * @return A new zero bounding box
     */
    public static Bounds zero() {
        return new Bounds(new float3(), new float3(), true);
    }

    /**
     * Returns a new, empty bounding box at the specified position.
     *
     * @param center The position of the bounding box
     * @return A new zero bounding box
     */
    public static Bounds zero(constFloat3 center) {
        return new Bounds(Arguments.checkNull(center, "center"), center);
    }

    /**
     * Returns a new, empty bounding box at the specified position.
     *
     * @param center The position of the bounding box
     * @return A new zero bounding box
     */
    public static Bounds zero(constInt3 center) {
        return new Bounds(Arguments.checkNull(center, "center"), center);
    }

    /**
     * Returns a new bounding box with its lower bounds at (0,0).
     *
     * @param size The size of the bounding box (equivalently: its upper bounds)
     * @return A new bounding box with the specified size
     */
    public static Bounds fromZero(constFloat3 size) {
        return new Bounds(float3.zero, Arguments.checkNull(size, "size"));
    }

    /**
     * Returns a new bounding box with its lower bounds at (0,0).
     *
     * @param size The size of the bounding box (equivalently: its upper bounds)
     * @return A new bounding box with the specified size
     */
    public static Bounds fromZero(constInt3 size) {
        return new Bounds(float3.zero, Arguments.checkNull(size, "size"));
    }

    /**
     * Returns a new bounding box with the specified extents from the specified position.
     *
     * @param center The center of the bounding box
     * @param extents The extents (half size) of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds extending(constFloat3 center, constFloat3 extents) {
        return new Bounds(center.subed(extents), center.added(extents), true);
    }

    /**
     * Returns a new bounding box with the specified extents from the specified position.
     *
     * @param center The center of the bounding box
     * @param extents The extents (half size) of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds extending(constFloat3 center, constInt3 extents) {
        return new Bounds(center.subed(extents), center.added(extents), true);
    }

    /**
     * Returns a new bounding box with the specified extents from the specified position.
     *
     * @param center The center of the bounding box
     * @param extents The extents (half size) of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds extending(constInt3 center, constFloat3 extents) {
        return new Bounds(center.subed(extents), center.added(extents), true);
    }

    /**
     * Returns a new bounding box with the specified extents from the specified position.
     *
     * @param center The center of the bounding box
     * @param extents The extents (half size) of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds extending(constInt3 center, constInt3 extents) {
        float3 centerF = center.toF();
        return new Bounds(centerF.subed(extents), centerF.added(extents), true);
    }

    /**
     * Returns a new bounding box with the specified size centered at the specified position.
     *
     * @param center The center of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSize(constFloat3 center, constFloat3 size) {
        float3 extents = size.scaled(0.5f);
        return new Bounds(center.subed(extents), extents.add(size), true);
    }

    /**
     * Returns a new bounding box with the specified size centered at the specified position.
     *
     * @param center The center of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSize(constFloat3 center, constInt3 size) {
        float3 extents = size.scaled(0.5f);
        return new Bounds(center.subed(extents), extents.add(size), true);
    }

    /**
     * Returns a new bounding box with the specified size centered at the specified position.
     *
     * @param center The center of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSize(constInt3 center, constFloat3 size) {
        float3 extents = size.scaled(0.5f);
        return new Bounds(center.subed(extents), extents.add(size), true);
    }

    /**
     * Returns a new bounding box with the specified size centered at the specified position.
     *
     * @param center The center of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSize(constInt3 center, constInt3 size) {
        float3 extents = size.scaled(0.5f);
        return new Bounds(center.subed(extents), extents.add(size), true);
    }

    /**
     * Returns a new bounding box with the specified size starting at the specified lower bounds.
     *
     * @param min The lower bounds of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSizeFromMin(constFloat3 min, constFloat3 size) {
        return new Bounds(min.clone(), min.added(size), true);
    }

    /**
     * Returns a new bounding box with the specified size starting at the specified lower bounds.
     *
     * @param min The lower bounds of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSizeFromMin(constFloat3 min, constInt3 size) {
        return new Bounds(min.clone(), min.added(size), true);
    }

    /**
     * Returns a new bounding box with the specified size starting at the specified lower bounds.
     *
     * @param min The lower bounds of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSizeFromMin(constInt3 min, constFloat3 size) {
        return new Bounds(min.toF(), min.added(size), true);
    }

    /**
     * Returns a new bounding box with the specified size starting at the specified lower bounds.
     *
     * @param min The lower bounds of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSizeFromMin(constInt3 min, constInt3 size) {
        return new Bounds(min.toF(), min.toF().add(size), true);
    }

    /**
     * Returns a new bounding box with the specified size up to the specified upper bounds.
     *
     * @param max The upper bounds of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSizeFromMax(constFloat3 max, constFloat3 size) {
        return new Bounds(max.subed(size), max.clone(), true);
    }

    /**
     * Returns a new bounding box with the specified size up to the specified upper bounds.
     *
     * @param max The upper bounds of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSizeFromMax(constFloat3 max, constInt3 size) {
        return new Bounds(max.subed(size), max.clone(), true);
    }

    /**
     * Returns a new bounding box with the specified size up to the specified upper bounds.
     *
     * @param max The upper bounds of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSizeFromMax(constInt3 max, constFloat3 size) {
        return new Bounds(max.subed(size), max.toF(), true);
    }

    /**
     * Returns a new bounding box with the specified size up to the specified upper bounds.
     *
     * @param max The upper bounds of the bounding box
     * @param size The size of the bounding box
     * @return A new bounding box with the specified position and size
     */
    public static Bounds withSizeFromMax(constInt3 max, constInt3 size) {
        return new Bounds(max.toF().subed(size), max.toF(), true);
    }

    /**
     * Returns a new bounding box with the specified vectors as lower and upper bounds references.
     * Modifying the vectors also modifies the bounding box, and vice versa.
     *
     * @param minRef The vector to use as lower bounds of the bounding box
     * @param maxRef The vector to use as upper bounds of the bounding box
     * @return A new bounding box using the specified vector instances
     */
    public static Bounds ref(float3 minRef, float3 maxRef) {
        return new Bounds(Arguments.checkNull(minRef, "minRef"), Arguments.checkNull(maxRef, "maxRef"), true);
    }

    /**
     * Returns the smallest bounding box which contains both specified rectangles.
     *
     * @param a The first bounding box to be contained
     * @param b The second bounding box to be contained
     * @return A bounding box containing both specified rectangles
     */
    public static Bounds combining(constBounds a, constBounds b) {
        return new Bounds(float3.min(a.min, b.min), float3.max(a.max, b.max), true);
    }
}
