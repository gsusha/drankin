package ru.sfedu.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.test.api.DataProviderCSV;
import ru.sfedu.test.api.DataProviderXML;
import ru.sfedu.test.model.beans.Film;

import java.io.IOException;

public class TestClient {
    private static final Logger log = LogManager.getLogger(TestClient.class);

    public static void main(String[] args) throws IOException {
        testCsvCrud();
        testXmlCrud();
    }

    public TestClient() {
        log.debug("TestClient: starting application");
    }

    private static void testXmlCrud() throws IOException {
        Film film;
        DataProviderXML dataProviderXML = new DataProviderXML();

        Film film1 = new Film("The Lion King", 1994);
        Film film2 = new Film("Interstellar", 2014);
        Film film3 = new Film("Forrest Gump", 1994);

        // Вставляем
        film1 = dataProviderXML.append(film1);
        film2 = dataProviderXML.append(film2);
        film3 = dataProviderXML.append(film3);

        // Получаем
        log.info(dataProviderXML.getFilms());
        log.info(dataProviderXML.getById(film1.getId()));

        // Удаляем
        log.info(dataProviderXML.delete(film2.getId()));

        // Апдейтим
        film = dataProviderXML.getById(film3.getId());
        film.setName("It");
        film.setYear(2017);
        log.info(dataProviderXML.update(film));
    }

    private static void testCsvCrud() throws IOException {
        Film film;
        DataProviderCSV dataProviderCSV = new DataProviderCSV();

        Film film1 = new Film("The Green Mile", 1999);
        Film film2 = new Film("The Shawshank Redemption", 1994);
        Film film3 = new Film("The Lord of the Rings", 2001);

        // Вставляем
        film1 = dataProviderCSV.append(film1);
        film2 = dataProviderCSV.append(film2);
        film3 = dataProviderCSV.append(film3);

        // Получаем
        log.info(dataProviderCSV.getFilms());
        log.info(dataProviderCSV.getById(film1.getId()));

        // Удаляем
        log.info(dataProviderCSV.delete(film2.getId()));

        // Апдейтим
        film = dataProviderCSV.getById(film3.getId());
        film.setName("It");
        film.setYear(2017);
        log.info(dataProviderCSV.update(film));
    }

}
