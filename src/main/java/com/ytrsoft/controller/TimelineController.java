package com.ytrsoft.controller;

import com.ytrsoft.config.Query;
import com.ytrsoft.domain.Timeline;
import com.ytrsoft.service.TimelineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/timeline")
public class TimelineController {

    private final TimelineService ts;

    public TimelineController(TimelineService ts) {
        this.ts = ts;
    }

    @GetMapping("/{id}")
    public Query<List<Timeline>> list(@PathVariable String id) {
        return new Query<>(ts.list(id));
    }

}
