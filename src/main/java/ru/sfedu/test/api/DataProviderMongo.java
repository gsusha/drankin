package ru.sfedu.test.api;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.sfedu.test.Constants;
import ru.sfedu.test.model.Result;
import ru.sfedu.test.model.beans.Film;
import ru.sfedu.test.utils.ConfigurationUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProviderMongo implements IDataProvider {
    private final ConnectionString connectionString = new ConnectionString(
            ConfigurationUtil.getConfigurationEntry(Constants.MONGO_DB));
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
    private final MongoClient mongoClient = MongoClients.create(settings);
    private final MongoDatabase database = mongoClient.getDatabase("test");

    public DataProviderMongo() throws IOException {
    }

    @Override
    public List<Film> getFilms() {
        MongoCollection<Document> collection = database.getCollection("films");
        List films = new ArrayList<>();
        collection.find().into(films);
        System.out.println(films);
        return null;
    }

    @Override
    public Film getById(long id) {
        return null;
    }

    @Override
    public Film append(Film film) {
        return null;
    }

    @Override
    public Result<Film> delete(long id) {
        return null;
    }

    @Override
    public Result<Film> update(Film film) {
        return null;
    }
}
