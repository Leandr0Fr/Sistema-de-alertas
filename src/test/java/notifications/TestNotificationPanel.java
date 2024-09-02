package notifications;

import org.junit.After;
import src.alerts.Alert;
import src.alerts.topics.Topic;
import src.notifications.NotificationPanel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestNotificationPanel {
    private NotificationPanel notificationPanel;

    @Before
    public void setUp() {
        this.notificationPanel = new NotificationPanel();
    }

    @Test(expected = NullPointerException.class)
    public void testNullUpdate() {
        this.notificationPanel.update(null);
    }

    @Test()
    public void testAddAlert() {
        Topic topicTest = new Topic("test", "this topic is for testing");
        Alert alertTest = new Alert(topicTest, true);
        this.notificationPanel.update(alertTest);

        assertEquals(1, this.notificationPanel.getAlerts().size());
    }

    @Test()
    public void testZeroAlerts() {
        assertEquals(0, this.notificationPanel.getAlerts().size());
    }

}
