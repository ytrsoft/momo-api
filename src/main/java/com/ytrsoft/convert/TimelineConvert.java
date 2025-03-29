package com.ytrsoft.convert;

import com.ytrsoft.domain.News;
import com.ytrsoft.domain.Timeline;
import com.ytrsoft.util.JSONUtil;
import org.json.JSONObject;

public class TimelineConvert extends ListConvert<Timeline> {

    @Override
    protected String listKey() {
        return "feeds";
    }

    @Override
    protected Timeline item(JSONObject input) {
        JSONObject source = input.optJSONObject("source");
        return JSONUtil.parseObject(Timeline.class, source);
    }

}
