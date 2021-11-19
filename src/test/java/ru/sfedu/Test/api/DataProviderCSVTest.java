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

    public void testGetters() {
        dataProviderCSV.getFilms();
    }

    public void testAppend() {
        dataProviderCSV.append(film1);
    }

    public void testDelete() {
        dataProviderCSV.delete(1636669035601L);
    }

    public void testUpdate() throws IOException {
        Film film = dataProviderCSV.getById(1636669035602L);
        film.setName(film2.getName());
        film.setYear(film2.getYear());
        dataProviderCSV.update(film);
    }
}