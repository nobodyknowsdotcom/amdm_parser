package com.example.amdm_parser.controller;

import com.example.amdm_parser.dto.Song;
import com.example.amdm_parser.repository.SongsTopicRepository;
import com.example.amdm_parser.service.SongTopicSaver;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ParserController {
    private final SongsTopicRepository repo;
    private final SongTopicSaver topicSaverService;

    public ParserController(SongsTopicRepository repo, SongTopicSaver topicSaverService) {
        this.repo = repo;
        this.topicSaverService = topicSaverService;
    }

    @PostMapping("/updateRepository")
    public String updateRepository(){
        topicSaverService.saveAllTopicsToRepository();
        return "Ok!";
    }

    @GetMapping("/{topicType}")
    public ArrayList<Song> getSongsPagination(@PathVariable String topicType,
                                              @RequestParam int page, @RequestParam int size){
        Pageable sortedByPosition =
                PageRequest.of(page, size, Sort.by("position"));
        return repo.findAllByCategory(topicType,sortedByPosition);
    }
}
