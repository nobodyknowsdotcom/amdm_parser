package com.amdm_parser.controller;

import com.amdm_parser.dto.TopicPage;
import com.amdm_parser.service.TopicPageFactory;
import com.amdm_parser.utils.SortCategories;
import com.amdm_parser.utils.TopicCategories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaginationController {
    final TopicPageFactory topicPageFactory;

    public PaginationController(TopicPageFactory topicPageFactory) {
        this.topicPageFactory = topicPageFactory;
    }

    @GetMapping("/{topicCategory}")
    public TopicPage getSongsPagination(@PathVariable TopicCategories topicCategory, @RequestParam int page,
                                        @RequestParam int size, @RequestParam SortCategories sort){
        log.info(String.format("Got request /%s?page=%s&size=%s&sort=%s", topicCategory, page, size, sort));
        return topicPageFactory.getTopicPage(topicCategory, size, page, sort);
    }
}
