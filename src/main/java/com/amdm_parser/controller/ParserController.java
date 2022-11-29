package com.amdm_parser.controller;

import com.amdm_parser.dto.TopicPage;
import com.amdm_parser.service.PaginationService;
import com.amdm_parser.utils.SortCategories;
import com.amdm_parser.utils.TopicCategories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParserController {
    final
    PaginationService paginationService;

    public ParserController(PaginationService paginationService) {
        this.paginationService = paginationService;
    }

    @GetMapping("/{topicCategory}")
    public TopicPage getSongsPagination(@PathVariable TopicCategories topicCategory, @RequestParam int page,
                                        @RequestParam int size, @RequestParam SortCategories sort){
        return paginationService.getTopicPage(topicCategory, size, page, sort);
    }
}
