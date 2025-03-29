package com.ytrsoft.controller;

import com.ytrsoft.config.Result;
import com.ytrsoft.dto.CommentDTO;
import com.ytrsoft.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService cs;

    public CommentController(CommentService cs) {
        this.cs = cs;
    }

    @GetMapping("/{id}")
    public Result list(@PathVariable String id) {
        return new Result(cs.list(id));
    }

    @DeleteMapping("/{id}")
    public Result remove(@PathVariable String id) {
        return new Result(cs.remove(id));
    }

    @PostMapping("/publish")
    public Result publish(@RequestBody CommentDTO commit) {
        return new Result(cs.publish(commit));
    }

}
