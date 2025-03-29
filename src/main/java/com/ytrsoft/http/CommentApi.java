package com.ytrsoft.http;

import org.json.JSONObject;

public interface CommentApi {

    @Request("/api/feed/v2/comment/publish")
    JSONObject publish(JSONObject params);

    @Request("/api/feed/v2/comment/remove")
    JSONObject remove(JSONObject params);

    @Request("/v2/feed/comment/comments")
    JSONObject list(JSONObject params);

}
