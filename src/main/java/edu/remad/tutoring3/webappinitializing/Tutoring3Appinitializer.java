package edu.remad.tutoring3.webappinitializing;

import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import edu.remad.tutoring3.config.FreeMarkerConfig;
import edu.remad.tutoring3.config.SpringSecurityConfig;
import edu.remad.tutoring3.config.TutoringAppConfig;
import edu.remad.tutoring3.config.VelocityConfig;

public class Tutoring3Appinitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { TutoringAppConfig.class, FreeMarkerConfig.class, VelocityConfig.class, SpringSecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
    @Override
	protected WebApplicationContext createRootApplicationContext() {
		Class<?>[] configClasses = getRootConfigClasses();
		if (!ObjectUtils.isEmpty(configClasses)) {
			AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
			context.register(configClasses);

			return context;
		} else {
			return null;
		}
	}

}
