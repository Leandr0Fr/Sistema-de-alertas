package database;

import org.junit.After;
import src.alerts.topics.Topic;
import src.observers.ObserverPanel;
import src.database.Database;
import src.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDatabase {

    private Database database;
    private User testUser;
    private ObserverPanel testObserverPanel;

    @Before
    public void setUp() {
        database = Database.getInstance();
        testUser = new User("testuser", "test@example.com");
        testObserverPanel = this.testUser.getNotificationPanel();
    }

    @After()
    public void clean(){
        database.getUsers().clear();
        database.getObservers().clear();
        database.getTopics().clear();
    }

    @Test()
    public void testSingletonInstance() {
        Database anotherDatabase = Database.getInstance();
        assertEquals(database, anotherDatabase);
    }

    @Test()
    public void testAddUser() {
        database.addUser(testUser);
        assertEquals(1, database.getNumRecordsUser());
        assertTrue(database.getUsers().containsValue(testUser));
        assertTrue(database.getObservers().containsValue(testObserverPanel));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDuplicateUser() {
        User testUserCarlos = new User("Carlos", "test@example.com");
        User testUserCarlosDuplicate = new User("Carlos", "test@example.com");
        database.addUser(testUserCarlos);
        database.addUser(testUserCarlosDuplicate);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullUser() {
        database.addUser(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullTopic() {
        database.addTopic(null);
    }

    @Test()
    public void testAddTopic() {
        assertEquals(0, database.getTopics().size());

        Topic topic = new Topic("Test", "This topic is for testing");
        database.addTopic(topic);

        assertEquals(topic, database.getTopics().get(0));
        assertEquals(1, database.getTopics().size());

    }
}
