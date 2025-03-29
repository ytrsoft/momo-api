package com.ytrsoft.service;

import com.ytrsoft.http.NewApi;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class NewService {

    private final NewApi newApi;

    public NewService(NewApi newApi) {
        this.newApi = newApi;
    }

    public JSONObject list() {
        JSONObject params = new JSONObject();
        params.put("count", "20");
        params.put("lat", "28.19645");
        params.put("lng", "112.977301");
        return newApi.list(params);
    }

}
