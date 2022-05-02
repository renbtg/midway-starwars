package com.midway.starwarsapi.startup;

import com.midway.starwarsapi.dto.starwars.FilmDto;
import com.midway.starwarsapi.dto.starwars.FilmResultSet;
import com.midway.starwarsapi.service.starwarsapi.StarwarsFilmRestService;
import com.midway.starwarsapi.service.starwarsapi.StarwarsPeopleRestService;
import com.midway.starwarsapi.service.starwarsapi.StarwarsPlanetRestService;
import com.midway.starwarsapi.service.starwarsapi.StarwarsRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@EnableScheduling
public class FilmLoader  {
    private static boolean alreadyRun = false;

    @Autowired private StarwarsFilmRestService starwarsFilmRestService;
    @Autowired private StarwarsPeopleRestService starwarsPeopleRestService;
    @Autowired private StarwarsPlanetRestService starwarsPlanetRestService;

    public void runOnceOnStartup() {
        if (alreadyRun) {
            return; // oops, called again during app execution? ODD! It would take too long.
        }
        alreadyRun = true;

        StarwarsRestService.addService(starwarsFilmRestService);
        StarwarsRestService.addService(starwarsPeopleRestService);
        StarwarsRestService.addService(starwarsPlanetRestService);

        List<FilmDto> films = starwarsFilmRestService.getList(new FilmResultSet(), new FilmDto());
        int z=1;
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
}

