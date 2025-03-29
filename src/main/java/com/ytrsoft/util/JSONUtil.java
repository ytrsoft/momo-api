package com.ytrsoft.util;

import com.alibaba.fastjson2.JSON;
import com.ytrsoft.domain.Alias;
import com.ytrsoft.domain.Select;
import org.json.JSONArray;
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
                    Select select = field.getAnnotation(Select.class);
                    if (select != null) {
                        JSONObject json = input;
                        String[] parts = select.value().split("\\.");
                        for (String part : parts) {
                           if (json.has(part)) {
                               Object value = json.get(part);
                               if (value instanceof JSONObject) {
                                   json = (JSONObject) value;
                               } else if (value instanceof JSONArray) {
                                   JSONArray array = (JSONArray) value;
                                   JSONArray temp = new JSONArray();
                                   for (int i = 0; i < array.length() ; i++) {
                                       Object item = array.get(i);
                                       if (!(item instanceof JSONObject)) {
                                           result.put(k2, value);
                                           break;
                                       }
                                       String pop = parts[parts.length - 1];
                                       temp.put(((JSONObject)item).opt(pop));
                                   }
                                   result.put(k2, temp);
                                   break;
                               } else {
                                   result.put(k2, value);
                                   break;
                               }
                           }
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
