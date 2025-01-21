package com.ytrsoft.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteStream {

    private final ByteArrayOutputStream bas;

    private static final Logger logger = LoggerFactory.getLogger(ByteStream.class);

    public ByteStream() {
        bas = new ByteArrayOutputStream();
    }

    public void put(byte[] bytes) {
        try {
            bas.write(bytes);
        } catch (IOException e) {
            logger.error("写入错误: {}", e.getMessage());
        }
    }

    public byte[] get() {
        return bas.toByteArray();
    }


}
