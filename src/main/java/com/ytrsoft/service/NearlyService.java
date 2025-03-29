package com.ytrsoft.service;

import com.ytrsoft.http.NearlyApi;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class NearlyService {

    private final NearlyApi nearlyApi;

    public NearlyService(NearlyApi nearlyApi) {
        this.nearlyApi = nearlyApi;
    }

    public JSONObject list(String exp) {
        String[] values = exp.split(",");
        String lat = values[1];
        String lng = values[0];
        JSONObject params = new JSONObject();
        params.put("online_time", "1");
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("age_min", "18");
        params.put("age_max", "100");
        params.put("sex", "F");
        return nearlyApi.list(params);
    }

}
