package com.amdm_parser.service;

import com.amdm_parser.dto.Song;
import com.amdm_parser.dto.TopicPage;
import com.amdm_parser.repository.SongsRepository;
import com.amdm_parser.utils.TopicCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {
    private final SongsRepository repository;

    public PaginationService(SongsRepository repository) {
        this.repository = repository;
    }
    public TopicPage getTopicPage(TopicCategories category, Pageable pageable){

        String categoryName = category.getName();
        Page<Song> songPage = repository.findAllByCategory(categoryName, pageable);
        return new TopicPage(songPage.getContent(), songPage.isLast(), songPage.isFirst());
    }
}
