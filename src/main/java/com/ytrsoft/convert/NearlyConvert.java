package com.ytrsoft.convert;

import com.ytrsoft.domain.Nearly;

import com.ytrsoft.util.JSONUtil;
import org.json.JSONObject;

public class NearlyConvert extends ListConvert<Nearly> {

    @Override
    protected Nearly item(JSONObject input) {
        JSONObject source = input.optJSONObject("source");
        return JSONUtil.parseObject(Nearly.class, source);
    }

}
