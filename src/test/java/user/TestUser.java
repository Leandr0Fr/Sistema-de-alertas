package user;

import alerts.topics.Topic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUser {
    private User user;
    private User validUser;
    private String validName = "Menem";
    private String validEmail = "plainaddress@gmail.com";
    private String invalidEmail = "plainaddress";

    @Before
    public void setUp() {
        this.validUser = new User(this.validName, this.validEmail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() {
        user = new User(this.validName, this.invalidEmail);
    }

    @Test()
    public void testValidEmail() {
        user = new User(this.validName, this.validEmail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoidName() {
        user = new User("", this.validEmail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        user = new User(null, this.validEmail);
    }

    @Test()
    public void testValidName() {
        user = new User(this.validName, this.validEmail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddExistingTopic() {
        this.validUser.addTopicDenied("test");
        this.validUser.addTopicDenied("test");
    }

    @Test()
    public void testAddTopic() {
        int amountTopicsDeniedBefore = this.validUser.getTopicsDenied().size();
        assertEquals(0, amountTopicsDeniedBefore);

        this.validUser.addTopicDenied("test");
        int amountTopicsDeniedAfter = this.validUser.getTopicsDenied().size();
        assertEquals(1, amountTopicsDeniedAfter);

        assertNotEquals(amountTopicsDeniedAfter, amountTopicsDeniedBefore);
    }
}
