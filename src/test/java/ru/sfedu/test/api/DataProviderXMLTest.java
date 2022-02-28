package ru.sfedu.test.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.test.model.ResultState;
import ru.sfedu.test.model.beans.Film;

import java.io.IOException;

public class DataProviderXMLTest {
    DataProviderXML dataProviderXML;

    @BeforeEach
    public void setUp() throws Exception {
        dataProviderXML = new DataProviderXML();
    }

    @AfterEach
    public void tearDown() {
        dataProviderXML = null;
    }

    @Test
    public void testAppend() throws IOException {
        Film film = new Film("Twilight", 2008);
        film = dataProviderXML.append(film);
        Assertions.assertEquals(film, dataProviderXML.getById(film.getId()));
    }

    @Test
    public void testGettersPositive() throws IOException {
        dataProviderXML.getFilms();
        Film film = new Film("Twilight 2", 2009);
        film = dataProviderXML.append(film);
        Assertions.assertEquals(film, dataProviderXML.getById(film.getId()));
    }

    @Test
    public void testDeletePositive() throws IOException {
        Film film = new Film("Twilight 3", 2010);
        film = dataProviderXML.append(film);
        Assertions.assertEquals(ResultState.Success, dataProviderXML.delete(film.getId()).getResultState());
    }

    @Test
    public void testUpdatePositive() throws IOException {
        Film film = new Film("Twilight 4.1", 2011);
        film = dataProviderXML.append(film);
        film.setName("Twilight 4.2");
        film.setYear(2012);
        Assertions.assertEquals(ResultState.Success, dataProviderXML.update(film).getResultState());
    }

    @Test
    public void testGettersNegative() {
        Assertions.assertNull(dataProviderXML.getById(0L));
    }

    @Test
    public void testDeleteNegative() {
        Assertions.assertEquals(ResultState.Warning, dataProviderXML.delete(0L).getResultState());
    }

    @Test
    public void testUpdateNegative() throws IOException {
        Assertions.assertEquals(ResultState.Warning,
                dataProviderXML.update(new Film("Twilight 5", 2052)).getResultState());
    }
}