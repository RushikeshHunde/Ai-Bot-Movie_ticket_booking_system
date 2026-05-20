package com.TicketBookingSystem.repository;

import com.TicketBookingSystem.model.MovieNotification;
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
 * AOT generated JPA repository implementation for {@link notificationRepository}.
 */
@Generated
public class notificationRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public notificationRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link notificationRepository#existsByEmailAndMovieId(java.lang.String,java.lang.Long)}.
   */
  public boolean existsByEmailAndMovieId(String email, Long movieId) {
    String queryString = "SELECT m.id FROM MovieNotification m WHERE m.email = :email AND m.movieId = :movieId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("email", email);
    query.setParameter("movieId", movieId);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }

  /**
   * AOT generated implementation of {@link notificationRepository#findByMovieId(java.lang.Long)}.
   */
  public List<MovieNotification> findByMovieId(Long movieId) {
    String queryString = "SELECT m FROM MovieNotification m WHERE m.movieId = :movieId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("movieId", movieId);

    return (List<MovieNotification>) query.getResultList();
  }
}
