package com.example.amdm_parser.controller;

import com.example.amdm_parser.dto.Song;
import com.example.amdm_parser.repository.SongsTopicRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParserController {
    private final SongsTopicRepository repository;

    public ParserController(SongsTopicRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{topicType}")
    public List<Song> getSongsPagination(@PathVariable String topicType,
                                         @RequestParam int page, @RequestParam int size){
        Pageable sortedByPosition =
                PageRequest.of(page, size, Sort.by("position"));
        return repository.findAllByCategory(topicType,sortedByPosition);
    }
}
