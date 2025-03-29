package com.ytrsoft.convert;

import com.ytrsoft.domain.Profile;
import com.ytrsoft.util.JSONUtil;
import org.json.JSONObject;

public class ProfileConvert extends AbsConvert<Profile> {

    @Override
    protected Profile next(JSONObject input) {
        JSONObject profile = input.optJSONObject("profile");
        return JSONUtil.parseObject(Profile.class, profile);
    }

}
