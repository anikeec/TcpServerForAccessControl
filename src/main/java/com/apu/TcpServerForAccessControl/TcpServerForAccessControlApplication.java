package com.apu.TcpServerForAccessControl;

import java.io.PrintStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import com.apu.TcpServerForAccessControl.utils.logging.LoggingOutputStream;

@SpringBootApplication
@ImportResource("/META-INF/spring/integration/integration.xml")
public class TcpServerForAccessControlApplication {
    
    private static final Logger logger = LogManager.getLogger(TcpServerForAccessControlApplication.class);

	public static void main(String[] args) {
	    System.setOut(new PrintStream(new LoggingOutputStream(LogManager.getLogger("outLog"), Level.ALL), true));
		SpringApplication.run(TcpServerForAccessControlApplication.class, args);
	}
}
