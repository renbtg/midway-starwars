package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.StarshipDto;
import com.midway.starwarsapi.dto.starwars.VehicleDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StarshipRestService extends RestService<StarshipDto> {

    @Cacheable(value="starships-rest")
    /*@Caching(
            put= { @CachePut(value="planets-rest", key="#id", condition="#cachedData==false") },
            cacheable = { @Cacheable(value ="planets-rest", key="#id", condition="#cachedData==true") }
    ) WOULD REQUIRE EXTRA PARAMETER - does @Caching adds to cache if not present? IT SHOULD! GUESS IT DOES!*/
    @Override
    public StarshipDto getEntity(int id) {

        return obtainEntity(new StarshipDto(id));
    }

    @Override
    public void fillDetails(StarshipDto entity) {

    }
}
