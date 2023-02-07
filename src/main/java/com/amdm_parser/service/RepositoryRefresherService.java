package com.amdm_parser.service;

import com.amdm_parser.dto.Song;
import com.amdm_parser.repository.SongsRepository;
import com.amdm_parser.utils.TopicCategories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

/**
 * Запланированная служба, которая сохраняет все песни из всех категорий в базу данных.
 */
@Slf4j
@Service
public class RepositoryRefresherService {
    final SongsRepository songsRepository;
    final ParserService parserService;

    public RepositoryRefresherService(SongsRepository songsRepository, ParserService parserService) {
        this.songsRepository = songsRepository;
        this.parserService = parserService;
    }
    @Scheduled(cron = "${parser.schedulerConfig}", zone = "GMT+5")
    @Transactional
    public void saveAllTopicsToRepository(){
        for (TopicCategories category : TopicCategories.values()){
            List<Song> songsTopic = parserService.getSongsByCategory(category);

            if (!songsTopic.isEmpty()){
                try{
                    songsRepository.deleteAllByCategory(category.name().toLowerCase());
                    log.info(String.format("Purged %s category", category.name()));
                } catch (Exception e){
                    log.error(String.format("Cannot delete %s category", category.name()));
                    e.printStackTrace();
                }
                try{
                    songsRepository.saveAll(songsTopic);
                    log.info(String.format("Saved %s category, category count: %s", category.name(), songsTopic.size()));
                }
                catch (Exception e){
                    log.error(String.format("Cannot save %s category", category.name().toLowerCase()));
                    e.printStackTrace();
                }
            }
        }
    }
}
