package com.ytrsoft.util;

import com.alibaba.fastjson2.JSON;
import com.ytrsoft.domain.Alias;
import com.ytrsoft.domain.Select;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Optional;

public final class JSONUtil {

    private JSONUtil() {
        throw new UnsupportedOperationException();
    }

    public static <T> T parseObject(Class<T> clazz, JSONObject... inputs) {
        JSONObject result = new JSONObject();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            String aliasName = Optional.ofNullable(field.getAnnotation(Alias.class))
                    .map(Alias::value)
                    .orElse(fieldName);

            for (JSONObject input : inputs) {
                if (field.isAnnotationPresent(Select.class)) {
                    handleSelectField(result, field, input, fieldName);
                } else {
                    result.put(fieldName, input.opt(aliasName));
                }
            }
        }
        return JSON.parseObject(result.toString(), clazz);
    }

    private static void handleSelectField(JSONObject result, Field field, JSONObject input, String fieldName) {
        Select select = field.getAnnotation(Select.class);
        if (select == null) return;

        String[] parts = select.value().split("\\.");
        JSONObject json = input;

        for (String part : parts) {
            if (!json.has(part)) return;

            Object value = json.opt(part);
            if (value instanceof JSONObject obj) {
                json = obj;
            } else if (value instanceof JSONArray array) {
                handleJSONArray(result, array, fieldName, parts[parts.length - 1]);
                return;
            } else {
                result.put(fieldName, value);
                return;
            }
        }
    }

    private static void handleJSONArray(JSONObject result, JSONArray array, String fieldName, String popKey) {
        JSONArray tempArray = new JSONArray();

        for (Object item : array) {
            if (item instanceof JSONObject obj) {
                tempArray.put(obj.opt(popKey));
            } else {
                result.put(fieldName, array);
                return;
            }
        }
        result.put(fieldName, tempArray);
    }
}
