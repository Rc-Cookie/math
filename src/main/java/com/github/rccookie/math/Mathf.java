package com.github.rccookie.math;

import java.util.Random;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Range;

/**
 * Extended math utility class.
 */
@SuppressWarnings({"ManualMinMaxCalculation", "DuplicatedCode"})
public final class Mathf {

    private Mathf() {
        throw new UnsupportedOperationException();
    }


    /**
     * Constant by which to multiply an angular value in degrees to obtain an
     * angular value in radians.
     */
    public static final float TO_RAD = 0.017453292519943295f;

    /**
     * Constant by which to multiply an angular value in radians to obtain an
     * angular value in degrees.
     */
    public static final float TO_DEG = 57.29577951308232f;

    /**
     * The constant pi, in float precision.
     */
    public static final float PI = (float) Math.PI;

    /**
     * The constant e, in float precision.
     */
    public static final float E = (float) Math.E;


    //#region min/max/clamp

    /**
     * Returns the minimum of the two values.
     *
     * @param a The first value
     * @param b The second value
     * @return The smaller value of the two
     */
    @Contract(pure = true)
    public static int min(int a, int b) {
        return Math.min(a, b);
    }

    /**
     * Returns the minimum of the two values.
     *
     * @param a The first value
     * @param b The second value
     * @return The smaller value of the two
     */
    @Contract(pure = true)
    public static long min(long a, long b) {
        return Math.min(a, b);
    }

    /**
     * Returns the minimum of the two values.
     *
     * @param a The first value
     * @param b The second value
     * @return The smaller value of the two
     */
    @Contract(pure = true)
    public static float min(float a, float b) {
        return a < b ? a : b;
    }

    /**
     * Returns the minimum of the two values.
     *
     * @param a The first value
     * @param b The second value
     * @return The smaller value of the two
     */
    @Contract(pure = true)
    public static double min(double a, double b) {
        return a < b ? a : b;
    }

    /**
     * Returns the minimum of the values. If the array is empty, {@link Integer#MAX_VALUE}
     * will be returned.
     *
     * @param values The values to find the minimum of
     * @return The minimum value
     */
    @Contract(pure = true)
    public static int min(int... values) {
        if(values.length == 0) return Integer.MAX_VALUE;
        int x = values[0];
        for(int i=1; i<values.length; i++)
            x = Math.min(x, values[i]);
        return x;
    }

    /**
     * Returns the minimum of the values. If the array is empty, {@link Long#MAX_VALUE}
     * will be returned.
     *
     * @param values The values to find the minimum of
     * @return The minimum value
     */
    @Contract(pure = true)
    public static long min(long... values) {
        if(values.length == 0) return Integer.MAX_VALUE;
        long x = values[0];
        for(int i=1; i<values.length; i++)
            x = Math.min(x, values[i]);
        return x;
    }

    /**
     * Returns the minimum of the values. If the array is empty, {@link Float#POSITIVE_INFINITY}
     * will be returned.
     *
     * @param values The values to find the minimum of
     * @return The minimum value
     */
    @Contract(pure = true)
    public static float min(float... values) {
        if(values.length == 0) return Float.POSITIVE_INFINITY;
        float x = values[0];
        for(int i=1; i<values.length; i++)
            if(values[i] < x) x = values[i];
        return x;
    }

    /**
     * Returns the minimum of the values. If the array is empty, {@link Double#POSITIVE_INFINITY}
     * will be returned.
     *
     * @param values The values to find the minimum of
     * @return The minimum value
     */
    @Contract(pure = true)
    public static double min(double... values) {
        if(values.length == 0) return Double.POSITIVE_INFINITY;
        double x = values[0];
        for(int i=1; i<values.length; i++)
            if(values[i] < x) x = values[i];
        return x;
    }

    /**
     * Returns the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The minimum component
     */
    @Contract(pure = true)
    public static int min(constInt2 v) {
        return Math.min(v.x, v.y);
    }

    /**
     * Returns the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The minimum component
     */
    @Contract(pure = true)
    public static int min(constInt3 v) {
        return Math.min(Math.min(v.x, v.y), v.z);
    }

    /**
     * Returns the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The minimum component
     */
    @Contract(pure = true)
    public static int min(constInt4 v) {
        return Math.min(Math.min(v.x, v.y), Math.min(v.z, v.w));
    }

    /**
     * Returns the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The minimum component
     */
    @Contract(pure = true)
    public static int min(constIntN v) {
        return min(v.components);
    }

    /**
     * Returns the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The minimum component
     */
    @Contract(pure = true)
    public static float min(constFloat2 v) {
        return v.x < v.y ? v.x : v.y;
    }

    /**
     * Returns the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The minimum component
     */
    @Contract(pure = true)
    public static float min(constFloat3 v) {
        return min(min(v.x, v.y), v.z);
    }

    /**
     * Returns the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The minimum component
     */
    @Contract(pure = true)
    public static float min(constFloat4 v) {
        return min(min(v.x, v.y), min(v.z, v.w));
    }

    /**
     * Returns the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The minimum component
     */
    @Contract(pure = true)
    public static float min(constFloatN v) {
        return min(v.components);
    }

    /**
     * Returns the index of the minimum of the values. If the array is empty, -1
     * will be returned.
     *
     * @param values The values to find the minimum of
     * @return The index of the minimum value
     */
    @Contract(pure = true)
    public static int minIndex(int... values) {
        if(values.length == 0) return -1;
        int x = 0;
        for(int i=1; i<values.length; i++)
            if(values[i] < values[x]) x = i;
        return x;
    }

    /**
     * Returns the index of the minimum of the values. If the array is empty, -1
     * will be returned.
     *
     * @param values The values to find the minimum of
     * @return The index of the minimum value
     */
    @Contract(pure = true)
    public static int minIndex(long... values) {
        if(values.length == 0) return -1;
        int x = 0;
        for(int i=1; i<values.length; i++)
            if(values[i] < values[x]) x = i;
        return x;
    }

    /**
     * Returns the index of the minimum of the values. If the array is empty, -1
     * will be returned.
     *
     * @param values The values to find the minimum of
     * @return The index of the minimum value
     */
    @Contract(pure = true)
    public static int minIndex(float... values) {
        if(values.length == 0) return -1;
        int x = 0;
        for(int i=1; i<values.length; i++)
            if(values[i] < values[x]) x = i;
        return x;
    }

    /**
     * Returns the index of the minimum of the values. If the array is empty, -1
     * will be returned.
     *
     * @param values The values to find the minimum of
     * @return The index of the minimum value
     */
    @Contract(pure = true)
    public static int minIndex(double... values) {
        if(values.length == 0) return -1;
        int x = 0;
        for(int i=1; i<values.length; i++)
            if(values[i] < values[x]) x = i;
        return x;
    }

    /**
     * Returns the index of the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The index of the minimum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 1)
    public static int minIndex(constInt2 v) {
        return v.x < v.y ? 1 : 0;
    }

    /**
     * Returns the index of the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The index of the minimum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 2)
    public static int minIndex(constInt3 v) {
        return v.x < v.y ? v.x < v.z ? 0 : 2 : v.y < v.z ? 1 : 2;
    }

    /**
     * Returns the index of the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The index of the minimum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 3)
    public static int minIndex(constInt4 v) {
        if(v.x < v.y)
            return v.x < v.z ? v.x < v.w ? 0 : 3 : v.z < v.w ? 2 : 3;
        return v.y < v.z ? v.y < v.w ? 1 : 3 : v.z < v.w ? 2 : 3;
    }

    /**
     * Returns the index of the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The index of the minimum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int minIndex(constIntN v) {
        return min(v.components);
    }

    /**
     * Returns the index of the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The index of the minimum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 1)
    public static int minIndex(constFloat2 v) {
        return v.x < v.y ? 1 : 0;
    }

    /**
     * Returns the index of the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The index of the minimum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 2)
    public static int minIndex(constFloat3 v) {
        return v.x < v.y ? v.x < v.z ? 0 : 2 : v.y < v.z ? 1 : 2;
    }

    /**
     * Returns the index of the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The index of the minimum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 3)
    public static int minIndex(constFloat4 v) {
        if(v.x < v.y)
            return v.x < v.z ? v.x < v.w ? 0 : 3 : v.z < v.w ? 2 : 3;
        return v.y < v.z ? v.y < v.w ? 1 : 3 : v.z < v.w ? 2 : 3;
    }

    /**
     * Returns the index of the minimum component of the given vector.
     *
     * @param v The vector to find the minimum component of
     * @return The index of the minimum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int minIndex(constFloatN v) {
        return minIndex(v.components);
    }

    /**
     * Returns the maximum of the two values.
     *
     * @param a The first value
     * @param b The second value
     * @return The greater value of the two
     */
    @Contract(pure = true)
    public static int max(int a, int b) {
        return Math.max(a, b);
    }

    /**
     * Returns the maximum of the two values.
     *
     * @param a The first value
     * @param b The second value
     * @return The greater value of the two
     */
    @Contract(pure = true)
    public static long max(long a, long b) {
        return Math.max(a, b);
    }

    /**
     * Returns the maximum of the two values.
     *
     * @param a The first value
     * @param b The second value
     * @return The greater value of the two
     */
    @Contract(pure = true)
    public static float max(float a, float b) {
        return a > b ? a : b;
    }

    /**
     * Returns the maximum of the two values.
     *
     * @param a The first value
     * @param b The second value
     * @return The greater value of the two
     */
    @Contract(pure = true)
    public static double max(double a, double b) {
        return a > b ? a : b;
    }

    /**
     * Returns the maximum of the values. If the array is empty, {@link Integer#MIN_VALUE}
     * will be returned.
     *
     * @param values The values to find the maximum of
     * @return The maximum value
     */
    @Contract(pure = true)
    public static int max(int... values) {
        if(values.length == 0) return Integer.MIN_VALUE;
        int x = values[0];
        for(int i=1; i<values.length; i++)
            x = Math.max(x, values[i]);
        return x;
    }

    /**
     * Returns the maximum of the values. If the array is empty, {@link Long#MIN_VALUE}
     * will be returned.
     *
     * @param values The values to find the maximum of
     * @return The maximum value
     */
    @Contract(pure = true)
    public static long max(long... values) {
        if(values.length == 0) return Long.MIN_VALUE;
        long x = values[0];
        for(int i=1; i<values.length; i++)
            x = Math.max(x, values[i]);
        return x;
    }

    /**
     * Returns the maximum of the values. If the array is empty, {@link Float#NEGATIVE_INFINITY}
     * will be returned.
     *
     * @param values The values to find the maximum of
     * @return The maximum value
     */
    @Contract(pure = true)
    public static float max(float... values) {
        if(values.length == 0) return Float.NEGATIVE_INFINITY;
        float x = values[0];
        for(int i=1; i<values.length; i++)
            if(values[i] < x) x = values[i];
        return x;
    }

    /**
     * Returns the maximum of the values. If the array is empty, {@link Double#NEGATIVE_INFINITY}
     * will be returned.
     *
     * @param values The values to find the maximum of
     * @return The maximum value
     */
    @Contract(pure = true)
    public static double max(double... values) {
        if(values.length == 0) return Double.NEGATIVE_INFINITY;
        double x = values[0];
        for(int i=1; i<values.length; i++)
            if(values[i] < x) x = values[i];
        return x;
    }

    /**
     * Returns the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The maximum component
     */
    @Contract(pure = true)
    public static int max(constInt2 v) {
        return Math.max(v.x, v.y);
    }

    /**
     * Returns the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The maximum component
     */
    @Contract(pure = true)
    public static int max(constInt3 v) {
        return Math.max(Math.max(v.x, v.y), v.z);
    }

    /**
     * Returns the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The maximum component
     */
    @Contract(pure = true)
    public static int max(constInt4 v) {
        return Math.max(Math.max(v.x, v.y), Math.max(v.z, v.w));
    }

    /**
     * Returns the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The maximum component
     */
    @Contract(pure = true)
    public static int max(constIntN v) {
        return max(v.components);
    }

    /**
     * Returns the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The maximum component
     */
    @Contract(pure = true)
    public static float max(constFloat2 v) {
        return v.x > v.y ? v.x : v.y;
    }

    /**
     * Returns the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The maximum component
     */
    @Contract(pure = true)
    public static float max(constFloat3 v) {
        return max(max(v.x, v.y), v.z);
    }

    /**
     * Returns the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The maximum component
     */
    @Contract(pure = true)
    public static float max(constFloat4 v) {
        return max(max(v.x, v.y), max(v.z, v.w));
    }

    /**
     * Returns the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The maximum component
     */
    @Contract(pure = true)
    public static float max(constFloatN v) {
        return max(v.components);
    }

    /**
     * Returns the index of the maximum of the values. If the array is empty, -1
     * will be returned.
     *
     * @param values The values to find the maximum of
     * @return The index of the maximum value
     */
    @Contract(pure = true)
    public static int maxIndex(int... values) {
        if(values.length == 0) return -1;
        int x = 0;
        for(int i=1; i<values.length; i++)
            if(values[i] > values[x]) x = i;
        return x;
    }

    /**
     * Returns the index of the maximum of the values. If the array is empty, -1
     * will be returned.
     *
     * @param values The values to find the maximum of
     * @return The index of the maximum value
     */
    @Contract(pure = true)
    public static int maxIndex(long... values) {
        if(values.length == 0) return -1;
        int x = 0;
        for(int i=1; i<values.length; i++)
            if(values[i] > values[x]) x = i;
        return x;
    }

    /**
     * Returns the index of the maximum of the values. If the array is empty, -1
     * will be returned.
     *
     * @param values The values to find the maximum of
     * @return The index of the maximum value
     */
    @Contract(pure = true)
    public static int maxIndex(float... values) {
        if(values.length == 0) return -1;
        int x = 0;
        for(int i=1; i<values.length; i++)
            if(values[i] > values[x]) x = i;
        return x;
    }

    /**
     * Returns the index of the maximum of the values. If the array is empty, -1
     * will be returned.
     *
     * @param values The values to find the maximum of
     * @return The index of the maximum value
     */
    @Contract(pure = true)
    public static int maxIndex(double... values) {
        if(values.length == 0) return -1;
        int x = 0;
        for(int i=1; i<values.length; i++)
            if(values[i] > values[x]) x = i;
        return x;
    }

    /**
     * Returns the index of the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The index of the maximum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 1)
    public static int maxIndex(constInt2 v) {
        return v.x < v.y ? 1 : 0;
    }

    /**
     * Returns the index of the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The index of the maximum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 2)
    public static int maxIndex(constInt3 v) {
        return v.x < v.y ? v.x < v.z ? 0 : 2 : v.y < v.z ? 1 : 2;
    }

    /**
     * Returns the index of the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The index of the maximum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 3)
    public static int maxIndex(constInt4 v) {
        if(v.x < v.y)
            return v.x < v.z ? v.x < v.w ? 0 : 3 : v.z < v.w ? 2 : 3;
        return v.y < v.z ? v.y < v.w ? 1 : 3 : v.z < v.w ? 2 : 3;
    }

    /**
     * Returns the index of the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The index of the maximum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int maxIndex(constIntN v) {
        return max(v.components);
    }

    /**
     * Returns the index of the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The index of the maximum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 1)
    public static int maxIndex(constFloat2 v) {
        return v.x < v.y ? 1 : 0;
    }

    /**
     * Returns the index of the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The index of the maximum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 2)
    public static int maxIndex(constFloat3 v) {
        return v.x < v.y ? v.x < v.z ? 0 : 2 : v.y < v.z ? 1 : 2;
    }

    /**
     * Returns the index of the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The index of the maximum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = 3)
    public static int maxIndex(constFloat4 v) {
        if(v.x < v.y)
            return v.x < v.z ? v.x < v.w ? 0 : 3 : v.z < v.w ? 2 : 3;
        return v.y < v.z ? v.y < v.w ? 1 : 3 : v.z < v.w ? 2 : 3;
    }

    /**
     * Returns the index of the maximum component of the given vector.
     *
     * @param v The vector to find the maximum component of
     * @return The index of the maximum component
     */
    @Contract(pure = true)
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int maxIndex(constFloatN v) {
        return maxIndex(v.components);
    }

    /**
     * Clamps the given value between the specified lower and upper bound.
     * <p>a should be &lt;= b, otherwise the result may be anything.</p>
     *
     * @param x The value to clamp
     * @param a The lower bound
     * @param b The upper bound
     * @return The value clamped
     */
    @Contract(pure = true)
    public static int clamp(int x, int a, int b) {
        return x < a ? a : x > b ? b : x;
    }

    /**
     * Clamps the given value between the specified lower and upper bound.
     * <p>a should be &lt;= b, otherwise the result may be anything.</p>
     *
     * @param x The value to clamp
     * @param a The lower bound
     * @param b The upper bound
     * @return The value clamped
     */
    @Contract(pure = true)
    public static long clamp(long x, long a, long b) {
        return x < a ? a : x > b ? b : x;
    }

    /**
     * Clamps the given value between the specified lower and upper bound.
     * <p>a should be &lt;= b, otherwise the result may be anything.</p>
     *
     * @param x The value to clamp
     * @param a The lower bound
     * @param b The upper bound
     * @return The value clamped
     */
    @Contract(pure = true)
    public static float clamp(float x, float a, float b) {
        return x < a ? a : x > b ? b : x;
    }

    /**
     * Clamps the given value between the specified lower and upper bound.
     * <p>a should be &lt;= b, otherwise the result may be anything.</p>
     *
     * @param x The value to clamp
     * @param a The lower bound
     * @param b The upper bound
     * @return The value clamped
     */
    @Contract(pure = true)
    public static double clamp(double x, double a, double b) {
        return x < a ? a : x > b ? b : x;
    }

    /**
     * Calculates a smooth minimum between the two given values. The result <code>x</code> will
     * for any value smoothing value be in the range <code>[min(a,b), max(a,b)]</code>
     *
     * @param a The first value
     * @param b The second value
     * @param s The amount of smoothing to do, should be >= 1, where 1 means no smoothing
     * @return A smooth minimum of the two values
     */
    public static float smoothMin(float a, float b, float s) {
        float h = max(s - (a<b ? b-a : a-b), 0) / s;
        return (a<b ? a : b) - h*h*h * s/6;
    }

    /**
     * Calculates a smooth maximum between the two given values. The result <code>x</code> will
     * for any valid smoothing value be in the range <code>[min(a,b), max(a,b)]</code>
     *
     * @param a The first value
     * @param b The second value
     * @param s The amount of smoothing to do, should be >= 1, where 1 means no smoothing
     * @return A smooth maximum of the two values
     */
    public static float smoothMax(float a, float b, float s) {
        float h = max(s - (a>b ? a-b : b-a), 0) / s;
        return (a>b ? a : b) + h*h*h * s/6;
    }

    /**
     * Calculates a smooth minimum between the two given values. The result <code>x</code> will
     * for any value smoothing value be in the range <code>[min(a,b), max(a,b)]</code>
     *
     * @param a The first value
     * @param b The second value
     * @param s The amount of smoothing to do, should be >= 1, where 1 means no smoothing
     * @return A smooth minimum of the two values
     */
    public static double smoothMin(double a, double b, double s) {
        double h = max(s - (a<b ? b-a : a-b), 0) / s;
        return (a<b ? a : b) - h*h*h * s/6;
    }

    /**
     * Calculates a smooth maximum between the two given values. The result <code>x</code> will
     * for any valid smoothing value be in the range <code>[min(a,b), max(a,b)]</code>
     *
     * @param a The first value
     * @param b The second value
     * @param s The amount of smoothing to do, should be >= 1, where 1 means no smoothing
     * @return A smooth maximum of the two values
     */
    public static double smoothMax(double a, double b, double s) {
        double h = max(s - (a>b ? a-b : b-a), 0) / s;
        return (a>b ? a : b) + h*h*h * s/6;
    }

    //#endregion

    //#region abs/sign

    /**
     * Returns the absolute value of x, that is, -x if x is negative, otherwise x itself.
     *
     * @param x The value to get the absolute of
     * @return The absolute of <code>x</code>
     */
    @Contract(pure = true)
    public static int abs(int x) {
        return Math.abs(x);
    }

    /**
     * Returns the absolute value of x, that is, -x if x is negative, otherwise x itself.
     *
     * @param x The value to get the absolute of
     * @return The absolute of <code>x</code>
     */
    @Contract(pure = true)
    public static long abs(long x) {
        return Math.abs(x);
    }

    /**
     * Returns the absolute value of x, that is, -x if x is negative, otherwise x itself.
     *
     * @param x The value to get the absolute of
     * @return The absolute of <code>x</code>
     */
    @Contract(pure = true)
    public static float abs(float x) {
        return Math.abs(x);
    }

    /**
     * Returns the absolute value of x, that is, -x if x is negative, otherwise x itself.
     *
     * @param x The value to get the absolute of
     * @return The absolute of <code>x</code>
     */
    @Contract(pure = true)
    public static double abs(double x) {
        return Math.abs(x);
    }

    /**
     * Returns -1, 0 or 1 if x is negative, 0 or positive.
     *
     * @param x The value to get the sign of
     * @return The signum function for x
     */
    public static int sign(int x) {
        return x != 0 ? x > 0 ? 1 : -1 : 0;
    }

    /**
     * Returns -1, 0 or 1 if x is negative, 0 or positive.
     *
     * @param x The value to get the sign of
     * @return The signum function for x
     */
    public static int sign(long x) {
        return x != 0 ? x > 0 ? 1 : -1 : 0;
    }

    /**
     * Returns -1, 0 or 1 if x is negative, 0 or positive. The result of
     * {@link Float#NaN} is undefined.
     *
     * @param x The value to get the sign of
     * @return The signum function for x
     */
    public static int sign(float x) {
        return x == 0 ? 0 : 1 - (2 * (Float.floatToRawIntBits(x) >>> 31));
    }

    /**
     * Returns -1, 0 or 1 if x is negative, 0 or positive. The result of
     * {@link Double#NaN} is undefined.
     *
     * @param x The value to get the sign of
     * @return The signum function for x
     */
    public static int sign(double x) {
        return x == 0 ? 0 : 1 - (2 * (int) (Double.doubleToRawLongBits(x) >>> 63));
    }

    /**
     * Returns -1, 0, 1 or {@link Float#NaN} if x is negative, 0, positive or {@link Float#NaN}.
     *
     * @param x The value to get the sign of
     * @return The signum function for x
     */
    public static float signF(float x) {
        return Math.signum(x);
    }

    /**
     * Returns -1, 0, 1 or {@link Double#NaN} if x is negative, 0, positive or {@link Double#NaN}.
     *
     * @param x The value to get the sign of
     * @return The signum function for x
     */
    public static double signD(double x) {
        return Math.signum(x);
    }

    //#endregion

    //#region rounding

    /**
     * Returns x rounded to the nearest int.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static int round(float x) {
        return Math.round(x);
    }

    /**
     * Returns x rounded to the nearest int.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static int round(double x) {
        return round((float) x);
    }

    /**
     * Returns x rounded to the nearest long.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static long roundL(float x) {
        return roundL((double) x);
    }

    /**
     * Returns x rounded to the nearest long.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static long roundL(double x) {
        return Math.round(x);
    }

    /**
     * Returns x rounded down to the nearest int.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static int floor(float x) {
        return (int) Math.floor(x);
    }

    /**
     * Returns x rounded down to the nearest int.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static int floor(double x) {
        return (int) Math.floor(x);
    }

    /**
     * Returns x rounded down to the nearest long.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static long floorL(float x) {
        return (long) Math.floor(x);
    }

    /**
     * Returns x rounded down to the nearest long.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static long floorL(double x) {
        return (long) Math.floor(x);
    }

    /**
     * Returns x rounded down to the nearest mathematical integer.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static double floorD(double x) {
        return Math.floor(x);
    }

    /**
     * Returns x rounded up to the nearest int.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static int ceil(float x) {
        return (int) Math.ceil(x);
    }

    /**
     * Returns x rounded up to the nearest int.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static int ceil(double x) {
        return (int) Math.ceil(x);
    }

    /**
     * Returns x rounded up to the nearest long.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static long ceilL(float x) {
        return (long) Math.ceil(x);
    }

    /**
     * Returns x rounded up to the nearest long.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static long ceilL(double x) {
        return (long) Math.ceil(x);
    }

    /**
     * Returns x rounded up to the nearest mathematical integer.
     *
     * @param x The value to round
     * @return The rounded value
     */
    @Contract(pure = true)
    public static double ceilD(double x) {
        return Math.ceil(x);
    }

    //#endregion

    //#region mapping

    /**
     * Remaps the given value from the range <code>[fromLow, fromHigh]</code> to
     * the range <code>[toLow, toHigh]</code>.
     *
     * @param x The value to map, usually in the range <code>[fromLow, fromHigh]</code>
     * @param fromLow The lower bound of the input range to map
     * @param fromHigh The upper bound of the input range to map, must differ from <code>fromLow</code>
     * @param toLow The lower bound of the output range to map to
     * @param toHigh The upper bound of the output range to map to, should be different from <code>toLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[toLow, toHigh]</code> if and
     *         only if the input was in the range <code>[fromLow, fromHigh]</code>
     * @apiNote Watch out for integer precision overflows with this method, <code>(x-fromLow)*(toHigh-toLow)</code>
     *          must still be within integer bounds. Otherwise, consider using the <code>long</code> precision
     *          variant of this method instead.
     */
    @Contract(pure = true)
    public static int map(int x, int fromLow, int fromHigh, int toLow, int toHigh) {
        return (x - fromLow) * (toHigh - toLow) / (fromHigh - fromLow) + toLow;
    }

    /**
     * Remaps the given value from the range <code>[from.x, from.y]</code> to
     * the range <code>[toLow, toHigh]</code>.
     *
     * @param x The value to map, usually in the range <code>[from.x, from.y]</code>
     * @param from The range of the input to map, x and y must be different from each other
     * @param toLow The lower bound of the output range to map to
     * @param toHigh The upper bound of the output range to map to, should be different from <code>toLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[toLow, toHigh]</code> if and
     *         only if the input was in the range <code>[from.x, from.y]</code>
     * @apiNote Watch out for integer precision overflows with this method, <code>(x-from.x)*(toHigh-toLow)</code>
     *          must still be within integer bounds. Otherwise, consider using the <code>long</code> precision
     *          variant of this method instead.
     */
    @Contract(pure = true)
    public static int map(int x, int2 from, int toLow, int toHigh) {
        return (x - from.x()) * (toHigh - toLow) / (from.y() - from.x()) + toLow;
    }

    /**
     * Remaps the given value from the range <code>[fromLow, fromHigh]</code> to
     * the range <code>[to.x, to.y]</code>.
     *
     * @param x The value to map, usually in the range <code>[fromLow, fromHigh]</code>
     * @param fromLow The lower bound of the input range to map
     * @param fromHigh The upper bound of the input range to map, must differ from <code>fromLow</code>
     * @param to The output range to map to, x and y should be different from each other
     * @return The value <code>x</code> mapped, the result is in the range <code>[to.x, to.y]</code> if and
     *         only if the input was in the range <code>[fromLow, fromHigh]</code>
     * @apiNote Watch out for integer precision overflows with this method, <code>(x-fromLow)*(to.y-to.x)</code>
     *          must still be within integer bounds. Otherwise, consider using the <code>long</code> precision
     *          variant of this method instead.
     */
    @Contract(pure = true)
    public static int map(int x, int fromLow, int fromHigh, int2 to) {
        return (x - fromLow) * (to.y() - to.x()) / (fromHigh - fromLow) + to.x();
    }

    /**
     * Remaps the given value from the range <code>[from.x, from.y]</code> to
     * the range <code>[to.x, to.y]</code>.
     *
     * @param x The value to map, usually in the range <code>[from.x, from.y]</code>
     * @param from The range of the input to map, x and y must be different from each other
     * @param to The output range to map to, x and y should be different from each other
     * @return The value <code>x</code> mapped, the result is in the range <code>[to.x, to.y]</code> if and
     *         only if the input was in the range <code>[from.x, from.y]</code>
     * @apiNote Watch out for integer precision overflows with this method, <code>(x-from.x)*(to.y-to.x)</code>
     *          must still be within integer bounds. Otherwise, consider using the <code>long</code> precision
     *          variant of this method instead.
     */
    @Contract(pure = true)
    public static int map(int x, int2 from, int2 to) {
        return (x - from.x()) * (to.y() - to.x()) / (from.y() - from.x()) + to.x();
    }

    /**
     * Remaps the given value from the range <code>[0,1]</code> to the range <code>[toLow, toHigh]</code>.
     *
     * @param x The value to map, usually in the range <code>[0,1]</code>
     * @param toLow The lower bound of the output range to map to
     * @param toHigh The upper bound of the output range to map to, should be different from <code>toLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[toLow, toHigh]</code> if and
     *         only if the input was in the range <code>[0,1]</code>
     */
    @Contract(pure = true)
    public static int mapFrom01(int x, int toLow, int toHigh) {
        return x * (toHigh - toLow) + toLow;
    }

    /**
     * Remaps the given value from the range <code>[0,1]</code> to the range <code>[to.x, to.y]</code>.
     *
     * @param x The value to map, usually in the range <code>[0,1]</code>
     * @param to The output range to map to, x and y should be different from each other
     * @return The value <code>x</code> mapped, the result is in the range <code>[to.x, to.y]</code> if and
     *         only if the input was in the range <code>[0,1]</code>
     */
    @Contract(pure = true)
    public static int mapFrom01(int x, int2 to) {
        return x * (to.y() - to.x()) + to.x();
    }

    /**
     * Remaps the given value from the range <code>[fromLow, fromHigh]</code> to
     * the range <code>[toLow, toHigh]</code>.
     *
     * @param x The value to map, usually in the range <code>[fromLow, fromHigh]</code>
     * @param fromLow The lower bound of the input range to map
     * @param fromHigh The upper bound of the input range to map, must differ from <code>fromLow</code>
     * @param toLow The lower bound of the output range to map to
     * @param toHigh The upper bound of the output range to map to, should be different from <code>toLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[toLow, toHigh]</code> if and
     *         only if the input was in the range <code>[fromLow, fromHigh]</code>
     */
    @Contract(pure = true)
    public static long map(long x, long fromLow, long fromHigh, long toLow, long toHigh) {
        return (x - fromLow) * (toHigh - toLow) / (fromHigh - fromLow) + toLow;
    }

    /**
     * Remaps the given value from the range <code>[0,1]</code> to the range <code>[toLow, toHigh]</code>.
     *
     * @param x The value to map, usually in the range <code>[0,1]</code>
     * @param toLow The lower bound of the output range to map to
     * @param toHigh The upper bound of the output range to map to, should be different from <code>toLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[toLow, toHigh]</code> if and
     *         only if the input was in the range <code>[0,1]</code>
     */
    @Contract(pure = true)
    public static long mapFrom01(long x, long toLow, long toHigh) {
        return x * (toHigh - toLow) + toLow;
    }

    /**
     * Remaps the given value from the range <code>[fromLow, fromHigh]</code> to
     * the range <code>[toLow, toHigh]</code>.
     *
     * @param x The value to map, usually in the range <code>[fromLow, fromHigh]</code>
     * @param fromLow The lower bound of the input range to map
     * @param fromHigh The upper bound of the input range to map, must differ from <code>fromLow</code>
     * @param toLow The lower bound of the output range to map to
     * @param toHigh The upper bound of the output range to map to, should be different from <code>toLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[toLow, toHigh]</code> if and
     *         only if the input was in the range <code>[fromLow, fromHigh]</code>
     */
    @Contract(pure = true)
    public static float map(float x, float fromLow, float fromHigh, float toLow, float toHigh) {
        return (x - fromLow) / (fromHigh - fromLow) * (toHigh - toLow) + toLow;
    }

    /**
     * Remaps the given value from the range <code>[from.x, from.y]</code> to
     * the range <code>[toLow, toHigh]</code>.
     *
     * @param x The value to map, usually in the range <code>[from.x, from.y]</code>
     * @param from The range of the input to map, x and y must be different from each other
     * @param toLow The lower bound of the output range to map to
     * @param toHigh The upper bound of the output range to map to, should be different from <code>toLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[toLow, toHigh]</code> if and
     *         only if the input was in the range <code>[from.x, from.y]</code>
     */
    @Contract(pure = true)
    public static float map(float x, float2 from, float toLow, float toHigh) {
        return (x - from.x()) / (from.y() - from.x()) * (toHigh - toLow) + toLow;
    }

    /**
     * Remaps the given value from the range <code>[fromLow, fromHigh]</code> to
     * the range <code>[to.x, to.y]</code>.
     *
     * @param x The value to map, usually in the range <code>[fromLow, fromHigh]</code>
     * @param fromLow The lower bound of the input range to map
     * @param fromHigh The upper bound of the input range to map, must differ from <code>fromLow</code>
     * @param to The output range to map to, x and y should be different from each other
     * @return The value <code>x</code> mapped, the result is in the range <code>[to.x, to.y]</code> if and
     *         only if the input was in the range <code>[fromLow, fromHigh]</code>
     */
    @Contract(pure = true)
    public static float map(float x, float fromLow, float fromHigh, float2 to) {
        return (x - fromLow) / (fromHigh - fromLow) * (to.y() - to.x()) + to.x();
    }

    /**
     * Remaps the given value from the range <code>[from.x, from.y]</code> to
     * the range <code>[to.x, to.y]</code>.
     *
     * @param x The value to map, usually in the range <code>[from.x, from.y]</code>
     * @param from The range of the input to map, x and y must be different from each other
     * @param to The output range to map to, x and y should be different from each other
     * @return The value <code>x</code> mapped, the result is in the range <code>[to.x, to.y]</code> if and
     *         only if the input was in the range <code>[from.x, from.y]</code>
     */
    @Contract(pure = true)
    public static float map(float x, float2 from, float2 to) {
        return (x - from.x()) / (from.y() - from.x()) * (to.y() - to.x()) + to.x();
    }

    /**
     * Remaps the given value from the range <code>[fromLow, fromHigh]</code> to
     * the range <code>[0,1]</code>.
     *
     * @param x The value to map, usually in the range <code>[fromLow, fromHigh]</code>
     * @param fromLow The lower bound of the input range to map
     * @param fromHigh The upper bound of the input range to map, must differ from <code>fromLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[0,1]</code> if and
     *         only if the input was in the range <code>[fromLow, fromHigh]</code>
     */
    @Contract(pure = true)
    public static float map01(float x, float fromLow, float fromHigh) {
        return (x - fromLow) / (fromHigh - fromLow);
    }

    /**
     * Remaps the given value from the range <code>[from.x, from.y]</code> to
     * the range <code>[0,1]</code>.
     *
     * @param x The value to map, usually in the range <code>[from.x, from.y]</code>
     * @param from The range of the input to map, x and y must be different from each other
     * @return The value <code>x</code> mapped, the result is in the range <code>[0,1]</code> if and
     *         only if the input was in the range <code>[from.x, from.y]</code>
     */
    @Contract(pure = true)
    public static float map01(float x, float2 from) {
        return (x - from.x()) / (from.y() - from.x());
    }

    /**
     * Remaps the given value from the range <code>[0,1]</code> to the range <code>[toLow, toHigh]</code>.
     *
     * @param x The value to map, usually in the range <code>[0,1]</code>
     * @param toLow The lower bound of the output range to map to
     * @param toHigh The upper bound of the output range to map to, should be different from <code>toLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[toLow, toHigh]</code> if and
     *         only if the input was in the range <code>[0,1]</code>
     */
    @Contract(pure = true)
    public static float mapFrom01(float x, float toLow, float toHigh) {
        return x * (toHigh - toLow) + toLow;
    }

    /**
     * Remaps the given value from the range <code>[0,1]</code> to the range <code>[to.x, to.y]</code>.
     *
     * @param x The value to map, usually in the range <code>[0,1]</code>
     * @param to The output range to map to, x and y should be different from each other
     * @return The value <code>x</code> mapped, the result is in the range <code>[to.x, to.y]</code> if and
     *         only if the input was in the range <code>[0,1]</code>
     */
    @Contract(pure = true)
    public static float mapFrom01(float x, float2 to) {
        return x * (to.y() - to.x()) + to.x();
    }

    /**
     * Remaps the given value from the range <code>[fromLow, fromHigh]</code> to
     * the range <code>[toLow, toHigh]</code>.
     *
     * @param x The value to map, usually in the range <code>[fromLow, fromHigh]</code>
     * @param fromLow The lower bound of the input range to map
     * @param fromHigh The upper bound of the input range to map, must differ from <code>fromLow</code>
     * @param toLow The lower bound of the output range to map to
     * @param toHigh The upper bound of the output range to map to, should be different from <code>toLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[toLow, toHigh]</code> if and
     *         only if the input was in the range <code>[fromLow, fromHigh]</code>
     */
    @Contract(pure = true)
    public static double map(double x, double fromLow, double fromHigh, double toLow, double toHigh) {
        return (x - fromLow) / (fromHigh - fromLow) * (toHigh - toLow) + toLow;
    }

    /**
     * Remaps the given value from the range <code>[fromLow, fromHigh]</code> to
     * the range <code>[0,1]</code>.
     *
     * @param x The value to map, usually in the range <code>[fromLow, fromHigh]</code>
     * @param fromLow The lower bound of the input range to map
     * @param fromHigh The upper bound of the input range to map, must differ from <code>fromLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[0,1]</code> if and
     *         only if the input was in the range <code>[fromLow, fromHigh]</code>
     */
    @Contract(pure = true)
    public static double map01(double x, double fromLow, double fromHigh) {
        return (x - fromLow) / (fromHigh - fromLow);
    }

    /**
     * Remaps the given value from the range <code>[0,1]</code> to the range <code>[toLow, toHigh]</code>.
     *
     * @param x The value to map, usually in the range <code>[0,1]</code>
     * @param toLow The lower bound of the output range to map to
     * @param toHigh The upper bound of the output range to map to, should be different from <code>toLow</code>
     * @return The value <code>x</code> mapped, the result is in the range <code>[toLow, toHigh]</code> if and
     *         only if the input was in the range <code>[0,1]</code>
     */
    @Contract(pure = true)
    public static double mapFrom01(double x, double toLow, double toHigh) {
        return x * (toHigh - toLow) + toLow;
    }

    //#endregion

    //#region lerp

    /**
     * Lerps ("linearly interpolates") <code>x</code> towards <code>to</code> by the given
     * amount. For example, an amount of 0 will return exactly <code>x</code>, and an amount of 1
     * will return <code>to</code>.
     *
     * @param x The value to interpolate
     * @param to The target to interpolate towards
     * @param amount The amount to interpolate by, usually a value between 0 and 1
     * @return The interpolated value of <code>x</code>
     */
    @Contract(pure = true)
    public static float lerp(float x, float to, float amount) {
        return (x - to) * amount + x;
    }

    /**
     * Lerps ("linearly interpolates") <code>x</code> towards <code>to</code> by the given
     * amount. For example, an amount of 0 will return exactly <code>x</code>, and an amount of 1
     * will return <code>to</code>.
     *
     * @param x The value to interpolate
     * @param to The target to interpolate towards
     * @param amount The amount to interpolate by, usually a value between 0 and 1
     * @return The interpolated value of <code>x</code>
     */
    @Contract(pure = true)
    public static double lerp(double x, double to, double amount) {
        return (x - to) * amount + x;
    }

    //#endregion

    //#region trigonometry
    //#region sin/cos/tan

    /**
     * Sin value steps per degree.
     */
    private static final int SIN_PRECISION = 100;
    private static final int MODULUS = 360 * SIN_PRECISION;
    private static final float[] SIN = new float[MODULUS];

    static {
        for(int i = 0; i< SIN.length; i++)
            SIN[i] = (float) Math.sin((i * Math.PI) / (SIN_PRECISION * 180));
    }


    public static void preload() { }


    private static float sinLookup(int a) {
        return a >= 0 ? SIN[a >= MODULUS ? a % MODULUS : a] : -SIN[-a >= MODULUS ? -a % MODULUS : -a];
    }

    /**
     * Returns the trigonometric sine of an angle <b>in degrees</b>, with reduces precision
     * for much higher speed.
     *
     * @param a The angle in degrees
     * @return The sine of that angle
     */
    @Contract(pure = true)
    public static float sin(float a) {
        return sinLookup((int)(a * SIN_PRECISION + 0.5));
    }

    /**
     * Returns the trigonometric sine of an angle <b>in degrees</b>.
     *
     * @param a The angle in degrees
     * @return The sine of that angle
     */
    @Contract(pure = true)
    public static double sin(double a) {
        return Math.sin(a);
    }

    /**
     * Returns the trigonometric cosine of an angle <b>in degrees</b>, with reduces precision
     * for much higher speed.
     *
     * @param a The angle in degrees
     * @return The cosine of that angle
     */
    @Contract(pure = true)
    public static float cos(float a) {
        return sinLookup((int)((a + 90) * SIN_PRECISION + 0.5));
    }

    /**
     * Returns the trigonometric cosine of an angle <b>in degrees</b>.
     *
     * @param a The angle in degrees
     * @return The cosine of that angle
     */
    @Contract(pure = true)
    public static double cos(double a) {
        return Math.cos(a);
    }

    /**
     * Returns the trigonometric tangent of an angle <b>in degrees</b>, with reduces precision
     * for much higher speed.
     *
     * @param a The angle in degrees
     * @return The tangent of that angle
     */
    @Contract(pure = true)
    public static float tan(float a) {
        return sin(a) / cos(a);
    }

    /**
     * Returns the trigonometric tangent of an angle <b>in degrees</b>.
     *
     * @param a The angle in degrees
     * @return The tangent of that angle
     */
    @Contract(pure = true)
    public static double tan(double a) {
        return Math.tan(a);
    }

    //#endregion

    //#region asin/acos/atan/atan2

    private static final int Size_Ac = 100000;

    private static final float[] Atan2 = new float[Size_Ac + 1];
//    private static final float[] Atan2_PM = new float[Size_Ac + 1];
//    private static final float[] Atan2_MP = new float[Size_Ac + 1];
//    private static final float[] Atan2_MM = new float[Size_Ac + 1];
//
//    private static final float[] Atan2_R = new float[Size_Ac + 1];
//    private static final float[] Atan2_RPM = new float[Size_Ac + 1];
//    private static final float[] Atan2_RMP = new float[Size_Ac + 1];
//    private static final float[] Atan2_RMM = new float[Size_Ac + 1];

    static {
        for (int i = 0; i <= Size_Ac; i++) {
            float d = (float) i / Size_Ac;
            float x = 1;
            float y = x * d;
            float v = (float) Math.atan2(y, x) * TO_DEG;
            Atan2[i] = v;
//            Atan2_PM[i] = 360 - v;
//            Atan2_MP[i] = -v;
//            Atan2_MM[i] = -360 + v;
//
//            Atan2_R[i] = 180 - v;
//            Atan2_RPM[i] = 90 + v;
//            Atan2_RMP[i] = -180 + v;
//            Atan2_RMM[i] = -180 - v;
        }
    }

    @Contract(pure = true)
    public static float atan2(float y, float x) {
        if (y < 0) {
            if (x < 0)
                return y < x ?
                        Atan2/*_RMM*/[(int) ((1 - x / y) * Size_Ac)] - 135 :
                        Atan2/*_MM*/[(int) (y / x * Size_Ac)] - 180;
            y = -y;
            return y > x ?
                    Atan2/*_RMP*/[(int) (x / y * Size_Ac)] - 90 :
                    Atan2/*_MP*/[(int) ((1 - y / x) * Size_Ac)] - 45;
        }
        if (x < 0) {
            x = -x;
            return y > x ?
                    Atan2/*_RPM*/[(int) (x / y * Size_Ac)] + 90 :
                    Atan2/*_PM*/[(int) ((1 - y / x) * Size_Ac)] + 135;
        }
        return y > x ?
                Atan2/*_R*/[(int) ((1 - x / y) * Size_Ac)] + 45 :
                Atan2[(int) (y / x * Size_Ac)];
    }



    private static final float ASIN_MAX_VALUE_FOR_TABS = (float) StrictMath.sin(StrictMath.toRadians(73.0));
    static final int ASIN_TABS_SIZE = 8193;
    static final float ASIN_DELTA = ASIN_MAX_VALUE_FOR_TABS/(ASIN_TABS_SIZE - 1);
    static final float ASIN_INDEXER = 1/ASIN_DELTA;

    static final float ONE_DIV_F2 = 1/2f;
    static final float ONE_DIV_F3 = 1/6f;
    static final float ONE_DIV_F4 = 1/24f;

    private static final class MyTAsin {
        static final float[] asinTab = new float[ASIN_TABS_SIZE];
        static final float[] asinDer1DivF1Tab = new float[ASIN_TABS_SIZE];
        static final float[] asinDer2DivF2Tab = new float[ASIN_TABS_SIZE];
        static final float[] asinDer3DivF3Tab = new float[ASIN_TABS_SIZE];
        static final float[] asinDer4DivF4Tab = new float[ASIN_TABS_SIZE];
        static {
            init();
        }

        private MyTAsin() { }

        private static strictfp void init() {
            for (int i=0;i<ASIN_TABS_SIZE;i++) {
                // x: in [0,ASIN_MAX_VALUE_FOR_TABS].
                float x = i * ASIN_DELTA;
                float oneMinusXSqInv = 1/(1-x*x);
                float oneMinusXSqInv0_5 = (float) StrictMath.sqrt(oneMinusXSqInv);
                float oneMinusXSqInv1_5 = oneMinusXSqInv0_5*oneMinusXSqInv;
                float oneMinusXSqInv2_5 = oneMinusXSqInv1_5*oneMinusXSqInv;
                float oneMinusXSqInv3_5 = oneMinusXSqInv2_5*oneMinusXSqInv;
                asinTab[i] = (float) StrictMath.asin(x);
                asinDer1DivF1Tab[i] = oneMinusXSqInv0_5;
                asinDer2DivF2Tab[i] = (x*oneMinusXSqInv1_5) * ONE_DIV_F2;
                asinDer3DivF3Tab[i] = ((1+2*x*x)*oneMinusXSqInv2_5) * ONE_DIV_F3;
                asinDer4DivF4Tab[i] = ((5+2*x*(2+x*(5-2*x)))*oneMinusXSqInv3_5) * ONE_DIV_F4;
            }
        }
    }

    static final float ASIN_MAX_VALUE_FOR_POWTABS = (float) StrictMath.sin(StrictMath.toRadians(88.6));
    static final int ASIN_POWTABS_POWER = 84;

    static final float ASIN_POWTABS_ONE_DIV_MAX_VALUE = 1/ASIN_MAX_VALUE_FOR_POWTABS;
    static final int ASIN_POWTABS_SIZE = /*(FM_USE_POWTABS_FOR_ASIN || SFM_USE_POWTABS_FOR_ASIN) ? (1<<12) + 1 : */0;
    static final int ASIN_POWTABS_SIZE_MINUS_ONE = ASIN_POWTABS_SIZE - 1;

    static final class MyTAsinPow {
        static final float[] asinParamPowTab = new float[ASIN_POWTABS_SIZE];
        static final float[] asinPowTab = new float[ASIN_POWTABS_SIZE];
        static final float[] asinDer1DivF1PowTab = new float[ASIN_POWTABS_SIZE];
        static final float[] asinDer2DivF2PowTab = new float[ASIN_POWTABS_SIZE];
        static final float[] asinDer3DivF3PowTab = new float[ASIN_POWTABS_SIZE];
        static final float[] asinDer4DivF4PowTab = new float[ASIN_POWTABS_SIZE];
        static {
            init();
        }
        private static strictfp void init() {
//            if (FM_USE_POWTABS_FOR_ASIN || SFM_USE_POWTABS_FOR_ASIN) {
//                for (int i=0;i<ASIN_POWTABS_SIZE;i++) {
//                    // x: in [0,ASIN_MAX_VALUE_FOR_POWTABS].
//                    float x = StrictMath.pow(i*(1.0/ASIN_POWTABS_SIZE_MINUS_ONE), 1.0/ASIN_POWTABS_POWER) * ASIN_MAX_VALUE_FOR_POWTABS;
//                    float oneMinusXSqInv = 1/(1-x*x);
//                    float oneMinusXSqInv0_5 = StrictMath.sqrt(oneMinusXSqInv);
//                    float oneMinusXSqInv1_5 = oneMinusXSqInv0_5*oneMinusXSqInv;
//                    float oneMinusXSqInv2_5 = oneMinusXSqInv1_5*oneMinusXSqInv;
//                    float oneMinusXSqInv3_5 = oneMinusXSqInv2_5*oneMinusXSqInv;
//                    asinParamPowTab[i] = x;
//                    asinPowTab[i] = StrictMath.asin(x);
//                    asinDer1DivF1PowTab[i] = oneMinusXSqInv0_5;
//                    asinDer2DivF2PowTab[i] = (x*oneMinusXSqInv1_5) * ONE_DIV_F2;
//                    asinDer3DivF3PowTab[i] = ((1+2*x*x)*oneMinusXSqInv2_5) * ONE_DIV_F3;
//                    asinDer4DivF4PowTab[i] = ((5+2*x*(2+x*(5-2*x)))*oneMinusXSqInv3_5) * ONE_DIV_F4;
//                }
//            }
        }
    }

    static final float ASIN_PIO2_HI = (float) Double.longBitsToDouble(0x3FF921FB54442D18L); // 1.57079632679489655800e+00
    static final float ASIN_PIO2_LO = (float) Double.longBitsToDouble(0x3C91A62633145C07L); // 6.12323399573676603587e-17
    static final float ASIN_PS0     = (float) Double.longBitsToDouble(0x3fc5555555555555L); //  1.66666666666666657415e-01
    static final float ASIN_PS1     = (float) Double.longBitsToDouble(0xbfd4d61203eb6f7dL); // -3.25565818622400915405e-01
    static final float ASIN_PS2     = (float) Double.longBitsToDouble(0x3fc9c1550e884455L); //  2.01212532134862925881e-01
    static final float ASIN_PS3     = (float) Double.longBitsToDouble(0xbfa48228b5688f3bL); // -4.00555345006794114027e-02
    static final float ASIN_PS4     = (float) Double.longBitsToDouble(0x3f49efe07501b288L); //  7.91534994289814532176e-04
    static final float ASIN_PS5     = (float) Double.longBitsToDouble(0x3f023de10dfdf709L); //  3.47933107596021167570e-05
    static final float ASIN_QS1     = (float) Double.longBitsToDouble(0xc0033a271c8a2d4bL); // -2.40339491173441421878e+00
    static final float ASIN_QS2     = (float) Double.longBitsToDouble(0x40002ae59c598ac8L); //  2.02094576023350569471e+00
    static final float ASIN_QS3     = (float) Double.longBitsToDouble(0xbfe6066c1b8d0159L); // -6.88283971605453293030e-01
    static final float ASIN_QS4     = (float) Double.longBitsToDouble(0x3fb3b8c5b12e9282L); //  7.70381505559019352791e-02

    /**
     * @param value In range [-1; 1]
     */
    @Contract(pure = true)
    public static float asin(float value) {
        boolean negateResult = false;
        if (value < 0.0) {
            value = -value;
            negateResult = true;
        }
        if (value <= ASIN_MAX_VALUE_FOR_TABS) {
            int index = (int)(value * ASIN_INDEXER + 0.5);
            float delta = value - index * ASIN_DELTA;
            float result = MyTAsin.asinTab[index]
                    + delta * (MyTAsin.asinDer1DivF1Tab[index]
                    + delta * (MyTAsin.asinDer2DivF2Tab[index]
                    + delta * (MyTAsin.asinDer3DivF3Tab[index]
                    + delta * MyTAsin.asinDer4DivF4Tab[index])));
            result *= TO_DEG;
            return negateResult ? -result : result;
        } /*else if (USE_POWTABS_FOR_ASIN && (value <= ASIN_MAX_VALUE_FOR_POWTABS)) {
            int index = (int)(powFast(value * ASIN_POWTABS_ONE_DIV_MAX_VALUE, ASIN_POWTABS_POWER) * ASIN_POWTABS_SIZE_MINUS_ONE + 0.5);
            float delta = value - MyTAsinPow.asinParamPowTab[index];
            float result = MyTAsinPow.asinPowTab[index]
                    + delta * (MyTAsinPow.asinDer1DivF1PowTab[index]
                    + delta * (MyTAsinPow.asinDer2DivF2PowTab[index]
                    + delta * (MyTAsinPow.asinDer3DivF3PowTab[index]
                    + delta * MyTAsinPow.asinDer4DivF4PowTab[index])));
            return negateResult ? -result : result;
        }*/ else { // value > ASIN_MAX_VALUE_FOR_TABS, or value is NaN
            // This part is derived from fdlibm.
            if (value < 1.0) {
                float t = (1f - value)*0.5f;
                float p = t*(ASIN_PS0+t*(ASIN_PS1+t*(ASIN_PS2+t*(ASIN_PS3+t*(ASIN_PS4+t*ASIN_PS5)))));
                float q = 1f+t*(ASIN_QS1+t*(ASIN_QS2+t*(ASIN_QS3+t*ASIN_QS4)));
                float s = (float) Math.sqrt(t);
                float z = s+s*(p/q);
                float result = (ASIN_PIO2_HI-((z+z)-ASIN_PIO2_LO)) * TO_DEG;
                return negateResult ? -result : result;
            } else { // value >= 1.0, or value is NaN
                if (value == 1.0) {
                    return negateResult ? -90 : 90;
                } else {
                    return Float.NaN;
                }
            }
        }
    }

    /**
     * @param value In range [-1; 1]
     */
    @Contract(pure = true)
    public static float acos(float value) {
        return 90 - asin(value);
    }

    @Contract(pure = true)
    public static double asin(double x) {
        return Math.asin(x);
    }

    @Contract(pure = true)
    public static double acos(double x) {
        return Math.acos(x);
    }

    @Contract(pure = true)
    public static float atan(float x) {
        return (float) Math.atan(x);
    }

    @Contract(pure = true)
    public static float cot(float x) {
        return (float) Math.atan(-x);
    }

    @Contract(pure = true)
    public static double atan(double x) {
        return Math.atan(x);
    }

    @Contract(pure = true)
    public static double cot(double x) {
        return Math.atan(-x);
    }

    @Contract(pure = true)
    public static double atan2(double y, double x) {
        return Math.atan2(y,x);
    }

    //#endregion

    //#region sinh/cosh/tanh

    @Contract(pure = true)
    public static float sinh(float x) {
        return (float) Math.sinh(x);
    }

    @Contract(pure = true)
    public static double sinh(double x) {
        return Math.sinh(x);
    }

    @Contract(pure = true)
    public static float cosh(float x) {
        return (float) Math.cosh(x);
    }

    @Contract(pure = true)
    public static double cosh(double x) {
        return Math.cosh(x);
    }

    @Contract(pure = true)
    public static float tanh(float x) {
        return (float) Math.tanh(x);
    }

    @Contract(pure = true)
    public static double tanh(double x) {
        return Math.tanh(x);
    }

    //#endregion
    //#endregion trigonometry

    //#region roots/power

    /**
     * Returns the real, positive square root of x, or {@link Float#NaN}.
     *
     * @param x The value to get the square root of
     * @return The square root of x
     */
    @Contract(pure = true)
    @Range(from = 0, to = Long.MAX_VALUE)
    public static float sqrt(float x) {
        return (float) Math.sqrt(x);
    }

    /**
     * Returns the real, positive square root of x, or {@link Double#NaN}.
     *
     * @param x The value to get the square root of
     * @return The square root of x
     */
    @Contract(pure = true)
    @Range(from = 0, to = Long.MAX_VALUE)
    public static double sqrt(double x) {
        return Math.sqrt(x);
    }

    /**
     * Returns the real, positive cube root of x, or {@link Float#NaN}.
     *
     * @param x The value to get the cube root of
     * @return The cube root of x
     */
    @Contract(pure = true)
    @Range(from = 0, to = Long.MAX_VALUE)
    public static float cbrt(float x) {
        return (float) Math.cbrt(x);
    }

    /**
     * Returns the real, positive cube root of x, or {@link Double#NaN}.
     *
     * @param x The value to get the cube root of
     * @return The cube root of x
     */
    @Contract(pure = true)
    @Range(from = 0, to = Long.MAX_VALUE)
    public static double cbrt(double x) {
        return Math.cbrt(x);
    }

    /**
     * Returns the real result of b raised to e (aka b^e), or {@link Float#NaN}.
     *
     * @param b The basis of the computation
     * @param e The exponent to raise the basis to
     * @return b^e
     */
    @Contract(pure = true)
    public static float pow(float b, float e) {
        return (float) Math.pow(b,e);
    }

    /**
     * Returns the real result of b raised to e (aka b^e), or {@link Double#NaN}.
     *
     * @param b The basis of the computation
     * @param e The exponent to raise the basis to
     * @return b^e
     */
    @Contract(pure = true)
    public static double pow(double b, double e) {
        return Math.pow(b,e);
    }

    //#endregion

    //#region exp/ln

    private static final double LN2 = Math.log(2);

    /**
     * Returns e^x (e raised to the power of x).
     *
     * @param x The value to raise e by
     * @return e^x
     */
    @Contract(pure = true)
    public static float exp(float x) {
        return (float) Math.exp(x);
    }

    /**
     * Returns e^x (e raised to the power of x).
     *
     * @param x The value to raise e by
     * @return e^x
     */
    @Contract(pure = true)
    public static double exp(double x) {
        return Math.exp(x);
    }

    /**
     * Returns the natural logarithm of x.
     *
     * @param x The value to get the natural logarithm for
     * @return The natural logarithm of x; ln(x)
     */
    @Contract(pure = true)
    public static float ln(float x) {
        return (float) Math.log(x);
    }

    /**
     * Returns the natural logarithm of x.
     *
     * @param x The value to get the natural logarithm for
     * @return The natural logarithm of x; ln(x)
     */
    @Contract(pure = true)
    public static double ln(double x) {
        return Math.log(x);
    }

    /**
     * Returns the logarithm of <code>x</code> for the basis <code>b</code>. Note
     * that this is simply computed as <code>ln(x) / ln(b)</code>, thus <code>ln(b)</code>
     * should be cached elsewhere if frequently used.
     *
     * @param b The basis for the logarithm
     * @param x The value to get the logarithm base b for
     * @return log_b(x)
     */
    @Contract(pure = true)
    public static float log(float b, float x) {
        if(x == 2) return log2(x);
        if(x == 10) return log10(x);
        return ln(x) / ln(b);
    }

    /**
     * Returns the logarithm of <code>x</code> for the basis <code>b</code>. Note
     * that this is simply computed as <code>ln(x) / ln(b)</code>, thus <code>ln(b)</code>
     * should be cached elsewhere if frequently used.
     *
     * @param b The basis for the logarithm
     * @param x The value to get the logarithm base b for
     * @return log_b(x)
     */
    @Contract(pure = true)
    public static double log(double b, double x) {
        if(x == 2) return log2(x);
        if(x == 10) return log10(x);
        return ln(x) / ln(b);
    }

    /**
     * Returns the logarithm of <code>x</code> for the basis 2.
     *
     * @param x The value to get the logarithm base 2 for
     * @return log_2(x)
     */
    @Contract(pure = true)
    public static float log2(float x) {
        return (float) log2((double) x);
    }

    /**
     * Returns the logarithm of <code>x</code> for the basis 2.
     *
     * @param x The value to get the logarithm base 2 for
     * @return log_2(x)
     */
    @Contract(pure = true)
    public static double log2(double x) {
        return ln(x) / LN2;
    }

    /**
     * Returns the logarithm of <code>x</code> for the basis 10.
     *
     * @param x The value to get the logarithm base 10 for
     * @return log_10(x)
     */
    @Contract(pure = true)
    public static float log10(float x) {
        return (float) Math.log10(x);
    }

    /**
     * Returns the logarithm of <code>x</code> for the basis 10.
     *
     * @param x The value to get the logarithm base 10 for
     * @return log_10(x)
     */
    @Contract(pure = true)
    public static double log10(double x) {
        return Math.log10(x);
    }

    //#endregion

    //#region random

    private static final Random R = new Random();


    /**
     * Returns a random float in the range [0,1).
     *
     * @return A random float
     */
    @Range(from = 0, to = 1)
    public static float rand() {
        return rand(0, 1);
    }

    /**
     * Returns a random float in the range [0, max).
     *
     * @param max The exclusive upper bounds
     * @return A random float
     */
    public static float rand(float max) {
        return rand(0, max);
    }

    /**
     * Returns a random float in the range [min, max).
     *
     * @param min The inclusive lower bound
     * @param max The exclusive upper bounds
     * @return A random float
     */
    public static float rand(float min, float max) {
        return min + R.nextFloat() * (max - min);
    }

    /**
     * Returns a random float in the range [0,1).
     *
     * @return A random float
     */
    @Range(from = 0, to = 1)
    public static double randD() {
        return randD(0, 1);
    }

    /**
     * Returns a random double in the range [0, max).
     *
     * @param max The exclusive upper bounds
     * @return A random double
     */
    public static double randD(double max) {
        return randD(0, max);
    }

    /**
     * Returns a random double in the range [min, max).
     *
     * @param min The inclusive lower bound
     * @param max The exclusive upper bounds
     * @return A random double
     */
    public static double randD(double min, double max) {
        return min + R.nextDouble() * (max - min);
    }

    /**
     * Returns a random int.
     *
     * @return A random int
     */
    public static int randI() {
        return R.nextInt();
    }

    /**
     * Returns a random non-negative int smaller that the specified limit.
     *
     * @param limit The exclusive upper bound
     * @return A non-negative random int smaller that the limit
     */
    public static int randI(int limit) {
        return R.nextInt(limit);
    }

    /**
     * Returns a random non-negative int from the range [min,max).
     *
     * @param min The inclusive lower bound
     * @param max The exclusive upper bounds
     * @return A non-negative random int smaller that the limit
     */
    public static int randI(int min, int max) {
        return min + randI(max - min);
    }

    /**
     * Returns a random long.
     *
     * @return A random long
     */
    public static long randL() {
        return R.nextLong();
    }

    //#endregion



    /**
     * Evaluates the sigmoid function at the specified point.
     *
     * @param x The point to evaluate at
     * @return The value of the sigmoid function at that point
     */
    @Contract(pure = true)
    @Range(from = 0, to = 1)
    public static float sigmoid(float x) {
        return 1 / (1 + exp(-x));
    }




    static float closestPoint(float low, float high, float min, float max) {
        float left = Math.abs(low - min) < Math.abs(high - min) ? low : high;
        float right = Math.abs(max - low) < Math.abs(max - high) ? low : high;
        return Math.abs(left - min) < Math.abs(max - right) ? left : right;
    }

    static int closestPoint(int low, int high, int min, int max) {
        int left = Math.abs(low - min) < Math.abs(high - min) ? low : high;
        int right = Math.abs(max - low) < Math.abs(max - high) ? low : high;
        return Math.abs(left - min) < Math.abs(max - right) ? left : right;
    }
}
