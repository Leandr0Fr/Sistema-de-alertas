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

    public void addTopicDenied(String topicTitle){
        if (topicsDenied.contains(topicTitle)) throw new IllegalArgumentException("El topico ya está agregado: "+ topicTitle);
        topicsDenied.add(topicTitle);
    }

    public List<String> getTopicsDenied() {
        return topicsDenied;
    }

    public NotificationPanel getNotificationPanel() {
        return notificationPanel;
    }
}
