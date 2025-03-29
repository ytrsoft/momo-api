package com.ytrsoft.controller;

import com.ytrsoft.config.Result;
import com.ytrsoft.domain.Comment;
import com.ytrsoft.dto.CommentDTO;
import com.ytrsoft.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService cs;

    public CommentController(CommentService cs) {
        this.cs = cs;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Comment>> list(@PathVariable String id) {
        return new ResponseEntity<>(cs.list(id), HttpStatus.OK);
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
