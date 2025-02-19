package com.ytrsoft.service;

import com.ytrsoft.core.ApiAccess;
import com.ytrsoft.core.Props;
import com.ytrsoft.parse.NearlyConvert;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NearlyService {

    private final Props props;

    private final NearlyConvert nc;

    public NearlyService(Props props, NearlyConvert nc) {
        this.props = props;
        this.nc = nc;
    }

    public List<Object> query(String lat, String lng) {
        ApiAccess access = new ApiAccess(ApiAccess.NEARLY, props);
        access.params("online_time", "1");
        access.params("lat", lat);
        access.params("lng", lng);
        access.params("age_min", "18");
        access.params("age_max", "100");
        access.params("sex", "F");
        JSONObject response = access.doRequest();
        return nc.convert(response);
    }

}
