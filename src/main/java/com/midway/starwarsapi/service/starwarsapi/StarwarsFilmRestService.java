package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.PeopleDto;
import com.midway.starwarsapi.dto.starwars.FilmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StarwarsFilmRestService extends StarwarsRestService<FilmDto>{
    @Autowired
    StarwarsPeopleRestService starwarsPeopleRestService;

    @Override
    public void fillDetails(FilmDto entity) {
        entity.setCharacterList(starwarsPeopleRestService.fetchOneByOne(new PeopleDto(),
                entity.getCharacterUrlList()));


        int z = 0;
    }


}
