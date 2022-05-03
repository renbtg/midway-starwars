package com.midway.starwarsapi.service.starwarsapi;

import com.midway.starwarsapi.dto.starwars.FilmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmRestService extends RestService<FilmDto> {
    public static final List<FilmDto> GLOBAL_FILM_LIST = new ArrayList<>(); // no need to synchronize so far
    @Autowired
    PeopleRestService peopleRestService;
    @Autowired
    PlanetRestService planetRestService;
    @Autowired
    SpeciesRestService speciesRestService;
    @Autowired
    VehicleRestService vehicleRestService;
    @Autowired
    StarshipRestService starshipRestService;

    @Cacheable(value="films-rest")
    /*@Caching(
            put= { @CachePut(value="films-rest", key="#id", condition="#cachedData==false") },
            cacheable = { @Cacheable(value ="films-rest", key="#id", condition="#cachedData==true") }
    )*/
    @Override
    public FilmDto getEntity(int id) {
        return obtainEntity(new FilmDto(id));
    }

    @Override
    public void fillDetails(FilmDto entity) {
        // FOR NOW, this is the single filDetails(...) implementation that deep-reads lists.
        entity.setPeopleDtoList(peopleRestService.fetchOneByOne(entity.getPeopleUrlList()));
        entity.setPlanetList(planetRestService.fetchOneByOne(entity.getPlanetUrlList()));
        entity.setSpeciesDtoList(speciesRestService.fetchOneByOne(entity.getSpeciesUrlList()));
        entity.setStarshipDtoList(starshipRestService.fetchOneByOne(entity.getStarshipUrlList()));
        entity.setVehicleDtoList(vehicleRestService.fetchOneByOne(entity.getVehicleUrlList()));
    }


}
