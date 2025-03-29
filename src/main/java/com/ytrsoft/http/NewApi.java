package com.ytrsoft.http;

import com.ytrsoft.convert.NewConvert;
import org.json.JSONObject;

public interface NewApi {

    @Request("/v2/feed/nearbyv2/lists")
    @Response(NewConvert.class)
    JSONObject list(JSONObject params);

}
