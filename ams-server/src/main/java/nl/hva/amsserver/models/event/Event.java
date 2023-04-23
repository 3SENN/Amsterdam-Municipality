package nl.hva.amsserver.models.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.hva.amsserver.models.quiz.Question;
import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.serializers.QuizSerializer;
import nl.hva.amsserver.serializers.QuizWithUserSerializer;
import nl.hva.amsserver.serializers.UserSerializer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;

/**
 * The type Event.
 */
@Entity //  annotation indicates that the class is a persistent Java class.
@Table(name = "event") // annotation provides the table that maps this entity.
public class Event implements Serializable {
    @Id // annotation is for the primary key.
    @GeneratedValue(strategy = GenerationType.AUTO)// annotation is used to define generation strategy for the primary key. (AI)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // LAZY: fetch when needed
    @JoinColumn(name = "level_id", nullable = false) // specify the foreign key column
    @JsonSerialize(using = QuizWithUserSerializer.class)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // LAZY: fetch when needed
    @JoinColumn(name = "user_id", nullable = false) // specify the foreign key column
    @JsonSerialize(using = UserSerializer.class)
    private User user;

    /**
     * Instantiates a new Event.
     */
    public Event() {

    }

    /**
     * Instantiates a new Event.
     *
     * @param quiz the quiz
     * @param user the user
     */
    public Event(Quiz quiz, User user) {
        this.quiz = quiz;
        this.user = user;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets quiz.
     *
     * @return the quiz
     */
    public Quiz getQuiz() {
        return quiz;
    }

    /**
     * Sets quiz.
     *
     * @param quiz the quiz
     */
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }


}
