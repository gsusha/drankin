package ru.sfedu.Test.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.beans.Film;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IDataProvider {
    List<Film> getFilms();              // Получить все фильмы
    Film getById(long id);              // Получить фильм по ID
    Result<Film> append(Film film);     // Вставить бин фильма в файл
    Result<Film> delete(long id);       // Удалить фильм по ID из файла
    Result<Film> update(Film film);     // Изменить фильм по ID в файле
}