package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.midway.starwarsapi.util.Util;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DtoResultSet<T extends AbstractDto> {
    @Getter @Setter private Integer count;

    @JsonProperty("previous")
    @Getter private String previousUri;
    public void sePreviousUri(String previousUri) {
        this.previousUri = previousUri;
        previousPage = Util.getNumberFromUrl(previousUri);
    }

    @JsonIgnore @Hidden
    @Getter @Setter private Integer previousPage;

    @JsonProperty("next")
    @Getter private String nextUri;
    public void seNextUri(String previousUri) {
        this.nextUri = nextUri;
        nextPage = Util.getNumberFromUrl(nextUri);
    }

    @JsonIgnore @Hidden
    @Getter @Setter private Integer nextPage;

    @Getter @Setter List<T> results;

}
