package com.ytrsoft.service;

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
        String account = props.getAccount();
        JSONObject result = new JSONObject();
        System.out.println("login - " + account);
        return result.toMap();
    }

}
