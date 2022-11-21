package com.example.amdm_parser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Song {
    private final String name;
    private final String artist;
    private final String url;
    private final int position;
}
