package com.ytrsoft.core;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApiAccess implements Api {

    private final String url;
    private String zip;
    private String sign;
    private final Props props;
    private final ApiSecurity security;
    private final Map<String, String> headers;
    private final JSONObject params;

    public ApiAccess(String url, Props props) {
        this.url = url + "?fr=" + props.getAccount();
        this.props = props;
        this.security = new ApiSecurity(props);
        this.headers = new HashMap<>();
        params = new JSONObject();
    }

    public ApiAccess put(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    private void setHeaders() {
        headers.put("cookie", props.getCookie());
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
        zip = Base64.encode(encoded);
        sign = security.sign(encoded);
        setHeaders();
    }

    public JSONObject doRequest() {
        initRequest();
        byte[] response = HttpClient.getInstance()
                .url(url)
                .headers(headers)
                .body(zip)
                .build();
        //byte[] decoded = Coded.decode(response, props.getKey().getBytes());
        String ck = "AgOxElKWAB9J85QSqr8QYp9sf8UMmyA7aH5iHhAMonyiRQrxfP2dX8h1IrU5NtH1w+X6tXjSGbzsVgpzY1+V5Kl/J1l64nM=";
        byte[] decode = Base64.decode(ck.getBytes());
        System.out.println(Arrays.toString(decode));
        byte[] decoded = Coded.decode(decode, props.getKey().getBytes());
        System.out.println(new String(decoded));
        System.out.println(Arrays.toString(response));
        String body = Brotli.decompress(decoded);
        System.out.println(body);
//        return JSON.deep(body);
        return new JSONObject();
    }

}
