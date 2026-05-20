package com.TicketBookingSystem.repository;

import com.TicketBookingSystem.model.Show;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Long;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link ShowRepository}.
 */
@Generated
public class ShowRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public ShowRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link ShowRepository#findByMovieId(java.lang.Long)}.
   */
  public List<Show> findByMovieId(Long movieId) {
    String queryString = "SELECT s FROM Show s WHERE s.movie.id = :movieId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("movieId", movieId);

    return (List<Show>) query.getResultList();
  }
}
