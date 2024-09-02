package user;

import alerts.Alert;
import alerts.InformativeAlert;
import alerts.topics.Topic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUser {
    private User user;
    private User validUser;
    private final String validName = "Menem";
    private final String validEmail = "plainaddress@gmail.com";
    private Alert alert;

    @Before
    public void setUp() {
        this.validUser = new User(this.validName, this.validEmail);
        Topic topic = new Topic("Test", "This topic is for testing");
        this.alert = new InformativeAlert(topic, true);
        this.validUser.getNotificationPanel().getAlerts().add(alert);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() {
        String invalidEmail = "plainaddress";
        user = new User(this.validName, invalidEmail);
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

    @Test()
    public void testViewAlert() {
        boolean alertIsReadBefore = this.validUser.getNotificationPanel().getAlerts().get(0).isRead();
        assertFalse(alertIsReadBefore);

        boolean returnViewAlert = this.validUser.viewAlert(this.alert);
        assertTrue(returnViewAlert);

        boolean alertIsReadAfter = this.validUser.getNotificationPanel().getAlerts().get(0).isRead();
        assertTrue(alertIsReadAfter);
    }

    @Test()
    public void testNoViewAlert() {
        Topic topicFootball = new Topic("Football", "River Plate 0 - 0 Independiente");
        Alert alertFootball = new InformativeAlert(topicFootball, false, "2024:09:03 20:00");
        boolean returnViewAlert = this.validUser.viewAlert(alertFootball);
        assertFalse(returnViewAlert);
    }


    @Test(expected = NullPointerException.class)
    public void testViewAlertNull() {
        this.validUser.viewAlert(null);
    }
}
