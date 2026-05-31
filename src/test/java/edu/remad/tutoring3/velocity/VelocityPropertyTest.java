package edu.remad.tutoring3.velocity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VelocityPropertyTest {

    @Test
    void gettersAndSettersWork() {
        VelocityProperty p = new VelocityProperty();
        p.setResourceLoaderPath("/templates/");
        p.setSuffix(".vm");
        p.setLayoutUrl("layouts/");

        assertEquals("/templates/", p.getResourceLoaderPath());
        assertEquals(".vm", p.getSuffix());
        assertEquals("layouts/", p.getLayoutUrl());
    }
}
