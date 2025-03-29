package com.ytrsoft.controller;

import com.ytrsoft.config.Action;
import com.ytrsoft.config.Query;
import com.ytrsoft.domain.Comment;
import com.ytrsoft.dto.CommentDTO;
import com.ytrsoft.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService cs;

    public CommentController(CommentService cs) {
        this.cs = cs;
    }

    @GetMapping("/{id}")
    public Query<List<Comment>> list(@PathVariable String id) {
        return new Query<>(cs.list(id));
    }

    @DeleteMapping("/{id}")
    public Action remove(@PathVariable String id) {
        return new Action(cs.remove(id));
    }

    @PostMapping("/publish")
    public Action publish(@RequestBody CommentDTO commit) {
        return new Action(cs.publish(commit));
    }

}
