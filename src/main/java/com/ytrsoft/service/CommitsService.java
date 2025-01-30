package com.ytrsoft.service;

import com.ytrsoft.core.ApiAccess;
import com.ytrsoft.core.Props;
import com.ytrsoft.entity.Commit;
import com.ytrsoft.parse.CommitsConvert;
import com.ytrsoft.parse.NewsConvert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommitsService {

    private final Props props;
    private final CommitsConvert cc;

    public CommitsService(Props props, CommitsConvert cc) {
        this.props = props;
        this.cc = cc;
    }

    public List<Object> query(String id) {
        ApiAccess access = new ApiAccess(ApiAccess.COMMENTS, props);
        access.params("feedid", id);
        access.params("sort_type", "early");
        access.params("index", "0");
        access.params("count", "50");
        JSONObject response = access.doRequest();
        return cc.convert(response);
    }

    public Map<String, Object> publish(Commit commit) {
        JSONObject content = new JSONObject();
        JSONArray arr = new JSONArray();
        ApiAccess access = new ApiAccess(ApiAccess.COMMENTS_PUBLISH, props);
        access.params("feedid", commit.getId());
        access.params("srcid", commit.getSrc());
        access.params("tomomoid", commit.getTo());
        content.put("text", commit.getContent());
        content.put("type", "1");
        arr.put(content);
        access.params("content", arr.toString());
        JSONObject response = access.doRequest();
        return response.toMap();
    }

    public Map<String, Object> remove(String id) {
        ApiAccess access = new ApiAccess(ApiAccess.COMMENTS_REMOVE, props);
        access.params("commentid", id);
        JSONObject response = access.doRequest();
        return response.toMap();
    }

}
