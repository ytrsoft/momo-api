package com.ytrsoft.core;

public class ApiSecurity {

    private final Props props;

    public ApiSecurity(Props props) {
        this.props = props;
    }

    public String sign(byte[] encoded) {
        byte[] key = props.getKey().getBytes();
        ByteStream bs = new ByteStream();
        bs.put(props.getUa().getBytes());
        bs.put(encoded);
        byte[] sign = Coded.sign(bs.get(), key);
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
