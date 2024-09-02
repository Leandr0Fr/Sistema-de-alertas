package src.user;

import src.alerts.Alert;
import src.notifications.NotificationPanel;
import src.utils.EmailValidatorImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static src.constants.ExceptionMessages.*;

public class User {
    private final String name;
    private final String email;
    private final NotificationPanel notificationPanel;
    private final List<String> topicsDenied;

    public User(String name, String email) {
        if (!EmailValidatorImp.isEmailValid(email)) throw new IllegalArgumentException(EMAIL_INVALID_EXCEPTION);
        if (name == null || name.isEmpty()) throw new IllegalArgumentException(NAME_NULL_VOID_EXCEPTION);
        this.name = name;
        this.email = email;
        this.notificationPanel = new NotificationPanel();
        this.topicsDenied = new ArrayList<>();
    }

    public void addTopicDenied(String topicTitle) {
        if (topicsDenied.contains(topicTitle))
            throw new IllegalArgumentException(TOPICS_EXISTS_EXCEPTION + topicTitle);
        topicsDenied.add(topicTitle);
    }

    public boolean viewAlert(Alert alert) {
        if (alert == null) throw new NullPointerException(ALERT_NULL_EXCEPTION);
        for (Alert a : getNotificationPanel().getAlerts()) {
            if (a.equals(alert)) {
                a.readAlert();
                return true;
            }
        }
        return false;
    }

    public List<String> getTopicsDenied() {
        return topicsDenied;
    }

    public NotificationPanel getNotificationPanel() {
        return notificationPanel;
    }

    public String getEmail(){
        return this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(getNotificationPanel(), user.getNotificationPanel()) && Objects.equals(getTopicsDenied(), user.getTopicsDenied());
    }

}
