package com.example.amdm_parser.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TopicCategories {
    TODAY ("https://amdm.ru/akkordi/popular"),
    WEEK ("https://amdm.ru/akkordi/popular/week"),
    MONTH ("https://amdm.ru/akkordi/popular/month"),
    ALL ("https://amdm.ru/akkordi/popular/all");

    private final String url;

    @Override
    public String toString(){
        return url;
    }
}
