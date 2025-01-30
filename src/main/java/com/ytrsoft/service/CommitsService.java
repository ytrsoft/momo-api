package com.ytrsoft.service;

import com.ytrsoft.core.ApiAccess;
import com.ytrsoft.core.Props;
import com.ytrsoft.parse.CommitsConvert;
import com.ytrsoft.parse.NewsConvert;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
