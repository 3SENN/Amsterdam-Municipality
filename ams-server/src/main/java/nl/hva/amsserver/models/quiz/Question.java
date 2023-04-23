package nl.hva.amsserver.models.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * The Quiz question model.
 *
 * @author Harmohat Khangura
 */
@Entity
@Table(name = "Question")
public class Question {
    @Id
    @SequenceGenerator(name = "question_seq",
            sequenceName = "question_sequence",
            initialValue = 11, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    private long id;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<Quiz> quizzes = new HashSet<>();
    private String question;
    @Column(columnDefinition = "TEXT")
    private String info;
    private int points;
    private int block;
    private boolean hasMultipleAnswers;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<QuestionOption> questionOptions = new HashSet<QuestionOption>();

    /**
     * Instantiates a new Quiz question.
     */
    public Question() {

    }

    /**
     * Instantiates a new Quiz question.
     *
     * @param question           the question
     * @param info               the info
     * @param points             the points to achieve when the question was guessed right
     * @param block              the block
     * @param hasMultipleAnswers If the question has multiple answers
     */
    public Question(String question, String info, int points, int block, boolean hasMultipleAnswers) {
        this.question = question;
        this.info = info;
        this.points = points;
        this.block = block;
        this.hasMultipleAnswers = hasMultipleAnswers;
    }

    public Question(long id, String question, String info, int points, int block, boolean hasMultipleAnswers) {
        this(question, info, points, block, hasMultipleAnswers);
        this.id = id;
    }

    /**
     * Instantiates a new Question.
     *
     * @param quizzes the quizzes
     */
    public Question(Set<Quiz> quizzes) {
        super();
        this.quizzes = quizzes;
    }


    public boolean associateQuestionOption(QuestionOption questionOption) {
        if (questionOptions.isEmpty()) return false;
        if (questionOptions.contains(questionOption)) return false;
        this.questionOptions.add(questionOption);
        return true;
    }

    public boolean dissociatesQuestionOption(QuestionOption questionOption) {
        if (questionOptions.contains(questionOption)) {
            questionOptions.remove(questionOption);
            return true;
        }
        return false;
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
     * Gets quizzes.
     *
     * @return the quizzes
     */
    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    /**
     * Sets quizzes.
     *
     * @param quizzes the quizzes
     */
    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    /**
     * Gets question.
     *
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets question.
     *
     * @param question the question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets info.
     *
     * @param info the info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets points.
     *
     * @param points the points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Gets block.
     *
     * @return the block
     */
    public int getBlock() {
        return block;
    }

    /**
     * Sets block.
     *
     * @param block the block
     */
    public void setBlock(int block) {
        this.block = block;
    }

    /**
     * Is has multiple answers boolean.
     *
     * @return the boolean
     */
    public boolean isHasMultipleAnswers() {
        return hasMultipleAnswers;
    }

    /**
     * Sets has multiple answers.
     *
     * @param hasMultipleAnswers the has multiple answers
     */
    public void setHasMultipleAnswers(boolean hasMultipleAnswers) {
        this.hasMultipleAnswers = hasMultipleAnswers;
    }

    public Set<QuestionOption> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(Set<QuestionOption> questionOptions) {
        this.questionOptions = questionOptions;
    }
}

