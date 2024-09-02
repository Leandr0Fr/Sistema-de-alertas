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

public class SystemAlert implements SubjectPanel {
    private Database db;

    public SystemAlert() {
        this.db = Database.getInstance();
    }

    public void notifyAll(Alert a) {
        if (a == null) throw new NullPointerException("alert no puede ser null");
        if (!db.getTopics().contains(a.getTopic())) throw new IllegalArgumentException("Topic no registrado");
        if (a.isUnique())
            throw new IllegalArgumentException("Una alerta Unica no puede ser enviada a todos los usuarios");
        notifyObservers(this.db.getObservers(), a);
    }

    @Override
    public void notifyObservers(Map<Integer, ObserverPanel> observers, Alert alert) {
        for (ObserverPanel observer : observers.values()) {
            observer.update(alert);
        }
    }

    public void notifyUser(Alert a, int userID) {
        if (a == null) throw new NullPointerException("alert no puede ser null");
        if (!a.isUnique())
            throw new IllegalArgumentException("una alerta que no es única no puede ser envíada a un usuario");
        if (!db.getTopics().contains(a.getTopic())) throw new IllegalArgumentException("Topic no registrado");
        if (userID < 0) throw new IllegalArgumentException("userID no puede ser negativo");
        if (!this.db.getUsers().containsKey(userID)) throw new IllegalArgumentException("userID no existe");

        notifyObserver(this.db.getObservers(), a, userID);
    }

    @Override
    public void notifyObserver(Map<Integer, ObserverPanel> observers, Alert alert, int userID) {
        observers.get(userID).update(alert);
    }

    public List<Alert> getNoReadAndNoExpiredAlerts(int userID) {
        if (userID < 0) throw new IllegalArgumentException("userID no puede ser negativo");
        if (!this.db.getUsers().containsKey(userID)) throw new IllegalArgumentException("userID no existe");

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
        if (topic == null) throw new NullPointerException("Topic no puede ser null");
        if (!this.db.getTopics().contains(topic)) throw new IllegalArgumentException("Topic no está registrado");

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
