package com.ytrsoft.service;

import com.ytrsoft.core.ApiAccess;
import com.ytrsoft.core.Base64;
import com.ytrsoft.core.Coded;
import com.ytrsoft.core.Props;
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
        String url = ApiAccess.PROFILE;
        JSONObject params = new JSONObject();
        params.put("myprofile_source", "self");
        params.put("signcount", "0");
        params.put("profile_source", "profile");
        params.put("newProfileExp", "B");
        params.put("source_info", "{\"type\":\"-1\",\"extra\":\"com.immomo.momo.fullsearch.activity.FullSearchActivity\",\"stack\":\"[{\\\"name\\\":\\\"SessionListInnerFragment\\\"},{\\\"name\\\":\\\"FullSearchActivity\\\"},{\\\"data\\\":\\\"{\\\\\\\"userid\\\\\\\":\\\\\\\"1085547122\\\\\\\"}\\\",\\\"name\\\":\\\"PersonalProfileActivityK\\\"}]\"}");
        params.put("remoteid", "1085547122");
        ApiAccess access = new ApiAccess(url, props);
        JSONObject object = access.withParams(params).doRequest();
        return object.toMap();
    }


}
