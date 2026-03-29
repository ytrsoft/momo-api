package com.ytrsoft.momo.core;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Deep JSON parser that recursively resolves nested JSON strings.
 *
 * <p>Some API responses contain JSON-encoded strings as values. This parser detects and
 * recursively expands them into proper {@link JSONObject} or {@link JSONArray} instances.
 */
public final class JsonParser {

  private JsonParser() {}

  /**
   * Parses a JSON string and recursively resolves any nested JSON strings within it.
   *
   * @param json the JSON string
   * @return the deeply parsed {@link JSONObject}
   */
  public static JSONObject deepParse(String json) {
    return resolveObject(new JSONObject(json));
  }

  private static JSONObject resolveObject(JSONObject obj) {
    JSONObject result = new JSONObject();
    for (String key : obj.keySet()) {
      result.put(key, resolveValue(obj.get(key)));
    }
    return result;
  }

  private static JSONArray resolveArray(JSONArray array) {
    JSONArray result = new JSONArray();
    for (int i = 0; i < array.length(); i++) {
      result.put(resolveValue(array.get(i)));
    }
    return result;
  }

  private static Object resolveValue(Object value) {
    if (value instanceof JSONObject) {
      return resolveObject((JSONObject) value);
    }
    if (value instanceof JSONArray) {
      return resolveArray((JSONArray) value);
    }
    if (value instanceof String) {
      return tryExpandJsonString((String) value);
    }
    return value;
  }

  private static Object tryExpandJsonString(String str) {
    String trimmed = str.trim();
    if (trimmed.isEmpty()) {
      return str;
    }
    char first = trimmed.charAt(0);
    if (first == '{') {
      try {
        return resolveObject(new JSONObject(trimmed));
      } catch (Exception ignored) {
        // Not valid JSON object — fall through.
      }
    } else if (first == '[') {
      try {
        return resolveArray(new JSONArray(trimmed));
      } catch (Exception ignored) {
        // Not valid JSON array — fall through.
      }
    }
    return str;
  }
}
