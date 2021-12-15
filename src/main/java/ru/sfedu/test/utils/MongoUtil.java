package ru.sfedu.test.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import ru.sfedu.test.Constants;
import ru.sfedu.test.model.HistoryContent;

import java.io.IOException;

public class MongoUtil {
    private static final Logger log = LogManager.getLogger(MongoUtil.class);

    public MongoUtil() {}

    private static MongoDatabase initDb(String dbName) {
        try {
            String mongoDb = ConfigurationUtil.getConfigurationEntry(Constants.MONGO_DB);
            ConnectionString connectionString = new ConnectionString(mongoDb);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            return mongoClient.getDatabase(dbName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveToLog(HistoryContent historyContent) {
        saveObject(historyContent);
    }

    private static <T> void saveObject(T object) {
        try {
            MongoDatabase database = initDb("Films");
            InsertOneResult insertOneResult = database.getCollection("logs")
                    .insertOne(Document.parse(objectToString(object)));
            if(!insertOneResult.wasAcknowledged())
                throw new IllegalArgumentException();
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }

    public static String objectToString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T stringToObject(String json, Class<T> objectType) {
        try {
            return new ObjectMapper().readValue(json, objectType);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
