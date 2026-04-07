package com.momo.sdk.http;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import lombok.extern.java.Log;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 基于 OkHttp 的 HTTP 客户端封装(单例)。
 *
 * <p>对外提供链式调用 API:{@code HttpClient.getInstance().url(..).headers(..).body(..).execute()},
 * 用于 Momo 协议中的 POST 表单请求。返回原始字节流,后续解密、解压由调用方负责。
 *
 * <p>本类是<strong>线程不安全</strong>的链式 builder 调用形态(继承自原工程实现)。多线程
 * 场景下应每次都从 {@link #getInstance()} 拿到实例后,在同一调用链上完成请求。
 */
@Log
public final class HttpClient {

  /** 连接超时时间(秒)。 */
  private static final int CONNECT_TIMEOUT_SECONDS = 10;

  /** 写超时时间(秒)。 */
  private static final int WRITE_TIMEOUT_SECONDS = 10;

  /** 读超时时间(秒)。 */
  private static final int READ_TIMEOUT_SECONDS = 30;

  private static volatile HttpClient instance;

  private final OkHttpClient client;

  private String url;
  private FormBody body;
  private Map<String, String> headers;

  private HttpClient() {
    this.client =
        new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build();
  }

  /**
   * 获取单例。
   *
   * @return 单例实例
   */
  public static HttpClient getInstance() {
    if (instance == null) {
      synchronized (HttpClient.class) {
        if (instance == null) {
          instance = new HttpClient();
        }
      }
    }
    return instance;
  }

  /**
   * 设置请求 URL。
   *
   * @param url 完整请求 URL
   * @return this
   */
  public HttpClient url(String url) {
    this.url = url;
    return this;
  }

  /**
   * 设置请求头。
   *
   * @param headers 请求头映射
   * @return this
   */
  public HttpClient headers(Map<String, String> headers) {
    this.headers = headers;
    return this;
  }

  /**
   * 设置表单 body。
   *
   * @param args 表单字段映射
   * @return this
   */
  public HttpClient body(Map<String, String> args) {
    FormBody.Builder builder = new FormBody.Builder();
    args.forEach(builder::add);
    this.body = builder.build();
    return this;
  }

  /**
   * 执行请求并返回响应字节流。
   *
   * @return 响应体字节;失败时返回长度为 0 的数组
   */
  public byte[] execute() {
    Request request = new Request.Builder().url(url).headers(Headers.of(headers)).post(body).build();
    try (Response response = client.newCall(request).execute()) {
      ResponseBody responseBody = response.body();
      if (responseBody != null) {
        return responseBody.bytes();
      }
    } catch (IOException e) {
      log.log(Level.SEVERE, "网络请求失败", e);
    }
    return new byte[0];
  }
}
