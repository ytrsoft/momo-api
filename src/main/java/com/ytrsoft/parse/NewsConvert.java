package com.ytrsoft.parse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class NewsConvert extends FeedListConvert {

    @Override
    protected JSONArray getList(JSONObject input) {
        return input.optJSONObject("data").optJSONArray("lists");
    }
}
