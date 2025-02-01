package com.ytrsoft.parse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public final class JsonSet {

    private JsonSet() {
        throw new UnsupportedOperationException();
    }

    public static void putSex(JSONObject source, JSONObject target) {
        if (source != null) {
            String value = source.optString("sex");
            if (StringUtils.isNotEmpty(value)) {
                target.put("sex", "F".equals(value) ? "女" : "男");
            }
        }
    }

    public static void putString(JSONObject source, JSONObject target, String key, String sk) {
        if (source != null) {
            String value = source.optString(key);
            if (StringUtils.isNotEmpty(value)) {
                target.put(sk, value);
            }
        }
    }

    public static void putString(JSONObject source, JSONObject target, String key) {
        putString(source, target, key, key);
    }

    public static void putInt(JSONObject source, JSONObject target, String key, String sk) {
        if (source != null) {
            int value = source.optInt(key);
            target.put(sk, value);
        }
    }

    public static void putInt(JSONObject source, JSONObject target, String key) {
        putString(source, target, key, key);
    }

    public static void putArray(JSONObject source, JSONObject target, String key, String sk) {
        if (source != null) {
            JSONArray value = source.optJSONArray(key);
            if (value != null && value.isEmpty()) {
                target.put(sk, value);
            }
        }
    }

    public static void putArray(JSONObject source, JSONObject target, String key) {
        putArray(source, target, key, key);
    }

    public static void putString(JSONObject source, JSONObject target, String pk, String key, String sk) {
        if (source.has(pk)) {
            JSONObject current = source.optJSONObject(pk);
            if (current != null) {
                putString(current, target, key, sk);
            }
        }
    }

    public static void putInt(JSONObject source, JSONObject target, String pk, String key, String sk) {
        if (source.has(pk)) {
            JSONObject current = source.optJSONObject(pk);
            if (current != null) {
                putInt(current, target, key, sk);
            }
        }
    }

}
