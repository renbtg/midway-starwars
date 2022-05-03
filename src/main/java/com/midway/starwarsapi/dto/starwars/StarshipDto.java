package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class StarshipDto extends AbstractDto {
    @Getter @Setter private String model;
    @JsonProperty("cost_in_credits") @Getter @Setter private Integer costInCredits;
    @Getter @Setter private Integer length;
    @JsonProperty("max_atmosphering_speed") @Getter @Setter private Integer maxAtmosphericSpeed;
    @Getter @Setter private String crew;
    @Getter @Setter private String passengers;
    @JsonProperty("cargo_capacity") @Getter @Setter private String cargoCapacity;
    @Getter @Setter private String consumables;
    @JsonProperty("hyperdrive_rating") @Getter @Setter private BigDecimal hyperdriveRating;
    @JsonProperty("MGLT") @Getter @Setter private Integer mglt;
    @JsonProperty("starship_class") @Getter @Setter private String starshipClass;

    @JsonProperty("films")
    @Getter @Setter private List<String> filmUrlList;
    @JsonProperty("filmObjects")
    @Getter @Setter private List<FilmDto> filmDtoList;

    @JsonProperty("pilots")
    @Getter @Setter private List<String> pilotUrlList;
    @JsonProperty("filmObjects")
    @Getter @Setter private List<PeopleDto> pilotDtoList;


    public StarshipDto() {}
    public StarshipDto(int id) {
        super(id);
    }

    @Override
    public String restEntityName() {
        return STARSHIP_REST_URL_PIECE;
    }
}
