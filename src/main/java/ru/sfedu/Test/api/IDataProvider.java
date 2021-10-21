package ru.sfedu.Test.api;

import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.beans.Film;

import java.io.File;
import java.util.List;

public interface IDataProvider {
    List<Film> getFilms();     // Получить все фильмы
    Film getById(long id);              // Получить фильм по ID
    Result append(Film film);           // Вставить фильм из бина в файл
    Result delete(long id);             // Удалить фильм по ID из файла
    Result update(long id);             // Изменить фильм по ID в файле
}