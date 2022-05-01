package com.midway.starwarsapi.flyway;

import com.midway.starwarsapi.service.starwarsapi.StarwarsFilmRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FilmLoader {

    @Autowired private StarwarsFilmRestService starwarsFilmRestService;

    @PostConstruct
    public void populateRedisCache() {
        var films = starwarsFilmRestService.getFilmResultSet();
        // @PutSomething
        int i = 0;
    }
}
