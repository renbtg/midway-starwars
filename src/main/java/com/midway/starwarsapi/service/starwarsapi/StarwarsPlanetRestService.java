package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.PlanetDto;
import org.springframework.stereotype.Service;

@Service
public class StarwarsPlanetRestService extends StarwarsRestService<PlanetDto> {

    @Override
    public void fillDetails(PlanetDto entity) {

    }
}
