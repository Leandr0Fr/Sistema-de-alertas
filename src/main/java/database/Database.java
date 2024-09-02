package database;

import alerts.topics.Topic;
import observers.ObserverPanel;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static Database instance;

    private int numRecordsUser;
    private Map<Integer, User> users;
    private Map<Integer, ObserverPanel> observers;
    private List<Topic> topics;

    private Database() {
        this.numRecordsUser = 0;
        this.observers = new HashMap<>();
        this.users = new HashMap<>();
        this.topics = new ArrayList<>();
    }

    public static Database getInstance(){
        if (instance == null) instance = new Database();
        return instance;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public int getNumRecordsUser() {
        return numRecordsUser;
    }

    public Map<Integer, ObserverPanel> getObservers() {
        return observers;
    }

    public List<Topic> getTopics() {
        return topics;
    }
}
