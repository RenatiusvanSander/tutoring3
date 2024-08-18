package edu.remad.tutoring3.velocity;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.EasyFactoryConfiguration;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.velocity.tools.generic.ResourceTool;
import org.apache.velocity.tools.generic.XmlTool;
import org.apache.velocity.tools.view.PagerTool;
import org.apache.velocity.tools.view.ParameterTool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.AbstractTemplateView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VelocityView extends AbstractTemplateView {

	private final static String SCREEN_CONTENT_KEY = "screen_content";
	private final static String LAYOUT_KEY = "layout";

	private VelocityEngine velocityEngine;
	private VelocityProperty velocityProperty;
	private EasyFactoryConfiguration toolBoxConfig;

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	@Override
	protected void initApplicationContext(ApplicationContext context) {
		super.initApplicationContext();
		if (this.velocityEngine == null) {
			setVelocityEngine(autodetectVelocityEngine());
		}
		if (this.velocityProperty == null) {
			velocityProperty = BeanFactoryUtils.beanOfTypeIncludingAncestors(obtainApplicationContext(),
					VelocityProperty.class, true, false);
		}
		
		setExposeSpringMacroHelpers(true);
		setExposeSessionAttributes(true);
	}

	protected VelocityEngine autodetectVelocityEngine() throws BeansException {
		try {
			return BeanFactoryUtils.beanOfTypeIncludingAncestors(obtainApplicationContext(), VelocityEngine.class, true,
					false);
		} catch (NoSuchBeanDefinitionException ex) {
			throw new ApplicationContextException("Expected a single VelocityEngine bean in the current "
					+ "Servlet web application context or the parent root context: VelocityConfiguration is "
					+ "the usual implementation. This bean may have any name.", ex);
		}
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// VelocityEngineUtils.mergeTemplate(velocityEngine, "Template_LOCATION", "UTF-8", model, null);
		// is another way to render
		
		ToolManager tm = new ToolManager();
		tm.configure(getConfig());
		tm.setVelocityEngine(velocityEngine);
		Context context = tm.createContext();

		doRender(context, response);
	}

	private void doRender(Context context, HttpServletResponse response) throws Exception {
		renderScreenContent(context);

		// #set( $layout = "MyLayout.vm" )
		String layoutUrlToUse = (String) context.get(LAYOUT_KEY);
		if (layoutUrlToUse != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Screen content template has requested layout [" + layoutUrlToUse + "]");
			}
		} else {
			// No explicit layout URL given -> use default layout of this view.
			String layoutUrl = "Default.vm";
			if (velocityProperty != null && isNotBlank(velocityProperty.getLayoutUrl())) {
				layoutUrl = velocityProperty.getLayoutUrl() + layoutUrl;
			}
			layoutUrlToUse = layoutUrl;
		}
		
		Template template = getTemplate(layoutUrlToUse);
		response.setCharacterEncoding("UTF-8");
		template.merge(context, response.getWriter());
	}

	/**
	 * The resulting context contains any mappings from render, plus screen content.
	 */
	private void renderScreenContent(Context velocityContext) throws Exception {
		logger.debug("Rendering screen content template [" + getUrl() + "]");

		StringWriter sw = new StringWriter();
		Template screenContentTemplate = getTemplate(getUrl());
		screenContentTemplate.merge(velocityContext, sw);
		// Put rendered content into Velocity context.
		velocityContext.put(SCREEN_CONTENT_KEY, sw.toString());
	}

	private Template getTemplate(String url) {
		return velocityEngine.getTemplate(url, "UTF-8");
	}

	private EasyFactoryConfiguration getConfig() {
		if (toolBoxConfig == null) {
			toolBoxConfig = new EasyFactoryConfiguration();
			toolBoxConfig.data("author", "Remy Meier");

			// scope request config
			toolBoxConfig.toolbox("request").property("xhtml", true).tool(org.apache.velocity.tools.view.LinkTool.class)
					.tool(PagerTool.class).property("createSession", Boolean.TRUE).tool(ResourceTool.class)
					.property("bundles", "resources,otherStuff").tool(ParameterTool.class);

			// scope session config
			toolBoxConfig.toolbox("session").property("locale", Locale.US)
					.tool(org.apache.velocity.tools.view.BrowserTool.class).property("languagesFilter", "en,fr");

			// toolbox scope application
			toolBoxConfig.toolbox("application").tool(DateTool.class).property("format", LAYOUT_KEY)
					.property("depth", "2").property("skip", "month")
					.tool(org.apache.velocity.tools.generic.MathTool.class).property("format", "#0.0")
					.tool(XmlTool.class).property("resource", "file.xml").property("safeMode", Boolean.FALSE)
					.tool(NumberTool.class).property("dateFormat", "yyyy-MM-dd");
			toolBoxConfig.number("version", 1.1);
		}

		return toolBoxConfig;
	}

}