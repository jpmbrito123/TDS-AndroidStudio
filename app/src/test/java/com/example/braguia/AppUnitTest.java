package com.example.braguia;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.example.braguia.data.App;
import com.example.braguia.data.Contact;
import com.example.braguia.data.Partner;
import com.example.braguia.data.Social;

@RunWith(MockitoJUnitRunner.class)
public class AppUnitTest {

    @Mock
    App mockedApp;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGettersAndSetters() {
        App app = new App();

        // Test setters
        app.setApp_name("My App");
        app.setApp_desc("This is my app.");
        app.setApp_landing_page_text("Welcome to my app!");

        // Test getters
        assertEquals("My App", app.getApp_name());
        assertEquals("This is my app.", app.getApp_desc());
        assertEquals("Welcome to my app!", app.getApp_landing_page_text());
    }

    @Test
    public void testSocials() {
        App app = new App();
        List<Social> socials = new ArrayList<>();
        Social social1 = new Social();
        social1.setSocialName("Facebook");
        socials.add(social1);
        app.setSocials(socials);

        assertNotNull(app.getSocials());
        assertEquals(1, app.getSocials().size());
        assertEquals("Facebook", app.getSocials().get(0).getSocialName());
    }

    @Test
    public void testContacts() {
        App app = new App();
        List<Contact> contacts = new ArrayList<>();
        Contact contact1 = new Contact();
        contact1.setEmail("test@example.com");
        contacts.add(contact1);
        app.setContacts(contacts);

        assertNotNull(app.getContacts());
        assertEquals(1, app.getContacts().size());
        assertEquals("test@example.com", app.getContacts().get(0).getEmail());
    }

    @Test
    public void testPartners() {
        App app = new App();
        List<Partner> partners = new ArrayList<>();
        Partner partner1 = new Partner();
        partner1.setPartnerName("Example Partner");
        partners.add(partner1);
        app.setPartners(partners);

        assertNotNull(app.getPartners());
        assertEquals(1, app.getPartners().size());
        assertEquals("Example Partner", app.getPartners().get(0).getPartnerName());
    }

    @Test
    public void testEquals() {
        List<Partner> partners = new ArrayList<>();
        Partner partner1 = new Partner();
        partner1.setPartnerName("Example Partner");
        partners.add(partner1);

        App app1 = new App();
        app1.setApp_name("My App");
        app1.setApp_desc("This is my app.");
        app1.setApp_landing_page_text("Welcome to my app!");
        app1.setPartners(partners);

        App app2 = new App();
        app2.setApp_name("My App");
        app2.setApp_desc("This is my app.");
        app2.setApp_landing_page_text("Welcome to my app!");
        app2.setPartners(partners);

        App app3 = new App();
        app3.setApp_name("Other App");
        app3.setApp_desc("This is another app.");
        app3.setApp_landing_page_text("Welcome to another app!");
        app3.setPartners(partners);

        App app4 = new App();
        app4.setApp_name("Other App");
        app4.setApp_desc("This is another app.");
        app4.setApp_landing_page_text("Welcome to another app!");

        // Test equals
        assertEquals(app1, app2);
        assertNotEquals(app1, app3);
        assertNotEquals(app3, app4);
    }
}
