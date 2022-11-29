package com.amdm_parser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TopicPage {
    private List<Song> songs;
    private boolean hasPrevious;
    private boolean hasNext;
}
