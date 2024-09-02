package alerts.topics;

import java.util.Objects;

public class Topic {
    private final String title;
    private final String description;

    public Topic(String title, String description) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("title no puede ser null o vacio");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("description no puede ser null o vacio");
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
