package com.amdm_parser.repository;

import com.amdm_parser.dto.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongsRepository extends CrudRepository<Song, Long> {
    Page<Song> findAllByCategory(String category, Pageable pageable);
    Integer countByCategory(String category);
    void deleteAllByCategory(String category);
}
