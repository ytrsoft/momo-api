# momo-sdk

Momo SDK — 核心加密通信与登录/登出功能封装。

纯 Java 库，无 Spring 依赖，兼容 JDK 1.8+，可用于 Java 后端和 Android 端。

## 构建

```bash
mvn clean package
```

产出两个 jar：

| 文件 | 说明 |
|------|------|
| `target/momo-sdk-2.0.0.jar` | 仅 SDK 代码，需自行引入依赖 |
| `target/momo-sdk-2.0.0-all.jar` | Fat JAR，包含所有依赖 |

## 依赖（使用非 fat jar 时需自行引入）

- OkHttp 4.12.0
- BouncyCastle bcprov-jdk15on 1.70
- Brotli dec 0.1.2
- org.json 20240303
- SLF4J 1.7.36
- JSR-305 annotations (provided scope)

## 快速开始

```java
import com.ytrsoft.momo.MomoClient;
import com.ytrsoft.momo.MomoConfig;
import com.ytrsoft.momo.model.LoginResult;

// 1. 构建配置（默认常量已内置，只需设置账号密码）
MomoConfig config = new MomoConfig.Builder()
    .account("your_account")
    .password("your_password")
    .build();

// 2. 创建客户端（自动完成密钥交换）
MomoClient client = new MomoClient(config);

// 3. 登录
LoginResult result = client.login();
System.out.println("Session: " + result.getSession());

// 4. 调用自定义 API（高级用法）
ApiAccess access = client.createApiAccess("/v2/nearby/people/lists");
access.param("lat", "39.9");
access.param("lng", "116.3");
JSONObject response = access.doRequest();

// 5. 登出
String token = client.logout();
```

## 自定义配置

```java
MomoConfig config = new MomoConfig.Builder()
    .id("custom_device_id")
    .apkSign("custom_apk_sign")
    .userAgent("custom_user_agent")
    .account("your_account")
    .password("your_password")
    .build();
```

## 扩展能力

### 自定义 HTTP 传输

实现 `HttpClient` 接口即可替换默认的 OkHttp 传输层（例如用于单元测试或 Android 特定网络栈）：

```java
MomoConfig config = new MomoConfig.Builder()
    .account("your_account")
    .password("your_password")
    .httpClient(new MyCustomHttpClient())
    .build();
```

### 请求拦截器

通过 `RequestInterceptor` 在每个请求发出前注入自定义逻辑（日志、指标、额外 Header 等）：

```java
MomoConfig config = new MomoConfig.Builder()
    .account("your_account")
    .password("your_password")
    .addInterceptor((url, headers, params) -> {
        headers.put("X-Custom-Header", "value");
        System.out.println(">> " + url);
    })
    .build();
```

## Android 使用

在 `build.gradle` 中添加：

```groovy
dependencies {
    implementation files('libs/momo-sdk-2.0.0-all.jar')
}
```

注意：Android 项目需排除 BouncyCastle Provider 冲突，
或使用 `bcprov-jdk15to18` 替代。

## 项目结构

```
com.ytrsoft.momo
├── MomoClient.java              // SDK 主入口
├── MomoConfig.java               // 配置类（Builder 模式）
├── MomoException.java            // 异常类（含 API 错误码）
├── core/
│   ├── HttpClient.java           // HTTP 传输接口（扩展点）
│   ├── OkHttpClientImpl.java     // 默认 OkHttp 实现
│   ├── RequestInterceptor.java   // 请求拦截器接口（扩展点）
│   ├── ApiAccess.java            // API 请求构建与执行
│   ├── BrotliDecoder.java        // Brotli 解压
│   ├── Crypto.java               // AES/MD5/SHA1/Base64 加密核心
│   ├── JsonParser.java           // 深度 JSON 解析
│   └── KeyExchange.java          // ECDH 密钥交换
└── model/
    └── LoginResult.java          // 登录结果
```

## v2.0.0 变更（相对 v1.0.0）

- **删除** `Base64Util`（冗余包装，合并入 `Crypto`）
- **删除** `ApiSecurity`（仅转发调用，合并入 `ApiAccess`）
- **删除** `HttpExecutor` 单例，改为 `HttpClient` 接口 + `OkHttpClientImpl` 默认实现
- **新增** `HttpClient` 接口 — 可替换 HTTP 传输层
- **新增** `RequestInterceptor` 接口 — 请求拦截器链
- **新增** `MomoException.errorCode` — 携带服务端错误码
- **改进** `KeyExchange.ExchangeResult` 改为不可变类
- **改进** `KeyExchange` 改为静态工厂方法，移除无状态单例
- **改进** 全面使用 `StandardCharsets.UTF_8`
- **改进** 全面使用 try-with-resources
- **改进** 移除残留 `System.out.println` 调试代码
- **改进** 遵循 Google Java Style（`@Nullable` 注解、Holder 单例、2空格缩进）
