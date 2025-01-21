package com.ytrsoft.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

public class ApiSecurity {

    private static final Logger logger = LoggerFactory.getLogger(ApiSecurity.class);

    private final Props props;

    public ApiSecurity(Props props) {
        this.props = props;
    }

    public String sign(byte[] encoded) {
        byte[] key = props.getKey().getBytes();
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            bos.write(props.getUa().getBytes());
            bos.write(encoded);
            byte[] bytes = Coded.sign(bos.toByteArray(), key);
            return Base64.encode(bytes);
        } catch (Exception e) {
            logger.error("签名时发生错误: ", e);
        }
        return "";
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
