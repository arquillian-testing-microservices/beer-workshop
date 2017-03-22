package org.beer.workshop.boundary;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.Set;

@ApplicationScoped
public class MongoCollectionProducer {

    @Inject
    MongoDatabase mongoDatabase;

    @Produces
    @MongoCollectionName
    public MongoCollection<Document> collection(InjectionPoint injectionPoint) {
        final MongoCollectionName mongoCollectionNameAnnotation = annotation(injectionPoint.getQualifiers(), MongoCollectionName.class);

        if (mongoCollectionNameAnnotation != null) {
            String collectionName = mongoCollectionNameAnnotation.value();
            return mongoDatabase.getCollection(collectionName);
        }

        throw new IllegalArgumentException("Filed not annotated with org.beer.workshop.boundary.MongoCollectionName");

    }

    private static <T> T annotation(Set<Annotation> annotations, Class<T> clazz) {

        for (Annotation annotation : annotations) {
            if(clazz.isInstance(annotation)) {
                return clazz.cast(annotation);
            }
        }

        return null;
    }

}
