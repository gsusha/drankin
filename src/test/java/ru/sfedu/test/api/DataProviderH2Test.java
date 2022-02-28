package ru.sfedu.test.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.test.model.ResultState;
import ru.sfedu.test.model.beans.Film;

import java.io.IOException;

public class DataProviderH2Test {
    DataProviderH2 dataProviderH2;

    @BeforeEach
    public void setUp() throws Exception {
        dataProviderH2 = new DataProviderH2();
    }

    @AfterEach
    public void tearDown() {
        dataProviderH2 = null;
    }

    @Test
    public void testAppend() throws IOException {
        Film film = new Film("Twilight", 2008);
        film = dataProviderH2.append(film);
        Assertions.assertEquals(film, dataProviderH2.getById(film.getId()));
    }

    @Test
    public void testGettersPositive() throws IOException {
        dataProviderH2.getFilms();
        Film film = new Film("Twilight 2", 2009);
        film = dataProviderH2.append(film);
        Assertions.assertEquals(film, dataProviderH2.getById(film.getId()));
    }

    @Test
    public void testDeletePositive() throws IOException {
        Film film = new Film("Twilight 3", 2010);
        film = dataProviderH2.append(film);
        Assertions.assertEquals(ResultState.Success, dataProviderH2.delete(film.getId()).getResultState());
    }

    @Test
    public void testUpdatePositive() throws IOException {
        Film film = new Film("Twilight 4.1", 2011);
        film = dataProviderH2.append(film);
        film.setName("Twilight 4.2");
        film.setYear(2012);
        Assertions.assertEquals(ResultState.Success, dataProviderH2.update(film).getResultState());
    }

    @Test
    public void testGettersNegative() {
        Assertions.assertNull(dataProviderH2.getById(0L));
    }

    @Test
    public void testDeleteNegative() {
        Assertions.assertEquals(ResultState.Warning, dataProviderH2.delete(0L).getResultState());
    }

    @Test
    public void testUpdateNegative() throws IOException {
        Assertions.assertEquals(ResultState.Warning,
                dataProviderH2.update(new Film("Twilight 5", 2052)).getResultState());
    }
}