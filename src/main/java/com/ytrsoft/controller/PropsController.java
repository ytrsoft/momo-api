package com.ytrsoft.controller;

import com.ytrsoft.core.Props;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/props")
public class PropsController {

    private final Props props;

    public PropsController(Props props) {
        this.props = props;
    }

    @GetMapping("/read")
    public ResponseEntity<Props> read() {
        return new ResponseEntity<>(props, HttpStatus.OK);
    }
}
