package edu.remad.tutoring3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.remad.tutoring3.velocityviewresolver.VelocityViewResolver;

@Configuration
public class VelocityConfig {

	@Bean
	VelocityViewResolver velocityViewResolver() {
		VelocityViewResolver resolver = new VelocityViewResolver();
		
		return resolver;
	}
}
