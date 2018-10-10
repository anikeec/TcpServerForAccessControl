package com.apu.TcpServerForAccessControl.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.zaxxer.hikari.HikariDataSource;

@ComponentScan("com.apu.TcpServerForAccessControl.utils.config")
@org.springframework.context.annotation.Configuration
public class DBConfig {

//    @Bean
//    public HikariDataSource hikariDataSource() {
//        HikariDataSource dataSource = new com.zaxxer.hikari.HikariDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");        
//        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/accesscontroldb");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("");
//        return dataSource;
//    }    
    
}
