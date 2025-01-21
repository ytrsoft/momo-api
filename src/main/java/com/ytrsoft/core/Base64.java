package com.ytrsoft.core;

public final class Base64 {

    private Base64() {
        throw new UnsupportedOperationException();
    }

    public static String encode(byte[] bytes) {
        return java.util.Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] decode(byte[] bytes) {
        return java.util.Base64.getDecoder().decode(bytes);
    }

}
