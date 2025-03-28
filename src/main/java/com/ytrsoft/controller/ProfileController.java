package com.ytrsoft.controller;

import com.ytrsoft.core.Props;
import com.ytrsoft.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final Props props;
    private final ProfileService ps;

    public ProfileController(Props props, ProfileService ps) {
        this.props = props;
        this.ps = ps;
    }

    @GetMapping("/query")
    public ResponseEntity<Map<String, Object>> query() {
        Map<String, Object> checked = ps.query(props.getUsr());
        return new ResponseEntity<>(checked, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> query(@PathVariable String id) {
        Map<String, Object> checked = ps.query(id);
        return new ResponseEntity<>(checked, HttpStatus.OK);
    }
}
