package com.amdm_parser.utils;

public enum SortCategories {
    NAME,
    ARTIST,
    CATEGORY,
    POSITION;

    public String getName(){
        return this.name().toLowerCase();
    }
}
