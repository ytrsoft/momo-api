package com.ytrsoft.core;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSON {

    private JSON() {
        throw new UnsupportedOperationException();
    }

    private static JSONArray parseJsonArray(JSONArray array) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < array.length(); i++) {
            result.put(parseValue(array.get(i)));
        }
        return result;
    }

    private static Object tryParseJsonString(String str) {
        try {
            return parseJson(new JSONObject(str));
        } catch (Exception e1) {
            try {
                return parseJsonArray(new JSONArray(str));
            } catch (Exception e2) {
                return str;
            }
        }
    }

    private static Object parseValue(Object value) {
        if (value instanceof JSONObject) {
            return parseJson((JSONObject) value);
        } else if (value instanceof JSONArray) {
            return parseJsonArray((JSONArray) value);
        } else if (value instanceof String) {
            return tryParseJsonString((String) value);
        } else {
            return value;
        }
    }

    private static JSONObject parseJson(JSONObject json) {
        JSONObject result = new JSONObject();
        for (String key : json.keySet()) {
            result.put(key, parseValue(json.get(key)));
        }
        return result;
    }

    public static JSONObject deep(String json) {
        return parseJson(new JSONObject(json));
    }
}
