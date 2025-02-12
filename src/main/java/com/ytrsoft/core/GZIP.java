package com.ytrsoft.core;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public final class GZIP {

    private static final Logger logger = LoggerFactory.getLogger(GZIP.class);

    public static String decompress(byte[] data) {
        StringBuilder sb = new StringBuilder();

        try (
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                GZIPInputStream zip = new GZIPInputStream(bis);
                InputStreamReader inputStreamReader = new InputStreamReader(zip, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(inputStreamReader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String decompress = sb.toString().trim();
            return StringEscapeUtils.unescapeJava(decompress);
        } catch (Exception e) {
            logger.error("数据解压失败: {}", e.getMessage(), e);
        }
        return "";
    }
}
