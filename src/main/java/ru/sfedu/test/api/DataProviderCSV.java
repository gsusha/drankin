package ru.sfedu.test.api;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import ru.sfedu.test.Constants;
import ru.sfedu.test.model.Result;
import ru.sfedu.test.model.ResultState;
import ru.sfedu.test.model.beans.Film;
import ru.sfedu.test.utils.ConfigurationUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataProviderCSV implements IDataProvider {
    private final String csvFile = ConfigurationUtil.getConfigurationEntry(Constants.CSV_FILE);
    private final File file = new File(csvFile);

    public DataProviderCSV() throws IOException {
        // TODO: Тут надо давать возможность указать цсв файл?
        file.createNewFile();
    }

    private Result<Film> write(List<Film> films) {
        try {
            Writer writer = new FileWriter(file);
            StatefulBeanToCsv<Film> beanToCsv = new StatefulBeanToCsvBuilder<Film>(writer).build();
            beanToCsv.write(films);
            writer.close();
        } catch (Exception e) {
            return new Result<Film>(
                    films,
                    ResultState.Error,
                    Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        return new Result<Film>(films, ResultState.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Film> getFilms() {
        try {
            return new CsvToBeanBuilder<Film>(new FileReader(file)).withType(Film.class).build().parse();
        } catch (Exception ignored) {}
        return new ArrayList<Film>();
    }

    @Override
    public Film getById(long id) {
        List<Film> films = getFilms().stream().filter(a -> a.getId() == id).toList();
        return films.isEmpty() ? null : films.get(0);
    }

    @Override
    public Film append(Film film) {
        try {
            if (getById(film.getId()) != null)
                film = new Film(film.getName(), film.getYear());
        } catch (Exception ignored) {}
        List<Film> films = getFilms();
        films.add(film);
        write(films);
        return film;
    }

    @Override
    public Result<Film> delete(long id) {
        if (getById(id) == null) {
            return new Result<Film>(getFilms(), ResultState.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Film> films;
        films = getFilms();
        films.removeIf(film -> (film.getId() == id));
        return write(films);
    }

    @Override
    public Result<Film> update(Film film) {
        if (getById(film.getId()) == null) {
            return new Result<Film>(getFilms(), ResultState.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            delete(film.getId());
            append(film);
        } catch (Exception e) {
            return new Result<Film>(List.of(film), ResultState.Error, e.toString());
        }
        return new Result<Film>(List.of(film), ResultState.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}
