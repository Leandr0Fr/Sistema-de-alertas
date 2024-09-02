package managers;

import alerts.topics.Topic;
import database.Database;
import user.User;

import java.util.Collection;


public class RegisterManager {
    private Database db;

    public RegisterManager() {
        this.db = Database.getInstance();
    }

    public void registerUser(String name, String email) {
        if (name == null) throw new NullPointerException("Name no puede ser null");
        if (email == null) throw new NullPointerException("Email no puede ser null");
        if (name.isEmpty()) throw new IllegalArgumentException("Name no puede ser vacío");
        if (email.isEmpty()) throw new IllegalArgumentException("Email no puede ser vacío");

        Collection<User> usersRegister = db.getUsers().values();
        for (User user : usersRegister) {
            if (user.getEmail().equals(email)) throw new IllegalArgumentException("Email ya registrado");
        }

        db.addUser(new User(name, email));
    }

    public void registerTopic(String title, String description){
        if (title == null) throw new NullPointerException("Title no puede ser null");
        if (description == null) throw new NullPointerException("Description no puede ser null");
        if (title.isEmpty()) throw new IllegalArgumentException("Title no puede ser vacío");
        if (description.isEmpty()) throw new IllegalArgumentException("Description no puede ser vacío");
        db.addTopic(new Topic(title, description));
    }

}
