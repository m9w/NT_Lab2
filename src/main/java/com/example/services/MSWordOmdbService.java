package com.example.services;

import com.example.pojo.PojoOmdb;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class MSWordOmdbService extends MSWordAbstract {
    Logger logger = LoggerFactory.getLogger(MSWordOmdbService.class);

    @Override
    public byte[] generate(List<PojoOmdb> movieOMDBS){
        XWPFDocument document = new XWPFDocument();
        for (PojoOmdb pojoOmdb: movieOMDBS) switch (pojoOmdb.getSearchType()) {
            case SEARCH_BY_ID_TYPE: addMovie(document, pojoOmdb); break;
            case SEARCH_BY_TITLE_TYPE: addSearchedList(document, pojoOmdb); break;
        }
        try {
            return close(document);
        } catch (IOException e) {
            logger.error("drop word generator", e);
        }
        return null;
    }

    private void addSearchedList(XWPFDocument doc, PojoOmdb pojo) {
        addTitle(doc, "Search query - " + pojo.getQuery());
        if(pojo.getMovie() != null) for (PojoOmdb pojoMovie: pojo.getMovie()) addDetails(doc, pojoMovie);
        if(pojo.getError() != null) addText(doc, "Error: " + pojo.getError());
        breakPage(doc);
    }

    private void addMovie(XWPFDocument document, PojoOmdb pojo) {
        addTitle(document, "Query - " + pojo.getQuery());
        addDetails(document, pojo);
        if(pojo.getError() != null) addText(document, "Error: " + pojo.getError());
        breakPage(document);
    }

    private void addDetails(XWPFDocument doc, PojoOmdb pojo) {
        if(pojo.getPoster() != null) addImage(doc, pojo.getPoster());
        if(pojo.getTitle() != null) addTitle(doc, pojo.getTitle());
        if(pojo.getYear() != null) addText(doc, "Year: " + pojo.getYear());
        if(pojo.getRated() != null) addText(doc, "Rated: " + pojo.getRated());
        if(pojo.getReleased() != null) addText(doc, "Released: " + pojo.getReleased());
        if(pojo.getRuntime() != null) addText(doc, "Runtime: " + pojo.getRuntime());
        if(pojo.getGenre() != null) addText(doc, "Genre: " + pojo.getGenre());
        if(pojo.getDirector() != null) addText(doc, "Director: " + pojo.getDirector());
        if(pojo.getWriter() != null) addText(doc, "Writer: " + pojo.getWriter());
        if(pojo.getActors() != null) addText(doc, "Actors: " + pojo.getActors());
        if(pojo.getPlot() != null) addText(doc, "Plot: " + pojo.getPlot());
        if(pojo.getLanguage() != null) addText(doc, "Language: " + pojo.getLanguage());
        if(pojo.getCountry() != null) addText(doc, "Country: " + pojo.getCountry());
        if(pojo.getAwards() != null) addText(doc, "Awards: " + pojo.getAwards());
        if(pojo.getMetascore() != null) addText(doc, "Metascore: " + pojo.getMetascore());
        if(pojo.getImdbRating() != null) addText(doc, "ImdbRating: " + pojo.getImdbRating());
        if(pojo.getImdbVotes() != null) addText(doc, "ImdbRating: " + pojo.getImdbRating());
        if(pojo.getImdbID() != null) addText(doc, "ImdbID: " + pojo.getImdbID());
        if(pojo.getType() != null) addText(doc, "Type: " + pojo.getType());
        if(pojo.getDVD() != null) addText(doc, "DVD: " + pojo.getDVD());
        if(pojo.getBoxOffice() != null)  addText(doc, "BoxOffice: " + pojo.getBoxOffice());
        if(pojo.getProduction() != null) addText(doc, "Production: " + pojo.getProduction());
        if(pojo.getWebsite() != null) addText(doc, "Website: " + pojo.getWebsite());
        if(pojo.getRatings() != null) {
            addText(doc, "Ratings:");
            pojo.getRatings().forEach(raiting -> addText(doc,  "• " + raiting.getSource() + ": " + raiting.getValue()));
        }
    }
}
