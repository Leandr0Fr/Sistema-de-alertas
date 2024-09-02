package notifications;

import alerts.Alert;
import observers.ObserverPanel;

import java.util.ArrayList;
import java.util.List;

public class NotificationPanel implements ObserverPanel {
    private List<Alert> alerts;

    public NotificationPanel() {
        this.alerts = new ArrayList<>();
    }

    @Override
    public void update(Alert alert) {
        if(alert == null){
            throw new NullPointerException("alert no puede ser null");
        }
        this.alerts.add(alert);
    }

    public List<Alert> getAlerts() {
        return alerts;
    }
}
