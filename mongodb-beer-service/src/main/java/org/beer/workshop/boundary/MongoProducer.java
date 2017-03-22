package org.beer.workshop.boundary;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class MongoProducer {

    @Produces
    public MongoDatabase createMongoClient() {
        return new MongoClient().getDatabase("test");
    }

}
