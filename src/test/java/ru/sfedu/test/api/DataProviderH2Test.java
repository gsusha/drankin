package ru.sfedu.test.api;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.test.model.ResultState;
import ru.sfedu.test.model.beans.Film;

import java.io.IOException;

public class DataProviderH2Test extends TestCase {
    DataProviderH2 dataProviderH2;

    @Before
    public void setUp() throws Exception {
        dataProviderH2 = new DataProviderH2();
    }

    @After
    public void tearDown() {
        dataProviderH2 = null;
    }

    @Test
    public void testAppend() throws IOException {
        Film film = new Film("Twilight", 2008);
        film = dataProviderH2.append(film);
        Assert.assertEquals(film, dataProviderH2.getById(film.getId()));
    }

    @Test
    public void testGettersPositive() throws IOException {
        dataProviderH2.getFilms();
        Film film = new Film("Twilight 2", 2009);
        film = dataProviderH2.append(film);
        Assert.assertEquals(film, dataProviderH2.getById(film.getId()));
    }

    @Test
    public void testDeletePositive() throws IOException {
        Film film = new Film("Twilight 3", 2010);
        film = dataProviderH2.append(film);
        Assert.assertEquals(ResultState.Success, dataProviderH2.delete(film.getId()).getResultState());
    }

    @Test
    public void testUpdatePositive() throws IOException {
        Film film = new Film("Twilight 4.1", 2011);
        film = dataProviderH2.append(film);
        film.setName("Twilight 4.2");
        film.setYear(2012);
        Assert.assertEquals(ResultState.Success, dataProviderH2.update(film).getResultState());
    }

    @Test
    public void testGettersNegative() {
        Assert.assertNull(dataProviderH2.getById(0L));
    }

    @Test
    public void testDeleteNegative() {
        Assert.assertEquals(ResultState.Warning, dataProviderH2.delete(0L).getResultState());
    }

    @Test
    public void testUpdateNegative() throws IOException {
        Assert.assertEquals(ResultState.Warning,
                dataProviderH2.update(new Film("Twilight 5", 2052)).getResultState());
    }
}