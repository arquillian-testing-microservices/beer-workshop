package org.beer.workshop.boundary;

import com.mongodb.client.MongoCollection;
import org.beer.workshop.entity.Beer;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Stateless
@Lock(LockType.READ)
public class Beers {

    @Inject
    @MongoCollectionName("beer")
    MongoCollection<Document> beers;

    @Inject
    Logger tracer;

    public void persist(Beer beer) {
        tracer.info("beer arrived: " + beer);
        final Document document = toDocument(beer);
        beers.insertOne(document);
        beer.setId(document.get("_id", ObjectId.class).toHexString());
        tracer.info("beer stored:" + beer);
    }

    public long remove(String beerId) {
        return beers.deleteOne(new Document("_id", new ObjectId(beerId))).getDeletedCount();
    }


    public List<Beer> listAll() {
        return StreamSupport.stream(beers.find().spliterator(), false)
                .map(this::toBeer)
                .collect(Collectors.toList());

    }

    private Beer toBeer(Document document) {
        Beer beer = new Beer();
        beer.setId(document.getObjectId("_id").toHexString());
        beer.setPrice(document.getDouble("price"));
        beer.setAlcohol(document.getDouble("alcohol"));
        beer.setName(document.getString("name"));

        return beer;
    }

    private Document toDocument(Beer beer) {
        Document document = new Document("name", beer.getName());

        if (beer.getId() != null) {
            document.append("_id", new ObjectId(beer.getId()));
        }

        document.append("price", beer.getPrice())
                .append("alcohol", beer.getAlcohol());

        return document;

    }

}
