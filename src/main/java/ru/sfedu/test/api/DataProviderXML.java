package ru.sfedu.test.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.test.Constants;
import ru.sfedu.test.model.Result;
import ru.sfedu.test.model.ResultState;
import ru.sfedu.test.model.beans.Film;
import ru.sfedu.test.model.beans.FilmsWrapper;
import ru.sfedu.test.utils.ConfigurationUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class DataProviderXML implements IDataProvider {
    private final Serializer serializer = new Persister();
    private final String xmlFile = ConfigurationUtil.getConfigurationEntry(Constants.XML_FILE);
    private final File file = new File(xmlFile);

    public DataProviderXML() throws IOException {
        // TODO: Тут надо давать возможность указать хмл файл?
        file.createNewFile();
    }

    private Result<Film> write(List<Film> films) {
        try {
            Writer writer = new FileWriter(file);
            serializer.write(new FilmsWrapper(films), writer);
            writer.close();
        } catch (Exception e) {
            return new Result<Film>(
                    films, ResultState.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        return new Result<Film>(films, ResultState.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public List<Film> getFilms() {
        try {
            return serializer.read(FilmsWrapper.class, file).getFilms();
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
