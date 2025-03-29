package com.ytrsoft.http;

import com.ytrsoft.convert.CommentConvert;
import com.ytrsoft.domain.Comment;
import org.json.JSONObject;

import java.util.List;

public interface CommentApi {

    @Request("/api/feed/v2/comment/publish")
    JSONObject publish(JSONObject params);

    @Request("/api/feed/v2/comment/remove")
    JSONObject remove(JSONObject params);

    @Request("/v2/feed/comment/comments")
    @Response(CommentConvert.class)
    List<Comment> list(JSONObject params);

}
