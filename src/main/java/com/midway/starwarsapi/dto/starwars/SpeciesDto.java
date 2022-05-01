package com.midway.starwarsapi.dto.starwars;

public class SpeciesDto extends AbstractDto {
    @Override
    String restEntityName() {
        return SPECIES_REST_URL_PIECE;
    }
}
