package com.ytrsoft.controller;

import com.ytrsoft.config.Result;
import com.ytrsoft.entity.Commit;
import com.ytrsoft.service.CommitService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commit")
public class CommitController {

    private final CommitService cs;

    public CommitController(CommitService cs) {
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
    public Result publish(@RequestBody Commit commit) {
        return new Result(cs.publish(commit));
    }

}
