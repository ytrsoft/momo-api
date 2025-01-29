package com.ytrsoft.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.UUID;

public class Utilize {

    private Utilize() {
        throw new UnsupportedOperationException();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static String readCheckOS() {
        ClassPathResource resource = new ClassPathResource(".ck_os");
        return FileUtil.readUtf8String(resource.getFile());
    }

    public static String getMapId() {
        long currentTimeMillis = System.currentTimeMillis() % 1000000;
        if (currentTimeMillis < 100000) {
            currentTimeMillis += 100000;
        }
        return currentTimeMillis + "" + (((int) (Math.random() * 9000.0d)) + 1000);
    }
}
