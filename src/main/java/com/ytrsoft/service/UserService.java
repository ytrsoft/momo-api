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
        ApiAccess access = new ApiAccess(ApiAccess.PROFILE, props);
        JSONObject response = access.doRequest();
        return response.toMap();
    }

}
