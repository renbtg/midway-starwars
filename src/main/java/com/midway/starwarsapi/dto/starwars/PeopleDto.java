package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class PeopleDto extends AbstractDto {
    @Getter @Setter private String name;
    @Getter @Setter private Integer height;
    @Getter @Setter private Integer mass;
    @JsonProperty("hair_color") @Getter @Setter private String hairColor;
    @JsonProperty("skin_color") @Getter @Setter private String skinColor;
    @JsonProperty("eye_color") @Getter @Setter private String eyeColor;
    @JsonProperty("birth_year") @Getter @Setter private String birth_year;
    @Getter @Setter private String gender;

    @JsonProperty("homeworld") @Getter @Setter private String homeWorldUrl;

    @JsonProperty("homeworldDetail") @Getter @Setter private PlanetDto homeWorldDetail;

    // TODO - referenced Lists like "films", "vehichles" etc.

    public PeopleDto() {}
    public PeopleDto(int id) {
        super(id);
    }
    @Override
    public String restEntityName() {
        return PEOPLE_REST_URL_PIECE;
    }
}
