package com.ytrsoft.http;

import org.json.JSONObject;

public interface TimelineApi {

    @Request("/v1/feed/user/timeline")
    JSONObject list(JSONObject params);


}
