package com.apu.TcpServerForAccessControl.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.vladmihalcea.flexypool.config.Configuration;
import com.vladmihalcea.flexypool.connection.ConnectionDecoratorFactoryResolver;
import com.vladmihalcea.flexypool.event.ConnectionAcquireTimeThresholdExceededEvent;
import com.vladmihalcea.flexypool.event.ConnectionAcquireTimeoutEvent;
import com.vladmihalcea.flexypool.event.ConnectionLeaseTimeThresholdExceededEvent;
import com.vladmihalcea.flexypool.event.Event;
import com.vladmihalcea.flexypool.event.EventListener;
import com.vladmihalcea.flexypool.event.EventListenerResolver;
import com.vladmihalcea.flexypool.metric.MetricsFactoryResolver;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.RetryConnectionAcquiringStrategy;
import com.zaxxer.hikari.HikariDataSource;

@org.springframework.context.annotation.Configuration
public class FlexyPoolConfiguration {

    final Level FLEXYPOOL = Level.forName("FLEXYPOOL", 550);
    
    @Value("flexyPoolApu")//${flexy.pool.uniqueId}
    private String uniqueId;
    
    public static class ConnectionAcquireTimeThresholdExceededEventListener
        extends EventListener<ConnectionAcquireTimeThresholdExceededEvent> {
    
        public static final Logger LOGGER = LogManager.getLogger(
                ConnectionAcquireTimeThresholdExceededEventListener.class);
        
        public ConnectionAcquireTimeThresholdExceededEventListener() {
            super(ConnectionAcquireTimeThresholdExceededEvent.class);
        }
        
        @Override
        public void on(ConnectionAcquireTimeThresholdExceededEvent event) {
            LOGGER.info("Caught event {}", event);
        }
    }
    
    public static class ConnectionLeaseTimeThresholdExceededEventListener
        extends EventListener<ConnectionLeaseTimeThresholdExceededEvent> {
    
        public static final Logger LOGGER = LogManager.getLogger(
                ConnectionLeaseTimeThresholdExceededEventListener.class);
        
        public ConnectionLeaseTimeThresholdExceededEventListener() {
            super(ConnectionLeaseTimeThresholdExceededEvent.class);
        }
        
        @Override
        public void on(ConnectionLeaseTimeThresholdExceededEvent event) {
            LOGGER.info("Caught event {}", event);
        }
    }
    
    public static class ConnectionAcquireTimeoutEventListener
        extends EventListener<ConnectionAcquireTimeoutEvent> {
    
        public static final Logger LOGGER = LogManager.getLogger(
                ConnectionAcquireTimeoutEventListener.class);
        
        public ConnectionAcquireTimeoutEventListener() {
            super(ConnectionAcquireTimeoutEvent.class);
        }
        
        @Override
        public void on(ConnectionAcquireTimeoutEvent event) {
            LOGGER.info("Caught event {}", event);
        }
    }

    public HikariDataSource hikariDataSource() {
        HikariDataSource dataSource = new com.zaxxer.hikari.HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");        
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/accesscontroldb");
        dataSource.setUsername("postgres");
        dataSource.setPassword("");
        return dataSource;
    }
    
    @Bean
    public Configuration<HikariDataSource> configuration() {
        return new Configuration.Builder<HikariDataSource>(
                uniqueId,
                hikariDataSource(),
                HikariCPPoolAdapter.FACTORY
        )
                .setMetricsFactory(MetricsFactoryResolver.INSTANCE.resolve())
                .setConnectionProxyFactory(ConnectionDecoratorFactoryResolver.INSTANCE.resolve())
                .setMetricLogReporterMillis(TimeUnit.SECONDS.toMillis(5))
                .setJmxEnabled(true)
                .setJmxAutoStart(true)
                .setConnectionAcquireTimeThresholdMillis(50L)
                .setConnectionLeaseTimeThresholdMillis(250L)
                .setEventListenerResolver(new EventListenerResolver() {
                    @Override
                    public List<EventListener<? extends Event>> resolveListeners() {
                        return Arrays.<EventListener<? extends Event>>asList(
                            new ConnectionAcquireTimeoutEventListener(),
                            new ConnectionAcquireTimeThresholdExceededEventListener(),
                            new ConnectionLeaseTimeThresholdExceededEventListener()
                        );
                    }
                })
                .build();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public FlexyPoolDataSource dataSource() {
        Configuration<HikariDataSource> configuration = configuration();
        return new FlexyPoolDataSource<HikariDataSource>(configuration,
                new IncrementPoolOnTimeoutConnectionAcquiringStrategy.Factory(5),
                new RetryConnectionAcquiringStrategy.Factory(2)
        );
    }
}