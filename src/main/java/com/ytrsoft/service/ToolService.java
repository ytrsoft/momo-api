package com.ytrsoft.service;

import com.ytrsoft.core.Base64;
import com.ytrsoft.core.Coded;
import com.ytrsoft.core.Props;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        System.out.println(Arrays.toString(decoded));
        String body = new String(decoded);
        JSONObject jsonObject = new JSONObject(body);
        return jsonObject.toMap();
    }

}
