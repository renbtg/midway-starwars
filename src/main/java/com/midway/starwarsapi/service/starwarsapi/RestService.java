package com.midway.starwarsapi.service.starwarsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midway.starwarsapi.dto.starwars.AbstractDto;
import com.midway.starwarsapi.dto.starwars.StarWarsResultSet;
import com.midway.starwarsapi.util.Util;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class StarwarsRestService<T extends AbstractDto> {

    @Value("${api.starwars.url.root}")
    protected String starwarsApiRootUrl; // showing off private/protected/public knowledge -- SHOULD be private with @getter

    private RestClientException restClientException = null;
    private static final List<StarwarsRestService> serviceList = new ArrayList<>();
    public static void addService(StarwarsRestService service) {
        serviceList.add(service);
    }

    protected T obtainEntity(T entity) {
        if (restClientException != null) {
            // early pessimistic return, won't retry until app restarted
            return entity.failedRestFetch(entity);
        }
        String url = String.format("%s/%s/%d", starwarsApiRootUrl, entity.restEntityName(), entity.getId());
        var restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(url, Object.class);
        } catch (RestClientException e) {
            // do not try to access endpoint until application is restarted
            restClientException = e;
            return null;
        }
        Object object = responseEntity.getBody();

        ObjectMapper mapper = Util.getObjectMapper();
        var dto = (T) mapper.convertValue(object, entity.getClass());

        fillAllDetails(dto);

        return dto;
    }

    private void fillAllDetails(T entity) {
        entity.setDefailFillingStarted(true);
        fillDetails(entity);
        entity.setDefailFillingFinished(true);
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
            pageResultSet.getResults().forEach(this::fillAllDetails);
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

        return mapper.convertValue(object, resultSetPrototype.getClass());
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
        throw new IllegalStateException(String.format("Could not find proxied rest service for class %s",
                getClass().getName()));
    }
}
