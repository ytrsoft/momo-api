package com.ytrsoft.controller;

import com.ytrsoft.core.Props;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tool")
public class ToolController {

    private final Props props;

    public ToolController(Props props) {
        this.props = props;
    }

    @GetMapping("/props")
    public ResponseEntity<Props> props() {
        return new ResponseEntity<>(props, HttpStatus.OK);
    }
}
