package edu.remad.tutoring3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.remad.tutoring3.systemenvironment.SystemEnvironment;
import edu.remad.tutoring3.systemenvironment.SystemEnvironmentFactory;

@Configuration
public class SystemEnvironmentConfig {

	@Bean
    SystemEnvironment systemEnvironment() {
		return SystemEnvironmentFactory.getInstance();
	}
}
