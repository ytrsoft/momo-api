package com.ytrsoft.config;

import com.ytrsoft.core.Props;
import com.ytrsoft.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MomoSetup implements CommandLineRunner {

    private int count = 0;
    private final Props props;
    private final UserService us;

    private static final Logger logger = LoggerFactory.getLogger(MomoSetup.class);

    public MomoSetup(Props props, UserService us) {
        this.props = props;
        this.us = us;
    }

    @Override
    public void run(String... args) {
        String session = us.login();
        props.setSession(session);
        logger.info("登录 = {}", session);
    }

    @EventListener(ContextClosedEvent.class)
    public void onDestroy() {
        String token = us.logout();
        props.setToken(token);
        logger.info("登出 = {}", token);
    }
}
