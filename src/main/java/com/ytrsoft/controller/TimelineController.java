package com.ytrsoft.controller;

import com.ytrsoft.service.TimelineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Object>> query(@PathVariable String id) {
        List<Object> queried = ts.query(id);
        return new ResponseEntity<>(queried, HttpStatus.OK);
    }

}
