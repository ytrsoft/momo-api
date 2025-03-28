package com.ytrsoft.parse;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoginConvert implements MapConvert {
    @Override
    public Map<String, Object> convert(JSONObject input) {
        if (input == null) {
            return new JSONObject().toMap();
        }
        JSONObject result = new JSONObject();
        JSONObject data = input.optJSONObject("data");
        String session = data.optString("session");
        result.put("session", session);
        return result.toMap();
    }
}
