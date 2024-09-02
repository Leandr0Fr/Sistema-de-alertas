package managers;

import org.junit.Before;
import org.junit.Test;


public class TestRegisterManager {
    private RegisterManager registerManager;
    private String validName = "Menem";
    private String validEmail = "test@gmail.com";

    @Before
    public void setUp() {
        this.registerManager = new RegisterManager();
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
}
