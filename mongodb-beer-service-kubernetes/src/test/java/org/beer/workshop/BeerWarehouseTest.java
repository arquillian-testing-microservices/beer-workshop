package org.beer.workshop;


import io.fabric8.kubernetes.api.model.Service;
import org.arquillian.cube.kubernetes.annotations.PortForward;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Named;
import java.net.URL;

@RunWith(Arquillian.class)
public class BeerWarehouseTest {

    @Named("beer")
    @ArquillianResource
    Service helloWorld;

    @org.arquillian.cube.kubernetes.annotations.Named("beer")
    @PortForward
    @ArquillianResource
    URL url;

    @Test
    public void service_should_not_null() {
        Assert.assertNotNull(helloWorld);
    }
}
