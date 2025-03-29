package com.ytrsoft.convert;

import org.json.JSONObject;

public interface Convert<T> {

    T convert(JSONObject input);

}
