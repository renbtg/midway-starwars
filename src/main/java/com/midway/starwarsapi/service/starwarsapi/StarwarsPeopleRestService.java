package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.PeopleDto;
import com.midway.starwarsapi.dto.starwars.PlanetDto;
import com.midway.starwarsapi.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StarwarsPeopleRestService extends StarwarsRestService<PeopleDto> {
    @Autowired
    StarwarsPlanetRestService starwarsPlanetRestService;


    @Cacheable(value="people-rest")
    @Override
    public PeopleDto getEntity(int id) {
        return obtainEntity(new PeopleDto(id));
    }

    @Override
    public void fillDetails(PeopleDto entity) {
        int homeWorldId = Util.getNumberFromUrl(entity.getHomeWorldUrl());
        entity.setHomeWorldDetail(starwarsPlanetRestService.getEntity(homeWorldId));
    }
}
