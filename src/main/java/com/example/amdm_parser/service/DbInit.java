package com.example.amdm_parser.service;

import com.example.amdm_parser.repository.SongsTopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class DbInit {
    private final TopicUpdaterService updaterService;
    private final SongsTopicRepository repository;

    public DbInit(TopicUpdaterService updaterService, SongsTopicRepository repository) {
        this.updaterService = updaterService;
        this.repository = repository;
    }

    @PostConstruct
    private void initDatabase() {
        if(repository.count() == 0){
            log.info("database is empty, starting parsing topics to database...");
            updaterService.saveAllTopicsToRepository();
            log.info(String.format("parsed topics successfully, database size is %s", repository.count()));
        }
    }
}
