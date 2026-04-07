package com.momo.sdk.crypto;

import java.util.Base64;
import lombok.experimental.UtilityClass;

/**
 * Base64 编解码工具类。
 *
 * <p>对 JDK 自带 {@link java.util.Base64} 的轻量封装,统一通过本类调用以便后续替换实现。
 */
@UtilityClass
public class Base64Utils {

  /**
   * 将字节数组编码为 Base64 字符串。
   *
   * @param bytes 待编码字节数组
   * @return 编码后的字符串
   */
  public String encode(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  /**
   * 解码 Base64 字节数组。
   *
   * @param bytes 待解码字节数组
   * @return 解码后的原始字节
   */
  public byte[] decode(byte[] bytes) {
    return Base64.getDecoder().decode(bytes);
  }
}
