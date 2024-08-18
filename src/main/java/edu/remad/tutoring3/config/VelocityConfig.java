package edu.remad.tutoring3.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.spring.VelocityEngineFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import edu.remad.tutoring3.velocity.VelocityProperty;
import edu.remad.tutoring3.velocity.VelocityViewResolver;

@Configuration
public class VelocityConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.velocity")
	VelocityProperty velocityProperty() {
		VelocityProperty velocityProperty = new VelocityProperty();
		velocityProperty.setLayoutUrl("/WEB-INF/templates/velocity/");
		
		return velocityProperty;
	}

	@Bean
	VelocityEngineFactoryBean velocityEngineFactoryBean(VelocityProperty velocityProperty) {
		VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
		bean.setConfigLocation(null);
		bean.setResourceLoaderPath(velocityProperty.getResourceLoaderPath());
		Map<String, Object> velocityPropertiesMap = new HashMap<>();
		velocityPropertiesMap.put(Velocity.ENCODING_DEFAULT, "UTF-8");
		velocityPropertiesMap.put(Velocity.INPUT_ENCODING, "UTF-8");
		velocityPropertiesMap.put(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityPropertiesMap.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		bean.setVelocityPropertiesMap(velocityPropertiesMap);
		
		return bean;
	}

	@Bean
	@ConditionalOnMissingBean(name = "velocityViewResolver")
	@ConditionalOnProperty(name = "spring.velocity.enabled", matchIfMissing = true)
	VelocityViewResolver velocityViewResolver(VelocityProperty velocityProperty) {
		VelocityViewResolver resolver = new VelocityViewResolver("/WEB-INF/templates/velocity/", ".vm");
		resolver.setOrder(Ordered.HIGHEST_PRECEDENCE + 100);

		return resolver;
	}
	
}
