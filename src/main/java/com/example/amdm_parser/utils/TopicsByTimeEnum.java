package com.example.amdm_parser.utils;

import lombok.Getter;

@Getter
public enum TopicsByTimeEnum {
    TODAY ("https://amdm.ru/akkordi/popular/"),
    WEEK ("https://amdm.ru/akkordi/popular/week"),
    MONTH ("https://amdm.ru/akkordi/popular/month"),
    ALL ("https://amdm.ru/akkordi/popular/all");

    private final String url;

    TopicsByTimeEnum(String link) {
        this.url = link;
    }


    @Override
    public String toString(){
        return url;
    }
}
