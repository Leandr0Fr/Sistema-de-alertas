package src.utils;

import org.apache.commons.validator.routines.EmailValidator;

import static src.constants.ExceptionMessages.*;

public class EmailValidatorImp {

    public static boolean isEmailValid(String email){
        if(email == null) throw new NullPointerException(EMAIL_NULL_EXCEPTION);
        if(email.isEmpty()) throw new IllegalArgumentException(EMAIL_VOID_EXCEPTION);
        return EmailValidator.getInstance().isValid(email);
    }
}
