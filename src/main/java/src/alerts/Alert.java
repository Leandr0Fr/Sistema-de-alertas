package src.alerts;


import src.alerts.topics.Topic;

import java.time.LocalDateTime;
import java.util.Objects;

import static src.constants.ExceptionMessages.*;
import static src.utils.ParseTime.parseTime;


public class Alert {
    private LocalDateTime expirationDate;
    private boolean isRead;
    private final boolean isUnique;
    private final Topic topic;

    public Alert(Topic topic, boolean isUnique) {
        if (topic == null) {
            throw new NullPointerException(TOPIC_NULL_EXCEPTION);
        }
        this.topic = topic;
        this.isUnique = isUnique;
    }

    public Alert(Topic topic, boolean isUnique, String expirationDate) {
        if (topic == null) {
            throw new NullPointerException(TOPIC_NULL_EXCEPTION);
        }
        this.expirationDate = parseTime(expirationDate);
        this.isUnique = isUnique;
        this.topic = topic;
    }


    public boolean isUnique() {
        return isUnique;
    }

    public void readAlert() {
        this.isRead = true;
    }

    public boolean isRead() {
        return isRead;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public Topic getTopic() {
        return topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return isRead() == alert.isRead() && isUnique() == alert.isUnique() && Objects.equals(getExpirationDate(), alert.getExpirationDate()) && Objects.equals(topic, alert.topic);
    }

}
