package com.ytrsoft.parse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class NearlyConvert extends DefaultListConvert {

    @Override
    protected JSONObject processItem(JSONObject object) {
        JSONObject item = new JSONObject();
        if (object != null) {
            JSONObject source = object.optJSONObject("source");
            if (source != null) {
                setInfo(source, item);
            }
        }
        return item;
    }

    @Override
    protected JSONArray getList(JSONObject input) {
        return input.optJSONObject("data").optJSONArray("lists");
    }

    private void setInfo(JSONObject source, JSONObject item) {
        JsonSet.putSex(source, item);
        JsonSet.putInt(source, item, "age");
        JsonSet.putString(source, item, "momoid", "id");
        JsonSet.putString(source, item, "constellation");
        JsonSet.putString(source, item, "show_location", "location");
        JsonSet.putString(source, item, "name");
        JsonSet.putString(source, item, "client");
        JsonSet.putString(source, item, "time_fmt_str", "status");
        JsonSet.putString(source, item, "sign");
        JsonSet.putArray(source, item, "photos");
    }

}
