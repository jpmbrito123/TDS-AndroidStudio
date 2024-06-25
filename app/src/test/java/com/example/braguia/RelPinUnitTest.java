package com.example.braguia;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.braguia.data.RelPin;

public class RelPinUnitTest {

    @Test
    public void testGettersAndSetters() {
        RelPin relPin = new RelPin();

        // Test setters
        relPin.setId("123");
        relPin.setValue("Value");
        relPin.setAttrib("Attrib");
        relPin.setPinId("456");

        // Test getters
        assertEquals("123", relPin.getId());
        assertEquals("Value", relPin.getValue());
        assertEquals("Attrib", relPin.getAttrib());
        assertEquals("456", relPin.getPinId());
    }

    @Test
    public void testEquals() {
        RelPin relPin1 = new RelPin();
        relPin1.setId("123");
        relPin1.setValue("Value");
        relPin1.setAttrib("Attrib");
        relPin1.setPinId("456");

        RelPin relPin2 = new RelPin();
        relPin2.setId("123");
        relPin2.setValue("Value");
        relPin2.setAttrib("Attrib");
        relPin2.setPinId("456");

        assertEquals(relPin1, relPin2);

        // Change one attribute of relPin2
        relPin2.setValue("Another Value");

        assertNotEquals(relPin1, relPin2);

        assertNotEquals(null, relPin1);

        assertEquals(relPin1, relPin1);
    }
}
