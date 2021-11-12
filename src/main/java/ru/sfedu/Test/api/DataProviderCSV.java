package ru.sfedu.Test.api;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Test.Constants;
import ru.sfedu.Test.TestClient;
import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.beans.Film;
import ru.sfedu.Test.utils.ConfigurationUtil;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataProviderCSV implements IDataProvider {
    private static final Logger log = LogManager.getLogger(TestClient.class);

    String csv_file = ConfigurationUtil.getConfigurationEntry(Constants.CSV_FILE);

    public DataProviderCSV() throws IOException {

    }

    @Override
    public Result append(Film film) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Film> films = new ArrayList<Film>();

        File file = new File(csv_file);
        if (file.exists() && file.length() > 0) {
            films = getFilms();
        }

        films.add(film);

        Writer writer = new FileWriter(csv_file);
        StatefulBeanToCsv<Film> beanToCsv = new StatefulBeanToCsvBuilder<Film>(writer).build();
        beanToCsv.write(films);
        writer.close();

        return null;
    }

    @Override
    public List<Film> getFilms() throws IOException {
        return new CsvToBeanBuilder<Film>(new FileReader(csv_file)).withType(Film.class).build().parse();
    }

    @Override
    public Film getById(long id) throws IOException {
        return getFilms().stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
    }

    @Override
    public Result delete(long id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Film> films = getFilms();
        films.removeIf(film -> (film.getId() == id));

        Writer writer = new FileWriter(csv_file);
        StatefulBeanToCsv<Film> beanToCsv = new StatefulBeanToCsvBuilder<Film>(writer).build();
        beanToCsv.write(films);
        writer.close();

        return null;
    }

    @Override
    public Result update(long id, Film film) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Film newFilm = getById(id);
        delete(id);
        newFilm.setName(film.getName());
        newFilm.setYear(film.getYear());
        append(newFilm);
        return null;
    }
}
