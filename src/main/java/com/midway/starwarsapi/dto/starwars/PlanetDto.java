package com.midway.starwarsapi.dto.starwars;

public class PlanetDto extends AbstractDto {

    public PlanetDto() {}
    public PlanetDto(int id) {
        super(id);
    }

    @Override
    public String restEntityName() {
        return AbstractDto.PLANET_REST_URL_PIECE;
    }
}
