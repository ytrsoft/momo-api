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

    private void setDefaultParams() {
        params.put("_ab_test_", props.getTest());
        params.put("_net_", props.getNet());
        params.put("_iid", props.getIid());
        params.put("_uid_", props.getUid());
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
    }

    private void initRequest() {
        setDefaultParams();
        byte[] data = params.toString().getBytes();
        byte[] encoded = security.encode(data);
        zip = Base64.encode(encoded);
        System.out.println(zip);
        sign = security.sign(encoded);
        setHeaders();
    }

    public JSONObject doRequest() {
        initRequest();
        return HttpClient.getInstance()
                .url(url)
                .headers(headers)
                .body(zip)
                .build();
    }

}
