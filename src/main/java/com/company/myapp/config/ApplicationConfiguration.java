package com.company.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfiguration {

	@Autowired
	private Environment environment;

	public String applicationName() {
		return environment.getProperty("spring.application.name");
	}

	public int httpPort() {
		return environment.getProperty("server.port", Integer.class,8888);

	}

	public int socketPort() {
		return environment.getProperty("socket.port", Integer.class,6666);
	}
}
