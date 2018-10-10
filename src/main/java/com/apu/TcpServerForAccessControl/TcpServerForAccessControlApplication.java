package com.apu.TcpServerForAccessControl;

import java.io.PrintStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.apu.TcpServerForAccessControl.utils.logging.LoggingOutputStream;

@SpringBootApplication
//@EnableCaching
//(scanBasePackages = {"com.apu.TcpServerForAccessControl.utils", "com.apu.TcpServerForAccessControlDB.entity"})
//@EnableJpaRepositories(basePackageClasses=com.apu.TcpServerForAccessControlDB.repository.AccessMessageRepository.class)
//@EnableAutoConfiguration
@EnableJpaRepositories(basePackages= {"com.apu.TcpServerForAccessControlDB.repository"})
//@EnableMongoRepositories(basePackages= {"com.apu.TcpServerForAccessControlMongoDB.repository"})
@EntityScan(basePackages = {"com.apu.TcpServerForAccessControlDB.entity"}) 
@ComponentScan(basePackages= {"com.apu.TcpServerForAccessControl.utils.config"})
@ImportResource("/META-INF/spring/integration/integration.xml")
public class TcpServerForAccessControlApplication {
    
    private static final Logger logger = LogManager.getLogger(TcpServerForAccessControlApplication.class);

	public static void main(String[] args) {
	    System.setOut(new PrintStream(new LoggingOutputStream(LogManager.getLogger("outLog"), Level.ALL), true));
		SpringApplication.run(TcpServerForAccessControlApplication.class, args);
	}
}
