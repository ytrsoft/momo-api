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
        String url = "https://api.immomo.com/v1/user/nice/check";
        String args = "{\"myprofile_source\":\"self\",\"signcount\":\"0\",\"_ab_test_\":\"nearbypeopleliveexp-kmjyjy_blank;location-spxwuo_blank;aisayhi-bmhtje_blank;test-rsxyxo_blank;morenew-wkhqld_A;active-wklfmo_blank\",\"_iid\":\"07c3dd68e2a1783adac3e0ca3fd862ad\",\"source_info\":\"{\\\"type\\\":\\\"-1\\\",\\\"extra\\\":\\\"com.immomo.momo.fullsearch.activity.FullSearchActivity\\\",\\\"stack\\\":\\\"[{\\\\\\\"name\\\\\\\":\\\\\\\"SessionListInnerFragment\\\\\\\"},{\\\\\\\"name\\\\\\\":\\\\\\\"FullSearchActivity\\\\\\\"},{\\\\\\\"data\\\\\\\":\\\\\\\"{\\\\\\\\\\\\\\\"userid\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"994491371\\\\\\\\\\\\\\\"}\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"PersonalProfileActivityK\\\\\\\"}]\\\"}\",\"profile_source\":\"profile\",\"newProfileExp\":\"B\",\"remoteid\":\"994491371\",\"_net_\":\"wifi\",\"_uid_\":\"a3931e93ff9cb0bc16e38cf3a14aa599\"}";
        ApiAccess access = new ApiAccess(url, props);
        JSONObject object = access.withParams(args).doRequest();
        return object.toMap();
    }


}
