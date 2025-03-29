package com.ytrsoft.service;

import com.ytrsoft.http.TimelineApi;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TimelineService {

    private final TimelineApi timelineApi;

    public TimelineService(TimelineApi timelineApi) {
        this.timelineApi = timelineApi;
    }

    public JSONObject list(String id) {
        JSONObject params = new JSONObject();
        params.put("remoteid", id);
        return timelineApi.list(params);
    }

}
