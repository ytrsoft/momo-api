package com.ytrsoft.convert;

import org.json.JSONObject;

public class CommitConvert extends AbsConvert {

    @Override
    protected JSONObject next(JSONObject input) {
        return input;
    }
}
