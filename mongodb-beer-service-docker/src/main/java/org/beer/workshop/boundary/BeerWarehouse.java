package org.beer.workshop.boundary;

import redis.clients.jedis.Jedis;

public class BeerWarehouse {

    private Jedis jedis;

    public BeerWarehouse(String host, int port) {
        this.jedis = new Jedis(host, port);
    }

    public long increaseStock(String beerId, long numberOfBeers) {
        return this.jedis.incrBy(beerId, numberOfBeers);
    }

    public long decreaseStock(String beerId, long numberOfBeers) {
        return this.jedis.decrBy(beerId, numberOfBeers);
    }

    public long remainingStock(String beerId) {
        return Long.parseLong(this.jedis.get(beerId));
    }

    public void buyBeer(String beerId, long numberOfBeers) {
        final long newStock = decreaseStock(beerId, numberOfBeers);

        if (newStock < 0) {
            // Restore stock
            increaseStock(beerId, numberOfBeers);
            throw new IllegalStateException("Not enough beers on the warehouse");
        }

    }

}
