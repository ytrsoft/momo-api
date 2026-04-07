package com.momo.sdk.config;

import com.momo.sdk.crypto.CryptoUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Momo SDK 用户侧配置(不可变)。
 *
 * <p>本类只承载真正"因人而异"的两个字段:登录账号和密码。设备 ID、UA、APK 签名、接口地址
 * 等所有协议级常量都已抽到 {@link MomoConstants},不再要求调用方传入。
 *
 * <p>使用示例:
 *
 * <pre>{@code
 * MomoConfig config = MomoConfig.builder()
 *     .username("13800000000")
 *     .password("plain-password")
 *     .build();
 * }</pre>
 */
@Getter
@Builder
@ToString(exclude = {"password"})
public class MomoConfig {

  /** 登录账号。 */
  private final String username;

  /** 登录明文密码,通过 {@link #getPassword()} 取出时会被自动 MD5。 */
  private final String password;

  /**
   * 返回 MD5 处理后的密码,覆盖 Lombok 默认 getter。
   *
   * @return MD5 摘要后的密码字符串
   */
  public String getPassword() {
    return CryptoUtils.md5(password);
  }
}
