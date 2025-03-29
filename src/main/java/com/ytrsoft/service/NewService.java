package com.ytrsoft.service;

import com.ytrsoft.domain.News;
import com.ytrsoft.http.NewApi;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewService {

    private final NewApi newApi;

    public NewService(NewApi newApi) {
        this.newApi = newApi;
    }

    public List<News> list(String exp) {
        String[] values = exp.split(",");
        String lat = values[1];
        String lng = values[0];
        JSONObject params = new JSONObject();
        params.put("count", "20");
        params.put("lat", lat);
        params.put("lng", lng);
        return newApi.list(params);
    }

}
