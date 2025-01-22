package com.ytrsoft.core;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class HttpClient {

    private String url;
    private FormBody body;
    private Map<String, String> headers;
    private final OkHttpClient client;

    private static volatile HttpClient instance;

    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);


    private HttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        client = builder.build();
    }

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

    public HttpClient headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public HttpClient body(String zip) {
        body = new FormBody.Builder()
                .add("mzip", zip)
                .build();
        return this;
    }

    public HttpClient url(String url) {
        this.url = url;
        return this;
    }

    private Request buildRequest() {
        Request.Builder rb = new Request.Builder();
        rb.url(url);
        rb.headers(Headers.of(headers));
        rb.post(body);
        return rb.build();
    }


    public byte[] build() {
        Request request = buildRequest();
        try {
            Response response = client.newCall(request).execute();
          ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return responseBody.bytes();
            }
        } catch (IOException e) {
            logger.error("网络请求失败: {}", e.getMessage(), e);
        }
        return new byte[0];
    }
}