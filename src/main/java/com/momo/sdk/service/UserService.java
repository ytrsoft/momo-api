package com.momo.sdk.service;

import com.momo.sdk.config.MomoConfig;
import com.momo.sdk.config.MomoConstants;
import com.momo.sdk.config.MomoSession;
import com.momo.sdk.http.ApiProxyFactory;
import com.momo.sdk.http.IdUtils;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.json.JSONObject;

/**
 * 用户身份相关的高层服务:登录、登出。
 *
 * <p>原工程通过 Spring AOP 代理 + 注解扫描完成 HTTP 路由,这里改为通过
 * {@link ApiProxyFactory} 创建一个由 JDK 动态代理生成的 {@link UserApi} 实例,既保留了
 * "接口 + 注解"的声明式风格,又彻底摆脱了 Spring 容器。
 *
 * <p>本类只做两件事:
 *
 * <ol>
 *   <li>把入参装配成符合协议的 {@link JSONObject}
 *   <li>把 {@link UserApi} 返回的 {@link JSONObject} 提取成业务字段(SESSIONID / l_token)
 *       并写回 {@link MomoSession}
 * </ol>
 */
@Log
public class UserService {

  private final MomoConfig config;
  private final MomoSession session;
  private final UserApi userApi;

  /**
   * 构造服务,内部立刻为 {@link UserApi} 生成一个代理实例。
   *
   * @param config 用户配置
   * @param session 运行期会话
   */
  public UserService(MomoConfig config, MomoSession session) {
    this.config = config;
    this.session = session;
    this.userApi = ApiProxyFactory.create(UserApi.class, config, session);
  }

  /**
   * 执行登录。
   *
   * <p>成功后会自动把服务端下发的 SESSIONID 写回 {@link MomoSession},便于后续接口直接使用。
   *
   * @return 服务端返回的 SESSIONID;失败返回 {@code null}
   */
  public String login() {
    JSONObject params = new JSONObject();
    params.put("account", config.getUsername());
    params.put("password", config.getPassword());
    params.put("etype", MomoConstants.ETYPE);
    params.put("apksign", MomoConstants.APK_SIGN);
    params.put("uid", MomoConstants.DEVICE_ID);

    JSONObject body = new JSONObject();
    body.put("ck", session.getCk());
    body.put("X-KV", session.getKv());
    body.put("map_id", IdUtils.mapId());
    body.put("code_version", MomoConstants.CODE_VERSION);

    JSONObject response = userApi.login(params, body);

    String sessionId = extractSession(response);
    if (sessionId != null) {
      session.setSession(sessionId);
      log.info("登录成功,SESSIONID 已写入会话上下文");
    } else {
      log.log(Level.WARNING, "登录失败,响应内容: {0}", response);
    }
    return sessionId;
  }

  /**
   * 执行登出,必须在 {@link #login()} 成功之后调用。
   *
   * @return 服务端下发的 l_token;失败返回 {@code null}
   */
  public String logout() {
    JSONObject params = new JSONObject();
    params.put("source", MomoConstants.LOGOUT_SOURCE);

    JSONObject response = userApi.logout(params);

    String token = extractToken(response);
    if (token != null) {
      session.setToken(token);
      session.setSession(null);
      log.info("登出成功,SESSIONID 已清除");
    } else {
      log.log(Level.WARNING, "登出失败,响应内容: {0}", response);
    }
    return token;
  }

  /** 从登录响应中提取 SESSIONID。 */
  private String extractSession(JSONObject response) {
    if (response == null) {
      return null;
    }
    JSONObject data = response.optJSONObject("data");
    if (data == null) {
      return null;
    }
    String value = data.optString("session", null);
    return (value == null || value.isEmpty()) ? null : value;
  }

  /** 从登出响应中提取 l_token。 */
  private String extractToken(JSONObject response) {
    if (response == null) {
      return null;
    }
    JSONObject data = response.optJSONObject("data");
    JSONObject lToken =
        (data != null) ? data.optJSONObject("l_token") : response.optJSONObject("l_token");
    if (lToken == null) {
      return null;
    }
    String value = lToken.optString("value", null);
    return (value == null || value.isEmpty()) ? null : value;
  }
}
