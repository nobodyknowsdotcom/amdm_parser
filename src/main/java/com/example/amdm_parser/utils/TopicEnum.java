package com.example.amdm_parser.utils;

import lombok.Getter;

@Getter
public enum TopicEnum {
    TODAY ("https://amdm.ru/akkordi/popular/"),
    WEEK ("https://amdm.ru/akkordi/popular/week"),
    MONTH ("https://amdm.ru/akkordi/popular/month"),
    ALL ("https://amdm.ru/akkordi/popular/all");

    private final String url;

    TopicEnum(String link) {
        this.url = link;
    }


    @Override
    public String toString(){
        return url;
    }
}
