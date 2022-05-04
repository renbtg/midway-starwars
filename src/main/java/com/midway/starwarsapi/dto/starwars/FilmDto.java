package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.midway.starwarsapi.constants.DateTimeConstants;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class FilmDto extends AbstractDto {
    @Getter @Setter private Integer version = 1;

    @JsonProperty("title")
    public String getName() {
        return super.getName();
    }
    @JsonProperty("title") // TODO - do we need both getter and setter to have overriden "title" for name ?
    public void setName(String name) {
        super.setName(name);
    }

    @JsonProperty("episode_id")
    @Getter @Setter String episodeId;

    @JsonProperty("opening_crawl")
    @Getter @Setter String openingCrawl;

    @Getter @Setter private String director;

    @Getter @Setter private String producer;

    @JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.JUST_DATE_FORMAT)
    @Getter @Setter private LocalDate releaseDate;

    @JsonProperty("characters")
    @Getter @Setter private List<String> peopleUrlList;
    @JsonProperty("characterObjects")
    @Getter @Setter private List<PeopleDto> peopleDtoList;

    @JsonProperty("planets")
    @Getter @Setter private List<String> planetUrlList;
    @JsonProperty("planetObjects")
    @Getter @Setter private List<PlanetDto> planetList;

    @JsonProperty("starships")
    @Getter @Setter private List<String> starshipUrlList;
    @JsonProperty("starshipObjects")
    @Getter @Setter private List<StarshipDto> starshipDtoList;

    @JsonProperty("vehicles")
    @Getter @Setter private List<String> vehicleUrlList;
    @JsonProperty("vehicleObjects")
    @Getter @Setter private List<VehicleDto> vehicleDtoList;

    @JsonProperty("species")
    @Getter @Setter private List<String> speciesUrlList;
    @JsonProperty("speciesObjects")
    @Getter @Setter private List<SpeciesDto> speciesDtoList;

    public FilmDto() {}
    public FilmDto(int id) {
        super(id);
    }
    @Override
    public String restEntityName() {
        return FILM_REST_URL_PIECE;
    }

}
