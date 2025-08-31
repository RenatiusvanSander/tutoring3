package edu.remad.tutoring3.velocity;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * Resolves Velocity View
 * 
 * @author edu.remad
 * @since 2025
 */
public class VelocityViewResolver extends AbstractTemplateViewResolver {

	/**
	 * Constructor
	 */
	public VelocityViewResolver() {
		setViewClass(requiredViewClass());
	}

	@Override
	protected Class<?> requiredViewClass() {
		return VelocityView.class;
	}

	/**
	 * Constructor
	 * 
	 * @param prefix prefix to use
	 * @param suffix suffix to use
	 */
	public VelocityViewResolver(String prefix, String suffix) {
		setViewClass(requiredViewClass());
		setPrefix(prefix);
		setSuffix(suffix);
	}

	protected AbstractUrlBasedView instantiateView() {
		return (getViewClass() == VelocityView.class ? new VelocityView() : super.instantiateView());
	}
}
