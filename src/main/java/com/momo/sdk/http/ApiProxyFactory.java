package com.momo.sdk.http;

import com.momo.sdk.config.MomoConfig;
import com.momo.sdk.config.MomoSession;
import java.lang.reflect.Proxy;
import lombok.experimental.UtilityClass;

/**
 * API 接口代理工厂。
 *
 * <p>给定一个使用 {@code @Request} 注解描述的接口,返回一个由 {@link ApiInvocationHandler}
 * 驱动的动态代理实例。等效于原工程的 Spring {@code ProxyFactoryBean},但完全基于 JDK 反射,
 * 不需要任何 IoC 容器。
 *
 * <p>用法示例:
 *
 * <pre>{@code
 * UserApi userApi = ApiProxyFactory.create(UserApi.class, config, session);
 * JSONObject loginResult = userApi.login(params, body);
 * }</pre>
 */
@UtilityClass
public class ApiProxyFactory {

  /**
   * 为给定接口创建代理实例。
   *
   * @param apiInterface 由 {@code @Request} 描述的接口 {@code Class}
   * @param config 用户配置
   * @param session 运行期会话
   * @param <T> 接口类型
   * @return 代理实例
   */
  @SuppressWarnings("unchecked")
  public <T> T create(Class<T> apiInterface, MomoConfig config, MomoSession session) {
    if (!apiInterface.isInterface()) {
      throw new IllegalArgumentException("ApiProxyFactory 只支持接口类型: " + apiInterface.getName());
    }
    ApiInvocationHandler handler = new ApiInvocationHandler(config, session);
    return (T)
        Proxy.newProxyInstance(
            apiInterface.getClassLoader(), new Class<?>[] {apiInterface}, handler);
  }
}
