package nl.hva.amsserver.models.dto;

/**
 * The type Count participants.
 * <br/>
 * Is used to return a specific format in the REST controllers. format contains:
 * quizId - total amount of users who are registered for the quiz - max amount of users who can register for the quiz
 *
 * @author Harmohat Khangura
 */
public class CountParticipants {
   private long quizId;
   private long registered;
   private int max;

    /**
     * Instantiates a new Count participants.
     *
     * @param quizId     the quiz id
     * @param max        the max
     * @param registered the registered
     */
    public CountParticipants(long quizId, int max, long registered) {
        this.quizId = quizId;
        this.registered = registered;
        this.max = max;
    }

    /**
     * Gets quiz id.
     *
     * @return the quiz id
     */
    public long getQuizId() {
        return quizId;
    }

    /**
     * Sets quiz id.
     *
     * @param quizId the quiz id
     */
    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    /**
     * Gets registered.
     *
     * @return the registered
     */
    public long getRegistered() {
        return registered;
    }

    /**
     * Sets registered.
     *
     * @param registered the registered
     */
    public void setRegistered(long registered) {
        this.registered = registered;
    }

    /**
     * Gets max.
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets max.
     *
     * @param max the max
     */
    public void setMax(int max) {
        this.max = max;
    }
}
