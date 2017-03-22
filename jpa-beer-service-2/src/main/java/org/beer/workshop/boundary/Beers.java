package org.beer.workshop.boundary;

import org.beer.workshop.entity.Beer;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Lock(LockType.READ)
public class Beers {

    @PersistenceContext(unitName = "core-persistence-unit")
    EntityManager em;

    @Inject
    Logger tracer;

    public void persist(Beer beer) {
        tracer.info("beer arrived: " + beer);
        em.persist(beer);
        tracer.info("beer stored:" + beer);
    }

    public int remove(Long beer) {
        final Query query = em.createQuery("DELETE FROM Beer b WHERE b.id = :entityId");
        query.setParameter("entityId", beer);
        return query.executeUpdate();
    }

    public Beer findById(Long id) {
        TypedQuery<Beer> findByIdQuery = em
                .createQuery(
                        "SELECT DISTINCT b FROM Beer b WHERE b.id = :entityId ORDER BY b.id",
                        Beer.class);
        findByIdQuery.setParameter("entityId", id);
        Beer beer;
        try {
            beer = findByIdQuery.getSingleResult();
        } catch (NoResultException nre) {
            beer = null;
        }

        return beer;
    }

    public List<Beer> listAll(Integer startPosition, Integer maxResult) {
        TypedQuery<Beer> findAllQuery = em.createQuery(
                "SELECT DISTINCT b FROM Beer b ORDER BY b.id", Beer.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }

    public Beer update(Long id, Beer entity) {

        if (em.find(Beer.class, id) != null) {
            return em.merge(entity);
        }

        return null;
    }

}
