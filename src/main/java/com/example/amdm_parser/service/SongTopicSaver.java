package com.example.amdm_parser.service;

import com.example.amdm_parser.dto.Song;
import com.example.amdm_parser.repository.SongsTopicRepository;
import com.example.amdm_parser.utils.TopicCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SongTopicSaver {
    final
    SongsTopicRepository songsRepository;

    public SongTopicSaver(SongsTopicRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    public void saveAllTopics(){
        for (TopicCategories category : TopicCategories.values()){

        }
    }

    public void saveSongsTopic(ArrayList<Song> songsList){
        try{
            songsRepository.saveAll(songsList);
        } catch (Exception ignored){

        }
    }
}
