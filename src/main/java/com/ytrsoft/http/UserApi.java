package com.ytrsoft.http;

import com.ytrsoft.convert.LoginConvert;
import com.ytrsoft.convert.LogoutConvert;
import org.json.JSONObject;

public interface UserApi {

    @Request("/api/v2/login")
    @Response(LoginConvert.class)
    JSONObject login(JSONObject params, JSONObject body);

    @Request("/api/setting/momologout")
    @Response(LogoutConvert.class)
    JSONObject logout(JSONObject params);

}
