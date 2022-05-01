package com.midway.starwarsapi.dto.starwars;

public class SpeciesDto extends AbstractDto {
    @Override
    public String restEntityName() {
        return SPECIES_REST_URL_PIECE;
    }
}
