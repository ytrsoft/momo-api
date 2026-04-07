package com.momo.sdk.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口方法元数据:声明一个 Momo HTTP 接口。
 *
 * <p>用法示例:
 *
 * <pre>{@code
 * @Api
 * public interface UserApi {
 *
 *   @Request(value = "/api/v2/login", login = true)
 *   JSONObject login(JSONObject params, JSONObject body);
 *
 *   @Request("/api/setting/momologout")
 *   JSONObject logout(JSONObject params);
 * }
 * }</pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Request {

  /**
   * 接口路径,自动拼接到 {@code MomoConstants.BASE_URL} 之后。
   *
   * @return 接口相对路径,如 {@code /api/v2/login}
   */
  String value();

  /**
   * 是否走登录响应解析分支。
   *
   * <p>登录接口的响应可能是明文 JSON(失败)或加密+Brotli 压缩(成功),需要按头两个字节
   * 是否等于 {@code 0x02 0x03} 来分流;普通接口的响应一律是加密压缩的。
   *
   * @return {@code true} 表示走 {@code doLogin()} 分支
   */
  boolean login() default false;
}
