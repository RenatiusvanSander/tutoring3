package edu.remad.tutoring3.systemenvironment;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import edu.remad.tutoring3.persistence.models.AddressEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;

class SystemEnvironmentTest {

    @Test
    void gettersReturnValuesFromMap() {
        Map<String, String> props = new HashMap<>();
        props.put("TUTOR_ADMIN", "admin");
        props.put("TUTOR_ADMIN_PASSWORD", "secret");
        props.put("TUTOR_USER", "user1");
        props.put("TUTOR_STAGE", "dev");
        props.put("TUTOR_USER_PASSWORD", "upw");

        SystemEnvironment env = new SystemEnvironment(props);

        assertEquals("admin", env.getAppAdmin());
        assertEquals("secret", env.getAppAdminPassword());
        assertEquals("user1", env.getAppUser());
        assertEquals("dev", env.getAppStage());
        assertEquals("upw", env.getAppUserPassword());
    }

    @Test
    void getProperties_returnsCopyNotBackingMap() {
        Map<String, String> props = new HashMap<>();
        props.put("A", "1");
        SystemEnvironment env = new SystemEnvironment(props);

        Map<String, String> copy = env.getProperties();
        copy.put("NEW", "X");

        Map<String, String> copy2 = env.getProperties();
        assertNull(copy2.get("NEW"), "Modifying returned map should not change internal properties");
    }

    @Test
    void printSystemEnvironments_outputsAllEntries_includingEntityToString() {
        // create entities and use their toString in values to demonstrate entity usage
        AddressEntity addr = new AddressEntity();
        addr.setId(7L);
        addr.setAddressStreet("Main St");
        addr.setAddressHouseNo("12A");
        addr.setAddressZipCode(12345);
        addr.setPlace("Town");
        addr.setCreationDate(LocalDateTime.now());

        UserEntity user = new UserEntity();
        user.setUserId(5L);
        user.setName("John");
        user.setEmail("j@example.com");
        user.setEmailVerified(Boolean.TRUE);
        user.setGivenName("John");
        user.setFamilyName("Doe");
        user.setPreferredUsername("jdoe");
        user.setSub("sub-123");
        user.setCreationDate(LocalDateTime.now());

        Map<String, String> props = new HashMap<>();
        props.put("ADDR", addr.toString());
        props.put("USER", user.toString());

        SystemEnvironment env = new SystemEnvironment(props);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try {
            System.setOut(new PrintStream(out));
            env.printSystemEnvironments();
        } finally {
            System.setOut(original);
        }

        String printed = out.toString();
        // be permissive about exact formatting, ensure keys and some entity content appear
        assertTrue(printed.contains("ADDR"), () -> "Printed output did not contain key ADDR: " + printed);
        assertTrue(printed.contains("Main St"), () -> "Printed output did not contain address street: " + printed);
        assertTrue(printed.contains("USER"), () -> "Printed output did not contain key USER: " + printed);
        // UserEntity does not override toString in this project; assert the classname appears
        assertTrue(printed.contains("UserEntity"), () -> "Printed output did not contain user entity class name: " + printed);
    }

    @Test
    void defaultConstructor_createsEmptyProperties() {
        SystemEnvironment env = new SystemEnvironment();
        assertTrue(env.getProperties().isEmpty());
        assertNull(env.getAppAdmin());
    }
}
