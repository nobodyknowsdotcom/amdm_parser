package com.amdm_parser.service;

import com.amdm_parser.dto.Song;
import com.amdm_parser.dto.TopicPage;
import com.amdm_parser.repository.SongsRepository;
import com.amdm_parser.utils.SortCategories;
import com.amdm_parser.utils.TopicCategories;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Принимает категорию, размер страницы, номер страницы и категорию сортировки и возвращает объект TopicPage, содержащий
 * список песен, и логическое значение, указывающее, есть ли следующая страница.
 */
@Service
public class PaginationService {
    private final SongsRepository repository;

    public PaginationService(SongsRepository repository) {
        this.repository = repository;
    }
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
