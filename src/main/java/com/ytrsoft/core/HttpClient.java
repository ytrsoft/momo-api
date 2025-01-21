package com.ytrsoft.core;

import okhttp3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private static final MediaType FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded");

    private static volatile HttpClient instance;
    private final OkHttpClient client;
    private FormBody formBody;
    private String url;
    private Map<String, String> headers;

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
        formBody = new FormBody.Builder()
                .add("mzip", zip)
                .build();
        return this;
    }

    public HttpClient url(String url) {
        this.url = url;
        return this;
    }

    public JSONObject build() {

        JSONObject jsonObject = new JSONObject();

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .post(formBody);

        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (body != null) {
                jsonObject = new JSONObject(body.string());
            }
        } catch (IOException e) {
            logger.error("网络请求失败: {}", e.getMessage(), e);
        }
        return jsonObject;
    }
}