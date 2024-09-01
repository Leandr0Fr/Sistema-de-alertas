package alerts.topics;

import static org.junit.Assert.*;

import org.junit.Test;

public class TopicTest {
    private Topic topic;

    @Test(expected = IllegalArgumentException.class)
    public void testNullDescription() {
        topic = new Topic("test", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullTittle() {
        topic = new Topic(null, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoidTittle() {
        topic = new Topic("", "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoidDescription() {
        topic = new Topic("test", "");
    }

    @Test()
    public void testValidParams() {
        String testTitle = "Football";
        String testDescription = "Estudiantes 7 - Gimnasia L.P 0";
        topic = new Topic(testTitle, testDescription);

        String titleTopic = topic.getTitle();
        assertEquals(titleTopic, testTitle);

        String descriptionTopic = topic.getDescription();
        assertEquals(descriptionTopic, testDescription);
    }
}
