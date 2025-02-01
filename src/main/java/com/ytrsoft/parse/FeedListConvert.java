package com.ytrsoft.parse;

import org.json.JSONObject;

public abstract class FeedListConvert extends DefaultListConvert  {

    protected JSONObject processItem(JSONObject object) {
        JSONObject item = new JSONObject();
        if (object != null) {
            JSONObject source = object.optJSONObject("source");
            if (source != null) {
                JSONObject map = object.optJSONObject("logmap");
                setContentData(map, source, item);
            }
        }
        return item;
    }

    private void setContentData(JSONObject map, JSONObject source, JSONObject item) {
        if (source.has("contentData")) {
            JsonSet.putString(map, item, "lng");
            JsonSet.putString(map, item, "lat");
            JsonSet.putString(source, item, "id");
            JSONObject contentData = source.optJSONObject("contentData");
            if (contentData != null) {
                JsonSet.putString(contentData, item, "content");
                JsonSet.putArray(contentData, item, "images", "pics");
                JsonSet.putInt(contentData, item, "distance");
                JsonSet.putInt(contentData, item, "likeCount", "like");
                JsonSet.putInt(contentData, item, "createTime");
                JsonSet.putString(contentData, item, "site", "sname", "site");
                setVideo(contentData, item);
            }
            setBasicInfo(source, item);
        }
    }

    private void setBasicInfo(JSONObject source, JSONObject item) {
        if (source.has("basicInfo")) {
            JSONObject basicInfo = source.optJSONObject("basicInfo");
            if (basicInfo != null) {
                JsonSet.putString(basicInfo, item, "owner");
                JsonSet.putString(basicInfo, item, "name");
                JsonSet.putInt(basicInfo, item, "age");
                JsonSet.putSex(basicInfo, item);
            }
        }
    }

    private void setVideo(JSONObject source, JSONObject item) {
        if (source.has("microvideo")) {
            JSONObject microvideo = source.optJSONObject("microvideo");
            JSONObject video = microvideo.optJSONObject("video");
            if (video.has("guid")) {
                item.put("video", video.optString("guid"));
            }
        }
    }

}
