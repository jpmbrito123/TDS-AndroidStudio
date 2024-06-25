package com.example.braguia;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.braguia.data.RelTrail;

public class RelTrailUnitTest {

    @Test
    public void testGettersAndSetters() {
        RelTrail relTrail = new RelTrail();

        // Test setters
        relTrail.setId("123");
        relTrail.setValue("Value");
        relTrail.setAttrib("Attrib");
        relTrail.setTrailId("456");

        // Test getters
        assertEquals("123", relTrail.getId());
        assertEquals("Value", relTrail.getValue());
        assertEquals("Attrib", relTrail.getAttrib());
        assertEquals("456", relTrail.getTrailId());
    }

    @Test
    public void testEquals() {
        RelTrail relTrail1 = new RelTrail();
        relTrail1.setId("123");
        relTrail1.setValue("Value");
        relTrail1.setAttrib("Attrib");
        relTrail1.setTrailId("456");

        RelTrail relTrail2 = new RelTrail();
        relTrail2.setId("123");
        relTrail2.setValue("Value");
        relTrail2.setAttrib("Attrib");
        relTrail2.setTrailId("456");

        assertEquals(relTrail1, relTrail2);

        relTrail2.setValue("Another Value");

        assertNotEquals(relTrail1, relTrail2);

        assertNotEquals(null, relTrail1);

        assertEquals(relTrail1, relTrail1);
    }
}
