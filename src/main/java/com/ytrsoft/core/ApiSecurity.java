package com.ytrsoft.core;

import cn.hutool.core.util.ArrayUtil;

public class ApiSecurity {

    private final Props props;

    public ApiSecurity(Props props) {
        this.props = props;
    }

    public String sign(byte[] encoded) {
        byte[] ua = props.getUa().getBytes();
        byte[] key = props.getKey().getBytes();
        byte[] data = ArrayUtil.addAll(ua, encoded);
        byte[] sign = Coded.sign(data, key);
        return Base64.encode(sign);
    }

    public byte[] encode(byte[] data) {
        byte[] key = props.getKey().getBytes();
        return Coded.encode(data, key);
    }

    public String decode(byte[] data) {
        byte[] key = props.getKey().getBytes();
        byte[] decoded = Coded.decode(data, key);
        return new String(decoded);
    }

}
