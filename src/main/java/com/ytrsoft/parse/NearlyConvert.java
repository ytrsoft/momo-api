package com.ytrsoft.parse;

import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class NearlyConvert extends DefaultListConvert {

    @Override
    protected JSONObject processItem(JSONObject jsonObject) {
        if (jsonObject == null) return null;

        JSONObject item = new JSONObject();
        JSONObject source = jsonObject.optJSONObject("source");

        if (source == null) return null;

        addBasicInfo(source, item);
        addOptionalFields(source, item);
        addAvatar(source, item);

        return item;
    }

    private void addBasicInfo(JSONObject source, JSONObject item) {
        item.put("age", source.optInt("age"));
        item.put("id", source.getString("momoid"));
        item.put("constellation", source.getString("constellation"));
        item.put("location", source.optString("show_location"));
        item.put("name", source.optString("name"));
        item.put("client", source.optString("client"));
        item.put("status", source.optString("time_fmt_str"));
        item.put("sex", convertSex(source.optString("sex")));
    }

    private void addOptionalFields(JSONObject source, JSONObject item) {
        addFieldIfNotEmpty(source, item);
    }

    private void addFieldIfNotEmpty(JSONObject source, JSONObject item) {
        String value = source.optString("sign");
        if (Strings.isNotEmpty(value)) {
            item.put("sign", value);
        }
    }

    private void addAvatar(JSONObject source, JSONObject item) {
        JSONArray photos = source.optJSONArray("photos");
        if (photos != null && !photos.isEmpty()) {
            item.put("avatar", photos.optString(0));
        }
    }

    private String convertSex(String sex) {
        return "F".equals(sex) ? "女" : "男";
    }
}
