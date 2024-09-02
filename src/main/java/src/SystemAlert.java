package src;

import src.alerts.Alert;
import src.alerts.InformativeAlert;
import src.alerts.UrgentAlert;
import src.alerts.topics.Topic;
import src.database.Database;
import src.notifications.NotificationPanel;
import src.observers.ObserverPanel;
import src.observers.SubjectPanel;

import java.time.LocalDateTime;
import java.util.*;

import static src.constants.ExceptionMessages.*;

public class SystemAlert implements SubjectPanel {
    private final Database db;

    public SystemAlert() {
        this.db = Database.getInstance();
    }

    public void notifyAll(Alert a) {
        if (a == null) throw new NullPointerException(ALERT_NULL_EXCEPTION);
        if (!db.getTopics().contains(a.getTopic())) throw new IllegalArgumentException(TOPICS_NOT_EXISTS_EXCEPTION);
        if (a.isUnique())
            throw new IllegalArgumentException(ALERT_UNIQUE_SEND_ALL_EXCEPTION);
        notifyObservers(this.db.getObservers(), a);
    }

    @Override
    public void notifyObservers(Map<Integer, ObserverPanel> observers, Alert alert) {
        for (ObserverPanel observer : observers.values()) {
            observer.update(alert);
        }
    }

    public void notifyUser(Alert a, int userID) {
        if (a == null) throw new NullPointerException(ALERT_NULL_EXCEPTION);
        if (!a.isUnique())
            throw new IllegalArgumentException(ALERT_GLOBAL_SEND_USER_EXCEPTION);
        if (!db.getTopics().contains(a.getTopic())) throw new IllegalArgumentException(TOPICS_NOT_EXISTS_EXCEPTION);
        if (userID < 0) throw new IllegalArgumentException(USERID_NEGATIVE_EXCEPTION);
        if (!this.db.getUsers().containsKey(userID)) throw new IllegalArgumentException(USERID_NOT_EXISTS_EXCEPTION);

        notifyObserver(this.db.getObservers(), a, userID);
    }

    @Override
    public void notifyObserver(Map<Integer, ObserverPanel> observers, Alert alert, int userID) {
        observers.get(userID).update(alert);
    }

    public List<Alert> getNoReadAndNoExpiredAlerts(int userID) {
        if (userID < 0) throw new IllegalArgumentException(USERID_NEGATIVE_EXCEPTION);
        if (!this.db.getUsers().containsKey(userID)) throw new IllegalArgumentException(USERID_NOT_EXISTS_EXCEPTION);

        List<Alert> alerts = new ArrayList<>();
        NotificationPanel notificationPanel = db.getUsers().get(userID).getNotificationPanel();

        List<Alert> allAlerts = notificationPanel.getAlerts();
        for (int i = allAlerts.size() - 1; i >= 0; i--) {
            Alert alert = allAlerts.get(i);
            if (alert instanceof UrgentAlert && isAlertNoReadAndNoExpired(alert)) {
                alerts.add(alert);
            }
        }

        for (Alert alert : allAlerts) {
            if (alert instanceof InformativeAlert && isAlertNoReadAndNoExpired(alert)) {
                alerts.add(alert);
            }
        }

        return alerts;
    }

    private boolean isAlertNoReadAndNoExpired(Alert alert) {
        LocalDateTime localTime = LocalDateTime.now();
        return !alert.isRead() && (alert.getExpirationDate() == null || alert.getExpirationDate().isAfter(localTime));
    }

    public List<Map.Entry<String, Alert>> getNoReadAlertsWithSpecificTopic(Topic topic) {
        if (topic == null) throw new NullPointerException(TOPIC_NULL_EXCEPTION);
        if (!this.db.getTopics().contains(topic)) throw new IllegalArgumentException(TOPICS_NOT_EXISTS_EXCEPTION);

        List<Map.Entry<String, Alert>> alertsOut = new ArrayList<>();

        Collection<ObserverPanel> notificationPanels = db.getObservers().values();

        for (ObserverPanel panel : notificationPanels) {
            NotificationPanel notificationPanel = (NotificationPanel) panel;
            List<Alert> alerts = notificationPanel.getAlerts();

            for (int i = alerts.size() - 1; i >= 0; i--) {
                Alert alert = alerts.get(i);
                if (alert instanceof UrgentAlert && isAlertNoReadAndNoExpired(alert) && isCommonTopics(topic, alert)) {
                    Map.Entry<String, Alert> alertMap = new AbstractMap.SimpleEntry<>(alert.isUnique() ? "Unique" : "Global", alert);
                    if (!alertsOut.contains(alertMap)) alertsOut.add(alertMap);
                }
            }

            for (Alert alert : alerts) {
                if (alert instanceof InformativeAlert && isAlertNoReadAndNoExpired(alert) && isCommonTopics(topic, alert)) {
                    Map.Entry<String, Alert> alertMap = new AbstractMap.SimpleEntry<>(alert.isUnique() ? "Unique" : "Global", alert);
                    if (!alertsOut.contains(alertMap)) alertsOut.add(alertMap);
                }
            }
        }
        return alertsOut;
    }

    private boolean isCommonTopics(Topic topic, Alert alert) {
        return alert.getTopic().getTitle().equals(topic.getTitle());
    }

}
