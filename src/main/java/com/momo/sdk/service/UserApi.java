package com.momo.sdk.service;

import com.momo.sdk.http.annotation.Api;
import com.momo.sdk.http.annotation.Request;
import org.json.JSONObject;

/**
 * 用户身份相关接口的注解定义。
 *
 * <p>本接口不需要手写实现,运行时由 {@code ApiProxyFactory} 通过 JDK 动态代理生成。每个方法
 * 都通过 {@link Request} 注解描述自己的接口路径与响应分支:
 *
 * <ul>
 *   <li>{@link #login(JSONObject, JSONObject)} —— {@code login = true},走加密分流逻辑
 *   <li>{@link #logout(JSONObject)} —— 普通业务接口
 * </ul>
 */
@Api
public interface UserApi {

  /**
   * 登录接口。
   *
   * @param params 加密 query 参数(account/password/etype/apksign/uid)
   * @param body 表单 body(ck/X-KV/map_id/code_version)
   * @return 解析后的 JSON 响应
   */
  @Request(value = "/api/v2/login", login = true)
  JSONObject login(JSONObject params, JSONObject body);

  /**
   * 登出接口。
   *
   * @param params 加密 query 参数(source)
   * @return 解析后的 JSON 响应
   */
  @Request("/api/setting/momologout")
  JSONObject logout(JSONObject params);
}
