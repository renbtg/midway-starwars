package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.midway.starwarsapi.view.StarWarsView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@JsonView(StarWarsView.SimpleDto.class)
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
