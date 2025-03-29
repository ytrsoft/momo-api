package com.ytrsoft.util;

import com.alibaba.fastjson2.JSON;
import com.ytrsoft.domain.Alias;
import com.ytrsoft.domain.Select;
import org.json.JSONObject;

import java.lang.reflect.Field;

public final class JSONUtil {

    private JSONUtil() {
        throw new UnsupportedOperationException();
    }

    public static <T> T parseObject(Class<T> clazz, JSONObject... inputs) {
        JSONObject result = new JSONObject();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            for (JSONObject input : inputs) {
                String k1 = field.getName();
                String k2 = field.getName();
                if (field.isAnnotationPresent(Alias.class)) {
                    Alias alias = field.getAnnotation(Alias.class);
                    if (alias != null) {
                        k1 = alias.value();
                    }
                }
                if (field.isAnnotationPresent(Select.class)) {
                    Select path = field.getAnnotation(Select.class);
                    if (path != null) {
                        JSONObject json = input;
                        String[] parts = path.value().split("\\.");
                        for (int i = 0; i < parts.length - 1 ; i++) {
                            if (json.has(parts[i])) {
                                json = json.getJSONObject(parts[i]);
                            }
                        }
                        if (json.has(parts[parts.length - 1])) {
                            result.put(k2, json.opt(parts[parts.length - 1]));
                        }
                    }
                } else {
                    result.put(k2, input.opt(k1));
                }
            }
        }
        return JSON.parseObject(result.toString(), clazz);
    }


}
