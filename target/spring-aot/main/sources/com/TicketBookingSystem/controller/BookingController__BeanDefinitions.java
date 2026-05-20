package com.TicketBookingSystem.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link BookingController}.
 */
@Generated
public class BookingController__BeanDefinitions {
  /**
   * Get the bean definition for 'bookingController'.
   */
  public static BeanDefinition getBookingControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(BookingController.class);
    InstanceSupplier<BookingController> instanceSupplier = InstanceSupplier.using(BookingController::new);
    instanceSupplier = instanceSupplier.andThen(BookingController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
