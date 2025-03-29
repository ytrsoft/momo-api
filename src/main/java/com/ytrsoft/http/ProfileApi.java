package com.ytrsoft.http;

import com.ytrsoft.convert.NearlyConvert;
import org.json.JSONObject;

public interface ProfileApi {

    @Request("/v2/nearby/people/lists")
    @Response(NearlyConvert.class)
    JSONObject list(JSONObject params);

}
