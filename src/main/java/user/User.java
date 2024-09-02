package user;

import notifications.NotificationPanel;
import utils.EmailValidatorImp;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private NotificationPanel notificationPanel;
    private List<String> topicsDenied;

    public User(String name, String email) {
        if (!EmailValidatorImp.isEmailValid(email)) throw new IllegalArgumentException("email no válido");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("name no puede ser vacío ni null");
        this.name = name;
        this.email = email;
        this.notificationPanel = new NotificationPanel();
        this.topicsDenied = new ArrayList<>();
    }

}
