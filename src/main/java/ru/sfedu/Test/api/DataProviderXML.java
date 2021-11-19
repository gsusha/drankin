package ru.sfedu.Test.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.Test.Constants;
import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.ResultState;
import ru.sfedu.Test.model.beans.Film;
import ru.sfedu.Test.utils.ConfigurationUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class DataProviderXML implements IDataProvider {
    String xmlFile = ConfigurationUtil.getConfigurationEntry(Constants.XML_FILE);

    public DataProviderXML() throws IOException {
    }

    @Override
    public List<Film> getFilms() {
        return null;
    }

    @Override
    public Film getById(long id) {
        Serializer serializer = new Persister();
        File file = new File(xmlFile);

        try {
            Film film = serializer.read(Film.class, file);
            return film;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result append(Film film) {
        Serializer serializer = new Persister();
        try {
            // TODO: Фильмы вставляются не с новой строки
            Writer writer = new FileWriter(xmlFile, false);
            serializer.write(film, writer);
        } catch (Exception e) {
            return new Result<Film>(List.of(film), ResultState.Error, e.toString());
        }
        return new Result<Film>(List.of(film), ResultState.Success, Constants.RESULT_MESSAGE_APPEND);
    }

    @Override
    public Result delete(long id) {
        return null;
    }

    @Override
    public Result update(Film film) {
        return null;
    }
}
