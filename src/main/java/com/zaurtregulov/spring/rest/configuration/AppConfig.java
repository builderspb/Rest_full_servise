package com.zaurtregulov.spring.rest.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("com.zaurtregulov.spring.rest")
@PropertySource("classpath:db.properties")
@EnableWebMvc
@EnableTransactionManagement
public class AppConfig {

    private final Environment env;
    ;

    @Autowired
    public AppConfig(Environment env) {
        this.env = env;

    }

    // Метод для создания и настройки DataSource, используемого для доступа к базе данных
    @Bean
    public DataSource getDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(Objects.requireNonNull(env.getProperty("db.driver")));
            dataSource.setJdbcUrl(env.getProperty("db.url"));
            dataSource.setUser(env.getProperty("db.username"));
            dataSource.setPassword(env.getProperty("db.password"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    //    Метод для настройки свойств Hibernate, таких как диалект, отображение SQL-запросов и т. д.
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.connection.characterEncoding", "utf8");
        properties.put("hibernate.connection.CharSet", "utf8");
        properties.put("hibernate.connection.useUnicode", true);

        return properties;
    }

    //    Метод для создания и настройки фабрики SessionFactory, которая управляет SessionFactory
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        // Установка DataSource и пакетов для сканирования для SessionFactory
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan("com.zaurtregulov.spring.rest.entity");
// Настройка свойств Hibernate
// (в случае использования entityManagerFactory необходимо использовать адаптер, чтобы указать JPA с каким провайдером мы собираемся работать)
/** JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
 *  entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
 */
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    // Метод для создания и настройки менеджера транзакций JPA, который управляет транзакциями в приложении
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        // Установка SessionFactory в JpaTransactionManager
        transactionManager.setSessionFactory(sessionFactory().getObject());

        return transactionManager;
    }


    // Возвращает бин объекта ModelMapper, для автоматического маппинга данных из безнес сущности в объект DTO и обратно
    @Bean
    public ModelMapper mapperMapper() {

        return new ModelMapper();
    }

}
