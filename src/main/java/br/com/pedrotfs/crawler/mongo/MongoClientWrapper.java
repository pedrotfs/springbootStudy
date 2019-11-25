package br.com.pedrotfs.crawler.mongo;

import com.mongodb.BasicDBList;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

@Component
public class MongoClientWrapper {

    @Value("${mongo.connection}")
    private String mongoConnection;

    @Value("${mongo.database}")
    private String databaseName;

    @Value("${mongo.database.collection.schedule}")
    private String schedule;

    @Value("${mongo.database.collection.schedule}")
    private String collectionSchedule;

    @Value("${mongo.database.collection.requests}")
    private String collectionRequest;

    @Value("${mongo.database.collection.register}")
    private String collectionRegister;

    private MongoClient mongoClient;

    private Document requestQueryDocument;

    public MongoDatabase setUpDbConnection()
    {
        mongoClient = MongoClients.create(mongoConnection);
        final MongoDatabase database = mongoClient.getDatabase(databaseName);
        final MongoCollection<Document> scheduleCollection = database.getCollection(schedule);
        final FindIterable<Document> documents = scheduleCollection.find();
        if(documents.first() == null) {
            BasicDBList array = new BasicDBList();
            array.add(DayOfWeek.TUESDAY.toString());
            array.add(DayOfWeek.THURSDAY.toString());
            array.add(DayOfWeek.SATURDAY.toString());
            final Document schedule = new Document("_id", "ltf").append("daysOfWeek", array);
            scheduleCollection.insertOne(schedule);
        }
        return database;
    }

    public void persistRequest(final String request) {
        if(request.isEmpty() || request.equalsIgnoreCase("X")) {
            return;
        }
        Document document = new Document().append("_id", "ltf").append("date", LocalDate.now().toString()).append("request", request);
        Bson query = getRequestQueryDocument();
        final MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionRequest);
        if(collection.find(query).first() == null) {
            collection.insertOne(document);
        } else {
            collection.replaceOne(query, document);
        }
    }

    public void persistPreSentRecords(final String register) {
        boolean debugInsert = true;
        final MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionRegister);
        Bson query = getRequestQueryDocument();
        if(collection.find(query).first() == null && debugInsert) {
            collection.insertOne(Document.parse(register));
        }
    }

    public boolean shouldUpdateSource()
    {
        final Document bson = getRequestQueryDocument();
        final Document scheduleDocument = mongoClient.getDatabase(databaseName).getCollection(collectionSchedule).find(bson).first();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH);
        if(isDayScheduledForUpdate(scheduleDocument)) {
            final Document requestDocument = recoverLastRequestDocument();
            if(requestDocument != null) {
                final LocalDate date = LocalDate.parse(requestDocument.get("date").toString(), formatter);
                return date.isBefore(LocalDate.now());
            }
        }
        return false; //needs database to save when last download and schedule
    }

    public String recoverLastRequest() {
        if(recoverLastRequestDocument() == null) {
            return "";
        }
        return recoverLastRequestDocument().get("request").toString();
    }

    public Document recoverLastRequestDocument() {
        Bson bson = getRequestQueryDocument();
        return mongoClient.getDatabase(databaseName).getCollection(collectionRequest).find(bson).first();
    }

    private boolean isDayScheduledForUpdate(Document schedule) {
        return ((ArrayList) schedule.get("daysOfWeek")).contains(LocalDate.now().getDayOfWeek().toString());
    }

    private Document getRequestQueryDocument() {
        if(requestQueryDocument == null) {
            requestQueryDocument = new Document("_id", "ltf");
        }
        return requestQueryDocument;
    }
}
