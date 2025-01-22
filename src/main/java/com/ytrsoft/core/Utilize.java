package com.ytrsoft.core;

import cn.hutool.core.lang.UUID;

public class Utilize {

    private Utilize() {
        throw new UnsupportedOperationException();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().toUpperCase();
    }

}
