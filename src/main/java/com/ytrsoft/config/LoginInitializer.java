package com.ytrsoft.config;

import com.ytrsoft.core.Props;
import com.ytrsoft.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoginInitializer implements CommandLineRunner {

    private final Props props;
    private final UserService us;

    private static final Logger logger = LoggerFactory.getLogger(LoginInitializer.class);

    public LoginInitializer(Props props, UserService us) {
        this.props = props;
        this.us = us;
    }

    @Override
    public void run(String... args) {
        Map<String, Object> login = us.login();
        String session = (String) login.get("session");
        props.setSession(session);
        logger.info("SESSIONID={}", session);
    }
}
