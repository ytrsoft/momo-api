package com.ytrsoft.service;

import com.ytrsoft.http.FollowApi;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private final FollowApi followApi;

    public FollowService(FollowApi followApi) {
        this.followApi = followApi;
    }

    public JSONObject follow(String id) {
        return followApi.follow(id);
    }

    public JSONObject remove(String id) {
        JSONObject params = new JSONObject();
        params.put("remoteid", id);
        return followApi.remove(params);
    }

}
