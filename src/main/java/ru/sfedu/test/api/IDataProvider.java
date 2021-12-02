package ru.sfedu.test.api;

import ru.sfedu.test.model.Result;
import ru.sfedu.test.model.beans.Film;

import java.util.List;

public interface IDataProvider {
    List<Film> getFilms();              // Получить все фильмы

    Film getById(long id);              // Получить фильм по ID

    Film append(Film film);             // Вставить бин фильма в файл

    Result<Film> delete(long id);       // Удалить фильм по ID из файла

    Result<Film> update(Film film);     // Изменить фильм по ID в файле
}