package observers;

import alerts.Alert;

import java.util.Map;

public interface SubjectPanel {
    void notifyObservers(Map<Integer, ObserverPanel> observers, Alert alert);

    void notifyObserver(Map<Integer, ObserverPanel> observers, Alert alert, int userID);

}
