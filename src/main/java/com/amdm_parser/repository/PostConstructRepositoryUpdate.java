package com.amdm_parser.repository;

import com.amdm_parser.service.RepositoryRefresherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class PostConstructRepositoryUpdate {
    private final RepositoryRefresherService repositoryRefresherService;
    private final SongsTopicRepository repository;

    public PostConstructRepositoryUpdate(RepositoryRefresherService repositoryRefresherService, SongsTopicRepository repository) {
        this.repositoryRefresherService = repositoryRefresherService;
        this.repository = repository;
    }

   @PostConstruct
    private void initDatabase() {
        if(repository.count() == 0){
            log.info("database is empty, starting parsing topics to database...");
            repositoryRefresherService.saveAllTopicsToRepository();
            log.info(String.format("parsed topics successfully, database size is %s", repository.count()));
        }
    }
}
