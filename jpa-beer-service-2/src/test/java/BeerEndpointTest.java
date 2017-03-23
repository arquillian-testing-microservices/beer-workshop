import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.beer.workshop.boundary.BeerEndpoint;
import org.beer.workshop.entity.Beer;
import org.beer.workshop.tracing.boundary.LoggerProducer;
import org.hamcrest.CoreMatchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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
                .addAsWebInfResource("persistence-test.xml", "classes/META-INF/persistence.xml");

        return webArchive;

    }

    @ArquillianResource
    URL serviceUrl;

    @Test
    public void should_find_all_beers() {

        // Given:

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(serviceUrl.toExternalForm() + "rest/beers/");

        createBeer(requestSpecBuilder);

        // When: Then:

        given()
                .contentType(ContentType.JSON)
                .spec(requestSpecBuilder.build())
                .get()
                .then()
                .assertThat()
                .body("name", hasItems("Voll Damn"));


    }

    private void createBeer(RequestSpecBuilder requestSpecBuilder) {
        Beer beer = new Beer();
        beer.setName("Voll Damn");
        beer.setPrice(1.5);
        beer.setAlcohol(7.2);

        given()
                .contentType(ContentType.JSON)
                .spec(requestSpecBuilder.build())
                .body(beer)
                .post()
                .then()
                .assertThat()
                .statusCode(201);
    }

}
