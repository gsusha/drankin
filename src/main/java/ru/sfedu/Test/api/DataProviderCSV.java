package ru.sfedu.Test.api;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import ru.sfedu.Test.Constants;
import ru.sfedu.Test.model.Result;
import ru.sfedu.Test.model.beans.Film;
import ru.sfedu.Test.utils.ConfigurationUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataProviderCSV implements IDataProvider {

    String csv_file = ConfigurationUtil.getConfigurationEntry(Constants.CSV_FILE);

    public DataProviderCSV() throws IOException {

    }

    @Override
    public List<Film> getFilms() throws IOException {
        return new CsvToBeanBuilder<Film>(new FileReader(csv_file)).withType(Film.class).build().parse();
    }

    @Override
    public Film getById(long id)  throws FileNotFoundException {
        return (Film) new CsvToBeanBuilder<Film>(new FileReader(csv_file)).withType(Film.class).build().parse().get((int) id);
    }

    @Override
    public Result append(Film film) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
//        List<Film> beans = new ArrayList<Film>();
//        beans.add(film);

        Writer writer = new FileWriter(csv_file);
        StatefulBeanToCsv<Film> beanToCsv = new StatefulBeanToCsvBuilder<Film>(writer)
                .withQuotechar(CSVWriter.DEFAULT_SEPARATOR).build();
        beanToCsv.write(film);
//        beanToCsv.write(beans);
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
