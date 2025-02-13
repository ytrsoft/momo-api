package com.ytrsoft.service;

import com.ytrsoft.core.*;
import com.ytrsoft.parse.LoginConvert;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final Props props;
    private final LoginConvert lc;

    public UserService(Props props, LoginConvert lc) {
        this.props = props;
        this.lc = lc;
    }

    public Map<String, Object> login(String account, String password) {
        Global.ACCOUNT = account;
        Global.PASSWORD = password;
        ApiAccess access = new ApiAccess(ApiAccess.LOGIN, props);
        access.params("account", account);
        access.params("password", Coded.md5(password));
        access.params("etype", "2");
        access.params("apksign", props.getSign());
        access.params("uid", props.getId());
        access.body("code_version", "2");
        access.body("map_id", Utilize.getMapId());
        access.body("ck", props.getCk());
        access.body("X-KV", props.getKv());
        JSONObject result;
        while (true) {
            result = access.doLogin();
            if (result != null) {
                break;
            } else {
                props.exchange();
            }
        }
        return lc.convert(result);
    }

}
