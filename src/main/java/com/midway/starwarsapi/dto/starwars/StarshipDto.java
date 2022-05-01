package com.midway.starwarsapi.dto.starwars;

public class StarshipDto extends AbstractDto {
    @Override
    public String restEntityName() {
        return STARSHIP_REST_URL_PIECE;
    }
}
