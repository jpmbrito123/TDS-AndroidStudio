package com.example.braguia;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.braguia.data.Social;

public class SocialUnitTest {

    @Test
    public void testGettersAndSetters() {
        Social social = new Social();

        // Test setters
        social.setSocialName("Social Name");
        social.setUrl("https://social.example.com");
        social.setShareLink("https://example.com/share");
        social.setApp("TestApp");

        // Test getters
        assertEquals("Social Name", social.getSocialName());
        assertEquals("https://social.example.com", social.getUrl());
        assertEquals("https://example.com/share", social.getShareLink());
        assertEquals("TestApp", social.getApp());
    }

    @Test
    public void testEquals() {
        Social social1 = new Social();
        social1.setSocialName("Social Name");
        social1.setUrl("https://social.example.com");
        social1.setShareLink("https://example.com/share");
        social1.setApp("TestApp");

        Social social2 = new Social();
        social2.setSocialName("Social Name");
        social2.setUrl("https://social.example.com");
        social2.setShareLink("https://example.com/share");
        social2.setApp("TestApp");

        assertEquals(social1, social2);

        // Change one attribute of social2
        social2.setSocialName("Another Social Name");

        assertNotEquals(social1, social2);

        assertNotEquals(null, social1);

        assertEquals(social1, social1);
    }
}
