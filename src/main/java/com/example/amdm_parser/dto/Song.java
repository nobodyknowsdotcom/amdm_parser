package com.example.amdm_parser.dto;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "songs")
public class Song {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "artist", nullable = false)
    private String artist;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "position", nullable = false)
    private int position;
}
