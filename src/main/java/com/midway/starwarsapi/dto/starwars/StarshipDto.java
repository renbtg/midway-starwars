package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class StarshipDto extends AbstractVehicleDto {
    @JsonProperty("hyperdrive_rating") @Getter @Setter private BigDecimal hyperdriveRating;
    @JsonProperty("MGLT") @Getter @Setter private Integer mglt;

    @JsonProperty("starship_class") @Override
    public String getClassOfVehicle() { return super.getClassOfVehicle(); }
    @JsonProperty("starship_class") @Override
    public void setClassOfVehicle(String classOfVehicle) {
        super.setClassOfVehicle(classOfVehicle);
    }

    public StarshipDto() {}
    public StarshipDto(int id) {
        super(id);
    }

    @Override
    public String restEntityName() {
        return STARSHIP_REST_URL_PIECE;
    }
}
