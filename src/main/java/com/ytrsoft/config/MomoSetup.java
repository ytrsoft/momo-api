package com.ytrsoft.config;

import com.ytrsoft.core.Props;
import com.ytrsoft.service.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MomoSetup implements CommandLineRunner {

    private final Props props;
    private final UserService us;

    private static final Logger logger = LoggerFactory.getLogger(MomoSetup.class);

    public MomoSetup(Props props, UserService us) {
        this.props = props;
        this.us = us;
    }

    @Override
    public void run(String... args) {
        JSONObject login = us.login();
        String session = login.optString("session");
        props.setSession(session);
        logger.info("登录 = {}", session);
    }

    @EventListener(ContextClosedEvent.class)
    public void onDestroy() {
        JSONObject logout = us.logout();
        String token = logout.optString("token");
        props.setToken(token);
        logger.info("登出 = {}", token);
    }
}
