package com.ytrsoft.convert;

import org.json.JSONObject;

public class LogoutConvert implements Convert<String> {

    @Override
    public String convert(JSONObject input) {
        JSONObject token = input.optJSONObject("l_token");
        return token.optString("value");
    }
}
