package com.momo.sdk.crypto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ECDH 密钥协商的结果。
 *
 * <p>包含三个字段:
 *
 * <ul>
 *   <li>{@code key} —— 协商出的共享 AES 主密钥(Base64)
 *   <li>{@code ck} —— 客户端公钥经 Momo 协议封装后的串(Base64)
 *   <li>{@code kv} —— ck 的 MD5 取前 8 位
 * </ul>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KeyExchangeResult {

  /** 协商出的共享 AES 主密钥(Base64)。 */
  private String key;

  /** 客户端公钥串(Base64)。 */
  private String ck;

  /** ck 的 MD5 前 8 位。 */
  private String kv;
}
