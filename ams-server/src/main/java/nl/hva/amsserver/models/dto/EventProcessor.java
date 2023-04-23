package nl.hva.amsserver.models.dto;

/**
 * The type Event processor.
 *
 * @author Harmohat Khangura
 */
public class EventProcessor {
    private long user;
    private long quiz;

    /**
     * Instantiates a new Event processor.
     *
     * @param user the user
     * @param quiz the quiz
     */
    public EventProcessor(long user, long quiz) {
        this.user = user;
        this.quiz = quiz;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public long getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(long user) {
        this.user = user;
    }

    /**
     * Gets quiz.
     *
     * @return the quiz
     */
    public long getQuiz() {
        return quiz;
    }

    /**
     * Sets quiz.
     *
     * @param quiz the quiz
     */
    public void setQuiz(long quiz) {
        this.quiz = quiz;
    }
}
