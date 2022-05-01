package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.PeopleDto;
import com.midway.starwarsapi.dto.starwars.FilmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StarwarsFilmRestService extends StarwarsRestService<FilmDto>{
    @Autowired
    StarwarsPeopleRestService starwarsPeopleRestService;

    @Cacheable("films-rest")
    @Override
    public FilmDto getEntity(int id) {
        return obtainEntity(new FilmDto(id));
    }

    @Override
    public void fillDetails(FilmDto entity) {
        entity.setCharacterList(starwarsPeopleRestService.fetchOneByOne(entity.getCharacterUrlList()));
    }


}
