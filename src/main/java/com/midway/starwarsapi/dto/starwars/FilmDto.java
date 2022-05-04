package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.midway.starwarsapi.constants.DateTimeConstants;
import com.midway.starwarsapi.view.StarWarsView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@JsonView(Object.class)
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

    @JsonView()
    @JsonProperty("episode_id")
    @Getter @Setter String episodeId;

    @NotNull(message="'opening_crawl' cannot be null")
    @Size(min=20, message="'opening_crawl' should have at least 20 characters")
    @JsonProperty("opening_crawl")
    @Getter @Setter String openingCrawl;

    @Getter @Setter private String director;

    @Getter @Setter private String producer;

    @JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.JUST_DATE_FORMAT)
    @Getter @Setter private LocalDate releaseDate;

    @JsonProperty("characters") @JsonView(StarWarsView.FullDto.class)
    @Getter @Setter private List<String> peopleUrlList;
    @JsonProperty("characterObjects")  @JsonView(StarWarsView.OneFilm.class)
    @Getter @Setter private List<PeopleDto> peopleDtoList;

    @JsonProperty("planets") @JsonView(StarWarsView.FullDto.class)
    @Getter @Setter private List<String> planetUrlList;
    @JsonProperty("planetObjects")  @JsonView(StarWarsView.OneFilm.class)
    @Getter @Setter private List<PlanetDto> planetList;

    @JsonProperty("starships") @JsonView(StarWarsView.FullDto.class)
    @Getter @Setter private List<String> starshipUrlList;
    @JsonProperty("starshipObjects") @JsonView(StarWarsView.OneFilm.class)
    @Getter @Setter private List<StarshipDto> starshipDtoList;

    @JsonProperty("vehicles") @JsonView(StarWarsView.FullDto.class)
    @Getter @Setter private List<String> vehicleUrlList;
    @JsonProperty("vehicleObjects") @JsonView(StarWarsView.OneFilm.class)
    @Getter @Setter private List<VehicleDto> vehicleDtoList;

    @JsonProperty("species") @JsonView(StarWarsView.FullDto.class)
    @Getter @Setter private List<String> speciesUrlList;
    @JsonProperty("speciesObjects") @JsonView(StarWarsView.OneFilm.class)
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
