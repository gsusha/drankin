package ru.sfedu.Test.model.beans;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.Test.Constants;

import java.io.IOException;
import java.io.Serializable;
import java.time.Year;
import java.util.Objects;

@Root
public class Film implements Serializable {

    @CsvBindByName(required = true)
    @Attribute
    private long id;

    @CsvBindByName(required = true)
    @Element
    private String name;

    @CsvBindByName(required = true)
    @Element
    private int year;

    public Film() {}

    public Film(String name, int year) throws IOException {
        this.id = System.currentTimeMillis();
        setName(name);
        setYear(year);
    }

    public void setName(String name) throws IOException {
        if (name.length() < 255) {
            this.name = name;
        } else throw new IOException(Constants.FILM_INVALID_NAME);
    }

    public void setYear(int year) throws IOException {
        if (year > 0 && year < Year.now().getValue() + 100) {
            this.year = year;
        } else throw new IOException(Constants.FILM_INVALID_YEAR);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
        // return "\nFilm { " + id + " // " + name + " (" + year +") }";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && year == film.year && name.equals(film.name);
    }

    public int hashCode() {
        return Objects.hash(id, name, year);
    }
}
