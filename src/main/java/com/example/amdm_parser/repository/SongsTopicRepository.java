package com.example.amdm_parser.repository;

import com.example.amdm_parser.dto.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongsTopicRepository extends CrudRepository<Song, Long> {
    List<Song> findAllByCategory(String category, Pageable pageable);
    List<Song> findAllByCategory(String category);
}
