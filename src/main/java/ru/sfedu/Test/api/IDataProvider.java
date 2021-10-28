package ru.sfedu.Test.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.beans.Film;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IDataProvider {
    List<Film> getFilms() throws IOException;              // Получить все фильмы
    Film getById(long id) throws FileNotFoundException;    // Получить фильм по ID
    Result append(Film film) throws IOException,
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException;                  // Вставить бин фильма в файл
    Result delete(long id);                                // Удалить фильм по ID из файла
    Result update(long id);                                // Изменить фильм по ID в файле
}