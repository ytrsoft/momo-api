package com.ytrsoft.parse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public abstract class DefaultListConvert implements ListConvert  {

    protected abstract JSONObject processItem(JSONObject item);

    @Override
    public List<Object> convert(JSONObject input) {
        JSONArray result = new JSONArray();
        int code = input.getInt("errcode");
        if (code == 0) {
            JSONArray list = getList(input);
            if (list != null) {
                for (int i = 0; i < list.length(); i++) {
                    JSONObject object = list.optJSONObject(i);
                    JSONObject item = processItem(object);
                    if (item != null && !item.isEmpty()) {
                        result.put(item);
                    }
                }
            }
        }
        return result.toList();
    }

    protected abstract JSONArray getList(JSONObject input);

}
