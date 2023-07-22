package com.github.rccookie.math.collision;

import com.github.rccookie.math.Ray2;
import com.github.rccookie.math.constFloat2;
import com.github.rccookie.math.constRay2;
import com.github.rccookie.math.float2;
import com.github.rccookie.util.Arguments;

public final class Raycast2D {

    final Shape2D shape;
    final constRay2 ray;
    final float distance;
    final float shapeIndex;
    final constFloat2 normal;

    public Raycast2D(Shape2D shape, constRay2 ray, float distance, float shapeIndex, constFloat2 normal) {
        this.shape = Arguments.checkNull(shape, "shape");
        this.ray = ray.clone();
        this.distance = distance;
        this.shapeIndex = shapeIndex;
        this.normal = normal.normed();
    }

    private Raycast2D(constRay2 ray, float maxLength) {
        shape = null;
        this.ray = ray.clone();
        distance = maxLength;
        shapeIndex = Float.NaN;
        normal = float2.NaN;
    }


    public boolean hit() {
        return shape != null;
    }

    public Shape2D shape() {
        return shape;
    }

    public constRay2 ray() {
        return ray;
    }

    public float2 point() {
        return ray.getPoint(distance);
    }

    public float distance() {
        return distance;
    }

    public constFloat2 normal() {
        return normal;
    }

    public float shapeIndex() {
        return shapeIndex;
    }


    public static Raycast2D miss(Ray2 ray, float maxLength) {
        return new Raycast2D(ray, maxLength);
    }
}
