package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class CharacterDto extends AbstractDto {
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

    public CharacterDto() {}
    public CharacterDto(int id) {
        super(id);
    }
    @Override
    public String restEntityName() {
        return CHARACTER_REST_URL_PIECE;
    }
}
