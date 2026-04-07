package com.momo.sdk.config;

import com.momo.sdk.crypto.KeyExchange;
import com.momo.sdk.crypto.KeyExchangeResult;
import lombok.Getter;
import lombok.Setter;

/**
 * Momo SDK 运行期会话状态。
 *
 * <p>该对象由 {@code MomoClient} 在构造时创建并持有,用于保存:
 *
 * <ul>
 *   <li>ECDH 密钥协商的结果(key / ck / kv)
 *   <li>登录成功后服务端下发的 SESSIONID
 *   <li>登出之前可能拿到的 l_token
 * </ul>
 *
 * <p>它不属于用户配置,因此与 {@link MomoConfig} 分离;但同时它是可变的,SDK 内部需要
 * 在登录、登出后写回最新值,以便后续接口调用复用。
 */
@Getter
public class MomoSession {

  /** 与服务器协商出的密钥材料。 */
  private final KeyExchangeResult keys;

  /** 登录成功后由服务端下发的 SESSIONID,登出后可能为空。 */
  @Setter private String session;

  /** 登出令牌,登出接口返回后写入。 */
  @Setter private String token;

  /** 通过即时执行密钥协商创建一个全新的会话上下文。 */
  public MomoSession() {
    this.keys = KeyExchange.getInstance().execute();
  }

  /**
   * 获取 AES 主密钥(Base64 字符串)。
   *
   * @return AES 主密钥
   */
  public String getKey() {
    return keys.getKey();
  }

  /**
   * 获取握手字段 {@code ck}。
   *
   * @return ck 字段
   */
  public String getCk() {
    return keys.getCk();
  }

  /**
   * 获取握手字段 {@code kv}。
   *
   * @return kv 字段
   */
  public String getKv() {
    return keys.getKv();
  }
}
