package com.TicketBookingSystem.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link MovieController}.
 */
@Generated
public class MovieController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static MovieController apply(RegisteredBean registeredBean, MovieController instance) {
    AutowiredFieldValueResolver.forRequiredField("movieRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("showRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
