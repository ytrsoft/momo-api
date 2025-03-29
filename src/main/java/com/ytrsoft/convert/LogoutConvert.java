package com.ytrsoft.convert;

import org.json.JSONObject;

public class LogoutConvert implements IConvert {

    @Override
    public JSONObject convert(JSONObject input) {
        JSONObject result = new JSONObject();
        JSONObject token = input.optJSONObject("l_token");
        String value = token.optString("value");
        result.put("token", value);
        return result;
    }
}
