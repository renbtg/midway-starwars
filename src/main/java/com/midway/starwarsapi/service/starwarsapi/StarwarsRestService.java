package com.midway.starwarsapi.service.starwarsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midway.starwarsapi.dto.starwars.AbstractDto;
import com.midway.starwarsapi.dto.starwars.CharacterDto;
import com.midway.starwarsapi.dto.starwars.PlanetDto;
import com.midway.starwarsapi.util.Util;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public abstract class StarwarsRestService<T extends AbstractDto> {
    @Value("${api.starwars.url.root}")
    @Getter private String starwarsApiRootUrl; // TODO - make StarwarsRestService abstract base class? No need, for now?

    public T getEntity(T entity) {

        String url = String.format("%s/%s/id", entity.getId(), starwarsApiRootUrl);
        var restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity =
                restTemplate.getForEntity(url, Object.class);
        Object object = responseEntity.getBody();

        ObjectMapper mapper = Util.getObjectMapper();
        var dto = mapper.convertValue(object, T.class);

        return dto;
    }

    public abstract void fillDetails(T entity);

    public <D extends AbstractDto> List<D> fetchOneByOne(D prototype, StarwarsRestService<D> service, List<String> urlList ) {
        return CollectionUtils.emptyIfNull(urlList)
                .stream().
                map(url -> {
                            prototype.setId(Util.getIdFromUrl(url));
                            return service.getEntity(prototype);
                        }
                ).toList();
    }
}
