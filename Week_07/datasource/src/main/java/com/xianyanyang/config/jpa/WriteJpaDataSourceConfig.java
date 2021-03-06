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
@EnableJpaRepositories(entityManagerFactoryRef = "writeEntityManagerFactory", transactionManagerRef = "writeTransactionManager", basePackages = {"com.xianyanyang.domain.repository"})
public class WriteJpaDataSourceConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("write")
    private DataSource writeDataSource;

    @Bean(name = "writeEntityManagerFactoryBean")
    @Primary
    public LocalContainerEntityManagerFactoryBean writeEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(writeDataSource)
                .properties(jpaProperties.getProperties())
                .packages("com.xianyanyang")
                .persistenceUnit("writePersistenceUnit")
                .build();
    }

    @Bean(name = "writeEntityManagerFactory")
    @Primary
    public EntityManagerFactory writeEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return this.writeEntityManagerFactoryBean(builder).getObject();
    }

    @Bean(name = "writeTransactionManager")
    @Primary
    public PlatformTransactionManager writeTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(writeEntityManagerFactory(builder));
    }
}
