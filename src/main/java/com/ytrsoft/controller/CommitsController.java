package com.ytrsoft.controller;

import com.ytrsoft.entity.Commit;
import com.ytrsoft.service.CommitsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commits")
public class CommitsController {

    private final CommitsService cs;

    public CommitsController(CommitsService cs) {
        this.cs = cs;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Object>> query(@PathVariable String id) {
        List<Object> queried = cs.query(id);
        return new ResponseEntity<>(queried, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> remove(@PathVariable String id) {
        Map<String, Object> result = cs.remove(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/publish")
    public ResponseEntity<Map<String, Object>> publish(@RequestBody Commit commit) {
        Map<String, Object> result = cs.publish(commit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
