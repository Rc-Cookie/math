package com.github.rccookie.math;

import com.github.rccookie.util.Arguments;

/**
 * A mutable 2D ray (an infinite line staring at a point).
 */
public final class Ray2 extends constRay2 {

    /**
     * Creates a new ray.
     *
     * @param origin The start of the ray
     * @param direction The direction of the ray, will be normalized
     */
    public Ray2(constFloat2 origin, constFloat2 direction) {
        super(origin.clone(), direction.normed(), true);
    }

    Ray2(float2 origin, float2 direction, boolean ignored) {
        super(origin, direction, ignored);
    }


    /**
     * Returns a mutable reference to the origin of this ray. The ray can be modified
     * by modifying the returned vector.
     *
     * @return A reference to the origin of this ray
     */
    @Override
    public float2 origin() {
        return (float2) super.origin();
    }

    /**
     * Returns a mutable reference to the direction of this ray. The ray can be modified
     * by modifying the returned vector. The direction vector should normally be normalized,
     * which has to be ensured by the modifier itself.
     *
     * @return A reference to the direction vector of this ray
     */
    @Override
    public float2 direction() {
        return (float2) super.direction();
    }

    /**
     * Normalizes the direction vector of this ray so that is has length 1.
     *
     * @return This ray
     */
    public Ray2 norm() {
        direction().norm();
        return this;
    }


    public static Ray2 ref(float2 originRef, float2 directionRef) {
        return new Ray2(Arguments.checkNull(originRef, "originRef"),
                Arguments.checkNull(directionRef, "directionRef").norm(), true);
    }

    public static Ray2 noNorm(constFloat2 origin, constFloat2 direction) {
        return new Ray2(origin.clone(), direction.clone(), true);
    }

    public static Ray2 refNoNorm(float2 originRef, float2 directionRef) {
        return new Ray2(Arguments.checkNull(originRef, "originRef"),
                Arguments.checkNull(directionRef, "directionRef"), true);
    }
}
