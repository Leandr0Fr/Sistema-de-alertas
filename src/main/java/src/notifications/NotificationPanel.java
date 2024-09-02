package src.notifications;

import src.alerts.Alert;
import src.observers.ObserverPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static src.constants.ExceptionMessages.ALERT_NULL_EXCEPTION;

public class NotificationPanel implements ObserverPanel {
    private final List<Alert> alerts;

    public NotificationPanel() {
        this.alerts = new ArrayList<>();
    }

    @Override
    public void update(Alert alert) {
        if (alert == null) {
            throw new NullPointerException(ALERT_NULL_EXCEPTION);
        }
        this.alerts.add(alert);
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationPanel that = (NotificationPanel) o;
        return Objects.equals(getAlerts(), that.getAlerts());
    }
}
