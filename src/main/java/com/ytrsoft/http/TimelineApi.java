package com.ytrsoft.http;

import com.ytrsoft.convert.TimelineConvert;
import org.json.JSONObject;

public interface TimelineApi {

    @Request("/v1/feed/user/timeline")
    @Response(TimelineConvert.class)
    JSONObject list(JSONObject params);

}
