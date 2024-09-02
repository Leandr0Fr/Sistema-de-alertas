import alerts.Alert;
import database.Database;
import observers.ObserverPanel;
import observers.SubjectPanel;

import java.util.Map;

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
        if (!a.isUnique()) throw new IllegalArgumentException("una alerta que no es única no puede ser envíada a un usuario");
        if (!db.getTopics().contains(a.getTopic())) throw new IllegalArgumentException("Topic no registrado");
        if (userID < 0) throw new IllegalArgumentException("userID no puede ser negativo");
        if (!this.db.getUsers().containsKey(userID)) throw new IllegalArgumentException("userID no existe");

        notifyObserver(this.db.getObservers(), a, userID);
    }

    @Override
    public void notifyObserver(Map<Integer, ObserverPanel> observers, Alert alert, int userID) {
        observers.get(userID).update(alert);
    }

}
