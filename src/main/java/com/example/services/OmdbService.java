package com.example.services;

import com.example.pojo.PojoOmdb;
import com.example.tools.HTTPLoaderImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class OmdbService implements SearchMovieService {
    private @Value("${app.apiKey}") String apiKey;
    private ObjectMapper mapper = new ObjectMapper();
    private static final String OMDB_URL = "http://omdbapi.com/";

    private PojoOmdb query(String query, PojoOmdb.SearchType searchType){
        try (HTTPLoaderImpl httpLoader = new HTTPLoaderImpl(OMDB_URL)){
            httpLoader.addParam("apikey", apiKey).addParam(searchType.flag, query);
            return mapper.readValue(httpLoader.HTTPGet(), PojoOmdb.class).setQuery(query).setSearchType(searchType);
        } catch (IOException ignored) {
            return null;
        }
    }

    @Override
    public PojoOmdb getById(String id){
        return query(id, PojoOmdb.SearchType.SEARCH_BY_ID_TYPE);
    }

    @Override
    public PojoOmdb getByTitle(String title){
        return query(title, PojoOmdb.SearchType.SEARCH_BY_TITLE_TYPE);
    }
}
