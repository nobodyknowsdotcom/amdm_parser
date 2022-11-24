package com.example.amdm_parser.service;

import com.example.amdm_parser.dto.Song;
import com.example.amdm_parser.utils.TopicCategories;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Service
public class AmDmParser {
    public ArrayList<Song> getSongsByCategory(TopicCategories category){
        ArrayList<Song> songsList = new ArrayList<>();
        Document firstPage = getPage(category.getUrl());
        int pagesCount = getPagesCount(firstPage);

        for (int i = 0; i < pagesCount; i++) {
            Document page = getPage(category.getUrl()+String.format("/page%s", i+1));
            Element songsContainer = getSongsContainer(page);
            songsList.addAll(getSongsListFromContainer(songsContainer));
        }

        log.info(String.format("Got %s songs by %s, pages count: %s", songsList.size(),
                category.getUrl(), pagesCount));
        return songsList;
    }

    private Document getPage(String url){
        Document page = new Document(url);
        try {
            page = Jsoup.connect(url).timeout(2*1000).get();
            log.info(String.format("Getting %s...", url));
            return page;
        } catch (IOException e) {
            log.error(String.format("Can't get %s", url));
        }
        return page;
    }

    private int getPagesCount(Document page){
        try{
            Element pagination = page.select("ul.nav-pages").first();
            return pagination.select("li").size();
        }
        catch (Selector.SelectorParseException e){
            return 1;
        }
    }

    private Element getSongsContainer(Document page){
        log.info("Exacting parent container...");
        return page.select("table.items").first();
    }

    private ArrayList<Song> getSongsListFromContainer(Element parentContainer){
        ArrayList<Song> result = new ArrayList<>();
        Elements songElements = parentContainer.select("td.artist_name");
        log.info("Exacting songs from parent container...");

        for(int i=0; i<songElements.size(); i++){
            try {
                Song parsedSong = parseElementIntoSong(songElements.get(i), i);
                result.add(parsedSong);
            }
            catch (Selector.SelectorParseException e){
                log.error("Can't parse song!");
                e.printStackTrace();
            }
        }
        return result;
    }

    private Song parseElementIntoSong(Element songElement, int index){
        String name = songElement.select("a.artist").get(1).text();
        String artist = songElement.select("a.artist").get(0).text();
        String url = songElement.select("a.artist").get(1).attr("href");
        return new Song(name, artist, url, index + 1);
    }
}
