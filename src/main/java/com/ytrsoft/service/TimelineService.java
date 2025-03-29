package com.ytrsoft.service;

import com.ytrsoft.domain.Timeline;
import com.ytrsoft.http.TimelineApi;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimelineService {

    private final TimelineApi timelineApi;

    public TimelineService(TimelineApi timelineApi) {
        this.timelineApi = timelineApi;
    }

    public List<Timeline> list(String id) {
        JSONObject params = new JSONObject();
        params.put("remoteid", id);
        return timelineApi.list(params);
    }

}
