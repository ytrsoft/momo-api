package com.ytrsoft.core;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ApiAccess implements Api {

    private final String url;
    private String zip;
    private String sign;
    private final Props props;
    private final ApiSecurity security;
    private final Map<String, String> headers;

    private ApiAccess(String url, Props props) {
        this.url = url + "?fr=" + props.getAccount();
        this.props = props;
        this.security = new ApiSecurity(props);
        this.headers = new HashMap<>();
    }

    private void loadHeader() {
        headers.put("cookie", props.getCookie());
        headers.put("X-SIGN", sign);
        headers.put("X-Span-Id", "0");
        headers.put("X-ACT", "br");
        headers.put("X-LV", "1");
        headers.put("X-KV", props.getKv());
        headers.put("Accept-Language", "zh-CN");
        headers.put("X-Trace-Id", Utilize.uuid());
    }

    public ApiAccess withParams(JSONObject params) {
        byte[] data = params.toString().getBytes();
        byte[] decoded = security.encode(data);
        zip = Base64.encode(decoded);
        sign = security.sign(decoded);
        loadHeader();
        return this;
    }


    public JSONObject doRequest() {
        HttpClient client = HttpClient.getInstance()
                .url(url).headers(headers).body(zip);
        return client.build();
    }



}
