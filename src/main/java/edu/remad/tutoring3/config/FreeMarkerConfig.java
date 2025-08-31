package edu.remad.tutoring3.config;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import edu.remad.tutoring3.Tutoring3Application;

/**
 * Configures Spring's FreeMarker and loaded by {@link Tutoring3Application}
 * 
 * @author edu.remad
 * @since 2025
 */
public class FreeMarkerConfig {

    @Bean
    FreeMarkerViewResolver freeMarkerViewResolver() { 
	    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver("",".ftl"); 
	    resolver.setCache(true); 
	    resolver.setOrder(1);
	    
	    return resolver; 
	}

    @Bean
    FreeMarkerConfigurer freeMarkerConfigurer() {
		Properties properties = new Properties();
		properties.put("auto_import", "/spring.ftl as spring");
		properties.put("template_exception_handler", "rethrow");

		FreeMarkerConfigurer freemarkerConfigurer = new FreeMarkerConfigurer();
		freemarkerConfigurer.setTemplateLoaderPath("/WEB-INF/templates/freemarker");
		freemarkerConfigurer.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		freemarkerConfigurer.setFreemarkerSettings(properties);

		return freemarkerConfigurer;
	}
}