package com.ytrsoft.convert;

import org.json.JSONObject;

public abstract class AbsConvert implements IConvert {

    @Override
    public JSONObject convert(JSONObject input) {
        int code = input.optInt("errcode");
        if (code != 0) {
            return new JSONObject();
        }
        JSONObject data = input.optJSONObject("data");
        return next(data);
    }

    protected abstract JSONObject next(JSONObject input);
}
