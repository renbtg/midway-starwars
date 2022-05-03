package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.PlanetDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PlanetRestService extends RestService<PlanetDto> {

    @Cacheable(value="planets-rest")
    /*@Caching(
            put= { @CachePut(value="planets-rest", key="#id", condition="#cachedData==false") },
            cacheable = { @Cacheable(value ="planets-rest", key="#id", condition="#cachedData==true") }
    ) WOULD REQUIRE EXTRA PARAMETER - does @Caching adds to cache if not present? IT SHOULD! GUESS IT DOES!*/
    @Override
    public PlanetDto getEntity(int id) {

        return obtainEntity(new PlanetDto(id));
    }

    @Override
    public void fillDetails(PlanetDto entity) {

    }
}
