package ru.sfedu.Test.model.beans;

import java.util.List;

public class FilmsWrapper {
    private List<Film> films;

    public FilmsWrapper(List<Film> films) {
        setFilms(films);
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    @Override
    public String toString() {
        return "FilmsWrapper{" +
                "films=" + films +
                '}';
    }
}
