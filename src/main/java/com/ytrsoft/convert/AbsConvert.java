package com.ytrsoft.convert;

import org.json.JSONObject;

public abstract class AbsConvert<T> implements Convert<T> {

    @Override
    public T convert(JSONObject input) {
        int code = input.optInt("errcode");
        if (code != 0) return null;
        return next(input.optJSONObject("data"));
    }

    protected abstract T next(JSONObject input);

}
