package com.amdm_parser.repository;

import com.amdm_parser.service.RepositoryRefresherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class RepositoryInitializer {
    private final RepositoryRefresherService updaterService;
    private final SongsRepository repository;

    public RepositoryInitializer(RepositoryRefresherService updaterService, SongsRepository repository) {
        this.updaterService = updaterService;
        this.repository = repository;
    }

   @PostConstruct
    private void initDatabase() {
        if(repository.count() == 0){
            log.info("Database is empty, starting parsing topics to database...");
            updaterService.saveAllTopicsToRepository();
            log.info(String.format("Got all songs successfully, database size is %s", repository.count()));
        }
    }
}
