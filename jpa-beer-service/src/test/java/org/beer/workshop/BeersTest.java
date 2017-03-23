package org.beer.workshop;

import org.beer.workshop.boundary.Beers;
import org.beer.workshop.entity.Beer;
import org.beer.workshop.tracing.boundary.LoggerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class BeersTest {

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addClasses(Beers.class, Beer.class, LoggerProducer.class)
                .addAsWebInfResource("persistence-test.xml", "classes/META-INF/persistence.xml")
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importTestDependencies().resolve().withTransitivity()
                        .as(JavaArchive.class));

        return webArchive;

    }

    @Inject
    Beers beers;

    @Test
    public void should_final_all_beers() {

        // Given:

        Beer beer = new Beer();
        beer.setName("Voll Damn");
        beer.setPrice(1.5);
        beer.setAlcohol(7.2);
        beers.persist(beer);

        // When:

        final List<Beer> beers = this.beers.listAll(0, Integer.MAX_VALUE);

        // Then:

        assertThat(beers)
                .hasSize(1)
                .extracting(Beer::getName)
                .contains("Voll Damn");

    }

}
