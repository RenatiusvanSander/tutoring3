package edu.remad.tutoring3.velocity;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class VelocityViewResolver extends AbstractTemplateViewResolver {

	public VelocityViewResolver() {
		setViewClass(requiredViewClass());
	}
	
	@Override
	protected Class<?> requiredViewClass() {
		return VelocityView.class;
	}
	
	public VelocityViewResolver(String prefix, String suffix) {
		setViewClass(requiredViewClass());
		setPrefix(prefix);
		setSuffix(suffix);
	}

	protected AbstractUrlBasedView instantiateView() {
		return (getViewClass() == VelocityView.class ? new VelocityView() : super.instantiateView());
	}
}
