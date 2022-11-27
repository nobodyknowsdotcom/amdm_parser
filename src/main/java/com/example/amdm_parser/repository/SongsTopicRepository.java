package com.example.amdm_parser.repository;

import com.example.amdm_parser.dto.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SongsTopicRepository extends CrudRepository<Song, Long> {
    ArrayList<Song> findAllByCategory(String category, Pageable pageable);
    ArrayList<Song> findAllByCategory(String category);
    void deleteAllByCategory(String category);
}
