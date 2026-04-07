package com.momo.sdk.http;

import com.momo.sdk.config.MomoConfig;
import com.momo.sdk.config.MomoConstants;
import com.momo.sdk.config.MomoSession;
import com.momo.sdk.http.annotation.Request;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

/**
 * 注解驱动的 API 接口动态代理处理器。
 *
 * <p>替代原工程中基于 Spring AOP {@code MethodInterceptor} + {@code ProxyFactoryBean} 的实现,
 * 这里改用纯 JDK 动态代理({@link java.lang.reflect.Proxy}),不依赖任何容器。
 *
 * <p>调度规则:
 *
 * <ol>
 *   <li>如果方法来自 {@link Object}(toString/hashCode/equals),走默认实现
 *   <li>如果方法没有 {@link Request} 注解,抛出 {@link UnsupportedOperationException}
 *   <li>否则把 {@code BASE_URL + Request.value()} 拼成 URL,把方法的 {@code JSONObject} 入参
 *       依次塞入 {@code params}、{@code body},再按 {@link Request#login()} 走 doLogin 或
 *       doRequest,最终把 {@link JSONObject} 返回
 * </ol>
 */
@RequiredArgsConstructor
public class ApiInvocationHandler implements InvocationHandler {

  private final MomoConfig config;
  private final MomoSession session;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // Object 类自带的方法走默认实现,避免代理对象 toString 时也去发请求
    if (method.getDeclaringClass() == Object.class) {
      return method.invoke(this, args);
    }

    Request request = method.getAnnotation(Request.class);
    if (request == null) {
      throw new UnsupportedOperationException(
          "接口方法缺少 @Request 注解: " + method.getDeclaringClass().getName() + "#" + method.getName());
    }

    String url = MomoConstants.BASE_URL + request.value();
    ApiAccess access = new ApiAccess(url, config, session);

    if (args != null && args.length > 0 && args[0] instanceof JSONObject) {
      access.params((JSONObject) args[0]);
    }
    if (args != null && args.length > 1 && args[1] instanceof JSONObject) {
      access.body((JSONObject) args[1]);
    }

    return request.login() ? access.doLogin() : access.doRequest();
  }
}
