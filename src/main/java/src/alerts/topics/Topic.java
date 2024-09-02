package src.alerts.topics;

import java.util.Objects;

import static src.constants.ExceptionMessages.*;

public class Topic {
    private final String title;
    private final String description;

    public Topic(String title, String description) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException(TITLE_NULL_VOID_EXCEPTION);
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException(DESCRIPTION_NULL_VOID_EXCEPTION);
        }
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(title, topic.title) && Objects.equals(description, topic.description);
    }
}
