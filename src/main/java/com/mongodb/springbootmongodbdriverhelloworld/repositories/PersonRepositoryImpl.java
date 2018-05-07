package com.mongodb.springbootmongodbdriverhelloworld.repositories;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.springbootmongodbdriverhelloworld.models.Person;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class PersonRepositoryImpl implements PersonRepository {

    private final MongoCollection<Person> personCollection;

    public PersonRepositoryImpl(MongoClient mongoClient) {
        MongoDatabase db = mongoClient.getDatabase("test");
        personCollection = db.getCollection("person", Person.class);
    }

    @Override
    public void insert(Person person) {
        personCollection.insertOne(person);
    }

    @Override
    public Person findOne(String id) {
        return personCollection.find(Filters.eq("_id", new ObjectId(id))).first();
    }

    @Override
    public List<Person> findAll() {
        FindIterable<Person> people = personCollection.find();
        List<Person> persons = new ArrayList<>();
        people.forEach((Consumer<Person>) persons::add);
        return persons;
    }

    @Override
    public void insertMany(List<Person> persons) {
        personCollection.insertMany(persons);
    }

    @Override
    public void delete(String id) {
        personCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    @Override
    public void deleteAll() {
        personCollection.deleteMany(new BsonDocument());
    }

    @Override
    public void update(Person person) {
        personCollection.replaceOne(Filters.eq("_id", person.getId()), person);
    }
}
