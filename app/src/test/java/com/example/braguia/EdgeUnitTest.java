package com.example.braguia;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

import com.example.braguia.data.Edge;
import com.example.braguia.data.Pin;

@RunWith(MockitoJUnitRunner.class)
public class EdgeUnitTest {

    @Mock
    Edge mockedEdge;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGettersAndSetters() {
        Edge edge = new Edge();
        Pin startPin = new Pin();
        Pin endPin = new Pin();

        // Test setters
        edge.setId("123");
        edge.setTransport("D");
        edge.setDuration("60");
        edge.setDescription("This is an edge.");
        edge.setTrail_id("456");
        edge.setStart_pin(startPin);
        edge.setEnd_pin(endPin);

        // Test getters
        assertEquals("123", edge.getId());
        assertEquals("D", edge.getTransport());
        assertEquals("60", edge.getDuration());
        assertEquals("This is an edge.", edge.getDescription());
        assertEquals("456", edge.getTrail_id());
        assertNotNull(edge.getStart_pin());
        assertNotNull(edge.getEnd_pin());
    }

    @Test
    public void testEquals() {
        Edge edge1 = new Edge();
        edge1.setId("123");
        edge1.setTransport("Bus");
        edge1.setDuration("1 hour");
        edge1.setDescription("This is an edge.");
        edge1.setTrail_id("456");

        Edge edge2 = new Edge();
        edge2.setId("123");
        edge2.setTransport("Bus");
        edge2.setDuration("1 hour");
        edge2.setDescription("This is an edge.");
        edge2.setTrail_id("456");

        Edge edge3 = new Edge();
        edge3.setId("456");
        edge3.setTransport("Train");
        edge3.setDuration("2 hours");
        edge3.setDescription("This is another edge.");
        edge3.setTrail_id("789");

        // Test equals
        assertEquals(edge1, edge2);
        assertNotEquals(edge1, edge3);
    }
}
