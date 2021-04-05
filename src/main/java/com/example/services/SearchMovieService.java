package com.example.services;

import com.example.pojo.PojoOmdb;

public interface SearchMovieService {
    /**
     * @param id get film using unique ID
     * @return PojoOmdb or null if service unavailable
     */
    PojoOmdb getById(String id);

    /**
     * @param title find all film using part title
     * @return PojoOmdb or null if service unavailable
     */
    PojoOmdb getByTitle(String title);
}
