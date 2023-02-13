package com.amdm_parser.controller;

import com.amdm_parser.dto.RepositoryMetrics;
import com.amdm_parser.dto.TopicPage;
import com.amdm_parser.service.PaginationService;
import com.amdm_parser.service.RepositoryMetricsService;
import com.amdm_parser.utils.TopicCategories;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер, который обрабатывает запросы на данные с разбивкой на страницы и метрики репозитория.
 */
@RestController
@Slf4j
public class ParserController {
    final PaginationService paginationService;
    final RepositoryMetricsService repositoryMetricsService;

    public ParserController(PaginationService paginationService, RepositoryMetricsService repositoryMetricsService) {
        this.paginationService = paginationService;
        this.repositoryMetricsService = repositoryMetricsService;
    }

    @GetMapping("/{topicCategory}")
    public TopicPage getSongsPagination(@PathVariable TopicCategories topicCategory, @NotNull Pageable pageable){
        log.info(String.format("Category %s, pageable %s", topicCategory.name(), pageable.toString()));
        return paginationService.getTopicPage(topicCategory, pageable);
    }

    @GetMapping(value = "/metrics")
    public RepositoryMetrics getRepositoryMetrics(){
        log.info("Processing repository metrics request...");
        return repositoryMetricsService.getRepositoryMetrics();
    }
}
