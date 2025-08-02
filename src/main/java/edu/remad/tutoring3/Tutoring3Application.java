package edu.remad.tutoring3;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import edu.remad.tutoring3.config.FreeMarkerConfig;
import edu.remad.tutoring3.config.TutoringAppConfig;
import edu.remad.tutoring3.config.VelocityConfig;

@SpringBootApplication
@Import({VelocityConfig.class, TutoringAppConfig.class, FreeMarkerConfig.class})
public class Tutoring3Application extends SpringBootServletInitializer {
	
	/**
	 * Starts the Spring Boot 3 Tutoring 3 Application and prints all Spring Beans of Context
	 * 
	 * @param arguments arguments to start with
	 */
	public static void main(String[] arguments) {
		ApplicationContext applicationContext = SpringApplication.run(Tutoring3Application.class, arguments);

		System.out.println("Which Spring Beans are part of context:");

		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Arrays.sort(applicationContext.getBeanDefinitionNames());
		for (String beanName : beanNames) {
			System.out.println("Initialized Bean into Spring Boot Context: " + beanName);
		}
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Tutoring3Application.class);
    }

}
