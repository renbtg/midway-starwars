package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.PeopleDto;
import com.midway.starwarsapi.dto.starwars.PlanetDto;
import com.midway.starwarsapi.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarwarsPeopleRestService extends StarwarsRestService<PeopleDto> {
    @Autowired
    StarwarsPlanetRestService starwarsPlanetRestService;


    @Override
    public void fillDetails(PeopleDto entity) {
        int homeWorldId = Util.getNumberFromUrl(entity.getHomeWorldUrl());
        entity.setHomeWorldDetail(starwarsPlanetRestService.getEntity(new PlanetDto(homeWorldId)));
    }
}
