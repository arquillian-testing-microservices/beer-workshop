package org.beer.workshop;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.arquillian.ape.nosql.NoSqlPopulator;
import org.arquillian.ape.nosql.mongodb.MongoDb;
import org.arquillian.cube.DockerUrl;
import org.arquillian.cube.HostIp;
import org.arquillian.cube.HostPort;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;

@RunWith(Arquillian.class)
public class BeerEndpointTest {

    @DockerUrl(containerName = "beer", exposedPort = 8080, context = "/beer/rest/beers/")
    @ArquillianResource
    URL beerService;

    @ArquillianResource
    @MongoDb
    NoSqlPopulator noSqlPopulator;

    @HostIp
    String dockerHost;

    @HostPort(containerName = "mongodb", value = 27017)
    int mongodbPort ;

    @Test
    public void should_find_all_beers() {

        // Given:

        noSqlPopulator
                .forServer(dockerHost, mongodbPort)
                .withStorage("test")
                .usingDataSet("beer.json")
                .execute();

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(beerService.toExternalForm());

        // When: Then:

        given()
                .contentType(ContentType.JSON)
                .spec(requestSpecBuilder.build())
                .get()
                .then()
                .assertThat()
                .body("name", hasItems("Voll Damm"));


    }

    @After
    public void cleanCollection() {
        noSqlPopulator.forServer(dockerHost, mongodbPort)
                .withStorage("test")
                .clean();
    }

}
