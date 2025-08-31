package edu.remad.tutoring3.config;

import org.springframework.context.annotation.Bean;

import edu.remad.tutoring3.Tutoring3Application;
import edu.remad.tutoring3.profiles.ProfileManager;
import edu.remad.tutoring3.systemenvironment.SystemEnvironment;
import edu.remad.tutoring3.systemenvironment.SystemEnvironmentFactory;

/**
 * Configures ProfileManager and, SystemEnvironment that is loaded by {@link Tutoring3Application}
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
		
		if(profileManager.getActiveProfile().equalsIgnoreCase("dev") || profileManager.getActiveProfile().equalsIgnoreCase("debug")) {
			systemEnv.printSystemEnvironments();
		}
		
		return systemEnv;
	}
	
}
