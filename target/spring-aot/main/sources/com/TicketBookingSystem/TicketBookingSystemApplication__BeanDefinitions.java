package com.TicketBookingSystem;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link TicketBookingSystemApplication}.
 */
@Generated
public class TicketBookingSystemApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'ticketBookingSystemApplication'.
   */
  public static BeanDefinition getTicketBookingSystemApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(TicketBookingSystemApplication.class);
    beanDefinition.setInstanceSupplier(TicketBookingSystemApplication::new);
    return beanDefinition;
  }
}
