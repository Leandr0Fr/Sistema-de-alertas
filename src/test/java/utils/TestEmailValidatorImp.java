package utils;

import src.utils.EmailValidatorImp;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestEmailValidatorImp {

    @Test(expected = IllegalArgumentException.class)
    public void testVoidEmail() {
        EmailValidatorImp.isEmailValid("");
    }

    @Test(expected = NullPointerException.class)
    public void testNullEmail() {
        EmailValidatorImp.isEmailValid(null);
    }

    @Test()
    public void testValidEmail() {
        String[] validEmails = {
                "test@example.com",
                "src.user.name@domain.com",
                "user_name@domain.co.in",
                "src.user+name@domain.com",
                "src.user@sub.domain.com",
                "firstname.lastname@domain.com",
                "src.user%name@domain.com",
                "src.user-name@domain.com",
                "user_name@domain.com",
                "src.user+name@domain.com",
                "src.user@domain-name.com",
                "\"src.user@domain\"@example.com",
                "\"john..doe\"@example.com"
        };

        for (String email : validEmails) {
            assertTrue(EmailValidatorImp.isEmailValid(email));
        }
    }

    @Test()
    public void testInvalidEmail() {
        String[] invalidEmails = {
                "plainaddress",
                "@missingusername.com",
                "username@.com",
                "username@.com.",
                "username@domain..com",
                "username@domain_com",
                "username@domain.c",
                "username@domain.com (Joe Smith)",
                "username@domain@domain.com",
                ".username@domain.com",
                "username@domain.com.",
                "username@-domain.com",
                "username@domain..com",
                "john..doe@example.com",
                "john.doe@example..com"
        };
        for (String email : invalidEmails) {
            assertFalse(EmailValidatorImp.isEmailValid(email));
        }
    }
}
