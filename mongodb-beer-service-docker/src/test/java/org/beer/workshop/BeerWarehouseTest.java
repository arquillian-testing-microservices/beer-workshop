package org.beer.workshop;

import org.arquillian.cube.docker.impl.client.containerobject.dsl.Container;
import org.arquillian.cube.docker.impl.client.containerobject.dsl.DockerContainer;
import org.beer.workshop.boundary.BeerWarehouse;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class BeerWarehouseTest {

    @DockerContainer
    Container redis = Container.withContainerName("warehouse")
                            .fromImage("redis:3.2.6")
                            .withPortBinding(6379)
                            .build();

    @Test
    public void should_decreaase_stock_when_beer_new_beer() {
        // Given:
        BeerWarehouse beerWarehouse =
                new BeerWarehouse(redis.getIpAddress(), redis.getBindPort(6379));

        // When:
        beerWarehouse.increaseStock("1", 100);
        beerWarehouse.buyBeer("1", 15);

        // Then:
        assertThat(beerWarehouse.remainingStock("1")).isEqualTo(85);

    }

}
