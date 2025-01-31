package com.ytrsoft.parse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProfileConvert implements MapConvert {

    @Override
    public Map<String, Object> convert(JSONObject input) {
        int code = input.getInt("errcode");
        if (code != 0) {
            return new JSONObject().toMap();
        }
        JSONObject profile = input.optJSONObject("data").optJSONObject("profile");
        JSONObject result = new JSONObject();
        if (profile != null) {
            put(profile, result, "device_info", "device", "device");
            put(profile, result, "sp_living", "living", "name");
            put(profile, result, "sp_company", "company", "name");
            put(profile, result, "sp_workplace", "workplace", "name");
            put(profile, result, "sp_hometown", "hometown", "name");
            put(profile, result, "sp_industry", "job", "name");
            result.put("location", profile.optString("show_location"));
            result.put("photos", profile.optJSONArray("photos"));
            result.put("status", profile.optInt("online_status"));
            result.put("level", profile.optJSONObject("growup").optInt("level"));
            result.put("vip", profile.optJSONObject("vip").optInt("active_level"));
            result.put("real", profile.optJSONObject("realAuth").optInt("status"));
            addBasicInfo(profile, result);
            addSchoolInfo(profile, result);
        }
        return result.toMap();
    }

    private void addSchoolInfo(JSONObject profile, JSONObject result) {
        JSONArray schools = profile.optJSONArray("sp_school");
        if (schools != null && !schools.isEmpty()) {
            String[] schoolNames = new String[schools.length()];
            for (int i = 0; i < schools.length() ; i++) {
                JSONObject item = schools.optJSONObject(i);
                schoolNames[i] = item.optString("name");
            }
            result.put("school", String.join(" ", schoolNames));
        }
    }

    private void addBasicInfo(JSONObject profile, JSONObject result) {
        result.put("sex", profile.optString("sex").equals("F") ? "女" : "男");
        result.put("name", profile.optString("name"));
        String sign = profile.optString("sign", "");
        if (StringUtils.isNotEmpty(sign)) {
            result.put("sign", sign);
        }
        String height = profile.optString("height", "");
        if (StringUtils.isNotEmpty(height)) {
            result.put("height", height);
        }
        result.put("created", profile.optString("regtime"));
        result.put("id", profile.optString("momoid"));
        result.put("constellation", profile.optString("constellation"));
    }

    private void put(JSONObject profile, JSONObject result, String pk, String key, String vk) {
        JSONObject parent = profile.optJSONObject(pk);
        if (parent != null) {
            String value = parent.optString(vk);
            if (StringUtils.isNotEmpty(value)) {
                result.put(key, value);
            }
        }
    }
}
