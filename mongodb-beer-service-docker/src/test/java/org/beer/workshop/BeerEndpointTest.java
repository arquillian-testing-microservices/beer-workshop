package org.beer.workshop;

import org.arquillian.cube.DockerUrl;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

@RunWith(Arquillian.class)
public class BeerEndpointTest {

    @DockerUrl(containerName = "beer", exposedPort = 8080, context = "/rest/beers/")
    @ArquillianResource
    URL beerService;

    @Test
    public void should_find_all_beers() {
        System.out.println(beerService);
    }

}
