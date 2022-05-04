package com.midway.starwarsapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.midway.starwarsapi.dto.starwars.FilmDto;
import com.midway.starwarsapi.service.starwarsapi.FilmRestService;
import com.midway.starwarsapi.util.ApiExpiration;
import com.midway.starwarsapi.util.Util;
import com.midway.starwarsapi.view.StarWarsView;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    public ResponseEntity<Collection<FilmDto>> getAllFilms() {
        return ResponseEntity.ok().body(FilmRestService.GLOBAL_FILM_MAP.values());
    }


    @GetMapping(value = "/films/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8081" })
    //@JsonView(OculinkView.FullProject.class)
    public ResponseEntity<FilmDto> getFilm(@PathVariable Integer id) {
        FilmDto filmDto = FilmRestService.GLOBAL_FILM_MAP.entrySet()
                .stream().filter(entry -> Objects.equals(entry.getKey(), id))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
        return Optional
                .ofNullable(filmDto)
                .map(result -> ResponseEntity.ok().body(result))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping(path = "/films/{id}", consumes = "application/json-patch+json")
    @CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8081" })
    public ResponseEntity<FilmDto> updateFilm(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        try {
            FilmDto filmDto = FilmRestService.GLOBAL_FILM_MAP.get(id);
            if (filmDto == null) {
                return ResponseEntity.notFound().build();
            } else {
                FilmDto filmDtoPatched = applyPatchToFilm(patch, filmDto);
                if (Objects.equals(filmDtoPatched, filmDto)) {
                    return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
                } else {
                    FilmRestService.GLOBAL_FILM_MAP.put(id, filmDtoPatched);
                    return ResponseEntity.ok(filmDtoPatched); // TODO - do we want to return body at all, even when OK?
                }
            }
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    private FilmDto applyPatchToFilm(JsonPatch patch, FilmDto targetCustomer) throws
            JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = Util.getObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, FilmDto.class);
    }
}
