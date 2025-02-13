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

    public void login(String account, String password) {
        Global.ACCOUNT = account;
        Global.PASSWORD = password;
        ApiAccess access = new ApiAccess(ApiAccess.LOGIN, props);
        access.params("account", account);
        access.params("password", password);
        access.params("etype", "2");
        access.params("apksign", props.getSign());
        access.params("uid", "a3931e93ff9cb0bc16e38cf3a14aa599");
        access.body("code_version", "2");
        access.body("map_id", Utilize.getMapId());
        access.body("ck", props.getCk());
        access.body("X-KV", props.getKv());
        JSONObject result = access.doLogin();
    }

}
