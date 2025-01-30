package com.ytrsoft.parse;

import org.json.JSONObject;

public interface Convert<T> {
    T convert(JSONObject input);
}
