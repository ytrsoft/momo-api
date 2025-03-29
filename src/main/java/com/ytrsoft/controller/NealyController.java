package com.ytrsoft.controller;

import com.ytrsoft.domain.Nearly;
import com.ytrsoft.service.NearlyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nearly")
public class NealyController {

    private final NearlyService ns;

    public NealyController(NearlyService ns) {
        this.ns = ns;
    }

    @GetMapping("/list/{exp}")
    public ResponseEntity<List<Nearly>> list(@PathVariable String exp) {
        return new ResponseEntity<>(ns.list(exp), HttpStatus.OK);
    }

}
