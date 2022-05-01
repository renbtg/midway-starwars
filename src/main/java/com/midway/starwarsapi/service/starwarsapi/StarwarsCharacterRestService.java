package com.midway.starwarsapi.service.starwarsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midway.starwarsapi.dto.starwars.CharacterDto;
import com.midway.starwarsapi.dto.starwars.PlanetDto;
import com.midway.starwarsapi.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StarwarsCharacterRestService extends StarwarsRestService<CharacterDto> {
    @Value("${api.starwars.url.root}")
    String starwarsApiRootUrl; // TODO - make StarwarsRestService abstract base class? No need, for now?

    @Autowired
    StarwarsPlanetRestService starwarsPlanetRestService;


    @Override
    public void fillDetails(CharacterDto entity) {
        int homeWorldId = Util.getIdFromUrl(entity.getHomeWorldUrl());
        entity.setHomeWorldDetail(starwarsPlanetRestService.getEntity(new PlanetDto(homeWorldId)));
    }
}
