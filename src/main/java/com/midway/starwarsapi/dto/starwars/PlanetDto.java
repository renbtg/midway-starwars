package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class PlanetDto extends AbstractDto {
    @Getter @Setter private String name;

    @JsonProperty("rotation_period") @Getter @Setter private String rotationPeriod;

    @JsonProperty("orbital_period") @Getter @Setter private String orbitalPeriod;

    @Getter @Setter private String diameter;

    @Getter @Setter private String climate;

    @Getter @Setter private String gravity;

    @Getter @Setter private String terrain;

    @JsonProperty("surface_water") @Getter @Setter private BigDecimal surfaceWater;

    @Getter @Setter private Long population;

    @JsonProperty("residents") @Getter @Setter private List<String> peopleUrlList;
    @JsonProperty("residentObjects") @Getter @Setter private List<PeopleDto> peopleDtoList;

    @JsonProperty("films") @Getter @Setter private List<String> filmUrlList;
    @JsonProperty("filmObjects") @Getter @Setter private List<FilmDto> filmDtoList;

    public PlanetDto() {}
    public PlanetDto(int id) {
        super(id);
    }

    @Override
    public String restEntityName() {
        return AbstractDto.PLANET_REST_URL_PIECE;
    }
}
