package com.example.amdm_parser.service;

import com.example.amdm_parser.model.Song;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Service
public class AmDmParser {
    @Cacheable(value = "Songs", key = "#url")
    public ArrayList<Song> getSongsList(String url){
        Document page = getPage(url);
        Element songsContainer = getSongsContainer(page);
        return getSongsListFromContainer(songsContainer);
    }

    private Document getPage(String url){
        Document page = new Document(url);
        try {
            page = Jsoup.connect(url).timeout(5*1000).get();
            log.info(String.format("Getting %s...", url));
            return page;
        } catch (IOException e) {
            log.error(String.format("Can't get %s", url));
        }
        return page;
    }

    private Element getSongsContainer(Document page){
        log.info("Exacting parent container...");
        return page.select("table.items").first();
    }

    private ArrayList<Song> getSongsListFromContainer(Element container){
        ArrayList<Song> result = new ArrayList<>();
        Elements songElements = container.select("td.artist_name");
        log.info("Exacting songs from parent container...");

        for(Element songElement : songElements){
            try {
                result.add(parseElementIntoSong(songElement));
            }
            catch (Exception e){
                log.error("Can't parse song!");
            }
        }
        return result;
    }

    private Song parseElementIntoSong(Element songElement){
        String name = songElement.select("a.artist").get(1).text();
        String artist = songElement.select("a.artist").get(0).text();
        String url = songElement.select("a.artist").get(1).attr("href");
        return new Song(name, artist, url);
    }
}
