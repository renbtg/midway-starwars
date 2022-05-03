package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.PlanetDto;
import com.midway.starwarsapi.dto.starwars.VehicleDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class VehicleRestService extends RestService<VehicleDto> {

    @Cacheable(value="vehicles-rest")
    /*@Caching(
            put= { @CachePut(value="planets-rest", key="#id", condition="#cachedData==false") },
            cacheable = { @Cacheable(value ="planets-rest", key="#id", condition="#cachedData==true") }
    ) WOULD REQUIRE EXTRA PARAMETER - does @Caching adds to cache if not present? IT SHOULD! GUESS IT DOES!*/
    @Override
    public VehicleDto getEntity(int id) {

        return obtainEntity(new VehicleDto(id));
    }

    @Override
    public void fillDetails(VehicleDto entity) {

    }
}
