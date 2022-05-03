package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.SpeciesDto;
import com.midway.starwarsapi.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SpeciesRestService extends RestService<SpeciesDto> {
    @Autowired
    PlanetRestService planetRestService;


    @Cacheable(value="species-rest")
    @Override
    public SpeciesDto getEntity(int id) {
        return obtainEntity(new SpeciesDto(id));
    }

    @Override
    public void fillDetails(SpeciesDto entity) {
        if (entity.getHomeWorldUrl() != null) {
            int homeWorldId = Util.getNumberFromUrl(entity.getHomeWorldUrl());
            entity.setHomeWorldDto(planetRestService.getEntity(homeWorldId));
        }
    }
}
