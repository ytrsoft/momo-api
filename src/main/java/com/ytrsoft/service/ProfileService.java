package com.ytrsoft.service;

import com.ytrsoft.core.ApiAccess;
import com.ytrsoft.core.Props;
import com.ytrsoft.parse.ProfileConvert;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProfileService {

    private final Props props;
    private final ProfileConvert pc;

    public ProfileService(Props props, ProfileConvert pc) {
        this.props = props;
        this.pc = pc;
    }

    public Map<String, Object> query(String id) {
        ApiAccess access = new ApiAccess(ApiAccess.PROFILE, props);
        access.params("remoteid", id);
        JSONObject response = access.doRequest();
        return pc.convert(response);
    }

}
