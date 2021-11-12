package ru.sfedu.Test.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.beans.Film;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IDataProvider {
    List<Film> getFilms() throws IOException;               // Получить все фильмы
    Film getById(long id) throws IOException;               // Получить фильм по ID
    Result append(Film film) throws IOException,            // Вставить бин фильма в файл
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException;
    Result delete(long id) throws IOException,              // Удалить фильм по ID из файла
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException;
    Result update(long id, Film film) throws IOException,   // Изменить фильм по ID в файле
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException;
}