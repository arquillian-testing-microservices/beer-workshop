import org.beer.workshop.boundary.BeerEndpoint;
import org.beer.workshop.boundary.Beers;
import org.beer.workshop.entity.Beer;
import org.beer.workshop.tracing.boundary.LoggerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class BeerEndpointTest {

    @Deployment(testable = false)
    public static WebArchive createDeploymentFile() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, BeerEndpoint.class.getPackage())
                .addClasses(Beer.class, LoggerProducer.class);

        return webArchive;

    }

    @Test
    public void should_find_all_beers() {

    }

}
