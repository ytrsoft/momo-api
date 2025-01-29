package com.ytrsoft.service;

import com.ytrsoft.core.ApiAccess;
import com.ytrsoft.core.Image;
import com.ytrsoft.core.Props;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProfileService {

    private final Props props;

    public ProfileService(Props props) {
        this.props = props;
    }

    public Map<String, Object> query(String id) {
        ApiAccess access = new ApiAccess(ApiAccess.PROFILE, props);
        access.params("remoteid", id);
        JSONObject response = access.doRequest();
        JSONObject profile = response.getJSONObject("data")
                .getJSONObject("profile");
        JSONObject ret = new JSONObject();
        ret.put("id", profile.getString("momoid"));
        ret.put("sex", profile.getString("sex").equals("F") ? "女" : "男");
        ret.put("name", profile.getString("name"));
        ret.put("age", profile.getInt("age"));
        String sign = profile.getString("sign");
        if (!StringUtils.isEmpty(sign)) {
            ret.put("sign", sign);
        }
        ret.put("constellation", profile.getString("constellation"));
        ret.put("location", profile.getString("show_location"));
        JSONArray photos = profile.getJSONArray("photos");
        ret.put("avatar", Image.parse(photos.getString(0)));
        return ret.toMap();
    }

}
