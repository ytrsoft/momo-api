package com.momo.sdk.http;

import com.momo.sdk.config.MomoConstants;
import com.momo.sdk.config.MomoSession;
import com.momo.sdk.crypto.Base64Utils;
import com.momo.sdk.crypto.CryptoUtils;
import lombok.RequiredArgsConstructor;

/**
 * 协议层加签与加解密助手。
 *
 * <p>所有方法都基于 {@link MomoConstants#USER_AGENT 固定 UA} 与
 * {@link MomoSession#getKey() 主密钥} 来工作:
 *
 * <ul>
 *   <li>{@link #sign(byte[])} —— UA + 密文一起 SHA-1 签名,再 Base64
 *   <li>{@link #encode(byte[])} —— 用主密钥 AES 加密
 *   <li>{@link #decode(byte[])} —— 用主密钥 AES 解密
 * </ul>
 */
@RequiredArgsConstructor
public class ApiSecurity {

  private final MomoSession session;

  /**
   * 对加密后的数据生成签名。
   *
   * @param encoded 已经过 AES 加密的字节流
   * @return Base64 编码的签名
   */
  public String sign(byte[] encoded) {
    byte[] ua = MomoConstants.USER_AGENT.getBytes();
    byte[] key = session.getKey().getBytes();
    byte[] data = CryptoUtils.concat(ua, encoded);
    byte[] signature = CryptoUtils.sign(data, key);
    return Base64Utils.encode(signature);
  }

  /**
   * 使用当前会话的主密钥加密数据。
   *
   * @param data 明文
   * @return 密文
   */
  public byte[] encode(byte[] data) {
    return CryptoUtils.encode(data, session.getKey().getBytes());
  }

  /**
   * 使用当前会话的主密钥解密数据。
   *
   * @param data 密文
   * @return 明文字符串
   */
  public String decode(byte[] data) {
    byte[] decoded = CryptoUtils.decode(data, session.getKey().getBytes());
    return new String(decoded);
  }
}
