package com.ytrsoft.http;

import org.json.JSONObject;

public interface NewApi {

    @Request("/v2/feed/nearbyv2/lists")
    JSONObject list(JSONObject params);

}
