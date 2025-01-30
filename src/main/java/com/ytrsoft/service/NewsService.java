package com.ytrsoft.service;

import com.ytrsoft.core.ApiAccess;

import com.ytrsoft.core.Props;
import com.ytrsoft.parse.NewsConvert;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    private final Props props;
    private final NewsConvert nc;

    public NewsService(Props props, NewsConvert nc) {
        this.props = props;
        this.nc = nc;
    }

    public List<Object> query() {
        ApiAccess access = new ApiAccess(ApiAccess.NEWS, props);
        access.params("count", "20");
        access.params("lat", "28.19645");
        access.params("lng", "112.977301");
        JSONObject response = access.doRequest();
        return nc.convert(response);
    }

}
