package com.midway.starwarsapi.dto.starwars;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class AbstractResultSet<T extends AbstractDto> {
    @Getter @Setter private Integer count;

    @JsonProperty("previous")
    @Getter @Setter private String previousUri;

    @Getter @Setter private Integer previousPage;

    @JsonProperty("next")
    @Getter @Setter private String nextUri;

    @Getter @Setter private Integer nextPage;

    @Getter @Setter List<T> results;

}
