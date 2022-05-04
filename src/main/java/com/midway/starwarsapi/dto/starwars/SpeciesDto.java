package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.midway.starwarsapi.view.StarWarsView;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonView(Object.class)
public class SpeciesDto extends AbstractDto {
    @Getter @Setter private String name;
    @Getter @Setter private String classification;
    @Getter @Setter private String designation;
    @JsonProperty("average_height") @Getter @Setter private Integer averageHeight;
    @JsonProperty("skin_colors") @Getter @Setter private String skinColors;
    @JsonProperty("average_lifespan") @Getter @Setter private String averageLifeSpan;
    @Getter @Setter private String language;

    @JsonProperty("homeworld") @Getter @Setter private String homeWorldUrl;
    @JsonProperty("homeworldObject") @Getter @Setter private PlanetDto homeWorldDto;


    @JsonProperty("films" ) @JsonView(StarWarsView.FullDto.class)
    @Getter @Setter private List<String> filmUrlList;
    @JsonProperty("filmObjects") @JsonView(StarWarsView.FullDto.class)
    @Getter @Setter private List<FilmDto> filmDtoList;

    @JsonProperty("people") @JsonView(StarWarsView.FullDto.class)
    @Getter @Setter private List<String> peopleUrlList;
    @JsonProperty("peopleObjects") @JsonView(StarWarsView.FullDto.class)
    @Getter @Setter private List<PeopleDto> peopleDtoList;

    public SpeciesDto() {}
    public SpeciesDto(int id) {
        super(id);
    }
    @Override
    public String restEntityName() {
        return SPECIES_REST_URL_PIECE;
    }

}
