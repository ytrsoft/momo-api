package com.ytrsoft.convert;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class ListConvert extends AbsConvert {

    @Override
    protected JSONObject next(JSONObject input) {
        JSONObject result = new JSONObject();
        JSONArray lists = input.optJSONArray("lists");
        JSONArray saved = new JSONArray();
        for (int i = 0; i < lists.length() ; i++) {
            JSONObject it = item(lists.optJSONObject(i));
            saved.put(it);
        }
        result.put("list", saved);
        return result;
    }


    protected abstract JSONObject item(JSONObject input);

}
