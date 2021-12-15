package ru.sfedu.test.api;

import ru.sfedu.test.Constants;
import ru.sfedu.test.model.HistoryContent;
import ru.sfedu.test.model.Result;
import ru.sfedu.test.model.ResultState;
import ru.sfedu.test.model.beans.Film;
import ru.sfedu.test.utils.ConfigurationUtil;
import ru.sfedu.test.utils.MongoUtil;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataProviderH2 implements IDataProvider {
    private final String url = ConfigurationUtil.getConfigurationEntry(Constants.H2_URL);
    private final String user = ConfigurationUtil.getConfigurationEntry(Constants.H2_USER);
    private final String password = ConfigurationUtil.getConfigurationEntry(Constants.H2_PASSWORD);

    public DataProviderH2() throws IOException{}

    private Result<Film> write(List<Film> films) {
        // TODO: Тут чота с SQL для записи. Наверн инсерт валуес
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            for (Film film: films) {
                statement.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS films(id LONG PRIMARY KEY, name VARCHAR(255), years INT);" +
                        "INSERT INTO films " +
                                "VALUES('" + film.getId() + "','" + film.getName() + "','" + film.getYear() + "');");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            //sendLogs("write", films.get(films.size() - 1), ResultState.Error);
            e.printStackTrace();
            return new Result<Film>(
                    films, ResultState.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        //sendLogs("write", films.get(films.size() - 1), ResultState.Success);
        return new Result<Film>(films, ResultState.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
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
    };

    @Override
    public List<Film> getFilms() {
        // TODO: Тут чота для получения. Наверн СЕЛЕКТ * (не ори на весь дом людей разбудеш ладно мам больше не буду)
        List<Film> films = new ArrayList<Film>();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM films");
            while (resultSet.next()) {
                Film film = new Film(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                films.add(film);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Film>();
        }
        return films;
    }

    @Override
    public Film getById(long id) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM films WHERE id = " + id);
            if (resultSet.next()) {
                Film film = new Film(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                return new Film(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

//        List<Film> films = getFilms().stream().filter(a -> a.getId() == id).toList();
//        return films.isEmpty() ? null : films.get(0);
    }

    @Override
    public Film append(Film film) {
        try {
            Film film1 = getById(film.getId());
            System.out.println(film1 != null);
            if (film1 != null)
                film = new Film(film.getName(), film.getYear());
        } catch (Exception ignored) {}
        List<Film> films = List.of(film);
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
