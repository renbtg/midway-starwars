package com.midway.starwarsapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.midway.starwarsapi.dto.starwars.FilmDto;
import com.midway.starwarsapi.dto.starwars.FilmResultSet;
import com.midway.starwarsapi.service.starwarsapi.FilmRestService;
import com.midway.starwarsapi.util.ApiExpiration;
import com.midway.starwarsapi.view.StarWarsView;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("midway-starwars-api")
@Validated
@Tag(name = "Midway Starwars API", description = "API starwars, processo seletivo da Midway")
public class ApiController implements ApiExpiration {
    private static final Logger logger = LogManager.getLogger(ApiController.class);

    @Autowired private FilmRestService filmRestService;

    @Value("${appVersion}")
    private String appVersion;
    @Value("${appBuildNumber}")
    private String appBuildNumber;

    @Value("${exportFiles.fixedDiskSpaceBufferGigabytes:0}")
    private Double fixedExportDiskSpaceBufferGigabytes;

    @GetMapping(value = "/version")
    public String getVersion() {
//        ApiExpiration.checkApiExpiration();
        return appVersion + "." + appBuildNumber;
    }




    @GetMapping(value = "/films", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(StarWarsView.AllFilms.class)
    public ResponseEntity<List<FilmDto>> getAllFilms() {

        return ResponseEntity.ok().body(FilmRestService.GLOBAL_FILM_LIST);
    }


    @GetMapping(value = "/films/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8081" })
    //@JsonView(OculinkView.FullProject.class)
    public ResponseEntity<FilmDto> getFilm(@PathVariable Integer id) {
        FilmDto filmDto = FilmRestService.GLOBAL_FILM_LIST.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
        return Optional
                .ofNullable(filmDto)
                .map(result -> ResponseEntity.ok().body(result))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
