package com.example.amdm_parser.dto;

import lombok.Data;

@Data
public class Song {
    private final String name;
    private final String artist;
    private final String url;
    private final int position;
}
