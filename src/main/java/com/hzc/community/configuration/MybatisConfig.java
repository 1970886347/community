package com.hzc.community.configuration;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@MapperScan("com.hzc.community.mapper")
public class MybatisConfig {
    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration createConfig(){
        return new org.apache.ibatis.session.Configuration();
    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(ResourcePatternResolver resolver, DataSource dataSource, org.apache.ibatis.session.Configuration config) {
        SqlSessionFactoryBean sqlSessionFactory=null;
        try {
            Resource[]resources=resolver.getResources("classpath:mapper/*.xml");
            sqlSessionFactory=new SqlSessionFactoryBean();
            sqlSessionFactory.setMapperLocations(resources);
            sqlSessionFactory.setTypeAliasesPackage("com.hzc.community.model");
            sqlSessionFactory.setDataSource(dataSource);
            sqlSessionFactory.setConfiguration(config);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }
}
