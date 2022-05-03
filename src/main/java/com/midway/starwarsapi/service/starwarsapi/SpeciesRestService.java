package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.PeopleDto;
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
        // TODO - Species(Dto+Service) and People(Dto+Service) could inherit from LivingBeing(Dto+Service)
        int homeWorldId = Util.getNumberFromUrl(entity.getHomeWorldUrl());
        entity.setHomeWorldDetail(planetRestService.getEntity(homeWorldId));
    }
}
