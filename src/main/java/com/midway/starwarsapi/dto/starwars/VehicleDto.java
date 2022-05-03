package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class VehicleDto extends AbstractVehicleDto {
    @JsonProperty("vehicle_class") @Override
    public String getClassOfVehicle() { return super.getClassOfVehicle(); }
    @JsonProperty("vehicle_class") @Override
    public void setClassOfVehicle(String classOfVehicle) {
        super.setClassOfVehicle(classOfVehicle);
    }

    public VehicleDto() {}
    public VehicleDto(int id) {
        super(id);
    }

    @Override
    public String restEntityName() {
        return VEHICLE_REST_URL_PIECE;
    }
}
