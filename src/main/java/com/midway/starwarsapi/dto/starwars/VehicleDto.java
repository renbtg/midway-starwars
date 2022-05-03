package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class VehicleDto extends AbstractVehicleDto {
    @JsonProperty("vehicle_class")
    @Override
    public String getClassOfVehicle() {
        return super.getClassOfVehicle();
    }
    @JsonProperty("vehicle_class")
    @Override

"TODO - terminar Starship que tbm deriva de AbstractVehicleDTO... depois, fazer People e Species derivar de LifeForm... depois, cincluir DTO <-> SERVICE, e entao fazer SPEC";
    public void setClassOfVehicle(String classOfVehicle) {
        super.setClassOfVehicle(classOfVehicle);
    }

    @JsonProperty("films")
    @Getter @Setter private List<String> filmUrlList;
    @JsonProperty("filmObjects")
    @Getter @Setter private List<FilmDto> filmDtoList;

    @JsonProperty("pilots")
    @Getter @Setter private List<String> pilotUrlList;
    @JsonProperty("filmObjects")
    @Getter @Setter private List<PeopleDto> pilotDtoList;


    public VehicleDto() {}
    public VehicleDto(int id) {
        super(id);
    }

    @Override
    public String restEntityName() {
        return VEHICLE_REST_URL_PIECE;
    }
}
