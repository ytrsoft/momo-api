package com.ytrsoft.service;

import com.ytrsoft.entity.Commit;
import com.ytrsoft.http.CommentApi;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CommitService {

    private final CommentApi commentApi;

    public CommitService(CommentApi commentApi) {
        this.commentApi = commentApi;
    }

    public JSONObject list(String id) {
        JSONObject params = new JSONObject();
        params.put("feedid", id);
        params.put("sort_type", "early");
        params.put("index", "0");
        params.put("count", "50");
        return commentApi.list(params);
    }

    public JSONObject publish(Commit commit) {
        JSONObject params = new JSONObject();
        JSONArray array = new JSONArray();
        params.put("feedid", commit.getId());
        params.put("srcid", commit.getSrc());
        params.put("tomomoid", commit.getTo());
        JSONObject content = new JSONObject();
        content.put("text", commit.getContent());
        content.put("type", "1");
        array.put(content);
        params.put("content", array.toString());
        return commentApi.publish(params);
    }

    public JSONObject remove(String id) {
        JSONObject params = new JSONObject();
        params.put("commentid", id);
        return commentApi.remove(params);
    }
}