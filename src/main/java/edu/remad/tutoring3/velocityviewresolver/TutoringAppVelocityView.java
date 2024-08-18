package edu.remad.tutoring3.velocityviewresolver;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.velocity.tools.view.JeeConfig;
import org.apache.velocity.tools.view.JeeContextConfig;
import org.apache.velocity.tools.view.JeeFilterConfig;
import org.apache.velocity.tools.view.JeeServletConfig;
import org.apache.velocity.tools.view.VelocityView;

public class TutoringAppVelocityView extends VelocityView {

    public TutoringAppVelocityView(ServletConfig config)
    {
        super(new JeeServletConfig(config));
    }

    public TutoringAppVelocityView(FilterConfig config)
    {
    	super(new JeeFilterConfig(config));
    }

    public TutoringAppVelocityView(ServletContext context)
    {
        super(new JeeContextConfig(context));
    }

    public TutoringAppVelocityView(JeeConfig config)
    {
    	super(config);
    }
}
