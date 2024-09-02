package alerts;


import alerts.topics.Topic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;


public class Alert {
    private LocalDateTime expirationDate;
    private boolean isRead;
    private boolean isUnique;
    private Topic topic;

    public Alert(Topic topic, boolean isUnique) {
        if (topic == null) {
            throw new NullPointerException("topic no puede ser declarado null");
        }
        this.topic = topic;
        this.isUnique = isUnique;
    }

    public Alert(Topic topic, boolean isUnique, String expirationDate) {
        if (topic == null) {
            throw new NullPointerException("topic no puede ser declarado null");
        }
        this.expirationDate = parseTime(expirationDate);
        this.isUnique = isUnique;
        this.topic = topic;
    }

    private LocalDateTime parseTime(String expirationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm");
        try {
            return LocalDateTime.parse(expirationDate, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void readAlert(){
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
