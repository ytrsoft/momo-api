package com.ytrsoft;

import com.ytrsoft.config.MomoBanner;
import com.ytrsoft.core.Props;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Props.class)
public class MomoBoot {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MomoBoot.class);
        app.setBanner(new MomoBanner());
        app.run(args);
    }

}
