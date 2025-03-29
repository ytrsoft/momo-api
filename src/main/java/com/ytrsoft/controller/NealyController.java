package com.ytrsoft.controller;

import com.ytrsoft.config.Query;
import com.ytrsoft.domain.Nearly;
import com.ytrsoft.service.NearlyService;
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
    public Query<List<Nearly>> list(@PathVariable String exp) {
        return new Query<>(ns.list(exp));
    }

}
