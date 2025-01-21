package com.ytrsoft.service;

import com.ytrsoft.core.Base64;
import com.ytrsoft.core.Coded;
import com.ytrsoft.core.Props;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ToolService {

    private final Props props;

    public ToolService(Props props) {
        this.props = props;
    }

    public Map<String, Object> unzip(String zip) {
        byte[] key = props.getKey().getBytes();
        byte[] data = Base64.decode(zip.getBytes());
        byte[] decoded = Coded.decode(data, key);
        String body = new String(decoded);
        JSONObject jsonObject = new JSONObject(body);
        return jsonObject.toMap();
    }

    public String genCode(String zip) {
        Map<String, Object> map = unzip(zip);
        StringBuilder sb = new StringBuilder();
        sb.append("JSONObject params = new JSONObject();\n");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String valueStr = (value == null) ? "null" : value.toString();
            sb.append(String.format("params.put(\"%s\", \"%s\");\n", key, valueStr));
        }
        return sb.toString();
    }

}
