package ru.sfedu.test.model.beans;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "films")
public class FilmsWrapper {

    @ElementList(entry = "film", inline = true, type = Film.class)
    private List<Film> films;

    public FilmsWrapper() {}

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
