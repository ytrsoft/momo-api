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
        JsonSet.putSex(profile, result);
        JsonSet.putString(profile, result, "name");
        JsonSet.putString(profile, result, "sign");
        JsonSet.putString(profile, result, "height");
        JsonSet.putString(profile, result, "age");
        JsonSet.putString(profile, result, "constellation");
        JsonSet.putString(profile, result, "regtime", "created");
        JsonSet.putString(profile, result, "momoid", "id");
        JsonSet.putString(profile, result, "show_location", "location");
        JsonSet.putArray(profile, result, "photos");
        JsonSet.putInt(profile, result, "online_status", "status");
        JsonSet.putInt(profile, result, "growup", "level", "level");
        JsonSet.putInt(profile, result, "vip", "active_level", "vip");
        JsonSet.putInt(profile, result, "realAuth", "status", "realAuth");
        JsonSet.putString(profile, result, "device_info", "device", "device");
        JsonSet.putString(profile, result, "sp_living", "name", "living");
        JsonSet.putString(profile, result, "sp_company", "name", "company");
        JsonSet.putString(profile, result, "sp_workplace", "name", "workplace");
        JsonSet.putString(profile, result, "sp_hometown", "name", "hometown");
        JsonSet.putString(profile, result, "sp_industry", "name", "job");
        setSchool(profile, result);
        return result.toMap();
    }

    private void setSchool(JSONObject profile, JSONObject result) {
        if (profile != null) {
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
    }

}
