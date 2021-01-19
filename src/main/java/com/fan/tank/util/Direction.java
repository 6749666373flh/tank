package com.fan.tank.util;

import java.util.Random;

public enum Direction {
    L, R, U, D;

    private static Random r = new Random();
    public static Direction randomDir() {
        return values()[r.nextInt(values().length)];
    }
}
