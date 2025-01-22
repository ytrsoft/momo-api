package com.ytrsoft.core;

import cn.hutool.core.lang.UUID;
import org.apache.commons.lang3.StringUtils;

public class Image {

    private Image() {
        throw new UnsupportedOperationException();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    private static String concat(String str) {
        if (StringUtils.isEmpty(str) || str.length() < 4) {
            return "";
        }
        return "/" + str.substring(0, 2) + "/" + str.substring(2, 4) + "/";
    }


    public static String parse(String str) {
        if (StringUtils.isEmpty(str) || str.length() <= 3) {
            return "";
        }
        return "https://img.momocdn.com/album" + concat(str) + str + "_L.jpg";
    }

}
