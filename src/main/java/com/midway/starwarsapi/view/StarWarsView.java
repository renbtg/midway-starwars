package com.midway.starwarsapi.view;

public interface StarWarsView {

    interface SimpleDto {}
    interface OneFilm extends SimpleDto {}


    interface FullDto extends SimpleDto {}

    interface AllFilms {} // very restricted

}
