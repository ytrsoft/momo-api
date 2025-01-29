package com.ytrsoft.core;

public interface Api {
    String BASE_URL = "https://api.immomo.com";
    String NEWS = BASE_URL + "/v2/feed/nearbyv2/lists";
    String COMMENTS = BASE_URL + "/v2/feed/comment/comments";
    String TIMELINE = BASE_URL + "/v1/feed/user/timeline";
    String PROFILE = BASE_URL + "/v3/user/profile/info";
    String NEARLY = BASE_URL + "/v2/nearby/people/lists";
    String PUBLISH = BASE_URL + "/api/feed/v2/comment/publish";
    String LOGIN = BASE_URL + "/api/v2/login";
}
