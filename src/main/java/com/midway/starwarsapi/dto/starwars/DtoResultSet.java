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
    @Getter private String previousUrl;
    public void sePreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
        previousPage = Util.getNumberFromUrl(previousUrl);
    }

    @JsonIgnore @Hidden
    @Getter @Setter private Integer previousPage;

    @JsonProperty("next")
    @Getter private String nextUrl;
    public void seNextUri(String previousUri) {
        this.nextUrl = nextUrl;
        nextPage = Util.getNumberFromUrl(nextUrl);
    }

    @JsonIgnore @Hidden
    @Getter @Setter private Integer nextPage;

    @Getter @Setter List<T> results;

}
