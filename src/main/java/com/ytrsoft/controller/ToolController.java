package com.ytrsoft.controller;

import com.ytrsoft.core.Props;
import com.ytrsoft.service.ToolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tool")
public class ToolController {

    private final Props props;
    private final ToolService ts;

    public ToolController(Props props, ToolService ts) {
        this.props = props;
        this.ts = ts;
    }

    @GetMapping("/props")
    public ResponseEntity<Props> props() {
        return new ResponseEntity<>(props, HttpStatus.OK);
    }

    @PostMapping("/unzip")
    public ResponseEntity<Map<String, Object>> unzip(@RequestParam("zip") String zip) {
        Map<String, Object> args = ts.unzip(zip);
        return new ResponseEntity<>(args, HttpStatus.OK);
    }
}
