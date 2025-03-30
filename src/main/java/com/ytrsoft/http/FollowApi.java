package com.ytrsoft.http;

import org.json.JSONObject;

@Api
public interface FollowApi {

    @Request("/api/follow")
    JSONObject follow(String id);

    @Request("/v1/user/relation/remfans")
    JSONObject remove(JSONObject params);

}
