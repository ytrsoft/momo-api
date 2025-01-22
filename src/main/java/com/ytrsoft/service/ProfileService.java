package com.ytrsoft.service;

import com.ytrsoft.core.ApiAccess;
import com.ytrsoft.core.Props;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProfileService {

    private final Props props;

    public ProfileService(Props props) {
        this.props = props;
    }

    public Map<String, Object> query(String id) {
        ApiAccess access = new ApiAccess(ApiAccess.PROFILE, props);
        access.put("remoteid", id);
        JSONObject response = access.doRequest();
        return response.toMap();
    }

}
