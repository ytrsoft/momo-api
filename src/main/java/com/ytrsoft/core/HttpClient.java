package com.ytrsoft.core;

import okhttp3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public final class HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    private static volatile HttpClient instance;
    private final OkHttpClient client;
    private RequestBody requestBody;
    private String url;
    private Map<String, String> headers;

    private HttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
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

    public HttpClient body(String body) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        requestBody = RequestBody.create(body, mediaType);
        return this;
    }

    public HttpClient url(String url) {
        this.url = url;
        return this;
    }

    public JSONObject build() {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL不能为空");
        }

        if (requestBody == null) {
            requestBody = RequestBody.create("", MediaType.parse("application/x-www-form-urlencoded"));
        }

        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);

        if (headers != null && !headers.isEmpty()) {
            requestBuilder.headers(Headers.of(headers));
        }

        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return new JSONObject(response.body().string());
            } else {
                return new JSONObject();
            }
        } catch (IOException e) {
            logger.error("网络请求发生异常: {}", e.getMessage(), e);
            return new JSONObject();
        }
    }
}
