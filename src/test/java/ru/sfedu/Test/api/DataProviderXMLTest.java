package ru.sfedu.Test.api;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.Test.model.ResultState;
import ru.sfedu.Test.model.beans.Film;

import java.io.IOException;

public class DataProviderXMLTest {
    DataProviderXML dataProviderXML;

    @Before
    public void setUp() throws Exception {
        dataProviderXML = new DataProviderXML();
    }

    @After
    public void tearDown() {
        dataProviderXML = null;
    }

    @Test
    public void testAppend() throws IOException {
        Film film = new Film("Twilight", 2008);
        film = dataProviderXML.append(film);
        Assert.assertEquals(film, dataProviderXML.getById(film.getId()));
    }

    @Test
    public void testGettersPositive() throws IOException {
        dataProviderXML.getFilms();
        Film film = new Film("Twilight 2", 2009);
        film = dataProviderXML.append(film);
        Assert.assertEquals(film, dataProviderXML.getById(film.getId()));
    }

    @Test
    public void testDeletePositive() throws IOException {
        Film film = new Film("Twilight 3", 2010);
        film = dataProviderXML.append(film);
        Assert.assertEquals(ResultState.Success, dataProviderXML.delete(film.getId()).getResultState());
    }

    @Test
    public void testUpdatePositive() throws IOException {
        Film film = new Film("Twilight 4.1", 2011);
        film = dataProviderXML.append(film);
        film.setName("Twilight 4.2");
        film.setYear(2012);
        Assert.assertEquals(ResultState.Success, dataProviderXML.update(film).getResultState());
    }

    @Test
    public void testGettersNegative() {
        Assert.assertNull(dataProviderXML.getById(0L));
    }

    @Test
    public void testDeleteNegative() {
        Assert.assertEquals(ResultState.Warning, dataProviderXML.delete(0L).getResultState());
    }

    @Test
    public void testUpdateNegative() throws IOException {
        Assert.assertEquals(ResultState.Warning,
                dataProviderXML.update(new Film("Twilight 5", 2052)).getResultState());
    }
}