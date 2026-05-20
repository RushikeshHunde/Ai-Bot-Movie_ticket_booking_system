package com.TicketBookingSystem.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link MovieController}.
 */
@Generated
public class MovieController__BeanDefinitions {
  /**
   * Get the bean definition for 'movieController'.
   */
  public static BeanDefinition getMovieControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(MovieController.class);
    InstanceSupplier<MovieController> instanceSupplier = InstanceSupplier.using(MovieController::new);
    instanceSupplier = instanceSupplier.andThen(MovieController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
