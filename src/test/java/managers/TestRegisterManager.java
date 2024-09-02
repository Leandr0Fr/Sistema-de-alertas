package managers;

import org.junit.Before;
import org.junit.Test;


public class TestRegisterManager {
    private RegisterManager registerManager;
    private String validName;
    private String validEmail;
    private String validTitle;
    private String validDescription;

    @Before
    public void setUp() {
        this.registerManager = new RegisterManager();
        this.validName = "Menem";
        this.validEmail = "test@gmail.com";
        this.validTitle = "Test";
        this.validDescription = "This topic is for testing.";
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoidName() {
        this.registerManager.registerUser("", this.validEmail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoidEmail() {
        this.registerManager.registerUser(this.validName, "");
    }

    @Test(expected = NullPointerException.class)
    public void testNullName() {
        this.registerManager.registerUser(null, this.validEmail);
    }

    @Test(expected = NullPointerException.class)
    public void testNullEmail() {
        this.registerManager.registerUser(this.validName, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExistingEmail() {
        this.registerManager.registerUser(this.validName, this.validEmail);
        this.registerManager.registerUser("Carlos", this.validEmail);
    }

    @Test()
    public void testAddValidUser() {
        this.registerManager.registerUser("Juan", "juan@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoidTitleTopic() {
        this.registerManager.registerTopic("", this.validDescription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoidDescriptionTopic() {
        this.registerManager.registerTopic(this.validTitle, "");
    }

    @Test(expected = NullPointerException.class)
    public void testNullTitleTopic() {
        this.registerManager.registerTopic(null, this.validDescription);
    }

    @Test(expected = NullPointerException.class)
    public void testNullDescriptionTopic() {
        this.registerManager.registerTopic(this.validTitle, null);
    }

    @Test()
    public void testAddValidTopic() {
        this.registerManager.registerTopic(this.validTitle, this.validDescription);
    }
}
