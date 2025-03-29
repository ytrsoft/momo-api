package com.ytrsoft.convert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class ListConvert<T> extends AbsConvert<List<T>> {

    @Override
    protected List<T> next(JSONObject input) {
        List<T> result = new ArrayList<>();
        JSONArray lists = input.optJSONArray(listKey());
        for (int i = 0; i < lists.length() ; i++) {
            result.add(item(lists.getJSONObject(i)));
        }
        return result;
    }

    protected String listKey() {
        return "lists";
    }

    protected abstract T item(JSONObject input);

}
