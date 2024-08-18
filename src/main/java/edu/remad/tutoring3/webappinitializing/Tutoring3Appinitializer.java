package edu.remad.tutoring3.webappinitializing;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import edu.remad.tutoring3.config.FreeMarkerConfig;
import edu.remad.tutoring3.config.TutoringAppConfig;

public class Tutoring3Appinitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[0];
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { TutoringAppConfig.class, FreeMarkerConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
