package com.ytrsoft.service;

import com.ytrsoft.http.ProfileApi;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class ProfileService {

    private final ProfileApi profileApi;

    public ProfileService(ProfileApi profileApi) {
        this.profileApi = profileApi;
    }

    public JSONObject query(String id) {
        JSONObject params = new JSONObject();
        params.put("remoteid", id);
        return profileApi.list(params);
    }

}
