package com.ytrsoft.service;

import com.ytrsoft.core.Props;
import com.ytrsoft.core.Utilize;
import com.ytrsoft.http.UserApi;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final Props props;
    private final UserApi userApi;

    public UserService(Props props, UserApi userApi) {
        this.props = props;
        this.userApi = userApi;
    }

    public JSONObject login() {
        JSONObject params = new JSONObject();
        params.put("account", props.getUsr());
        params.put("password", props.getPwd());
        params.put("etype", "2");
        params.put("apksign", props.getSign());
        params.put("uid", props.getId());
        JSONObject body = new JSONObject();
        body.put("ck", props.getCk());
        body.put("X-KV", props.getKv());
        body.put("map_id", Utilize.getMapId());
        body.put("code_version", "2");
        return userApi.login(params, body);
    }

    public JSONObject logout() {
        JSONObject params = new JSONObject();
        params.put("source", "1");
        return userApi.logout(params);
    }
}
