package com.midway.starwarsapi.startup;

import com.midway.starwarsapi.dto.starwars.FilmDto;
import com.midway.starwarsapi.dto.starwars.FilmResultSet;
import com.midway.starwarsapi.service.starwarsapi.FilmRestService;
import com.midway.starwarsapi.service.starwarsapi.PeopleRestService;
import com.midway.starwarsapi.service.starwarsapi.PlanetRestService;
import com.midway.starwarsapi.service.starwarsapi.RestService;
import com.midway.starwarsapi.service.starwarsapi.SpeciesRestService;
import com.midway.starwarsapi.service.starwarsapi.StarshipRestService;
import com.midway.starwarsapi.service.starwarsapi.VehicleRestService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class FilmLoader  {
    private static final Logger logger = LogManager.getLogger(FilmLoader.class);

    private static boolean alreadyRun = false;

    @Autowired private FilmRestService filmRestService;
    @Autowired private PeopleRestService peopleRestService;
    @Autowired private PlanetRestService planetRestService;
    @Autowired private SpeciesRestService speciesRestService;
    @Autowired private StarshipRestService starshipRestService;
    @Autowired private VehicleRestService vehicleRestService;

    private ExecutorService executorService;

    public void runOnceOnStartup() {
        if (alreadyRun) {
            return; // oops, called again during app execution? ODD! It would take too long.
        }
        alreadyRun = true;

        RestService.addService(filmRestService);
        RestService.addService(planetRestService);
        RestService.addService(peopleRestService);
        RestService.addService(speciesRestService);
        RestService.addService(starshipRestService);
        RestService.addService(vehicleRestService);

        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("myspringbean-thread-%d").build();

        executorService = Executors.newSingleThreadExecutor(factory);
        executorService.execute(() -> {
            try {
                // do something
                System.out.println("thread started in runOnceAtStartup() - TODO remove text");
                List<FilmDto> films = new ArrayList<>(); // will be thread-safe structure
                filmRestService.fillMap(FilmRestService.GLOBAL_FILM_MAP, new FilmResultSet(), new FilmDto());
                System.out.println("thread finishing in runOnceAtStartup() - TODO - remove text");
                int z=1;
            } catch (Exception e) {
                logger.error("error: ", e);
            }
        });
        executorService.shutdown();



    }

    @EventListener
    public void onEvent(AvailabilityChangeEvent<LivenessState> event) {
        switch (event.getState()) {
            case BROKEN:
                // notify others
                break;
            case CORRECT:
                runOnceOnStartup();
        }
    }

    @PreDestroy
    public void beanDestroy() {
        System.out.println("@PreDestroy...beanDestroy() called! TODO - remove text");
        if(executorService != null){
            executorService.shutdownNow();
        }
    }
}

