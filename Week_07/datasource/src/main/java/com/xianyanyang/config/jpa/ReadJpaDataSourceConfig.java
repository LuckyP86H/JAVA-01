package com.xianyanyang.config.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "readEntityManagerFactory", transactionManagerRef = "readTransactionManager", basePackages = {"com.xianyanyang.domain.repository"})
public class ReadJpaDataSourceConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier(value = "read")
    private DataSource readDataSource;

    @Bean(name = "readEntityManagerFactoryBean")
    @Primary
    public LocalContainerEntityManagerFactoryBean readEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(readDataSource)
                .properties(jpaProperties.getProperties())
                .packages("com.xianyanyang")
                .persistenceUnit("readPersistenceUnit")
                .build();
    }

    @Bean(name = "readEntityManagerFactory")
    @Primary
    public EntityManagerFactory readEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return this.readEntityManagerFactoryBean(builder).getObject();
    }

    @Bean(name = "readTransactionManager")
    @Primary
    public PlatformTransactionManager readTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(readEntityManagerFactory(builder));
    }
}
