package com.ytrsoft.service;

import com.ytrsoft.core.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ParseService {

    private final ApiSecurity security;

    public ParseService(Props props) {
        this.security = new ApiSecurity(props);
    }

    public Map<String, Object> unzip(String zip, Integer mode) {
        String body = security.decode(zip.getBytes());
        JSONObject jsonObject = new JSONObject(body);
        if (mode == -1) {
            jsonObject = JSON.deep(body);
        }
        return jsonObject.toMap();
    }

}
