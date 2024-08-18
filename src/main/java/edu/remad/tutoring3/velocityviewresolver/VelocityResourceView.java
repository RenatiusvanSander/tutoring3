package edu.remad.tutoring3.velocityviewresolver;

import java.util.Locale;
import java.util.Map;

import org.apache.velocity.tools.view.VelocityLayoutServlet;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VelocityResourceView implements View {

	private String viewName = "";
	
	private Locale locale = Locale.GERMAN;
	
	private TutoringAppVelocityLayoutServlet servlet;
	
	public VelocityResourceView() {
	}
	
	public VelocityResourceView(String viewName, Locale locale) {
		this.viewName = viewName;
		this.locale = locale;
	}
	
	public VelocityResourceView(String viewName, Locale locale, TutoringAppVelocityLayoutServlet servlet) {
		this.viewName = viewName;
		this.locale = locale;
		this.servlet = servlet;
	}
	
	public VelocityResourceView(TutoringAppVelocityLayoutServlet servlet) {
		this.servlet = servlet;
	}
	
	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO
	}

	@Override
	public String getContentType() {
		return AbstractView.DEFAULT_CONTENT_TYPE;
	}
	
	public String getViewName() {
		return viewName;
	}

	public Locale getLocale() {
		return locale;
	}

	public VelocityLayoutServlet getServlet() {
		return servlet;
	}
}
