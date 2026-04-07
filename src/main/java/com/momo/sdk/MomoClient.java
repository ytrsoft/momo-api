package com.momo.sdk;

import com.momo.sdk.config.MomoConfig;
import com.momo.sdk.config.MomoSession;
import com.momo.sdk.service.UserService;
import lombok.Getter;

/**
 * Momo SDK 主入口。
 *
 * <p>典型用法:
 *
 * <pre>{@code
 * MomoConfig config = MomoConfig.builder()
 *     .username("13800000000")
 *     .password("plain-password")
 *     .build();
 *
 * MomoClient client = new MomoClient(config);
 * String sessionId = client.login();
 * // ... 其他业务调用 ...
 * String token = client.logout();
 * }</pre>
 *
 * <p>{@link MomoClient} 在构造时会立刻执行一次 ECDH 密钥协商,因此请尽量复用同一个实例,
 * 避免频繁创建。
 */
public class MomoClient {

  /** 用户配置(不可变)。 */
  @Getter private final MomoConfig config;

  /** 运行期会话(可变,密钥协商结果 + session/token)。 */
  @Getter private final MomoSession session;

  /** 用户身份相关服务。 */
  private final UserService userService;

  /**
   * 使用给定配置创建客户端,内部立刻完成一次密钥协商。
   *
   * @param config 用户配置
   */
  public MomoClient(MomoConfig config) {
    this.config = config;
    this.session = new MomoSession();
    this.userService = new UserService(config, session);
  }

  /**
   * 登录。
   *
   * @return 服务端下发的 SESSIONID;失败返回 {@code null}
   * @see UserService#login()
   */
  public String login() {
    return userService.login();
  }

  /**
   * 登出,必须在 {@link #login()} 成功之后调用。
   *
   * @return 服务端下发的 l_token;失败返回 {@code null}
   * @see UserService#logout()
   */
  public String logout() {
    return userService.logout();
  }
}
