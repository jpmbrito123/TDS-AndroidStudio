package com.example.braguia;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.braguia.data.Partner;

public class PartnerUnitTest {

    @Test
    public void testGettersAndSetters() {
        Partner partner = new Partner();

        // Test setters
        partner.setPartnerName("Partner Name");
        partner.setPartnerPhone("1234567890");
        partner.setUrl("https://partner.example.com");
        partner.setEmail("partner@example.com");
        partner.setDescription("Test partner");
        partner.setApp("TestApp");

        // Test getters
        assertEquals("Partner Name", partner.getPartnerName());
        assertEquals("1234567890", partner.getPartnerPhone());
        assertEquals("https://partner.example.com", partner.getUrl());
        assertEquals("partner@example.com", partner.getEmail());
        assertEquals("Test partner", partner.getDescription());
        assertEquals("TestApp", partner.getApp());
    }

    @Test
    public void testEquals() {
        Partner partner1 = new Partner();
        partner1.setPartnerName("Partner Name");
        partner1.setPartnerPhone("1234567890");
        partner1.setUrl("https://partner.example.com");
        partner1.setEmail("partner@example.com");
        partner1.setDescription("Test partner");
        partner1.setApp("TestApp");

        Partner partner2 = new Partner();
        partner2.setPartnerName("Partner Name");
        partner2.setPartnerPhone("1234567890");
        partner2.setUrl("https://partner.example.com");
        partner2.setEmail("partner@example.com");
        partner2.setDescription("Test partner");
        partner2.setApp("TestApp");

        assertEquals(partner1, partner2);

        partner2.setPartnerName("Another Partner Name");

        assertNotEquals(partner1, partner2);

        assertNotEquals(null, partner1);

        assertEquals(partner1, partner1);
    }
}
