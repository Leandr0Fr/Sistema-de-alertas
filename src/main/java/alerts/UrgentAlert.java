package alerts;

import alerts.topics.Topic;

public class UrgentAlert extends Alert{
    public UrgentAlert(Topic topic, boolean isUnique) {
        super(topic, isUnique);
    }

    public UrgentAlert(Topic topic, boolean isUnique, String expirationDate) {
        super(topic, isUnique, expirationDate);
    }
}
