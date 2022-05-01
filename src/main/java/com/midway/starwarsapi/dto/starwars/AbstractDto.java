package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.midway.starwarsapi.constants.DateTimeConstants;
import com.midway.starwarsapi.util.Util;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDto {

    public static final String FILM_REST_URL_PIECE = "films";
    public static final String PEOPLE_REST_URL_PIECE = "people";
    public static final String SPECIES_REST_URL_PIECE = "species";
    public static final String VEHICLE_REST_URL_PIECE = "vehicles";
    public static final String PLANET_REST_URL_PIECE = "planets";
    public static final String STARSHIP_REST_URL_PIECE = "starships";

    @Getter @Setter private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.NEAR_ISO8601_DATEFORMAT)
    @Getter @Setter private LocalDateTime created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.NEAR_ISO8601_DATEFORMAT)
    @Getter @Setter private LocalDateTime edited;

    @Getter private String url;
    public void setUrl(String url) {
        this.url = url;
        id = Util.getNumberFromUrl(url);
    }

    public AbstractDto() {}
    public AbstractDto(int id) {
        this.id = id;
    }

    public abstract String restEntityName();
}
