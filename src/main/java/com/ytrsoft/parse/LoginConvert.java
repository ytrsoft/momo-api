package com.ytrsoft.parse;

import com.ytrsoft.core.Global;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoginConvert implements MapConvert {
    @Override
    public Map<String, Object> convert(JSONObject input) {
        if (!input.has("data")) {
            return input.toMap();
        }
        JSONObject result = new JSONObject();
        JSONObject data = input.optJSONObject("data");
        String session = data.optString("session");
        Global.SESSION = session;
        result.put("session", session);
        return input.toMap();
    }
}
