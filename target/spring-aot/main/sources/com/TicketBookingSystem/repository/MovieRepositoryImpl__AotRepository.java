package com.TicketBookingSystem.repository;

import com.TicketBookingSystem.model.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link MovieRepository}.
 */
@Generated
public class MovieRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public MovieRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link MovieRepository#findByCityIgnoreCase(java.lang.String)}.
   */
  public List<Movie> findByCityIgnoreCase(String city) {
    String queryString = "SELECT m FROM Movie m WHERE UPPER(m.city) = UPPER(:city)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("city", city != null ? city.toUpperCase() : city);

    return (List<Movie>) query.getResultList();
  }
}
