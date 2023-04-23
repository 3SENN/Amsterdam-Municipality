package nl.hva.amsserver.models.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.serializers.UserSerializer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The Quiz model.
 *
 * @author Harmohat Khangura
 */
@Entity
@Table(name = "quiz")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Quiz {
    @Id // annotation is for the primary key.
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private final int level = 2;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "created_by")
    @JsonSerialize(using = UserSerializer.class)
    private User createdBy;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "quizzes", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Set<Question> questions = new HashSet<>();
    private int amountToRegister;
    private String location;
    private String link;
    private String address;
    private int totalQuestions;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startsAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endsAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    /**
     * Instantiates a new Quiz.
     */
    public Quiz() {
    }

    /**
     * Instantiates a new Quiz.
     *
     * @param title            The title of the quiz
     * @param amountToRegister The amount of people who can register for the quiz
     * @param location         The location where the quiz is going to be played (online or irl)
     * @param link             the link
     * @param address          The address of the quiz if it is IRL
     * @param totalQuestions   Total questions for the quiz
     * @param startsAt         The datetime of when the quiz will start
     * @param endsAt           The datetime of when the quiz will end
     * @param createdAt        The datetime of when the quiz was created
     * @param updatedAt        The datetime of when the quiz was updated
     */
    public Quiz(String title, int amountToRegister, String location, String link, String address,
                int totalQuestions, LocalDateTime startsAt, LocalDateTime endsAt, LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.title = title;
        this.amountToRegister = amountToRegister;
        this.location = location;
        this.link = link;
        this.address = address;
        this.totalQuestions = totalQuestions;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Quiz(User createdBy) {
        super();
        this.createdBy = createdBy;
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.getQuizzes().remove(this);
    }

    public void remove() {
        for(Question question : new HashSet<>(questions)) {
            removeQuestion(question);
        }
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
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets amount to register.
     *
     * @return the amount to register
     */
    public int getAmountToRegister() {
        return amountToRegister;
    }

    /**
     * Sets amount to register.
     *
     * @param amountToRegister the amount to register
     */
    public void setAmountToRegister(int amountToRegister) {
        this.amountToRegister = amountToRegister;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets link.
     *
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets link.
     *
     * @param link the link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets total questions.
     *
     * @return the total questions
     */
    public int getTotalQuestions() {
        return totalQuestions;
    }

    /**
     * Sets total questions.
     *
     * @param totalQuestions the total questions
     */
    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    /**
     * Gets starts at.
     *
     * @return the starts at
     */
    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    /**
     * Sets starts at.
     *
     * @param startsAt the starts at
     */
    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    /**
     * Gets ends at.
     *
     * @return the ends at
     */
    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    /**
     * Sets ends at.
     *
     * @param endsAt the ends at
     */
    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets updated at.
     *
     * @return the updated at
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    /**
     * Sets updated at.
     *
     * @param updatedAt the updated at
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdBy=" + createdBy +
                ", amountToRegister=" + amountToRegister +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", totalQuestions=" + totalQuestions +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
