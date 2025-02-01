package com.ytrsoft.parse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommitsConvert implements ListConvert {

    private JSONObject processComment(JSONObject source) {
        JSONObject item = new JSONObject();
        JSONObject user = source.optJSONObject("user");
        if (user != null) {
            JsonSet.putSex(user, item);
            JsonSet.putString(user, item, "age");
            JsonSet.putString(user, item, "avatar");
            JsonSet.putString(user, item, "momoid", "userId");
            JsonSet.putString(user, item, "name", "from");

            JsonSet.putInt(source, item, "like_count", "like");
            JsonSet.putString(source, item, "toname", "to");
            JsonSet.putString(source, item, "commentid", "id");
            JsonSet.putString(source, item, "feedid");
            JsonSet.putString(source, item, "content");
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
