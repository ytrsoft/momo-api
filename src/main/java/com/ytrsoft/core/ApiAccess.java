package com.ytrsoft.core;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApiAccess implements Api {

    private final String url;
    private String sign;
    private final Props props;
    private final ApiSecurity security;
    private final Map<String, String> headers;
    private final Map<String, String> body;
    private final JSONObject params;

    public ApiAccess(String url, Props props) {
        this.url = url + "?fr=" + Global.ACCOUNT;
        this.props = props;
        this.security = new ApiSecurity(props);
        this.headers = new HashMap<>();
        params = new JSONObject();
        body = new HashMap<>();
    }

    public ApiAccess params(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    public ApiAccess body(String key, String value) {
        this.body.put(key, value);
        return this;
    }

    private void setHeaders() {
        if (Global.SESSION != null) {
            headers.put("cookie", "SESSIONID=" + Global.SESSION);
        }
        headers.put("X-SIGN", sign);
        headers.put("X-Span-Id", "0");
        headers.put("X-ACT", "br");
        headers.put("X-LV", "1");
        headers.put("X-KV", props.getKv());
        headers.put("Accept-Language", "zh-CN");
        headers.put("X-Trace-Id", Utilize.uuid());
        headers.put("User-Agent", props.getUa());
    }

    private void initRequest() {
        byte[] data = params.toString().getBytes();
        byte[] encoded = security.encode(data);
        String zip = Base64.encode(encoded);
        body.put("mzip", zip);
        sign = security.sign(encoded);
        setHeaders();
    }

    private byte[] readBody() {
        initRequest();
        return HttpClient.getInstance()
                .url(url)
                .headers(headers)
                .body(body)
                .build();
    }

    public JSONObject doRequest() {
        byte[] key = props.getKey().getBytes();
        byte[] response = readBody();
        byte[] bytes = Coded.decode(response, key);
        String body = Brotli.decompress(bytes);
        return JSON.deep(body);
    }

    public JSONObject doLogin() {
        byte[] bytes;
        byte[] key = props.getKey().getBytes();
        byte[] response = readBody();
        if (!(response[0] == 2 && response[1] == 3)) {
            return null;
        }
        bytes = Coded.decode(response, key);
        String body = GZIP.decompress(bytes);
        return JSON.deep(body);
    }

}
