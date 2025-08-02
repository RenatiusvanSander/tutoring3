package edu.remad.tutoring3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

public class TutoringAppConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	@Bean
	InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/templates/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2);

		return viewResolver;
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/static-resources/**").addResourceLocations("/static-resources")
				.setCachePeriod(365 * 24 * 60 * 60).resourceChain(true).addResolver(new EncodedResourceResolver())
				.addResolver(new PathResourceResolver());
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.freeMarker().cache(true).cacheLimit(1000);
		registry.jsp("/WEB-INF/templates/views/", ".jsp");
	}
}
