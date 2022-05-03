package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SpeciesDto extends AbstractDto {
    @Getter @Setter private String name;
    @Getter @Setter private String classification;
    @Getter @Setter private String designation;
    @JsonProperty("average_height") @Getter @Setter private Integer averageHeight;
    @JsonProperty("skin_colors") @Getter @Setter private String skinColors;
    @JsonProperty("average_lifespan") @Getter @Setter private Integer averageLifeSpan;
    @Getter @Setter private String language;

    @JsonProperty("homeworld") @Getter @Setter private String homeWorldUrl;
    @JsonProperty("homeworldDetail") @Getter @Setter private PlanetDto homeWorldDetail;


    @JsonProperty("films")
    @Getter @Setter private List<String> filmUrlList;
    @JsonProperty("filmObjects")
    @Getter @Setter private List<FilmDto> filmDtoList;

    @JsonProperty("people")
    @Getter @Setter private List<String> peopleUrlList;
    @JsonProperty("peopleObjects")
    @Getter @Setter private List<PeopleDto> peopleDtoList;

    // TODO - referenced Lists like "films", "vehichles" etc.

    public SpeciesDto() {}
    public SpeciesDto(int id) {
        super(id);
    }
    @Override
    public String restEntityName() {
        return SPECIES_REST_URL_PIECE;
    }

}
