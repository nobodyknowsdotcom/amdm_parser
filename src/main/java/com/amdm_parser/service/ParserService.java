package com.amdm_parser.service;

import com.amdm_parser.dto.Song;
import com.amdm_parser.utils.TopicCategories;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Скачивает html страницы с топами песен с amdm.ru и парсит в List<Song>
 */
@Slf4j
@Service
public class ParserService {
    @Value("${parser.songsOnPage}")
    private int pageSize;


    public List<Song> getSongsByCategory(TopicCategories category){
        ArrayList<Song> songs = new ArrayList<>();
        Document firstPage = getPage(category.getUrl());
        int pagesCount = getPagesCount(firstPage);

        for (int i = 0; i < pagesCount; i++) {
            Document page = getPage(category.getUrl()+String.format("/page%s", i+1));
            Element songsContainer = getSongsContainer(page);
            if (songsContainer != null){
                List<Song> songList = getSongsListFromContainer(songsContainer, category, i);
                songs.addAll(songList);
            }
        }
        numerateSongs(songs);

        log.info(String.format("Got %s songs by %s, pages count: %s", songs.size(),
                category.getUrl(), pagesCount));
        return songs;
    }
    private Document getPage(String url){
        Document page = new Document(url);
        try {
            page = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .timeout(2*1000).get();
            log.info(String.format("Getting %s...", url));
            return page;
        } catch (IOException e) {
            log.error(String.format("Can't get %s", url));
            e.printStackTrace();
        }
        return page;
    }
    private void numerateSongs(List<Song> songList){
        for (int i = 0; i<songList.size(); i++){
            songList.get(i).setPosition(i+1);
        }
    }
    private int getPagesCount(Document page){
        int pagesCount = 1;
        try{
            Element pagination = page.select("ul.nav-pages").first();
            pagesCount = pagination.select("li").size();
        }
        catch (NullPointerException ignored){
            log.error("Error processing pages count");
            ignored.printStackTrace();
        }
        return pagesCount;
    }
    private Element getSongsContainer(Document page){
        return page.select("table.items").first();
    }
    private ArrayList<Song> getSongsListFromContainer(Element parentContainer, TopicCategories category, int page){
        ArrayList<Song> result = new ArrayList<>();
        Elements songElements = parentContainer.select("td.artist_name");

        for (int i = 0; i < songElements.size(); i++) {
            Element songElement = songElements.get(i);
            try {
                Song parsedSong = parseElementIntoSong(songElement, (pageSize*page)+i, category);
                result.add(parsedSong);
            } catch (Selector.SelectorParseException e) {
                log.error("Can't parse song!");
                e.printStackTrace();
            }
        }
        return result;
    }
    private Song parseElementIntoSong(Element songElement, int index, TopicCategories category){
        Elements songElements = songElement.select("a.artist");
        String name = songElements.get(1).text();
        String artist = songElements.get(0).text();
        String url = songElements.get(1).attr("href");
        long id = Math.abs(url.hashCode() + category.name().hashCode());
        return new Song(id, name, artist, url, category.name().toLowerCase(Locale.ROOT), index + 1);
    }
}
