package org.beer.workshop;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.arquillian.ape.nosql.NoSqlPopulator;
import org.arquillian.ape.nosql.mongodb.MongoDb;
import org.beer.workshop.boundary.BeerEndpoint;
import org.beer.workshop.entity.Beer;
import org.beer.workshop.tracing.boundary.LoggerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;

@RunWith(Arquillian.class)
public class BeerEndpointTest {

    @Deployment(testable = false)
    public static WebArchive createDeploymentFile() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, BeerEndpoint.class.getPackage())
                .addClasses(Beer.class, LoggerProducer.class)
                .addAsLibraries(
                        Maven.resolver()
                                .loadPomFromFile("pom.xml")
                                .importCompileAndRuntimeDependencies()
                                .resolve().withTransitivity()
                                .as(JavaArchive.class)
                );

        return webArchive;

    }

    @ArquillianResource
    URL serviceUrl;

    @ArquillianResource
    @MongoDb
    NoSqlPopulator noSqlPopulator;

    @Test
    public void should_find_all_beers() {

        // Given:

        noSqlPopulator
                .forServer("localhost", 27017)
                .withStorage("test")
                .usingDataSet("beer.json")
                .execute();

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(serviceUrl.toExternalForm() + "rest/beers/");

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
        noSqlPopulator.forServer("localhost", 27017)
                .withStorage("test")
                .clean();
    }

}



