package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.PlanetDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StarwarsPlanetRestService extends StarwarsRestService<PlanetDto> {

    @Cacheable("planets-rest")
    @Override
    public PlanetDto getEntity(int id) {
        return obtainEntity(new PlanetDto(id));
    }

    @Override
    public void fillDetails(PlanetDto entity) {

    }
}
