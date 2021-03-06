package com.xianyanyang.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.xianyanyang.config.annotation.DataBaseType;
import com.xianyanyang.config.router.RoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DruidDataSourceConfig {

    /**
     * 读数据源
     *
     * @return 数据源
     */
    @Bean(name = "read")
    @ConfigurationProperties("app.datasource.druid.read")
    public DataSource read() {
        return new DruidDataSource();
    }

    /**
     * 写数据源
     *
     * @return 数据源
     */
    @Primary
    @Bean(name = "write")
    @ConfigurationProperties("app.datasource.druid.write")
    public DataSource write() {
        return new DruidDataSource();
    }

    /**
     * 数据源路由器
     *
     * @param readDruidDataSource
     * @param writeDruidDataSource
     * @return
     */
    @Bean
    public AbstractRoutingDataSource routingDataSource(@Qualifier(value = "read") DataSource readDruidDataSource,
                                                       @Qualifier(value = "write") DataSource writeDruidDataSource) {
        RoutingDataSource dataSource = new RoutingDataSource();
        dataSource.setTargetDataSources(new HashMap(2) {
            {
                put(DataBaseType.write, writeDruidDataSource);
                put(DataBaseType.read, readDruidDataSource);
            }
        });
        dataSource.setDefaultTargetDataSource(writeDruidDataSource);
        return dataSource;
    }

}
