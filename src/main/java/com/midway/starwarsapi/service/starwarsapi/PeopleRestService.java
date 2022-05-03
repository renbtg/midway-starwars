package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.PeopleDto;
import com.midway.starwarsapi.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PeopleRestService extends RestService<PeopleDto> {
    @Autowired
    PlanetRestService planetRestService;


    @Cacheable(value="people-rest")
    @Override
    public PeopleDto getEntity(int id) {
        return obtainEntity(new PeopleDto(id));
    }

    @Override
    public void fillDetails(PeopleDto entity) {
        if (entity.getHomeWorldUrl() != null) {
            int homeWorldId = Util.getNumberFromUrl(entity.getHomeWorldUrl());
            entity.setHomeWorldObject(planetRestService.getEntity(homeWorldId));
        }
    }
}
