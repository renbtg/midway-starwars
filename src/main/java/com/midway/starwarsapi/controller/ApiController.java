package com.midway.starwarsapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.midway.starwarsapi.dto.starwars.FilmResultSet;
import com.midway.starwarsapi.service.starwarsapi.StarwarsFilmRestService;
import com.midway.starwarsapi.util.ApiExpiration;
import com.midway.starwarsapi.view.StarWarsView;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("midway-starwars-api")
@Validated
@Tag(name = "Midway Starwars API", description = "API starwars, processo seletivo da Midway")
public class ApiController implements ApiExpiration {
    private static final Logger logger = LogManager.getLogger(ApiController.class);

    private final StarwarsFilmRestService starwarsFilmRestService;

    @Value("${appVersion}")
    private String appVersion;
    @Value("${appBuildNumber}")
    private String appBuildNumber;

    @Value("${exportFiles.fixedDiskSpaceBufferGigabytes:0}")
    private Double fixedExportDiskSpaceBufferGigabytes;

    public ApiController(StarwarsFilmRestService starwarsFilmRestService) {
        this.starwarsFilmRestService = starwarsFilmRestService;
    }

    @GetMapping(value = "/version")
    public String getVersion() {
//        ApiExpiration.checkApiExpiration();
        return appVersion + "." + appBuildNumber;
    }




    @GetMapping(value = "/films", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(StarWarsView.AllFilms.class)
    public ResponseEntity<FilmResultSet> getAllFilms() {
        return ResponseEntity.badRequest().build();
    }
}
