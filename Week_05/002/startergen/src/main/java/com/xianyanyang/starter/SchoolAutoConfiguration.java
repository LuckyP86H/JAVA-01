package com.xianyanyang.starter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SchoolProperties.class)
public class SchoolAutoConfiguration {

    @Bean
    public School school(SchoolProperties schoolProperties) {
        return schoolProperties.createSchool();
    }
}
