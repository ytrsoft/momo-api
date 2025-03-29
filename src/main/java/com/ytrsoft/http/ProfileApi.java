package com.ytrsoft.http;

import com.ytrsoft.convert.ProfileConvert;
import com.ytrsoft.domain.Profile;
import org.json.JSONObject;

public interface ProfileApi {

    @Request("/v3/user/profile/info")
    @Response(ProfileConvert.class)
    Profile query(JSONObject params);

}
