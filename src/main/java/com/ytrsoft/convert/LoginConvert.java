package com.ytrsoft.convert;

import org.json.JSONObject;

public class LoginConvert implements Convert<String> {

    @Override
    public String convert(JSONObject input) {
        JSONObject data = input.optJSONObject("data");
        return data.optString("session");
    }

}
