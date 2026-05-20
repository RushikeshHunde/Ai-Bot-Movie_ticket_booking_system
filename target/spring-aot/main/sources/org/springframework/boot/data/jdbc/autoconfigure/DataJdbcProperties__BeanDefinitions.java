package org.springframework.boot.data.jdbc.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DataJdbcProperties}.
 */
@Generated
public class DataJdbcProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'dataJdbcProperties'.
   */
  public static BeanDefinition getDataJdbcPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(DataJdbcProperties.class);
    beanDefinition.setInstanceSupplier(DataJdbcProperties::new);
    return beanDefinition;
  }
}
