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

    private static List<StarwarsRestService> serviceList = new ArrayList<>();
    public static void addService(StarwarsRestService service) {
        serviceList.add(service);
    }

    protected T obtainEntity(T entity) {
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
    /*
    Each implementor must just call obtainEntity(...), and decorate method with @Cacheable("foo-rest") where
    foo is one of planets, people, vehicles, species (always plural) etc.
     */
    public abstract T getEntity(int id);

    public abstract void fillDetails(T entity);

    public List<T> fetchOneByOne(List<String> urlList) {

        return CollectionUtils.emptyIfNull(urlList)
                .stream().
                map(url -> getSelf().getEntity(Util.getNumberFromUrl(url)))
                .toList();
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

    private StarwarsRestService<T> getSelf() {
        for (var svc: serviceList) {
            try {
                if (getClass().isAssignableFrom(svc.getClass())) {
                    return svc;
                } else {
                    int z = 0;
                    // do nothing;
                }
            }catch (RuntimeException ignore) {
                // do nothing
                int z = 0;
            }
        }
        return null;
    }
}
