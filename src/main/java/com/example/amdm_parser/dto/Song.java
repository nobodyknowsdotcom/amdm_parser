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
    @Column(name = "id", unique = true)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "artist")
    private String artist;
    @Column(name = "url")
    private String url;
    @Column(name = "category")
    private String category;
    @Column(name = "position")
    private int position;
}
