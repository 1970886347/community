package com.hzc.community.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DruidConfig {

    @Bean(name = "druidDataSource")
    @ConfigurationProperties(prefix = "spring.dataSource")
    DataSource h2() {
        DataSource dataSource=new DruidDataSource();
        return dataSource;
    }
}
