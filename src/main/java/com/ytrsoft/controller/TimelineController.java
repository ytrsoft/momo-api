package com.ytrsoft.controller;

import com.ytrsoft.config.Result;
import com.ytrsoft.service.TimelineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/timeline")
public class TimelineController {

    private final TimelineService ts;

    public TimelineController(TimelineService ts) {
        this.ts = ts;
    }

    @GetMapping("/{id}")
    public Result list(@PathVariable String id) {
        return new Result(ts.list(id));
    }

}
