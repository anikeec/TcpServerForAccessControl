package com.apu.TcpServerForAccessControl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("/META-INF/spring/integration/integration.xml")
public class TcpServerForAccessControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcpServerForAccessControlApplication.class, args);
	}
}
