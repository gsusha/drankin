package ru.sfedu.Test.api;

import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.beans.Film;

import java.util.List;

public interface IDataProvider {
    List<Film> getFilms();              // Получить все фильмы

    Film getById(long id);              // Получить фильм по ID

    Film append(Film film);             // Вставить бин фильма в файл

    Result<Film> delete(long id);       // Удалить фильм по ID из файла

    Result<Film> update(Film film);     // Изменить фильм по ID в файле
}