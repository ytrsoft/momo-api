package com.ytrsoft.convert;

import org.json.JSONObject;

public class LoginConvert implements IConvert {

    @Override
    public JSONObject convert(JSONObject input) {
        JSONObject result = new JSONObject();
        JSONObject data = input.optJSONObject("data");
        String session = data.optString("session");
        result.put("session", session);
        return result;
    }
}
