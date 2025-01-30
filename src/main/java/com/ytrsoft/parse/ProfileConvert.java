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
        JSONObject profile = input.optJSONObject("data").optJSONObject("profile");

        JSONObject result = new JSONObject();

        if (profile != null) {
            addDeviceInfo(profile, result);
            addSchoolInfo(profile, result);
            addHometownInfo(profile, result);
            addBasicInfo(profile, result);
            addPhotos(profile, result);
            addStatus(profile, result);
            addLevelAndVip(profile, result);
            addJobInfo(profile, result);
            addLocation(profile, result);
        }

        return result.toMap();
    }

    private void addDeviceInfo(JSONObject profile, JSONObject result) {
        JSONObject deviceInfo = profile.optJSONObject("device_info");
        if (deviceInfo != null) {
            result.put("device", deviceInfo.optString("device"));
        }
    }

    private void addSchoolInfo(JSONObject profile, JSONObject result) {
        JSONArray spSchoolArray = profile.optJSONArray("sp_school");
        if (spSchoolArray != null && !spSchoolArray.isEmpty()) {
            JSONObject spSchool = spSchoolArray.optJSONObject(0);
            if (spSchool != null) {
                result.put("school", spSchool.optString("name"));
            }
        }
    }

    private void addHometownInfo(JSONObject profile, JSONObject result) {
        JSONObject spHometown = profile.optJSONObject("sp_hometown");
        if (spHometown != null) {
            result.put("hometown", spHometown.optString("name"));
        }
    }

    private void addBasicInfo(JSONObject profile, JSONObject result) {
        result.put("sex", profile.optString("sex").equals("F") ? "女" : "男");
        result.put("name", profile.optString("name"));

        String sign = profile.optString("sign", null);
        if (StringUtils.isNotEmpty(sign)) {
            result.put("sign", sign);
        }

        String height = profile.optString("height", null);
        if (StringUtils.isNotEmpty(height)) {
            result.put("height", height);
        }

        result.put("created", profile.optString("regtime"));
        result.put("id", profile.optString("momoid"));
        result.put("constellation", profile.optString("constellation"));
    }

    private void addPhotos(JSONObject profile, JSONObject result) {
        result.put("photos", profile.optJSONArray("photos"));
    }

    private void addStatus(JSONObject profile, JSONObject result) {
        result.put("status", profile.optInt("online_status"));
    }

    private void addLevelAndVip(JSONObject profile, JSONObject result) {
        result.put("level", profile.optJSONObject("growup").optInt("level"));
        result.put("vip", profile.optJSONObject("vip").optInt("active_level"));
        result.put("real", profile.optJSONObject("realAuth").optInt("status"));
    }

    private void addJobInfo(JSONObject profile, JSONObject result) {
        JSONObject industry = profile.optJSONObject("sp_industry");
        if (industry != null) {
            result.put("job", industry.optString("name"));
        }
    }

    private void addLocation(JSONObject profile, JSONObject result) {
        result.put("location", profile.optString("show_location"));
    }
}
