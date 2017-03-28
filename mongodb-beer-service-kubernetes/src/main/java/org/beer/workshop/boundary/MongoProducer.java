package org.beer.workshop.boundary;


import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Arrays;
import java.util.Properties;

@ApplicationScoped
public class MongoProducer {

    @Produces
    public MongoDatabase createMongoClient() {
        final String host = System.getenv("MONGODB_HOSTNAME");
        final String mongodbPort = System.getenv("MONGODB_PORT");
        final String username = System.getenv("MONGODB_USER");
        final String password = System.getenv("MONGODB_PASSWORD");
        final String database = System.getenv("MONGODB_DATABASE");

        MongoCredential mongoCredential = null;

        if (username != null) {
            mongoCredential = MongoCredential.createCredential(username,
                    database,
                    password.toCharArray());

        }

        if (host != null) {
            if (mongodbPort != null) {
                ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(mongodbPort));
                return new MongoClient(serverAddress, Arrays.asList(mongoCredential)).getDatabase(database == null ? "test" : database);
            }
        }

        // Defaults
        return new MongoClient().getDatabase(database == null ? "test" : database);
    }

}
