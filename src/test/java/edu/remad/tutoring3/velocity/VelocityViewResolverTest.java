package edu.remad.tutoring3.velocity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.web.servlet.view.AbstractUrlBasedView;

class VelocityViewResolverTest {

    @Test
    void instantiateView_returnsVelocityView() {
        VelocityViewResolver resolver = new VelocityViewResolver();
        AbstractUrlBasedView view = resolver.instantiateView();
        assertNotNull(view);
        assertTrue(view instanceof VelocityView);
    }

    @Test
    void constructorWithPrefixSuffix_instantiatesView() {
        VelocityViewResolver resolver = new VelocityViewResolver("/pre/", ".vm");
        // ensure instantiateView still returns a VelocityView
        assertTrue(resolver.instantiateView() instanceof VelocityView);
    }
}
