package user;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestUser {
    private User user;

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() {
        user = new User("test", "plainaddress");
    }

    @Test()
    public void testValidEmail() {
        user = new User("Marcelo", "plainaddress@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoidName() {
        user = new User("", "plainaddress@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        user = new User(null, "plainaddress@gmail.com");
    }

    @Test()
    public void testValidName() {
        user = new User("Gallardo", "plainaddress@gmail.com");
    }
}
