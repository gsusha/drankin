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
    String url = ConfigurationUtil.getConfigurationEntry(Constants.H2_URL);
    String user = ConfigurationUtil.getConfigurationEntry(Constants.H2_USER);
    String password = ConfigurationUtil.getConfigurationEntry(Constants.H2_PASSWORD);

    public DataProviderH2() throws IOException {
    }

    private List<Film> read(String sql) throws SQLException, IOException {
        List<Film> films = new ArrayList<Film>();
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
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
        return films;
    }

    private void write(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
        statement.close();
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
            films = read("SELECT * FROM films");
        } catch (Exception ignored) {
        }
        return films;
    }

    @Override
    public Film getById(long id) {
        List<Film> films = new ArrayList<Film>();
        Film film = null;
        try {
            films = read("SELECT * FROM films WHERE id = " + id);
            if (films.isEmpty())
                return null;
            film = films.get(0);
        } catch (Exception ignored) {
        }
        return film;
    }

    @Override
    public Film append(Film film) {
        try {
            if (getById(film.getId()) != null)
                film.setId();
        } catch (Exception ignored) {
        }
        try {
            write("CREATE TABLE IF NOT EXISTS films(id LONG PRIMARY KEY, name VARCHAR(255), years INT);" +
                    "INSERT INTO films " +
                    "VALUES('" + film.getId() + "','" + film.getName() + "','" + film.getYear() + "');");
        } catch (Exception e) {
            sendLogs("append", film, ResultState.Error);
        }
        sendLogs("append", film, ResultState.Success);
        return film;
    }

    @Override
    public Result<Film> delete(long id) {
        Film film = getById(id);
        if (film == null) {
            return new Result<Film>(new ArrayList<>(), ResultState.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            write("DELETE FROM films WHERE id = " + id);
        } catch (Exception e) {
            sendLogs("delete", film, ResultState.Error);
            return new Result<Film>(
                    List.of(film), ResultState.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs("delete", film, ResultState.Success);
        return new Result<Film>(
                List.of(film), ResultState.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    @Override
    public Result<Film> update(Film film) {
        long id = film.getId();
        if (getById(id) == null) {
            return new Result<Film>(new ArrayList<>(), ResultState.Warning, Constants.RESULT_MESSAGE_NOT_FOUND);
        }
        try {
            write("UPDATE films " +
                    "SET name = '" + film.getName() + "', years = '" + film.getYear() + "" + "'" +
                    "WHERE id = " + id);
        } catch (Exception e) {
            sendLogs("update", film, ResultState.Error);
            return new Result<Film>(
                    List.of(film), ResultState.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs("update", film, ResultState.Success);
        return new Result<Film>(List.of(film), ResultState.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}
