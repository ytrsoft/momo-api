package com.ytrsoft.http;

import com.ytrsoft.convert.LoginConvert;
import com.ytrsoft.convert.LogoutConvert;
import org.json.JSONObject;

public interface UserApi {

    @Request("/api/v2/login")
    @Response(LoginConvert.class)
    String login(JSONObject params, JSONObject body);

    @Request("/api/setting/momologout")
    @Response(LogoutConvert.class)
    String logout(JSONObject params);

}
