package edu.remad.tutoring3.velocity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class VelocityViewTest {

    private VelocityView view;
    private VelocityEngine velocityEngine;
    private Template screenTemplate;
    private Template layoutTemplate;

    @BeforeEach
    void setUp() {
        view = new VelocityView();
        velocityEngine = mock(VelocityEngine.class);
        screenTemplate = mock(Template.class);
        layoutTemplate = mock(Template.class);

        view.setVelocityEngine(velocityEngine);
    }

    @Test
    void renderMergedTemplateModel_usesDefaultLayoutWhenNoProperty() throws Exception {
        view.setUrl("screen.vm");

        when(velocityEngine.getTemplate(eq("screen.vm"), eq("UTF-8"))).thenReturn(screenTemplate);
        when(velocityEngine.getTemplate(eq("Default.vm"), eq("UTF-8"))).thenReturn(layoutTemplate);

        // screen template should write its content into provided writer
        doAnswer(invocation -> {
            Context ctx = invocation.getArgument(0);
            StringWriter sw = (StringWriter) invocation.getArgument(1);
            sw.write("<p>screen-content</p>");
            return null;
        }).when(screenTemplate).merge(any(Context.class), any(StringWriter.class));

        // layout template merges into response writer
        doAnswer(invocation -> {
            Context ctx = invocation.getArgument(0);
            PrintWriter pw = (PrintWriter) invocation.getArgument(1);
            // include screen content value
            Object screen = ctx.get("screen_content");
            pw.write("<html>" + screen + "</html>");
            return null;
        }).when(layoutTemplate).merge(any(Context.class), any(PrintWriter.class));

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        StringWriter responseWriter = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(responseWriter));

        view.renderMergedTemplateModel(new HashMap<>(), req, resp);

        String out = responseWriter.toString();
        assertTrue(out.contains("screen-content"));
        assertTrue(out.startsWith("<html>"));

        verify(velocityEngine).getTemplate("screen.vm", "UTF-8");
        verify(velocityEngine).getTemplate("Default.vm", "UTF-8");
    }

    @Test
    void renderMergedTemplateModel_usesVelocityPropertyLayout() throws Exception {
        VelocityProperty prop = new VelocityProperty();
        prop.setLayoutUrl("layouts/");
        view = new VelocityView();
        view.setVelocityEngine(velocityEngine);
        // inject property via reflection (field is private)
        java.lang.reflect.Field f = VelocityView.class.getDeclaredField("velocityProperty");
        f.setAccessible(true);
        f.set(view, prop);

        view.setUrl("screen2.vm");

        when(velocityEngine.getTemplate(eq("screen2.vm"), eq("UTF-8"))).thenReturn(screenTemplate);
        when(velocityEngine.getTemplate(eq("layouts/Default.vm"), eq("UTF-8"))).thenReturn(layoutTemplate);

        doAnswer(invocation -> {
            StringWriter sw = (StringWriter) invocation.getArgument(1);
            sw.write("content2");
            return null;
        }).when(screenTemplate).merge(any(Context.class), any(StringWriter.class));

        doAnswer(invocation -> {
            Context ctx = invocation.getArgument(0);
            PrintWriter pw = (PrintWriter) invocation.getArgument(1);
            Object screen = ctx.get("screen_content");
            pw.write("LAYOUT:" + screen);
            return null;
        }).when(layoutTemplate).merge(any(Context.class), any(PrintWriter.class));

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter responseWriter = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(responseWriter));

        view.renderMergedTemplateModel(new HashMap<>(), req, resp);

        String out = responseWriter.toString();
        assertTrue(out.contains("content2"));
        assertTrue(out.startsWith("LAYOUT:"));
    }
}
