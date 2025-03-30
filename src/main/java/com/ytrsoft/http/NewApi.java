package com.ytrsoft.http;

import com.ytrsoft.convert.NewConvert;
import com.ytrsoft.domain.News;
import org.json.JSONObject;

import java.util.List;

@Api
public interface NewApi {

    @Request("/v2/feed/nearbyv2/lists")
    @Response(NewConvert.class)
    List<News> list(JSONObject params);

}
