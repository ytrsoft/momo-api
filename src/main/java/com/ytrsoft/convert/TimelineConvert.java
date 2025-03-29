package com.ytrsoft.convert;

import org.json.JSONObject;

public class TimelineConvert extends AbsConvert<JSONObject> {

    @Override
    protected JSONObject next(JSONObject input) {
        return input;
    }

}
