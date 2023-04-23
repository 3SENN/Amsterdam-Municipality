package nl.hva.amsserver.models.quiz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.serializers.QuizSerializer;
import nl.hva.amsserver.serializers.UserSerializer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity //  annotation indicates that the class is a persistent Java class.
@Table(name = "QuizResult")
public class QuizResult {
    @Id // annotation is for the primary key.
    @GeneratedValue(strategy = GenerationType.AUTO)
    // annotation is used to define generation strategy for the primary key. (AI)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // LAZY: fetch when needed
    @JoinColumn(name = "user_id", nullable = false) // specify the foreign key column
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonSerialize(using = UserSerializer.class)
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // LAZY: fetch when needed
    @JoinColumn(name = "quiz_id", nullable = false) // specify the foreign key column
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonSerialize(using = QuizSerializer.class)
    private Quiz quizId;

    @Column(name = "score")
    private int score;

    public QuizResult() {

    }

    public QuizResult(User userId, Quiz quizId, int score) {
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public User getUserId() {
        return userId;
    }

    public Quiz getQuizId() {
        return quizId;
    }

    public int getScore() {
        return score;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setQuizId(Quiz quizId) {
        this.quizId = quizId;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
