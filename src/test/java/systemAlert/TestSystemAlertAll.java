package systemAlert;

import org.junit.After;
import src.alerts.Alert;
import src.alerts.topics.Topic;
import src.database.Database;
import src.managers.RegisterManager;
import org.junit.Before;
import org.junit.Test;
import src.SystemAlert;

import static org.junit.Assert.*;

public class TestSystemAlertAll {
    private SystemAlert systemAlert;
    private Database database;
    private RegisterManager registerManager;

    @Before
    public void setUp() {
        this.database = Database.getInstance();
        this.systemAlert = new SystemAlert();
        this.registerManager = new RegisterManager();
    }

    @After()
    public void clean() {
        database.getUsers().clear();
        database.setNumRecordsUser(0);
        database.getObservers().clear();
        database.getTopics().clear();
    }

    @Test(expected = NullPointerException.class)
    public void testNotifyAlertNull() {
        this.systemAlert.notifyAll(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotifyAlertAllWithTopicNoRegister() {
        Topic topicTest = new Topic("test", "this topic is for testing");
        Alert alertTest = new Alert(topicTest, false);
        this.systemAlert.notifyAll(alertTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotifyAlertAllWithAlertUnique() {
        registerManager.registerTopic("test", "this topic is for testing");
        Alert alertTest = new Alert(database.getTopics().get(0), true);
        this.systemAlert.notifyAll(alertTest);
    }

    @Test()
    public void testNotifyAlertAllWithTwoUsers() {

        registerManager.registerUser("carlos", "test1@gmail.com");
        registerManager.registerUser("menem", "test2@gmail.com");
        registerManager.registerTopic("test", "this topic is for testing");

        Alert alertTest = new Alert(database.getTopics().get(0), false);
        this.systemAlert.notifyAll(alertTest);

        assertEquals(alertTest, database.getUsers().get(0).getNotificationPanel().getAlerts().get(0));
        assertEquals(alertTest, database.getUsers().get(1).getNotificationPanel().getAlerts().get(0));
    }

}
