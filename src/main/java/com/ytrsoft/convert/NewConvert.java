package com.ytrsoft.convert;

import org.json.JSONObject;

public class NewConvert extends ListConvert {

    @Override
    protected JSONObject item(JSONObject input) {
        return input;
    }
}
