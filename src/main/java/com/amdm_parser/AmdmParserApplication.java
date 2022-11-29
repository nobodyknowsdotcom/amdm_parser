package com.amdm_parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableScheduling
@SpringBootApplication
public class AmdmParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmdmParserApplication.class, args);
    }

}
