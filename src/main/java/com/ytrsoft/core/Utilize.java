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

    public static class CheckOS {




        public CheckOS() {

        }


    }
}
