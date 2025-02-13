package com.ytrsoft.core;

import cn.hutool.core.lang.UUID;

public class Utilize {

    private Utilize() {
        throw new UnsupportedOperationException();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static String getMapId() {
        long currentTimeMillis = System.currentTimeMillis() % 1000000;
        if (currentTimeMillis < 100000) {
            currentTimeMillis += 100000;
        }
        return currentTimeMillis + "" + (((int) (Math.random() * 9000.0d)) + 1000);
    }
}
