package systemAlert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.SystemAlert;
import src.alerts.Alert;
import src.alerts.InformativeAlert;
import src.alerts.UrgentAlert;
import src.database.Database;
import src.managers.RegisterManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestSystemAlertGetAlerts {
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

    @Test(expected = IllegalArgumentException.class)
    public void testUserIDNegativeGetAlertsNoRead() {
        this.systemAlert.getNoReadAndNoExpiredAlerts(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserIDNotExistsGetAlertsNoRead() {
        this.systemAlert.getNoReadAndNoExpiredAlerts(this.database.getUsers().size() + 1);
    }

    @Test()
    public void testGetAlertsNoReadWithNoAlerts() {
        this.registerManager.registerUser("Menem", "carlos@gmail.com");
        assertEquals(0, this.systemAlert.getNoReadAndNoExpiredAlerts(0).size());

    }

    @Test()
    public void testGetAlertsNoReadWithZeroAlerts() {
        this.registerManager.registerUser("Menem", "carlos@gmail.com");

        List<Alert> alertsUser = this.systemAlert.getNoReadAndNoExpiredAlerts(0);
        List<Alert> alertsExpected = new ArrayList<>();

        assertEquals(alertsExpected, alertsUser);

    }

    @Test()
    public void testGetAlertsNoReadWithAlertsRead() {
        this.registerManager.registerUser("Menem", "carlos@gmail.com");
        this.registerManager.registerTopic("Test", "this topic is for testing.");
        Alert alert = new UrgentAlert(database.getTopics().get(0), true);
        this.systemAlert.notifyUser(alert,0);
        this.database.getUsers().get(0).viewAlert(alert);

        List<Alert> alertsUser = this.systemAlert.getNoReadAndNoExpiredAlerts(0);
        List<Alert> alertsExpected = new ArrayList<>();

        assertEquals(alertsExpected, alertsUser);

    }
    @Test()
    public void testGetAlertsNoReadWithOneAlertUrgent() {
        this.registerManager.registerUser("Menem", "carlos@gmail.com");
        this.registerManager.registerTopic("Test", "this topic is for testing.");
        Alert alert = new UrgentAlert(database.getTopics().get(0), true);
        this.systemAlert.notifyUser(alert,0);

        List<Alert> alertsUser = this.systemAlert.getNoReadAndNoExpiredAlerts(0);
        List<Alert> alertsExpected = new ArrayList<Alert>();
        alertsExpected.add(alert);

        assertEquals(alertsExpected, alertsUser);
    }

    @Test()
    public void testGetAlertsWithFirstInformativeSecondUrgent() {
        this.registerManager.registerUser("Menem", "carlos@gmail.com");
        this.registerManager.registerTopic("Test", "this topic is for testing.");
        this.registerManager.registerTopic("Test2", "this topic is for testing.");
        Alert alert = new UrgentAlert(database.getTopics().get(0), true);
        Alert alertTwo = new InformativeAlert(database.getTopics().get(0), true);

        this.systemAlert.notifyUser(alertTwo,0);
        this.systemAlert.notifyUser(alert,0);


        List<Alert> alertsUser = this.systemAlert.getNoReadAndNoExpiredAlerts(0);
        List<Alert> alertsExpected = new ArrayList<Alert>();
        alertsExpected.add(alert);
        alertsExpected.add(alertTwo);

        assertEquals(alertsExpected, alertsUser);
    }
    @Test()
    public void testGetAlertsWithFirstUrgentSecondInformative() {
        this.registerManager.registerUser("Menem", "carlos@gmail.com");
        this.registerManager.registerTopic("Test", "this topic is for testing.");
        this.registerManager.registerTopic("Test2", "this topic is for testing.");
        Alert alert = new UrgentAlert(database.getTopics().get(0), true);
        Alert alertTwo = new InformativeAlert(database.getTopics().get(0), true);

        this.systemAlert.notifyUser(alert,0);
        this.systemAlert.notifyUser(alertTwo,0);

        List<Alert> alertsUser = this.systemAlert.getNoReadAndNoExpiredAlerts(0);
        List<Alert> alertsExpected = new ArrayList<Alert>();
        alertsExpected.add(alert);
        alertsExpected.add(alertTwo);

        assertEquals(alertsExpected, alertsUser);
    }

    @Test()
    public void testValidGetNoReadAndNoExpiredAlerts() {
        this.registerManager.registerUser("Menem", "carlos@gmail.com");
        this.registerManager.registerTopic("Test1", "this topic is for testing.");
        this.registerManager.registerTopic("Test2", "this topic is for testing.");
        this.registerManager.registerTopic("Test3", "this topic is for testing.");
        this.registerManager.registerTopic("Test4", "this topic is for testing.");
        this.registerManager.registerTopic("Test5", "this topic is for testing.");
        this.registerManager.registerTopic("Test6", "this topic is for testing.");
        Alert alertOne = new InformativeAlert(database.getTopics().get(0), true);
        Alert alertTwo = new InformativeAlert(database.getTopics().get(0), true);
        Alert alertThree = new UrgentAlert(database.getTopics().get(0), true);
        Alert alertFour = new InformativeAlert(database.getTopics().get(0), true);
        Alert alertFive = new UrgentAlert(database.getTopics().get(0), true);
        Alert alertSix = new InformativeAlert(database.getTopics().get(0), true);
        this.systemAlert.notifyUser(alertOne,0);
        this.systemAlert.notifyUser(alertTwo,0);
        this.systemAlert.notifyUser(alertThree,0);
        this.systemAlert.notifyUser(alertFour,0);
        this.systemAlert.notifyUser(alertFive,0);
        this.systemAlert.notifyUser(alertSix,0);

        List<Alert> alertsExpected = new ArrayList<Alert>();
        alertsExpected.add(alertFive);
        alertsExpected.add(alertThree);
        alertsExpected.add(alertOne);
        alertsExpected.add(alertTwo);
        alertsExpected.add(alertFour);
        alertsExpected.add(alertSix);

        List<Alert> alertsUser = this.systemAlert.getNoReadAndNoExpiredAlerts(0);
        assertEquals(alertsExpected, alertsUser);
    }

    @Test()
    public void testGetAlertsWithTwoAlertsExpired() {
        this.registerManager.registerUser("Menem", "carlos@gmail.com");
        this.registerManager.registerTopic("Test", "this topic is for testing.");
        this.registerManager.registerTopic("Test2", "this topic is for testing.");
        Alert alert = new UrgentAlert(database.getTopics().get(0), true,"2100:12:05 05:33");
        Alert alertTwo = new InformativeAlert(database.getTopics().get(0), true, "2100:12:05 05:33");

        this.systemAlert.notifyUser(alert,0);
        this.systemAlert.notifyUser(alertTwo,0);

        List<Alert> alertsUser = this.systemAlert.getNoReadAndNoExpiredAlerts(0);
        List<Alert> alertsExpected = new ArrayList<Alert>();

        assertEquals(alertsExpected, alertsUser);
    }
}
