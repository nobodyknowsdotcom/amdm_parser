package com.example.amdm_parser.controller;

import com.example.amdm_parser.dto.Song;
import com.example.amdm_parser.service.AmDmParser;
import com.example.amdm_parser.utils.TopicCategories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ParserController {
    private final AmDmParser parserService;

    public ParserController(AmDmParser parserService) {
        this.parserService = parserService;
    }

    @GetMapping("/{topic_type}")
    public ArrayList<Song> getSongsList(@PathVariable String topic_type){
        String topicUrl = TopicCategories.valueOf(topic_type.toUpperCase()).toString();
        return parserService.getSongsList(topicUrl);
    }
}
