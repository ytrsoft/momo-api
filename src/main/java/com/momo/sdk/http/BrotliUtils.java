package com.momo.sdk.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;
import org.brotli.dec.BrotliInputStream;

/**
 * Brotli 解压工具类。
 *
 * <p>用于解压 Momo 服务器返回的 Brotli 压缩响应体。
 */
@Log
@UtilityClass
public class BrotliUtils {

  /** 解压缓冲区大小。 */
  private final int BUFFER_SIZE = 1024;

  /**
   * 将 Brotli 压缩字节解压为字符串。
   *
   * @param data 压缩字节流
   * @return 解压后的字符串;失败返回空串
   */
  public String decompress(byte[] data) {
    try (ByteArrayInputStream in = new ByteArrayInputStream(data);
        BrotliInputStream brotli = new BrotliInputStream(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      byte[] buffer = new byte[BUFFER_SIZE];
      int length;
      while ((length = brotli.read(buffer)) != -1) {
        out.write(buffer, 0, length);
      }
      return out.toString();
    } catch (Exception e) {
      log.log(Level.SEVERE, "Brotli 解压失败", e);
    }
    return "";
  }
}
