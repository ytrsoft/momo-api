package com.ytrsoft.service;

import com.ytrsoft.core.ApiAccess;
import com.ytrsoft.core.Props;
import com.ytrsoft.parse.NewsConvert;
import com.ytrsoft.parse.TimelineConvert;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimelineService {

    private final Props props;
    private final TimelineConvert ts;

    public TimelineService(Props props, TimelineConvert ts) {
        this.props = props;
        this.ts = ts;
    }

    public List<Object> query(String id) {
        ApiAccess access = new ApiAccess(ApiAccess.TIMELINE, props);
        access.params("remoteid", id);
        JSONObject response = access.doRequest();
        return ts.convert(response);
    }

}
