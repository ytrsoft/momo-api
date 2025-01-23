package com.ytrsoft.controller;

import com.ytrsoft.core.Props;
import com.ytrsoft.service.ParseService;
import com.ytrsoft.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/parse")
public class ParseController {

    private final Props props;
    private final ParseService ps;

    public ParseController(Props props, ParseService ps) {
        this.props = props;
        this.ps = ps;
    }

    @GetMapping("/props")
    public ResponseEntity<Props> props() {
        return new ResponseEntity<>(props, HttpStatus.OK);
    }

    @PostMapping("/unzip")
    public ResponseEntity<Map<String, Object>> unzip(@RequestParam("zip") String zip,
        @RequestParam(value = "mode", defaultValue = "0") Integer mode) {
        Map<String, Object> body = ps.unzip(zip, mode);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
