package org.springframework.boot.data.jdbc.autoconfigure;

import java.lang.Class;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.ManagedTypes;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.IdGeneratingEntityCallback;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.convert.RelationResolver;
import org.springframework.data.jdbc.core.dialect.JdbcDialect;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.relational.RelationalManagedTypes;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * Bean definitions for {@link DataJdbcRepositoriesAutoConfiguration}.
 */
@Generated
public class DataJdbcRepositoriesAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'dataJdbcRepositoriesAutoConfiguration'.
   */
  public static BeanDefinition getDataJdbcRepositoriesAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(DataJdbcRepositoriesAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(DataJdbcRepositoriesAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration}.
   */
  @Generated
  public static class SpringBootJdbcConfiguration {
    /**
     * Get the bean instance supplier for 'org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration'.
     */
    private static BeanInstanceSupplier<DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration> getSpringBootJdbcConfigurationInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration>forConstructor(ApplicationContext.class, DataJdbcProperties.class)
              .withGenerator((registeredBean, args) -> new DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration(args.get(0), args.get(1)));
    }

    /**
     * Get the bean definition for 'springBootJdbcConfiguration'.
     */
    public static BeanDefinition getSpringBootJdbcConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class);
      beanDefinition.setInstanceSupplier(getSpringBootJdbcConfigurationInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance for 'jdbcManagedTypes'.
     */
    private static InstanceSupplier<RelationalManagedTypes> jdbcManagedTypesInstance() {
      return (registeredBean ->  {
        List<Class<?>> types = Collections.emptyList();
        ManagedTypes managedTypes = ManagedTypes.fromIterable(types);
        return RelationalManagedTypes.from(managedTypes);
      } );
    }

    /**
     * Get the bean definition for 'jdbcManagedTypes'.
     */
    public static BeanDefinition getJdbcManagedTypesBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(RelationalManagedTypes.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration");
      beanDefinition.setInstanceSupplier(SpringBootJdbcConfiguration.jdbcManagedTypesInstance());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jdbcMappingContext'.
     */
    private static BeanInstanceSupplier<JdbcMappingContext> getJdbcMappingContextInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<JdbcMappingContext>forFactoryMethod(DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class, "jdbcMappingContext", Optional.class, JdbcCustomConversions.class, RelationalManagedTypes.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration", DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class).jdbcMappingContext(args.get(0, Optional.class), args.get(1, JdbcCustomConversions.class), args.get(2, RelationalManagedTypes.class)));
    }

    /**
     * Get the bean definition for 'jdbcMappingContext'.
     */
    public static BeanDefinition getJdbcMappingContextBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(JdbcMappingContext.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration");
      beanDefinition.setInstanceSupplier(getJdbcMappingContextInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jdbcConverter'.
     */
    private static BeanInstanceSupplier<JdbcConverter> getJdbcConverterInstanceSupplier() {
      return BeanInstanceSupplier.<JdbcConverter>forFactoryMethod(DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class, "jdbcConverter", JdbcMappingContext.class, NamedParameterJdbcOperations.class, RelationResolver.class, JdbcCustomConversions.class, JdbcDialect.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration", DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class).jdbcConverter(args.get(0, JdbcMappingContext.class), args.get(1, NamedParameterJdbcOperations.class), args.get(2, RelationResolver.class), args.get(3, JdbcCustomConversions.class), args.get(4, JdbcDialect.class)));
    }

    /**
     * Get the bean definition for 'jdbcConverter'.
     */
    public static BeanDefinition getJdbcConverterBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(JdbcConverter.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration");
      beanDefinition.setInstanceSupplier(getJdbcConverterInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jdbcCustomConversions'.
     */
    private static BeanInstanceSupplier<JdbcCustomConversions> getJdbcCustomConversionsInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<JdbcCustomConversions>forFactoryMethod(DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class, "jdbcCustomConversions")
              .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration", DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class).jdbcCustomConversions());
    }

    /**
     * Get the bean definition for 'jdbcCustomConversions'.
     */
    public static BeanDefinition getJdbcCustomConversionsBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(JdbcCustomConversions.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration");
      beanDefinition.setInstanceSupplier(getJdbcCustomConversionsInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jdbcAggregateTemplate'.
     */
    private static BeanInstanceSupplier<JdbcAggregateTemplate> getJdbcAggregateTemplateInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<JdbcAggregateTemplate>forFactoryMethod(DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class, "jdbcAggregateTemplate", ApplicationContext.class, JdbcMappingContext.class, JdbcConverter.class, DataAccessStrategy.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration", DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class).jdbcAggregateTemplate(args.get(0, ApplicationContext.class), args.get(1, JdbcMappingContext.class), args.get(2, JdbcConverter.class), args.get(3, DataAccessStrategy.class)));
    }

    /**
     * Get the bean definition for 'jdbcAggregateTemplate'.
     */
    public static BeanDefinition getJdbcAggregateTemplateBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(JdbcAggregateTemplate.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration");
      beanDefinition.setInstanceSupplier(getJdbcAggregateTemplateInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'dataAccessStrategyBean'.
     */
    private static BeanInstanceSupplier<DataAccessStrategy> getDataAccessStrategyBeanInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<DataAccessStrategy>forFactoryMethod(DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class, "dataAccessStrategyBean", NamedParameterJdbcOperations.class, JdbcConverter.class, JdbcMappingContext.class, JdbcDialect.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration", DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class).dataAccessStrategyBean(args.get(0, NamedParameterJdbcOperations.class), args.get(1, JdbcConverter.class), args.get(2, JdbcMappingContext.class), args.get(3, JdbcDialect.class)));
    }

    /**
     * Get the bean definition for 'dataAccessStrategyBean'.
     */
    public static BeanDefinition getDataAccessStrategyBeanBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(DataAccessStrategy.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration");
      beanDefinition.setInstanceSupplier(getDataAccessStrategyBeanInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jdbcDialect'.
     */
    private static BeanInstanceSupplier<JdbcDialect> getJdbcDialectInstanceSupplier() {
      return BeanInstanceSupplier.<JdbcDialect>forFactoryMethod(DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class, "jdbcDialect", NamedParameterJdbcOperations.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration", DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class).jdbcDialect(args.get(0, NamedParameterJdbcOperations.class)));
    }

    /**
     * Get the bean definition for 'jdbcDialect'.
     */
    public static BeanDefinition getJdbcDialectBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(JdbcDialect.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration");
      beanDefinition.setInstanceSupplier(getJdbcDialectInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'idGeneratingBeforeSaveCallback'.
     */
    private static BeanInstanceSupplier<IdGeneratingEntityCallback> getIdGeneratingBeforeSaveCallbackInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<IdGeneratingEntityCallback>forFactoryMethod(DataJdbcRepositoriesAutoConfiguration.SpringBootJdbcConfiguration.class, "idGeneratingBeforeSaveCallback", JdbcMappingContext.class, NamedParameterJdbcOperations.class, JdbcDialect.class);
    }

    /**
     * Get the bean definition for 'idGeneratingBeforeSaveCallback'.
     */
    public static BeanDefinition getIdGeneratingBeforeSaveCallbackBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(IdGeneratingEntityCallback.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.data.jdbc.autoconfigure.DataJdbcRepositoriesAutoConfiguration$SpringBootJdbcConfiguration");
      beanDefinition.setInstanceSupplier(getIdGeneratingBeforeSaveCallbackInstanceSupplier());
      return beanDefinition;
    }
  }

  /**
   * Bean definitions for {@link DataJdbcRepositoriesAutoConfiguration.JdbcRepositoriesConfiguration}.
   */
  @Generated
  public static class JdbcRepositoriesConfiguration {
    /**
     * Get the bean definition for 'jdbcRepositoriesConfiguration'.
     */
    public static BeanDefinition getJdbcRepositoriesConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(DataJdbcRepositoriesAutoConfiguration.JdbcRepositoriesConfiguration.class);
      beanDefinition.setInstanceSupplier(DataJdbcRepositoriesAutoConfiguration.JdbcRepositoriesConfiguration::new);
      return beanDefinition;
    }
  }
}
