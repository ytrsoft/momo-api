package com.ytrsoft.http;

import com.ytrsoft.convert.NearlyConvert;
import com.ytrsoft.domain.Nearly;
import org.json.JSONObject;

import java.util.List;

@Api
public interface NearlyApi {

    @Request("/v2/nearby/people/lists")
    @Response(NearlyConvert.class)
    List<Nearly> list(JSONObject params);

}
