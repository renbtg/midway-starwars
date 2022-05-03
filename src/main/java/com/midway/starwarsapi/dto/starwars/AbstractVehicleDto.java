package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public abstract class AbstractVehicleDto extends AbstractDto {
    @Getter @Setter private String model;
    @Getter @Setter private String manufacturer;
    @JsonProperty("cost_in_credits") @Getter @Setter private String costInCredits;
    /*@JsonSerialize(using = BigDecimalSerializer.class) BIGDECIMAL */ @Getter @Setter private String length;
    @JsonProperty("max_atmospheric_speed") @Getter @Setter private Integer maxAtmosphericSpeed;
    @Getter @Setter private String crew;
    @Getter @Setter private Integer passengers;
    @JsonProperty("cargo_capacity") @Getter @Setter private String cargoCapacity;
    @Getter @Setter private String consumables;
    @Getter @Setter private String classOfVehicle; // concrete implementors define field name

    @JsonProperty("films")
    @Getter @Setter private List<String> filmUrlList;
    @JsonProperty("filmObjects")
    @Getter @Setter private List<FilmDto> filmDtoList;

    @JsonProperty("pilots")
    @Getter @Setter private List<String> peopleUrlList;
    @JsonProperty("pilotObjects")
    @Getter @Setter private List<PeopleDto> peopleDtoList;


    public AbstractVehicleDto() {}
    public AbstractVehicleDto(int id) {
        super(id);
    }

}
