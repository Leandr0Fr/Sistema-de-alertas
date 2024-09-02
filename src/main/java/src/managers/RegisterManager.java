package src.managers;

import src.alerts.topics.Topic;
import src.database.Database;
import src.user.User;

import java.util.Collection;

import static src.constants.ExceptionMessages.*;

public class RegisterManager {
    private final Database db;

    public RegisterManager() {
        this.db = Database.getInstance();
    }

    public void registerUser(String name, String email) {
        if (name == null) throw new NullPointerException(NAME_NULL_EXCEPTION);
        if (email == null) throw new NullPointerException(EMAIL_NULL_EXCEPTION);
        if (name.isEmpty()) throw new IllegalArgumentException(NAME_VOID_EXCEPTION);
        if (email.isEmpty()) throw new IllegalArgumentException(EMAIL_VOID_EXCEPTION);

        Collection<User> usersRegister = db.getUsers().values();
        for (User user : usersRegister) {
            if (user.getEmail().equals(email)) throw new IllegalArgumentException(EMAIL_EXISTS_EXCEPTION    );
        }

        db.addUser(new User(name, email));
    }

    public void registerTopic(String title, String description) {
        if (title == null) throw new NullPointerException(TITLE_NULL_EXCEPTION);
        if (description == null) throw new NullPointerException(DESCRIPTION_NULL_EXCEPTION);
        if (title.isEmpty()) throw new IllegalArgumentException(TITLE_VOID_EXCEPTION);
        if (description.isEmpty()) throw new IllegalArgumentException(DESCRIPTION_VOID_EXCEPTION);
        db.addTopic(new Topic(title, description));
    }

}
