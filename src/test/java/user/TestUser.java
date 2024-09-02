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
        user = new User("test", "plainaddress@gmail.com");
    }
}
