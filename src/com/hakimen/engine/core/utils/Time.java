package com.hakimen.engine.core.utils;

public class Time {

    public static final long NS_PER_SECOND = (long) 1e9;
    public static final long NS_PER_MILI_SECOND = (long) 1e6;

    public static final int TICK_SPEED = 60;
    public static final long NS_TICK = NS_PER_SECOND / TICK_SPEED;
    public static long now(){
        return System.nanoTime();
    }
}
