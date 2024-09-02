package alerts;

import src.alerts.topics.Topic;
import src.alerts.Alert;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class AlertTest {
    private Alert alert;
    private Topic topic;

    @Before
    public void setUp() {
        this.topic = new Topic("Test", "This topic is for testing");
    }

    @Test(expected = NullPointerException.class)
    public void testNullTopic() {
        this.alert = new Alert(null, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoidParseTime() {
        this.alert = new Alert(topic, true, "");
    }

    @Test()
    public void testValidParseTime() {
        String datetimeExpected = "2018-12-18T09:12";
        this.alert = new Alert(topic, true, "2018:12:18 09:12");

        assertEquals(datetimeExpected, this.alert.getExpirationDate().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidParseTime() {
        this.alert = new Alert(topic, true, "invalidString");
    }

    @Test()
    public void testReadAlert() {
        this.alert = new Alert(topic, true);
        boolean beforeIsReadStatus = this.alert.isRead();
        this.alert.readAlert();
        boolean afterIsReadStatus = this.alert.isRead();

        assertTrue(afterIsReadStatus);
        assertFalse(beforeIsReadStatus);
    }

    @Test()
    public void testUniqueAlert() {
        this.alert = new Alert(topic, true);
        assertTrue(this.alert.isUnique());
    }

    @Test()
    public void testNotUniqueAlert() {
        this.alert = new Alert(topic, false);
        assertFalse(this.alert.isUnique());
    }
}
