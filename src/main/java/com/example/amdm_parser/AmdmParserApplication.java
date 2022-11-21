package com.example.amdm_parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AmdmParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmdmParserApplication.class, args);
    }

}
