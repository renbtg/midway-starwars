package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.midway.starwarsapi.constants.DateTimeConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class AbstractDto {
    public static final String FILM_ENTITY_NAME_SINGULAR = "film";
    public static final String CHARACTER_ENTITY_NAME_SINGULAR = "character";
    public static final String SPECIES_ENTITY_NAME_SINGULAR = "species";
    public static final String VEHICLE_ENTITY_NAME_SINGULAR = "vehicle";
    public static final String PLANET_ENTITY_NAME_SINGULAR = "planet";
    public static final String STARSHIP_ENTITY_NAME_SINGULAR = "starship";

    public static final String FILM_ENTITY_NAME_PLURAL = "films";
    public static final String CHARACTER_ENTITY_NAME_PLURAL = "characters";
    public static final String SPECIES_ENTITY_NAME_PLURAL = "species";
    public static final String VEHICLE_ENTITY_NAME_PLURAL = "vehicles";
    public static final String PLANET_ENTITY_NAME_PLURAL = "planets";
    public static final String STARSHIP_ENTITY_NAME_PLURAL = "starships";

    public static final String FILM_REST_URL_PIECE = "films";
    public static final String CHARACTER_REST_URL_PIECE = "people";
    public static final String SPECIES_REST_URL_PIECE = "species";
    public static final String VEHICLE_REST_URL_PIECE = "vehicles";
    public static final String PLANET_REST_URL_PIECE = "planets";
    public static final String STARSHIP_REST_URL_PIECE = "starships";

    @Getter @Setter private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.NEAR_ISO8601_DATEFORMAT)
    @Getter @Setter private LocalDateTime created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.NEAR_ISO8601_DATEFORMAT)
    @Getter @Setter private LocalDateTime edited;

    @Getter @Setter private String url;

    public AbstractDto() {}
    public AbstractDto(int id) {
        this.id = id;
    }

    public abstract String restEntityName();
}
