package com.ytrsoft.http;

import com.ytrsoft.convert.CommitConvert;
import org.json.JSONObject;

public interface CommentApi {

    @Request("/api/feed/v2/comment/publish")
    JSONObject publish(JSONObject params);

    @Request("/api/feed/v2/comment/remove")
    JSONObject remove(JSONObject params);

    @Request("/v2/feed/comment/comments")
    @Response(CommitConvert.class)
    JSONObject list(JSONObject params);

}
