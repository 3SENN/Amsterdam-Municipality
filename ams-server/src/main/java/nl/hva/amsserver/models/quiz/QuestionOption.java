package nl.hva.amsserver.models.quiz;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * The Quiz question model.
 *
 * @author Harmohat Khangura
 */
@Entity
@Table(name = "QuestionOption")
public class QuestionOption {
    @Id
    @SequenceGenerator(name = "questionOption_seq",
            sequenceName = "questionOption_sequence",
            initialValue = 41, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionOption_seq")
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question", nullable = false)
    @JsonBackReference
    private Question question;

    @Column(name = "option_answer")
    private String option;
    @Column(name = "is_answer")
    private boolean isAnswer;

    /**
     * Instantiates a new Quiz question option.
     */
    public QuestionOption() {

    }

    /**
     * Instantiates a new Quiz question option.
     *
     * @param option       the option
     * @param isAnswer     if the option is the answer
     */
    public QuestionOption(String option, boolean isAnswer) {
        this.option = option;
        this.isAnswer = isAnswer;
    }

    public QuestionOption(String option, boolean isAnswer, Question question) {
        this(option, isAnswer);
        this.question = question;
    }

    public QuestionOption(Question question) {
        super();
        this.question = question;
    }

    public boolean associateQuestion(Question question) {
        this.question = question;
        return true;
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
     * Gets quiz question id.
     *
     * @return the quiz question id
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Sets quiz question id.
     *
     * @param question the quiz question id
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * Gets option.
     *
     * @return the option
     */
    public String getOption() {
        return option;
    }

    /**
     * Sets option.
     *
     * @param option the option
     */
    public void setOption(String option) {
        this.option = option;
    }

    /**
     * Gets is answer.
     *
     * @return the is answer
     */
    public boolean getIsAnswer() {
        return isAnswer;
    }

    /**
     * Sets is answer.
     *
     * @param isAnswer the is answer
     */
    public void setIsAnswer(boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    @Override
    public String toString() {
        return "QuestionOption{" +
                "id=" + id +
                ", question=" + question +
                ", option='" + option + '\'' +
                ", isAnswer=" + isAnswer +
                '}';
    }
}
