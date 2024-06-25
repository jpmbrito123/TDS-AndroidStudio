package com.example.braguia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.example.braguia.data.Edge;
import com.example.braguia.data.RelTrail;
import com.example.braguia.data.Trail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrailUnitTest {

    @Mock
    Trail mockedTrail;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGettersAndSetters() {
        Trail trail = new Trail();

        // Test setters
        trail.setId("123");
        trail.setUrl("https://example.com/trail.jpg");
        trail.setName("Example Trail");
        trail.setTrail_desc("This is an example trail.");
        trail.setTrail_duration("100");
        trail.setTrail_diff("E");

        // Test getters
        assertEquals("123", trail.getId());
        assertEquals("https://example.com/trail.jpg", trail.getUrl());
        assertEquals("Example Trail", trail.getName());
        assertEquals("This is an example trail.", trail.getTrail_desc());
        assertEquals("100", trail.getTrail_duration());
        assertEquals("E", trail.getTrail_diff());
    }

    @Test
    public void testEquals() {
        Trail trail1 = new Trail();
        trail1.setId("1");
        trail1.setUrl("https://example.com/trail1.jpg");

        Trail trail2 = new Trail();
        trail2.setId("1");
        trail2.setUrl("https://example.com/trail1.jpg");

        Trail trail3 = new Trail();
        trail3.setId("2");
        trail3.setUrl("https://example.com/trail1.jpg");

        // Test equals
        assertEquals(trail1, trail2);
        assertNotEquals(trail1, trail3);
    }

    @Test
    public void testRelTrails() {
        Trail trail = new Trail();
        List<RelTrail> relTrails = new ArrayList<>();
        RelTrail relTrail1 = new RelTrail();
        relTrail1.setId("1");
        relTrails.add(relTrail1);
        trail.setRelTrails(relTrails);

        assertNotNull(trail.getRelTrails());
        assertEquals(1, trail.getRelTrails().size());
        assertEquals("1", trail.getRelTrails().get(0).getId());
    }

    @Test
    public void testEdges() {
        Trail trail = new Trail();
        List<Edge> edges = new ArrayList<>();
        Edge edge1 = new Edge();
        edge1.setId("1");
        edges.add(edge1);
        trail.setEdges(edges);

        assertNotNull(trail.getEdges());
        assertEquals(1, trail.getEdges().size());
        assertEquals("1", trail.getEdges().get(0).getId());
    }
}