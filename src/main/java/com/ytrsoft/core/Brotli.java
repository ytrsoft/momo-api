package com.ytrsoft.core;

import org.brotli.dec.BrotliInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public final class Brotli {

    private static final Logger logger = LoggerFactory.getLogger(Brotli.class);

    public static String decompress(byte[] data) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BrotliInputStream bs = new BrotliInputStream(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int length;
            byte[] buffer = new byte[1024];
            while ((length = bs.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            return bos.toString();
        } catch (Exception e) {
            logger.error("数据解压失败: {}", e.getMessage(), e);
        }
        return "";
    }

}
