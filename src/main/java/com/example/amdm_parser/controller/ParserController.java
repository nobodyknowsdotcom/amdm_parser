package com.example.amdm_parser.controller;

import com.example.amdm_parser.dto.Song;
import com.example.amdm_parser.repository.SongsTopicRepository;
import com.example.amdm_parser.service.AmDmParser;
import com.example.amdm_parser.service.SongTopicSaver;
import com.example.amdm_parser.utils.TopicCategories;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ParserController {
    private final SongsTopicRepository repo;
    private final AmDmParser parserService;
    private final SongTopicSaver topicSaverService;

    public ParserController(SongsTopicRepository repo, AmDmParser parserService, SongTopicSaver topicSaverService) {
        this.repo = repo;
        this.parserService = parserService;
        this.topicSaverService = topicSaverService;
    }

    @PostMapping("/{topicType}")
    public int parseTopic(@PathVariable String topicType){
        TopicCategories category = TopicCategories.valueOf(topicType.toUpperCase());
        ArrayList<Song> songsList = parserService.getSongsByCategory(category);
        topicSaverService.saveSongsTopic(songsList);
        return (int) repo.count();
    }

    @GetMapping("/{topicType}")
    public List<Song> getSongsPagination(@PathVariable String topicType, 
                                         @RequestParam int page, @RequestParam int size){
        Pageable sortedByPosition =
                PageRequest.of(page, size, Sort.by("position"));
        return repo.findAllByCategory(topicType,sortedByPosition);
    }
}
