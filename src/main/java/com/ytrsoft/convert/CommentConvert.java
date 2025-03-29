package com.ytrsoft.convert;

import com.ytrsoft.domain.Comment;
import com.ytrsoft.util.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentConvert extends AbsConvert<List<Comment>> {

    @Override
    protected List<Comment> next(JSONObject input) {
        JSONObject list = input.optJSONObject("list");
        JSONArray comments = list.optJSONArray("comments");
        return convertTree(comments);
    }

    private List<Comment> convertTree(JSONArray comments) {
        List<Comment> commentList = new ArrayList<>();
        if (comments == null) {
            return commentList;
        }
        for (int i = 0; i < comments.length(); i++) {
            JSONObject node = comments.getJSONObject(i);
            if (node.has("source")) {
                node = node.optJSONObject("source");
            }
            Comment comment = handleNode(node);
            if (node.has("childs")) {
                JSONArray childs = node.optJSONArray("childs");
                comment.setChildren(convertTree(childs));
            }
            commentList.add(comment);
        }
        return commentList;
    }

    private Comment handleNode(JSONObject node) {
        return JSONUtil.parseObject(Comment.class, node);
    }

}
