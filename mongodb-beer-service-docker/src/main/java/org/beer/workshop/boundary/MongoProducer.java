package org.beer.workshop.boundary;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Properties;

@ApplicationScoped
public class MongoProducer {

    @Produces
    public MongoDatabase createMongoClient() {
        final String host = System.getenv("MONGODB_HOSTNAME");
        final String mongodbPort = System.getenv("MONGODB_PORT");

        if (host != null) {
            if (mongodbPort != null) {
                return new MongoClient(host, Integer.parseInt(mongodbPort)).getDatabase("test");
            } else {

            }
        }

        // Defaults
        return new MongoClient().getDatabase("test");
    }

}
