import alerts.Alert;
import alerts.topics.Topic;
import database.Database;
import managers.RegisterManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSystemAlertUniqueUser {
    private SystemAlert systemAlert;
    private Database database;
    private RegisterManager registerManager;

    @Before
    public void setUp() {
        this.database = Database.getInstance();
        this.systemAlert = new SystemAlert();
        this.registerManager = new RegisterManager();
    }

    @Test(expected = NullPointerException.class)
    public void testNotifyUserWithAlertNull() {
        this.systemAlert.notifyUser(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotifyUserWithTopicNoRegister() {
        Topic topicTest = new Topic("testNoRegister", "this topic is for testing");
        Alert alertTest = new Alert(topicTest, true);
        this.systemAlert.notifyUser(alertTest, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotifyUserWithAlertNotUnique() {
        registerManager.registerTopic("testRegister","this topic is for testing");
        Alert alertTest = new Alert(database.getTopics().get(0), false);
        this.systemAlert.notifyUser(alertTest, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotifyUserWithNegativeID() {
        registerManager.registerTopic("testRegister","this topic is for testing");
        Alert alertTest = new Alert(database.getTopics().get(0), true);
        this.systemAlert.notifyUser(alertTest, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotifyUserWithInvalidID() {
        registerManager.registerTopic("testRegister","this topic is for testing");
        Alert alertTest = new Alert(database.getTopics().get(0), true);
        this.systemAlert.notifyUser(alertTest, database.getNumRecordsUser()+1);
    }

    @Test()
    public void testNotifyUser() {
        registerManager.registerUser("menem", "test2@gmail.com");
        registerManager.registerTopic("testRegister","this topic is for testing");
        Alert alertTest = new Alert(database.getTopics().get(0), true);
        this.systemAlert.notifyUser(alertTest, 0);

        assertEquals(1, database.getUsers().get(0).getNotificationPanel().getAlerts().size());
    }
}
