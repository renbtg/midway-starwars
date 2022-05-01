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



    @Getter @Setter private String title;

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
    @Getter @Setter private List<String> characterUrlList;

    @JsonProperty("characterDetails")
    @Getter @Setter private List<PeopleDto> characterList;

    @JsonProperty("planets")
    @Getter @Setter private List<String> planetUrlList;

    @JsonProperty("planetDetails")
    @Getter @Setter private List<PlanetDto> planetList;

    @JsonProperty("starships")
    @Getter @Setter private List<String> starshipUrlList;

    @JsonProperty("starshipDetails")
    @Getter @Setter private List<StarshipDto> starshipDtoList;

    @JsonProperty("vehicles")
    @Getter @Setter private List<String> vehicleUrlList;

    @JsonProperty("vehicleDetails")
    @Getter @Setter private List<VehicleDto> vehicleDtoList;

    @JsonProperty("species")
    @Getter @Setter private List<String> speciesUrlList;

    @JsonProperty("speciesDetails")
    @Getter @Setter private List<SpeciesDto> speciesDtoList;

    public FilmDto() {}
    public FilmDto(int id) {
        super(id);
    }
    @Override
    public String restEntityName() {
        return AbstractDto.FILM_REST_URL_PIECE;
    }

}
