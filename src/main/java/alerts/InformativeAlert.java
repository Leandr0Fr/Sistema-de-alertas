package alerts;


import alerts.topics.Topic;

public class InformativeAlert extends Alert {
    public InformativeAlert(Topic topic, boolean isUnique) {
        super(topic, isUnique);
    }

    public InformativeAlert(Topic topic, boolean isUnique, String expirationDate) {
        super(topic, isUnique, expirationDate);
    }
}
