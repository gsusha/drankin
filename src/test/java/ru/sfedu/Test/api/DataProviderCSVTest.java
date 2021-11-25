package ru.sfedu.Test.api;

import junit.framework.TestCase;
import ru.sfedu.Test.model.beans.Film;

import java.io.IOException;

public class DataProviderCSVTest extends TestCase {
    DataProviderCSV dataProviderCSV;
    Film film1;
    Film film2;
    Film film3;
    Film film4;
    Film film5;

    public void setUp() throws Exception {
        super.setUp();
        dataProviderCSV = new DataProviderCSV();
        film1 = new Film("The Lion King", 1994);
        film2 = new Film("Ivan Vasil'evich menyaet professiyu", 1973);
        film3 = new Film("Interstellar", 2014);
        film4 = new Film("Forrest Gump", 1994);
        film5 = new Film("Coco", 2017);
    }

    public void tearDown() {
    }

    public void testAppend() {
        dataProviderCSV.append(film1);
        dataProviderCSV.append(film2);
        dataProviderCSV.append(film3);
        dataProviderCSV.append(film4);
        dataProviderCSV.append(film5);
    }

    public void testGetters() {
        dataProviderCSV.getFilms();
    }

    public void testDelete() {
        dataProviderCSV.delete(film1.getId());
        dataProviderCSV.delete(film3.getId());
        dataProviderCSV.delete(film5.getId());
    }

    public void testUpdate() throws IOException {
        Film film = new Film ("Dune", 2021);
        dataProviderCSV.append(film);
        film.setName("Star Wars IV: A New Hope");
        film.setYear(1977);
        dataProviderCSV.update(film);
    }
}