package com.midway.starwarsapi.dto.starwars;

public class VehicleDto extends AbstractDto {
    @Override
    String restEntityName() {
        return VEHICLE_REST_URL_PIECE;
    }
}
