package edu.remad.tutoring3.velocityviewresolver;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

public class VelocityViewResolver extends AbstractCachingViewResolver implements Ordered {

	private String prefix = "";

	private String suffix = "";

	private int order;

	@Nullable
	private Class<?> viewClass;

	public VelocityViewResolver() {
		setViewClass(VelocityResourceView.class);
		setOrder(0);
	}

	/**
	 * Creates instance of {@link VelocityViewResolver}
	 * 
	 * @param prefix is relative url-path to Velocity templates without suffix
	 * @param suffix is Velocity file extension with starting point for example
	 *               {@code .vm}
	 * @param order is number and sets priority. Lowest number has highest priority.
	 */
	public VelocityViewResolver(String prefix, String suffix, int order) {
		setPrefix(prefix);
		setSuffix(suffix);
		setOrder(order);
	}

	/**
	 * Creates instance of {@link VelocityViewResolver}
	 * 
	 * @param prefix is relative url-path to Velocity templates without suffix
	 * @param suffix is Velocity file extension with starting point for example
	 *               {@code .vm}
	 * @param order is number and sets priority. Lowest number has highest priority.
	 * @param viewClass The class that implements {@link View}
	 */
	public VelocityViewResolver(String prefix, String suffix, int order, Class<?> viewClass) {
		setPrefix(prefix);
		setSuffix(suffix);
		setOrder(order);
		setViewClass(viewClass);
	}

	// TODO create class from interface view
	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		return null;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Class<?> getViewClass() {
		return viewClass;
	}

	public void setViewClass(Class<?> viewClass) {
		this.viewClass = viewClass;
	}
}
