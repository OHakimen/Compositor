package com.hakimen.engine.core.utils;

public class Mathf {
    public static float normalize(double x, double y) {
        return (float)Math.sqrt(x * x + y * y);
    }

    public static float clamp(float x, float min, float max) {
        return Math.min(Math.max(x, min), max);
    }
    public static float lerp(float a, float b, float f)
    {
        return a + (b - a) * f;
    }

    public static float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }

    public static float sigmoidCurve(float x,float bias){
        return (float)(1/(1 + Math.pow(Math.E,-bias*x)));
    }
}
