package com.amdm_parser.service;

import com.amdm_parser.dto.RepositoryMetrics;
import com.amdm_parser.repository.SongsRepository;
import com.amdm_parser.utils.TopicCategories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RepositoryMetricsService {
    private final SongsRepository songsRepository;

    public RepositoryMetricsService(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    public RepositoryMetrics getRepositoryMetrics(){
        Map<TopicCategories, Integer> categorySizes = new HashMap<>();
        int repositorySize = 0;

        for (TopicCategories category : TopicCategories.values()){
            int categorySize = songsRepository.countByCategory(category.getName());

            repositorySize += categorySize;
            categorySizes.put(category, categorySize);
        }
        return new RepositoryMetrics(repositorySize, categorySizes);
    }
}
