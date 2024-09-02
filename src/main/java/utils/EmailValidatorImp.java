package utils;

import org.apache.commons.validator.routines.EmailValidator;
public class EmailValidatorImp {

    public static boolean isEmailValid(String email){
        if(email == null) throw new NullPointerException("Email no debe ser null");
        if(email.isEmpty()) throw new IllegalArgumentException("Email no debe ser vacío");
        return EmailValidator.getInstance().isValid(email);
    }
}
