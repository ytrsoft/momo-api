package com.ytrsoft.parse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommitsConvert implements ListConvert {

    private JSONObject processComment(JSONObject source) {
        JSONObject item = new JSONObject();

        String content = source.optString("content");
        String id = source.optString("commentid");
        String feedid = source.optString("feedid");
        String to = source.optString("toname");
        int like = source.optInt("like_count");

        JSONObject user = source.optJSONObject("user");
        if (user != null) {
            String sex = "F".equals(user.optString("sex")) ? "女" : "男";
            String from = user.optString("name");
            String avatar = user.optString("avatar");
            String userId = user.optString("momoid");
            String age = user.optString("age");

            item.put("content", content);
            item.put("id", id);
            item.put("feedid", feedid);
            item.put("to", to);
            item.put("like", like);
            item.put("userid", userId);
            item.put("sex", sex);
            item.put("from", from);
            item.put("avatar", avatar);
            item.put("age", age);
        }

        return item;
    }

    private JSONArray processChildren(JSONArray childs) {
        JSONArray children = new JSONArray();
        for (int i = 0; i < childs.length(); i++) {
            JSONObject child = childs.optJSONObject(i);
            if (child != null) {
                JSONObject item = processComment(child);
                JSONArray subChildren = child.optJSONArray("childs");
                if (subChildren != null && !subChildren.isEmpty()) {
                    item.put("children", processChildren(subChildren));
                }
                children.put(item);
            }
        }
        return children;
    }

    @Override
    public List<Object> convert(JSONObject input) {
        JSONArray result = new JSONArray();
        JSONArray comments = input.optJSONObject("data")
                .optJSONObject("list")
                .optJSONArray("comments");

        if (comments != null) {
            for (int i = 0; i < comments.length(); i++) {
                JSONObject comment = comments.optJSONObject(i);
                if (comment != null) {
                    JSONObject source = comment.optJSONObject("source");
                    JSONObject item = processComment(source);
                    JSONArray children = processChildren(source.optJSONArray("childs"));
                    if (!children.isEmpty()) {
                        item.put("children", children);
                    }
                    result.put(item);
                }
            }
        }

        return result.toList();
    }
}
