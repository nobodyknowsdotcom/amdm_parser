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

@Slf4j
@Service
public class ParserService {
    @Value("${parser.songsOnPage}")
    private int songsOnPage;

    /*
    Принимает категорию и отдает список песен со всех страниц каталога
    */
    public List<Song> getSongsByCategory(TopicCategories category){
        ArrayList<Song> result = new ArrayList<>();
        Document firstPage = getPage(category.getUrl());
        int pagesCount = getPagesCount(firstPage);
        /*
        Бежим по всем страницам, парсим их и складываем песни в songList
        */
        for (int i = 0; i < pagesCount; i++) {
            Document page = getPage(category.getUrl()+String.format("/page%s", i+1));
            Element songsContainer = getSongsContainer(page);
            result.addAll(getSongsListFromContainer(songsContainer, i, category));
        }

        log.info(String.format("Got %s songs by %s, pages count: %s", result.size(),
                category.getUrl(), pagesCount));
        return result;
    }

    /*
    * Скачивает html страницу по указанной ссылке и отдает как Document
    */
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
        int pagesCount = 1;
        try{
            Element pagination = page.select("ul.nav-pages").first();
            pagesCount = pagination.select("li").size();
        }
        catch (NullPointerException ignored){/*Если блок пагинации не найден, значит в каталоге всего одна страница
        В таком случае игнорируем NullPointerException и возвращаем 1*/}
        return pagesCount;
    }
    /*
    * Вытаскивает элемент-родитель, в котором лежит весь список песен
    */
    private Element getSongsContainer(Document page){
        return page.select("table.items").first();
    }
    /*
    * Вытаскивает из элемента-родителя весь список песен и возвращает как ArrayList
    */
    private ArrayList<Song> getSongsListFromContainer(Element parentContainer, int page, TopicCategories category){
        ArrayList<Song> result = new ArrayList<>();
        Elements songElements = parentContainer.select("td.artist_name");

        for(int i=0; i<songElements.size(); i++){
            try { // Передаем в метод Element (будущий Song), место в списке и категорию
                int songIndex = (page*songsOnPage)+i;
                Song parsedSong = parseElementIntoSong(songElements.get(i), songIndex, category);
                result.add(parsedSong);
            }
            catch (Selector.SelectorParseException e){
                log.error("Can't parse song!");
                e.printStackTrace();
            }
        }
        return result;
    }
    /*
    * Преобразует элемент из родительского элемента с песнями в экземпляр класса Song
    */
    private Song parseElementIntoSong(Element songElement, int index, TopicCategories category){
        Elements songElements = songElement.select("a.artist");
        String name = songElements.get(1).text();
        String artist = songElements.get(0).text();
        String url = songElements.get(1).attr("href");
        long id = Math.abs(url.hashCode() + category.name().hashCode());
        return new Song(id, name, artist, url, category.name().toLowerCase(Locale.ROOT), index + 1);
    }
}
