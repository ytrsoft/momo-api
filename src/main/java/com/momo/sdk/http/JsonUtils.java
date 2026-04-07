package com.momo.sdk.http;

import lombok.experimental.UtilityClass;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JSON 深度解析工具类。
 *
 * <p>Momo 服务端返回的字段中,部分嵌套对象会被序列化成字符串再放入 JSON。本工具会递归遍历
 * 整个 JSON 树,尝试把这种"字符串里的 JSON"反序列化回真正的对象/数组,方便上层取值。
 */
@UtilityClass
public class JsonUtils {

  /**
   * 对入参 JSON 字符串做深度解析。
   *
   * @param json 原始 JSON 字符串
   * @return 解析、展平后的 {@link JSONObject}
   */
  public JSONObject deep(String json) {
    return parseObject(new JSONObject(json));
  }

  /** 递归解析 {@link JSONObject}。 */
  private JSONObject parseObject(JSONObject json) {
    JSONObject result = new JSONObject();
    for (String key : json.keySet()) {
      result.put(key, parseValue(json.get(key)));
    }
    return result;
  }

  /** 递归解析 {@link JSONArray}。 */
  private JSONArray parseArray(JSONArray array) {
    JSONArray result = new JSONArray();
    for (int i = 0; i < array.length(); i++) {
      result.put(parseValue(array.get(i)));
    }
    return result;
  }

  /** 根据值的实际类型派发到不同的解析逻辑。 */
  private Object parseValue(Object value) {
    if (value instanceof JSONObject) {
      return parseObject((JSONObject) value);
    }
    if (value instanceof JSONArray) {
      return parseArray((JSONArray) value);
    }
    if (value instanceof String) {
      return tryParseString((String) value);
    }
    return value;
  }

  /**
   * 尝试将字符串当作 JSON 解析,先 object 后 array,失败则返回原字符串。
   */
  private Object tryParseString(String str) {
    try {
      return parseObject(new JSONObject(str));
    } catch (Exception ignored) {
      try {
        return parseArray(new JSONArray(str));
      } catch (Exception ignored2) {
        return str;
      }
    }
  }
}
