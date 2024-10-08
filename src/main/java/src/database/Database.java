package src.database;

import src.alerts.topics.Topic;
import src.observers.ObserverPanel;
import src.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static src.constants.ExceptionMessages.*;

public class Database {
    private static Database instance;

    private int numRecordsUser;
    private final Map<Integer, User> users;
    private final Map<Integer, ObserverPanel> observers;
    private final List<Topic> topics;

    private Database() {
        this.numRecordsUser = 0;
        this.observers = new HashMap<>();
        this.users = new HashMap<>();
        this.topics = new ArrayList<>();
    }

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public void addUser(User u) {
        if (u == null) throw new NullPointerException(USER_NULL_EXCEPTION);
        if (getUsers().containsValue(u)) throw new IllegalArgumentException(USER_EXISTS_EXCEPTION);
        getUsers().put(getNumRecordsUser(), u);
        getObservers().put(getNumRecordsUser(), u.getNotificationPanel());
        increaseCounter();
    }

    private void increaseCounter() {
        setNumRecordsUser(getNumRecordsUser() + 1);
    }

    public void addTopic(Topic t) {
        if (t == null) throw new NullPointerException(TOPIC_NULL_EXCEPTION);
        getTopics().add(t);
    }

    public void setNumRecordsUser(int n) {
        this.numRecordsUser = n;
    }

    public int getNumRecordsUser() {
        return numRecordsUser;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Map<Integer, ObserverPanel> getObservers() {
        return observers;
    }

    public List<Topic> getTopics() {
        return topics;
    }
}
