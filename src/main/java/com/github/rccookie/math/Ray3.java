package com.github.rccookie.math;

import com.github.rccookie.util.Arguments;

/**
 * A mutable 3D ray (an infinite line staring at a point).
 */
public final class Ray3 extends constRay3 {

    /**
     * Creates a new ray.
     *
     * @param origin The start of the ray
     * @param direction The direction of the ray, will be normalized
     */
    public Ray3(constFloat3 origin, constFloat3 direction) {
        super(origin.clone(), direction.normed(), true);
    }

    Ray3(float3 origin, float3 direction, boolean ignored) {
        super(origin, direction, ignored);
    }


    /**
     * Returns a mutable reference to the origin of this ray. The ray can be modified
     * by modifying the returned vector.
     *
     * @return A reference to the origin of this ray
     */
    @Override
    public float3 origin() {
        return (float3) super.origin();
    }

    /**
     * Returns a mutable reference to the direction of this ray. The ray can be modified
     * by modifying the returned vector. The direction vector should normally be normalized,
     * which has to be ensured by the modifier itself.
     *
     * @return A reference to the direction vector of this ray
     */
    @Override
    public float3 direction() {
        return (float3) super.direction();
    }

    /**
     * Normalizes the direction vector of this ray so that is has length 1.
     *
     * @return This ray
     */
    public Ray3 norm() {
        direction().norm();
        return this;
    }


    public static Ray3 ref(float3 originRef, float3 directionRef) {
        return new Ray3(Arguments.checkNull(originRef, "originRef"),
                Arguments.checkNull(directionRef, "directionRef").norm(), true);
    }

    public static Ray3 noNorm(constFloat3 origin, constFloat3 direction) {
        return new Ray3(origin.clone(), direction.clone(), true);
    }

    public static Ray3 refNoNorm(float3 originRef, float3 directionRef) {
        return new Ray3(Arguments.checkNull(originRef, "originRef"),
                Arguments.checkNull(directionRef, "directionRef"), true);
    }
}
