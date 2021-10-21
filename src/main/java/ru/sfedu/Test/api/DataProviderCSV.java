package ru.sfedu.Test.api;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.beans.Film;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DataProviderCSV implements IDataProvider {



    @Override
    public List<Film> getFilms() {
        return null;
    }

    @Override
    public Film getById(long id) {
        return null;
    }

    @Override
    public Result append(Film film) {
        return null;
    }

    @Override
    public Result delete(long id) {
        return null;
    }

    @Override
    public Result update(long id) {
        return null;
    }
}
