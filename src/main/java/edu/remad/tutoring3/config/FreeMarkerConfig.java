package edu.remad.tutoring3.config;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
public class FreeMarkerConfig {

    @Bean
    FreeMarkerViewResolver freeMarkerViewResolver() { 
	    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver(); 
	    resolver.setCache(true); 
	    resolver.setSuffix(".ftl");
	    resolver.setOrder(0);
	    
	    return resolver; 
	}

    @Bean
    FreeMarkerConfigurer freeMarkerConfigurer() {
		Properties properties = new Properties();
		properties.put("auto_import", "/spring.ftl as spring");
		properties.put("template_exception_handler", "rethrow");

		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/WEB-INF/templates/freemarker");
		configurer.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		configurer.setFreemarkerSettings(properties);

		return configurer;
	}
}