package com.midway.starwarsapi.service.starwarsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.midway.starwarsapi.dto.starwars.CharacterDto;
import com.midway.starwarsapi.dto.starwars.FilmDto;
import com.midway.starwarsapi.dto.starwars.FilmResultSet;
import com.midway.starwarsapi.util.Util;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StarwarsFilmRestService extends StarwarsRestService<FilmDto>{
    @Autowired
    StarwarsCharacterRestService starwarsCharacterRestService;

    @Value("${api.starwars.url.root}")
    String starwarsApiRootUrl;

    @Override
    public void fillDetails(FilmDto entity) {
        /*
        TODO - caches (varios), um para cada entidade: Film, Vehicle, Character etc.

        CACHE REDIS não é complicacao desnecessaria?
            RESPOSTA: NAO È! PRECISAMOS! Blz, ptestar acesso

            TODO - primeiro, testar acesso a cada entidade individual, se pega bem os campos,
            e depois reorganizar pra ter um Service para cada entidade e um cache para cada
            service

            ASSIM, se quisermos que filme FOO retorne character BAR, checamos no cache
            o BAR, em vez de acessar de novo a API
         */

        entity.setCharacterDtoList(fetchOneByOne(new CharacterDto(), starwarsCharacterRestService,
                entity.getCharactersUrlList()));
        // TODO - fetch remaining external entities
        // TODO - finish all other DTOs


        int z = 0;
    }


}
