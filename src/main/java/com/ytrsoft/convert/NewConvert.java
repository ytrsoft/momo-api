package com.ytrsoft.convert;

import com.ytrsoft.domain.News;
import com.ytrsoft.util.JSONUtil;
import org.json.JSONObject;

public class NewConvert extends ListConvert<News> {

    @Override
    protected News item(JSONObject input) {
        JSONObject source = input.optJSONObject("source");
        return JSONUtil.parseObject(News.class, source);
    }
}
