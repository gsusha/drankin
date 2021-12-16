package ru.sfedu.test.api;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import ru.sfedu.test.Constants;
import ru.sfedu.test.model.HistoryContent;
import ru.sfedu.test.model.Result;
import ru.sfedu.test.model.ResultState;
import ru.sfedu.test.model.beans.Film;
import ru.sfedu.test.utils.ConfigurationUtil;
import ru.sfedu.test.utils.MongoUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataProviderCSV implements IDataProvider {
    private final String csvFile = ConfigurationUtil.getConfigurationEntry(Constants.CSV_FILE);
    private final File file = new File(csvFile);

    public DataProviderCSV() throws IOException {
        file.createNewFile();
    }

    private Result<Film> write(List<Film> films) {
        try {
            Writer writer = new FileWriter(file);
            StatefulBeanToCsv<Film> beanToCsv = new StatefulBeanToCsvBuilder<Film>(writer).build();
            beanToCsv.write(films);
            writer.close();
        } catch (Exception e) {
            sendLogs("write", films.get(films.size() - 1), ResultState.Error);
            return new Result<Film>(
                    List.of(films.get(films.size() - 1)), ResultState.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs("write", films.get(films.size() - 1), ResultState.Success);
        return new Result<Film>(
                List.of(films.get(films.size() - 1)), ResultState.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    private void sendLogs(String methodName, Film film, ResultState resultState) {
        HistoryContent historyContent = new HistoryContent(
                UUID.randomUUID(),
                this.getClass().getSimpleName(),
                LocalDateTime.now().toString(),
                Constants.DEFAULT_ACTOR,
                methodName,
                MongoUtil.objectToString(film),
                resultState);
        MongoUtil.saveToLog(historyContent);
    }

    @Override
    public List<Film> getFilms() {
        List<Film> films = new ArrayList<Film>();
        try {
            films = new CsvToBeanBuilder<Film>(new FileReader(file)).withType(Film.class).build().parse();
        } catch (Exception ignored) {
        }
        return films;
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
                film.setId();
        } catch (Exception ignored) {
        }
        List<Film> films = getFilms();
        films.add(film);
        write(films);
        return film;
    }

    @Override
    public Result<Film> delete(long id) {
        Film film = getById(id);
        if (film == null) {
            return new Result<Film>(new ArrayList<>(), ResultState.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        List<Film> films;
        films = getFilms();
        films.removeIf(a -> (a.getId() == id));
        return write(films);
    }

    @Override
    public Result<Film> update(Film film) {
        long id = film.getId();
        if (getById(id) == null) {
            return new Result<Film>(new ArrayList<>(), ResultState.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            delete(id);
            append(film);
        } catch (Exception e) {
            return new Result<Film>(
                    List.of(film), ResultState.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        return new Result<Film>(List.of(film), ResultState.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}
