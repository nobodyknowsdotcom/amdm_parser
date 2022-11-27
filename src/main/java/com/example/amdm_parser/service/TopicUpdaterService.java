package com.example.amdm_parser.service;

import com.example.amdm_parser.dto.Song;
import com.example.amdm_parser.repository.SongsTopicRepository;
import com.example.amdm_parser.utils.TopicCategories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
public class TopicUpdaterService {
    final SongsTopicRepository songsRepository;
    final ParserService parserService;

    public TopicUpdaterService(SongsTopicRepository songsRepository, ParserService parserService) {
        this.songsRepository = songsRepository;
        this.parserService = parserService;
    }
    @Scheduled(cron = "${parser.schedulerConfig}", zone = "GMT+5")
    @Transactional
    public void saveAllTopicsToRepository(){
        for (TopicCategories category : TopicCategories.values()){
            ArrayList<Song> songsTopic = parserService.getSongsByCategory(category);


            if (!songsTopic.isEmpty()){
                songsRepository.deleteAllByCategory(category.name().toLowerCase());
                try{
                    songsRepository.saveAll(songsTopic);
                }
                catch (Exception e){
                    log.error(String.format("can't save %s category", category.name().toLowerCase()));
                    e.printStackTrace();
                }
            }
        }
    }
}
