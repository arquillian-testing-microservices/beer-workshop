package org.beer.workshop.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.beer.workshop.entity.Beer;

/**
 *
 */
@Path("/beers")
public class BeerEndpoint {

    @Inject
    Beers beers;

    @POST
    @Consumes("application/json")
    public Response create(Beer entity) {
        beers.persist(entity);
        return Response.created(
                UriBuilder.fromResource(BeerEndpoint.class)
                        .path(String.valueOf(entity.getId())).build()).build();
    }

    @GET
    @Produces("application/json")
    public List<Beer> listAll() {
        final List<Beer> results = beers.listAll();
        return results;
    }

    @GET
    @Path("/healthcheck")
    @Produces("text/plain")
    public Response healthcheck() {
        return Response.ok("OK").build();
    }

}
