package com.example.braguia;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.braguia.data.Contact;

public class ContactUnitTest {

    @Test
    public void testGettersAndSetters() {
        Contact contact = new Contact();

        // Test setters
        contact.setContactName("John Doe");
        contact.setContactPhone("1234567890");
        contact.setUrl("https://example.com");
        contact.setEmail("john@example.com");
        contact.setDescription("Test contact");
        contact.setApp("TestApp");

        // Test getters
        assertEquals("John Doe", contact.getContactName());
        assertEquals("1234567890", contact.getContactPhone());
        assertEquals("https://example.com", contact.getUrl());
        assertEquals("john@example.com", contact.getEmail());
        assertEquals("Test contact", contact.getDescription());
        assertEquals("TestApp", contact.getApp());
    }

    @Test
    public void testEquals() {
        // Create two contacts with the same attributes
        Contact contact1 = new Contact();
        contact1.setContactName("John Doe");
        contact1.setContactPhone("1234567890");
        contact1.setUrl("https://example.com");
        contact1.setEmail("john@example.com");
        contact1.setDescription("Test contact");
        contact1.setApp("TestApp");

        Contact contact2 = new Contact();
        contact2.setContactName("John Doe");
        contact2.setContactPhone("1234567890");
        contact2.setUrl("https://example.com");
        contact2.setEmail("john@example.com");
        contact2.setDescription("Test contact");
        contact2.setApp("TestApp");

        // Test that contact1 equals contact2
        assertEquals(contact1, contact2);

        // Change one attribute of contact2
        contact2.setContactName("Jane Doe");

        // Test that contact1 no longer equals contact2
        assertNotEquals(contact1, contact2);

        // Test equality with null
        assertNotEquals(null, contact1);

        // Test equality with itself
        assertEquals(contact1, contact1);
    }
}
