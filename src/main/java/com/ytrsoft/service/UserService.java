package com.ytrsoft.service;

import com.ytrsoft.core.*;
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
        ApiAccess access = new ApiAccess(ApiAccess.LOGIN, props);
        access.params("account", props.getAccount());
        access.params("etype", "2");
        access.params("bindSource", "bind_source_new_login")
        access.params("apksign", props.getSign());
        access.body("code_version", "2");
        access.body("map_id", Utilize.getMapId());
        access.body("ck", props.getCk());
        access.body("X-KV", props.getKv());
        JSONObject result = access.doLogin();
        return result.toMap();
    }

}
