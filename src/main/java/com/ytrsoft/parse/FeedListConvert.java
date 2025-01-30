package com.ytrsoft.parse;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class FeedListConvert extends DefaultListConvert  {

    protected JSONObject processItem(JSONObject object) {
        if (object == null) return null;

        JSONObject item = new JSONObject();
        JSONObject source = object.optJSONObject("source");
        JSONObject logmap = object.optJSONObject("logmap");
        item.put("lng", logmap.optString("lng"));
        item.put("lat", logmap.optString("lat"));

        if (source == null) return null;

        addContentData(source, item);

        return item;
    }

    private void addContentData(JSONObject source, JSONObject item) {
        if (source.has("contentData")) {
            JSONObject contentData = source.optJSONObject("contentData");
            if (contentData != null) {
                item.put("content", contentData.optString("content"));
                JSONArray pics = contentData.optJSONArray("pics");
                if (!pics.isEmpty()) {
                    item.put("images", pics);
                }
                item.put("id", contentData.optString("id"));
                item.put("distance", contentData.optInt("distance"));
                item.put("createTime", contentData.optInt("createTime"));
                addSiteInfo(contentData, item);
                addVideo(contentData, item);
            }
            addBasicInfo(source, item);
        }
    }

    private void addSiteInfo(JSONObject contentData, JSONObject item) {
        if (contentData.has("site")) {
            JSONObject site = contentData.optJSONObject("site");
            if (site != null) {
                item.put("site", site.optString("sname"));
            }
        }
    }

    private void addBasicInfo(JSONObject source, JSONObject item) {
        if (source.has("basicInfo")) {
            JSONObject basicInfo = source.optJSONObject("basicInfo");
            if (basicInfo != null) {
                item.put("id", basicInfo.optString("owner"));
                item.put("name", basicInfo.optString("name"));
                item.put("age", basicInfo.optInt("age"));
                item.put("sex", convertSex(basicInfo.optString("sex")));
            }
        }
    }

    private void addVideo(JSONObject source, JSONObject item) {
        if (source.has("microvideo")) {
            JSONObject microvideo = source.optJSONObject("microvideo");
            String id = microvideo.optJSONObject("video").getString("guid");
            item.put("video", id);
        }
    }

    private String convertSex(String sex) {
        return "F".equals(sex) ? "女" : "男";
    }

}
