package com.amdm_parser.service;

import com.amdm_parser.dto.Song;
import com.amdm_parser.dto.TopicPage;
import com.amdm_parser.repository.SongsTopicRepository;
import com.amdm_parser.utils.SortCategories;
import com.amdm_parser.utils.TopicCategories;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationService {
    private final SongsTopicRepository repository;

    public PaginationService(SongsTopicRepository repository) {
        this.repository = repository;
    }
    /*
    Вытаскивает из БД список песен и формирует TopicPage
    Если переданы некорректные параметры, кидает 502 ошибку.
     */
    public TopicPage getTopicPage(TopicCategories category, int size, int page, SortCategories sortCategories){
        String categoryName = category.getName();
        Pageable pageQuery = PageRequest.of(page, size, Sort.by(sortCategories.getName()));
        List<Song> songsList = repository.findAllByCategory(categoryName, pageQuery);
        boolean hasNext = hasNextPage(categoryName, size, page);

        return new TopicPage(songsList, pageQuery.hasPrevious(), hasNext);
    }

    private boolean hasNextPage(String category, int size, int currentPage){
        return (repository.findAllByCategory(category).size() - size * (currentPage+1)) > 0;
    }
}
