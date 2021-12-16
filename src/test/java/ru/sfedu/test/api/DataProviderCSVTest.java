package ru.sfedu.test.api;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.test.model.ResultState;
import ru.sfedu.test.model.beans.Film;

import java.io.IOException;

public class DataProviderCSVTest extends TestCase {
    DataProviderCSV dataProviderCSV;

    @Before
    public void setUp() throws Exception {
        dataProviderCSV = new DataProviderCSV();
    }

    @After
    public void tearDown() {
        dataProviderCSV = null;
    }

    @Test
    public void testAppend() throws IOException {
        Film film = new Film("Twilight", 2008);
        film = dataProviderCSV.append(film);
        Assert.assertEquals(film, dataProviderCSV.getById(film.getId()));
    }

    @Test
    public void testGettersPositive() throws IOException {
        dataProviderCSV.getFilms();
        Film film = new Film("Twilight 2", 2009);
        film = dataProviderCSV.append(film);
        Assert.assertEquals(film, dataProviderCSV.getById(film.getId()));
    }

    @Test
    public void testDeletePositive() throws IOException {
        Film film = new Film("Twilight 3", 2010);
        film = dataProviderCSV.append(film);
        Assert.assertEquals(ResultState.Success, dataProviderCSV.delete(film.getId()).getResultState());
    }

    @Test
    public void testUpdatePositive() throws IOException {
        Film film = new Film("Twilight 4.1", 2011);
        film = dataProviderCSV.append(film);
        film.setName("Twilight 4.2");
        film.setYear(2012);
        Assert.assertEquals(ResultState.Success, dataProviderCSV.update(film).getResultState());
    }

    @Test
    public void testGettersNegative() {
        Assert.assertNull(dataProviderCSV.getById(0L));
    }

    @Test
    public void testDeleteNegative() {
        Assert.assertEquals(ResultState.Warning, dataProviderCSV.delete(0L).getResultState());
    }

    @Test
    public void testUpdateNegative() throws IOException {
        Assert.assertEquals(ResultState.Warning,
                dataProviderCSV.update(new Film("Twilight 5", 2052)).getResultState());
    }
}