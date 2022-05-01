package com.midway.starwarsapi.service.starwarsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midway.starwarsapi.dto.starwars.CharacterDto;
import com.midway.starwarsapi.dto.starwars.PlanetDto;
import com.midway.starwarsapi.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StarwarsPlanetRestService extends StarwarsRestService<PlanetDto> {

    @Override
    public void fillDetails(PlanetDto entity) {

    }
}
