package com.ytrsoft.http;

import com.ytrsoft.convert.TimelineConvert;
import com.ytrsoft.domain.Timeline;
import org.json.JSONObject;

import java.util.List;

public interface TimelineApi {

    @Request("/v1/feed/user/timeline")
    @Response(TimelineConvert.class)
    List<Timeline> list(JSONObject params);

}
