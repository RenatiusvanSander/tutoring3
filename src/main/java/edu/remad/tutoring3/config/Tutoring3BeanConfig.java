package edu.remad.tutoring3.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.remad.tutoring3.Tutoring3Application;
import edu.remad.tutoring3.profiles.ProfileManager;
import edu.remad.tutoring3.systemenvironment.SystemEnvironment;
import edu.remad.tutoring3.systemenvironment.SystemEnvironmentFactory;

/**
 * Configures ProfileManager and, SystemEnvironment that is loaded by
 * {@link Tutoring3Application}
 * 
 * @author edu.remad
 * @since 2025
 */
public class Tutoring3BeanConfig {

	@Bean
	ProfileManager profileManager() {
		return new ProfileManager();
	}

	@Bean
	SystemEnvironment systemEnvironment(ProfileManager profileManager) {
		SystemEnvironment systemEnv = SystemEnvironmentFactory.getInstance();

		if (profileManager.getActiveProfile().equalsIgnoreCase("dev")
				|| profileManager.getActiveProfile().equalsIgnoreCase("debug")) {
			systemEnv.printSystemEnvironments();
		}

		return systemEnv;
	}

	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration cors = new CorsConfiguration();
		cors.setAllowedOrigins(
				Arrays.asList("http://localhost:4200", "https://localhost:4200", "https://localhost:8082"));
		cors.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "ATTACH"));
		cors.setAllowedHeaders(List.of("*"));
		cors.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", cors);

		return source;
	}

}
