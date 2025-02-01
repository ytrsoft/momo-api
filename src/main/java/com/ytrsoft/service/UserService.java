package com.ytrsoft.service;

import com.ytrsoft.core.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final Props props;

    private static final String AES_KEY = "Iu0WKHFy";

    public UserService(Props props) {
        this.props = props;
    }

    public Map<String, Object> login() {
        ApiAccess access = new ApiAccess(ApiAccess.LOGIN, props);
        byte[] decoded = Base64.decode(props.getCk().getBytes());
        byte[] bytes = Coded.encode(decoded, AES_KEY.getBytes());
        String ck = new String(bytes);
        access.params("account", props.getAccount());
        access.params("etype", "2");
        access.params("password", "");
        access.body("code_version", "2");
        access.body("map_id", Utilize.getMapId());
        access.body("ck", ck);
        String kv = Coded.md5(ck).substring(0, 8);
        access.body("X-KV", kv);
        JSONObject result = access.doRequest();
        return result.toMap();
    }

}
