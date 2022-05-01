package com.midway.starwarsapi.dto.starwars;

public class VehicleDto extends AbstractDto {
    @Override
    public String restEntityName() {
        return VEHICLE_REST_URL_PIECE;
    }
}
