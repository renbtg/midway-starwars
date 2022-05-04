package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.midway.starwarsapi.constants.DateTimeConstants;
import com.midway.starwarsapi.util.Util;
import com.midway.starwarsapi.view.StarWarsView;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonView(Object.class)
public abstract class AbstractDto {
    public static final String FILM_REST_URL_PIECE = "films";
    public static final String PEOPLE_REST_URL_PIECE = "people";
    public static final String SPECIES_REST_URL_PIECE = "species";
    public static final String VEHICLE_REST_URL_PIECE = "vehicles";
    public static final String PLANET_REST_URL_PIECE = "planets";
    public static final String STARSHIP_REST_URL_PIECE = "starships";


    @Getter @Setter private Integer id;
    @Getter @Setter private String name;
    @JsonView(StarWarsView.SimpleDto.class) @Getter @Setter private boolean failedRestCall = false;

    @JsonView(StarWarsView.SimpleDto.class) @Getter @Setter private boolean defailFillingStarted = false; // TODO - useful for OUR-api callers to know data-load completion? Or HTTP status 206?
    @JsonView(StarWarsView.SimpleDto.class) @Getter @Setter private boolean defailFillingFinished = false; // TODO - useful for OUR-api callers to know data-load completion? Or HTTP status 206?

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.NEAR_ISO8601_DATEFORMAT)
    @JsonView(StarWarsView.FullDto.class) @Getter @Setter private /*LocalDateTime*/ String created;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.NEAR_ISO8601_DATEFORMAT)
    @JsonView(StarWarsView.FullDto.class) @Getter @Setter private /*LocalDateTime*/ String edited;

    @JsonView(StarWarsView.FullDto.class) @Getter private String url;
    public void setUrl(String url) {
        this.url = url;
        id = Util.getNumberFromUrl(url);
    }

    public AbstractDto() {}
    public AbstractDto(int id) {
        this.id = id;
    }

    public abstract String restEntityName();

    @SuppressWarnings("unchecked")
    public <T extends AbstractDto> T failedRestFetch(T prototype) {
        try {
            T entity = (T) prototype.getClass().getDeclaredConstructor().newInstance();
            entity.setId(prototype.getId());
            entity.setFailedRestCall(true);
            return entity;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
            throw new IllegalArgumentException(String.format(
                    "Cannot instantiate object of class %s", prototype.getClass().getName()));
        }
    }
}
