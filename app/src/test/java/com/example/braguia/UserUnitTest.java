package com.example.braguia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.braguia.data.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserUnitTest {
    @Mock
    User mockedUser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGettersAndSetters() {
        User user = new User("admin", "user123", "John", "Doe", "john@example.com");

        // Test getters
        assertEquals("user123", user.getUsername());
        assertEquals("admin", user.getUser_type());
        assertEquals("John", user.getFirst_name());
        assertEquals("Doe", user.getLast_name());
        assertEquals("john@example.com", user.getEmail());

        // Test setters
        user.setUsername("newUser");
        user.setUser_type("user");
        user.setFirst_name("Jane");
        user.setLast_name("Smith");
        user.setEmail("jane@example.com");

        // Test getters again after setting new values
        assertEquals("newUser", user.getUsername());
        assertEquals("user", user.getUser_type());
        assertEquals("Jane", user.getFirst_name());
        assertEquals("Smith", user.getLast_name());
        assertEquals("jane@example.com", user.getEmail());
    }

    @Test
    public void testEquals() {
        User user1 = new User("admin", "user123", "John", "Doe", "john@example.com");
        User user2 = new User("admin", "user123", "John", "Doe", "john@example.com");
        User user3 = new User("user", "user456", "Jane", "Smith", "jane@example.com");

        // Test equals
        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
    }
}
