package org.ingredients.workshop;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.ingredients.workshop.boundary.Ingredients;
import org.ingredients.workshop.entity.Ingredient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URISyntaxException;
import java.net.URL;

@RunWith(Arquillian.class)
public class IngredientsTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "ingredients.war").addPackages(true, Ingredients.class.getPackage())
                .addClasses(Ingredient.class)
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                .importCompileAndRuntimeDependencies().resolve().withTransitivity().as(JavaArchive.class));
    }

    @ArquillianResource
    URL ingredientsService;

    @Test
    public void should_get_ingredients_by_beer_name() throws URISyntaxException {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(ingredientsService.toURI());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .spec(requestSpecBuilder.build())
                .pathParam("beerName", "Voll Damm")
                .get("{beerName}")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .body("$.size()", CoreMatchers.is(4));

    }

}
