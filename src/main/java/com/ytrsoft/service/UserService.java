package com.ytrsoft.service;

import com.ytrsoft.core.ApiAccess;
import com.ytrsoft.core.Props;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final Props props;

    public UserService(Props props) {
        this.props = props;
    }

    public Map<String, Object> login() {
        JSONObject body = new JSONObject();
        body.put("map_id", "6926786671");
        body.put("code_version", "2");
       // body.put("ck", props.getCk());
        body.put("X-KV", props.getKv());
        ApiAccess access = new ApiAccess(ApiAccess.LOGIN, props);
        JSONObject response = access.doRequest();
        return response.toMap();
    }

}
