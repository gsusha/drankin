package ru.sfedu.Test.api;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import ru.sfedu.Test.Constants;
import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.ResultState;
import ru.sfedu.Test.model.beans.Film;
import ru.sfedu.Test.utils.ConfigurationUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataProviderCSV implements IDataProvider {
    String csvFile = ConfigurationUtil.getConfigurationEntry(Constants.CSV_FILE);

    public DataProviderCSV() throws IOException {

    }

    @Override
    public List<Film> getFilms() {
        try {
            return new CsvToBeanBuilder<Film>(new FileReader(csvFile)).withType(Film.class).build().parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<Film>();
        }
    }

    @Override
    public Film getById(long id) {
        return getFilms().stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
    }

    @Override
    public Result<Film> append(Film film) {
        try {
            getById(film.getId()).getId();
            if (film.getId() == getById(film.getId()).getId()) {
                film = new Film(film.getName(), film.getYear());
            }
        } catch (Exception ignored) {};
        List<Film> films = new ArrayList<Film>();
        File file = new File(csvFile);
        if (file.exists() && file.length() > 0) {
            films = getFilms();
        }
        films.add(film);

        try {
            Writer writer = new FileWriter(csvFile);
            StatefulBeanToCsv<Film> beanToCsv = new StatefulBeanToCsvBuilder<Film>(writer).build();
            beanToCsv.write(films);
            writer.close();
        } catch (Exception e) {
            return new Result<Film>(films, ResultState.Error, e.toString());
        }

        return new Result<Film>(films, ResultState.Success, Constants.RESULT_MESSAGE_APPEND);
    }

    @Override
    public Result<Film> delete(long id) {
        List<Film> films = null;
        films = getFilms();
        films.removeIf(film -> (film.getId() == id));

        Writer writer = null;
        try {
            writer = new FileWriter(csvFile);
            StatefulBeanToCsv<Film> beanToCsv = new StatefulBeanToCsvBuilder<Film>(writer).build();
            beanToCsv.write(films);
            writer.close();
        } catch (Exception e) {
            return new Result<Film>(films, ResultState.Error, e.toString());
        }

        return new Result<Film>(films, ResultState.Success, Constants.RESULT_MESSAGE_DELETE);
    }

    @Override
    public Result<Film> update(Film film) {
        Film newFilm;
        try {
            newFilm = getById(film.getId());
            delete(film.getId());
            newFilm.setName(film.getName());
            newFilm.setYear(film.getYear());
            append(newFilm);
        } catch (Exception e) {
            return new Result<Film>(List.of(film), ResultState.Error, e.toString());
        }
        return new Result<Film>(List.of(newFilm), ResultState.Success, Constants.RESULT_MESSAGE_UPDATE);
    }
}
