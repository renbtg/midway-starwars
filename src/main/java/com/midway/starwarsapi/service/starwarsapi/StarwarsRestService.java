package com.midway.starwarsapi.service.starwarsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midway.starwarsapi.dto.starwars.AbstractDto;
import com.midway.starwarsapi.dto.starwars.StarWarsResultSet;
import com.midway.starwarsapi.util.Util;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public abstract class StarwarsRestService<T extends AbstractDto> {
    @Value("${api.starwars.url.root}")
    protected String starwarsApiRootUrl; // showing off private/protected/public knowledge -- SHOULD be private with @getter

    public T getEntity(T entity) {
        String url = String.format("%s/%s/%d", starwarsApiRootUrl, entity.restEntityName(), entity.getId());
        var restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity =
                restTemplate.getForEntity(url, Object.class);
        Object object = responseEntity.getBody();

        ObjectMapper mapper = Util.getObjectMapper();
        var dto = (T) mapper.convertValue(object, entity.getClass());

        fillDetails(dto);

        return dto;
    }

    public abstract void fillDetails(T entity);

    public List<T> fetchOneByOne(T prototype, List<String> urlList ) {
        return CollectionUtils.emptyIfNull(urlList)
                .stream().
                map(url -> {
                            prototype.setId(Util.getNumberFromUrl(url));
                            return getEntity(prototype);
                        }
                ).toList();
    }

    public List<T> getList(StarWarsResultSet<T> resultSetPrototype, T prototype) {
        List<T> list = new ArrayList<>();
        int currPage = 1;
        StarWarsResultSet<T> pageResultSet;
        do {
            pageResultSet = getPageResultSet(resultSetPrototype, prototype, currPage++);
            pageResultSet.getResults().forEach(this::fillDetails);
            list.addAll(pageResultSet.getResults());
        } while (pageResultSet.getNextPage() != null);
        return list;
    }

    private StarWarsResultSet<T> getPageResultSet(StarWarsResultSet<T> resultSetPrototype, T prototype, int page) {

        String url = String.format("%s/%s/?page=%d", starwarsApiRootUrl, prototype.restEntityName(), page); // TODO - suse standard GET URL formatter/caller for param "page"
        var restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity =
                restTemplate.getForEntity(url, Object.class);
        Object object = responseEntity.getBody();

        ObjectMapper mapper = Util.getObjectMapper();

        var resultSet = mapper.convertValue(object, resultSetPrototype.getClass());
        return resultSet;
    }

}
