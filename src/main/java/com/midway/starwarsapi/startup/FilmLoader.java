package com.midway.starwarsapi.startup;

import com.midway.starwarsapi.dto.starwars.FilmDto;
import com.midway.starwarsapi.dto.starwars.FilmResultSet;
import com.midway.starwarsapi.service.starwarsapi.StarwarsFilmRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class FilmLoader {

    @Autowired private StarwarsFilmRestService starwarsFilmRestService;

    @PostConstruct
    public void populateRedisCache() {
        List<FilmDto> films = starwarsFilmRestService.getList(new FilmResultSet(), new FilmDto());
        // @PutSomething
        int i = 0;
    }
}
