package ru.sfedu.Test.model.beans;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.io.IOException;
import java.time.Year;
import java.util.Objects;

public class Film implements Serializable {
    @CsvBindByName(column = "id", required = true)
    private long id;

    @CsvBindByName(column = "name", required = true)
    private String name;

    @CsvBindByName(column = "year", required = true)
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
        } else throw new IOException("Invalid name");
    }

    public void setYear(int year) throws IOException {
        if (year > 0 || year < Year.now().getValue()) {
            this.year = year;
        } else throw new IOException("Invalid year");
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
