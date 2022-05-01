package com.midway.starwarsapi.service.starwarsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midway.starwarsapi.dto.starwars.AbstractDto;
import com.midway.starwarsapi.dto.starwars.StarWarsResultSet;
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
"TODO - com metodos de classe base: quando for populado o objeto, popular o ID dele. Campo URL de ABSTRACTDTO tem a URL, basta parsear e setar o ID pra fácil uso posterior";

        String url = String.format("%s/%s/%d", starwarsApiRootUrl, entity.restEntityName(), entity.getId());
        var restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity =
                restTemplate.getForEntity(url, Object.class);
        Object object = responseEntity.getBody();

        ObjectMapper mapper = Util.getObjectMapper();
        var dto = mapper.convertValue(object, entity.getClass());

        return (T) dto;
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

    public List<T> getResultSet(StarWarsResultSet<T> resultSetPrototype, T prototype) {

        "TODO - retornar uma lista, fazendo todos GETNEXT necessarios"
        String url = String.format("%s/%s", starwarsApiRootUrl, prototype.restEntityName());
        var restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity =
                restTemplate.getForEntity(url, Object.class);
        Object object = responseEntity.getBody();

        ObjectMapper mapper = Util.getObjectMapper();
        var dto = mapper.convertValue(object, T.class);

        return (List<T>) dto;

    }
}
