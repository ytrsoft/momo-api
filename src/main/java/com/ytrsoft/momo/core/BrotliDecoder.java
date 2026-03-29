package com.ytrsoft.momo.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.brotli.dec.BrotliInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Brotli decompression utility.
 */
public final class BrotliDecoder {

  private static final Logger LOG = LoggerFactory.getLogger(BrotliDecoder.class);
  private static final int BUFFER_SIZE = 4096;

  private BrotliDecoder() {}

  /**
   * Decompresses Brotli-compressed data into a UTF-8 string.
   *
   * @param data the compressed data
   * @return the decompressed string, or an empty string on failure
   */
  public static String decompress(byte[] data) {
    try (BrotliInputStream brotli = new BrotliInputStream(new ByteArrayInputStream(data))) {
      ByteArrayOutputStream out = new ByteArrayOutputStream(data.length * 2);
      byte[] buffer = new byte[BUFFER_SIZE];
      int bytesRead;
      while ((bytesRead = brotli.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
      }
      return out.toString("UTF-8");
    } catch (IOException e) {
      LOG.error("Brotli decompression failed", e);
      return "";
    }
  }
}
