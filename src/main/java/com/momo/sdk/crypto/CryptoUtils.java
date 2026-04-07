package com.momo.sdk.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.logging.Level;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

/**
 * 通用加解密、签名、摘要工具类。
 *
 * <p>所有方法均为静态调用,内部封装 Momo 协议所使用的:
 *
 * <ul>
 *   <li>MD5 摘要
 *   <li>SHA-1 数据签名
 *   <li>AES/CBC/PKCS5Padding 对称加解密
 * </ul>
 *
 * <p>加密结果格式为:{@code HEAD(2B) || IV(4B) || NOP(1B) || CIPHER(...)},解密时按相同
 * 偏移还原。
 */
@Log
@UtilityClass
public class CryptoUtils {

  /** 加密结果固定头部。 */
  private final byte[] HEAD = new byte[] {2, 3};

  /** 头部与 IV 之间的占位字节。 */
  private final byte[] NOP = new byte[] {0};

  /** 随机 IV 长度(字节)。 */
  private final int IV_LENGTH = 4;

  /** AES key 长度(字节)。 */
  private final int AES_KEY_LENGTH = 16;

  /** AES 算法/模式/填充。 */
  private final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";

  /** SHA-1 算法名。 */
  private final String SHA1_ALGORITHM = "SHA-1";

  /**
   * 计算字符串的 MD5 摘要(小写十六进制)。
   *
   * @param str 待摘要字符串
   * @return 32 位小写十六进制字符串;若算法不可用则原样返回入参
   */
  public String md5(String str) {
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      byte[] hash = digest.digest(str.getBytes());
      StringBuilder sb = new StringBuilder();
      for (byte b : hash) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      log.log(Level.SEVERE, "MD5 计算失败: {0}", e.getMessage());
      return str;
    }
  }

  /**
   * 使用 SHA-1 对数据 + key 前 8 字节生成签名。
   *
   * @param data 待签名数据
   * @param key key 字节数组(至少 8 字节)
   * @return 20 字节签名结果
   */
  public byte[] sign(byte[] data, byte[] key) {
    try {
      MessageDigest sha1 = MessageDigest.getInstance(SHA1_ALGORITHM);
      sha1.update(data);
      sha1.update(key, 0, 8);
      return sha1.digest();
    } catch (NoSuchAlgorithmException e) {
      log.log(Level.SEVERE, "签名计算失败: {0}", e.getMessage());
      return new byte[20];
    }
  }

  /**
   * AES 加密。
   *
   * <p>结果包含固定头、IV 衍生信息及密文,可供 {@link #decode(byte[], byte[])} 还原。
   *
   * @param data 明文
   * @param key 主密钥(取前 16 字节)
   * @return 加密结果字节流
   */
  public byte[] encode(byte[] data, byte[] key) {
    try {
      byte[] iv = randomIv();
      Cipher cipher = buildCipher(Cipher.ENCRYPT_MODE, iv, key);
      byte[] cipherText = cipher.doFinal(data);
      return concat(HEAD, iv, NOP, cipherText);
    } catch (Exception e) {
      log.log(Level.SEVERE, "加密失败: {0}", e.getMessage());
      return new byte[data.length + 23];
    }
  }

  /**
   * AES 解密。
   *
   * @param data 由 {@link #encode(byte[], byte[])} 生成的密文
   * @param key 主密钥(取前 16 字节)
   * @return 解密后的明文字节
   */
  public byte[] decode(byte[] data, byte[] key) {
    try {
      byte[] iv = Arrays.copyOfRange(data, 2, 6);
      Cipher cipher = buildCipher(Cipher.DECRYPT_MODE, iv, key);
      byte[] cipherText = Arrays.copyOfRange(data, 7, data.length);
      return cipher.doFinal(cipherText);
    } catch (Exception e) {
      log.log(Level.SEVERE, "解密失败: {0}", e.getMessage());
      return new byte[data.length - 7];
    }
  }

  /** 生成 4 字节随机 IV。 */
  private byte[] randomIv() {
    byte[] iv = new byte[IV_LENGTH];
    new SecureRandom().nextBytes(iv);
    return iv;
  }

  /**
   * 构造 AES Cipher 实例。
   *
   * <p>IV 经过 SHA-1 扩展并截取前 16 字节,key 同样截取前 16 字节。
   */
  private Cipher buildCipher(int mode, byte[] iv, byte[] key) throws Exception {
    MessageDigest sha1 = MessageDigest.getInstance(SHA1_ALGORITHM);
    Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
    byte[] expandedIv = sha1.digest(iv);
    expandedIv = Arrays.copyOfRange(expandedIv, 0, AES_KEY_LENGTH);
    IvParameterSpec ivSpec = new IvParameterSpec(expandedIv);
    byte[] truncatedKey = Arrays.copyOfRange(key, 0, AES_KEY_LENGTH);
    SecretKeySpec keySpec = new SecretKeySpec(truncatedKey, "AES");
    cipher.init(mode, keySpec, ivSpec);
    return cipher;
  }

  /**
   * 拼接任意数量字节数组。
   *
   * <p>替代原工程中 Hutool 的 {@code ArrayUtil.addAll},避免引入额外依赖。
   *
   * @param arrays 待拼接的字节数组
   * @return 拼接后的字节数组
   */
  public byte[] concat(byte[]... arrays) {
    int total = 0;
    for (byte[] arr : arrays) {
      total += arr.length;
    }
    byte[] result = new byte[total];
    int offset = 0;
    for (byte[] arr : arrays) {
      System.arraycopy(arr, 0, result, offset, arr.length);
      offset += arr.length;
    }
    return result;
  }
}
