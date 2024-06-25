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

import com.example.braguia.data.RelPin;
import com.example.braguia.data.Pin;

@RunWith(MockitoJUnitRunner.class)
public class PinUnitTest {

    @Mock
    Pin mockedPin;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGettersAndSetters() {
        Pin pin = new Pin();

        // Test setters
        pin.setId("456");
        pin.setName("Example Pin");
        pin.setDescription("This is an example pin.");
        pin.setLatitude("40.7128");
        pin.setLongitude("-74.0060");
        pin.setAltitude("10.0");

        // Test getters
        assertEquals("456", pin.getId());
        assertEquals("Example Pin", pin.getName());
        assertEquals("This is an example pin.", pin.getDescription());
        assertEquals("40.7128", pin.getLatitude());
        assertEquals("-74.0060", pin.getLongitude());
        assertEquals("10.0", pin.getAltitude());
    }

    @Test
    public void testEquals() {
        Pin pin1 = new Pin();
        pin1.setId("1");
        pin1.setName("Example Pin");

        Pin pin2 = new Pin();
        pin2.setId("1");
        pin2.setName("Example Pin");

        Pin pin3 = new Pin();
        pin3.setId("2");
        pin3.setName("Example Pin");

        // Test equals
        assertEquals(pin1, pin2);
        assertNotEquals(pin1, pin3);
    }

    @Test
    public void testRelPins() {
        Pin pin = new Pin();
        List<RelPin> relPins = new ArrayList<>();
        RelPin relPin1 = new RelPin();
        relPin1.setId("1");
        relPins.add(relPin1);
        pin.setRelPins(relPins);

        assertNotNull(pin.getRelPins());
        assertEquals(1, pin.getRelPins().size());
        assertEquals("1", pin.getRelPins().get(0).getId());
    }
}
