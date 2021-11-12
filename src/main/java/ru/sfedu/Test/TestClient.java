package ru.sfedu.Test;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Test.api.DataProviderCSV;
import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.beans.Film;
import ru.sfedu.Test.utils.ConfigurationUtil;

import java.io.IOException;

public class TestClient {
    private static Logger log = LogManager.getLogger(TestClient.class);

    public static void main (String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        //logBasicSystemInfo();

        //log.info(ConfigurationUtil.getConfigurationEntry(Constants.APP_NAME));
        //log.info(ConfigurationUtil.getConfigurationEntry(Constants.APP_NAME));
        //log.info(ConfigurationUtil.getConfigurationEntry(Constants.PATH_TO_HOME));

        log.error("\nHeyyy");

        Film film;
        Result result;
        DataProviderCSV dataProviderCSV = new DataProviderCSV();

        // Вставляем
        Film film1 = new Film("The Green Mile", 1999);
        Film film2 = new Film("The Shawshank Redemption", 1994);
        Film film3 = new Film("The Lord of the Rings: The Return of the King", 2003);
        Film film4 = new Film("The Lord of the Rings: The Two Towers", 2002);
        Film film5 = new Film("The Lord of the Rings: The Fellowship of the Ring", 2001);
        result = dataProviderCSV.append(film1);
        result = dataProviderCSV.append(film2);
        result = dataProviderCSV.append(film3);
        result = dataProviderCSV.append(film4);
        result = dataProviderCSV.append(film5);
        log.info(dataProviderCSV.getFilms());

        // Получаем
        film = dataProviderCSV.getById(1636669035602L);
        log.info(film);

        // Удаляем
        result = dataProviderCSV.delete(1636669035602L);
        log.info(dataProviderCSV.getFilms());

        // Апдейтим
        result = dataProviderCSV.update(1636669035603L, new Film("It", 2017));
        log.info(dataProviderCSV.getFilms());
    }

    public TestClient() {
        log.debug("ru.sfedu.Test.TestClient: starting application.........");
    }

    private static void logBasicSystemInfo(){
        log.info("Launching the application...");
        log.info("Operating System: " + System.getProperty("os.name") + " "
                        + System.getProperty("os.version"));
        log.info("JRE: " + System.getProperty("java.version"));
        log.info("Java Launched From: " + System.getProperty("java.home"));
        log.info("Class Path: " + System.getProperty("java.class.path"));
        log.info("Library Path: " + System.getProperty("java.library.path"));
        log.info("User Home Directory: " + System.getProperty("user.home"));
        log.info("User Working Directory: " + System.getProperty("user.dir"));
        log.info("Test INFO logging.");
    }

}
