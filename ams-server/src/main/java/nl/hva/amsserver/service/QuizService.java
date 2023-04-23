package nl.hva.amsserver.service;

import nl.hva.amsserver.models.event.Event;
import nl.hva.amsserver.models.quiz.QuestionOption;
import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.quiz.Question;
import nl.hva.amsserver.repositories.event.EventRepositoryJPA;
import nl.hva.amsserver.repositories.quiz.QuestionOptionRepositoryJPA;
import nl.hva.amsserver.repositories.quiz.QuestionRepositoryJPA;
import nl.hva.amsserver.repositories.quiz.QuizRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Quiz service.
 *
 * @author Harmohat Khangura
 */
@Service
public class QuizService {
    @Autowired
    QuizRepositoryJPA quizRepositoryJPA;
    @Autowired
    QuestionRepositoryJPA questionRepositoryJPA;
    @Autowired
    QuestionOptionRepositoryJPA questionOptionRepositoryJPA;
    @Autowired
    EventRepositoryJPA eventRepositoryJPA;

    /**
     * Find all quizzes.
     *
     * @return the list of quizzes
     */
    public List<Quiz> findAll() {
        return new ArrayList<Quiz>(quizRepositoryJPA.findAll());
    }

    /**
     * Method gets quizzes where the user is not signed up for
     *
     * @param userId The id of the user
     * @return List of quizzes where the user is not signed up for
     */
    public List<Quiz> getQuizzesWhereUserIsNotSignedUpFor(long userId) {
        List<Quiz> quizzes = new ArrayList<>(this.findAll());
        List<Event> events = new ArrayList<>(eventRepositoryJPA.findAll());

        events.removeIf(event -> event.getUser().getId() != userId);
        for (Event event : events) quizzes.removeIf(quiz -> quiz.getId() == event.getQuiz().getId());

        return quizzes;
    }

    /**
     * Method gets the total amount of quizzes using the QuizRepositoryJPA
     *
     * @return The total amount of quizzes
     */
    public long getTotalAmountOfQuizzes() {
        return quizRepositoryJPA.count();
    }

    /**
     * Method gets the total amount of questions using the QuestionRepositoryJPA
     *
     * @return The total amount of questions
     */
    public long getTotalAmountOfQuestions() {
        return questionRepositoryJPA.count();
    }

    /**
     * Method finds quiz by id (optional) using the QuizRepositoryJPA
     *
     * @param id the id of the quiz
     * @return Optional quiz
     */
    public Optional<Quiz> findQuizById(long id) {
        return quizRepositoryJPA.findById(id);
    }

    /**
     * Method creates a new quiz using the QuizRepositoryJPA
     *
     * @param quiz The quiz object to create
     * @return The created quiz object
     */
    public Quiz createQuiz(Quiz quiz) {
        return quizRepositoryJPA.save(quiz);
    }

    /**
     * Method deletes the quiz by id using the QuizRepositoryJPA
     *
     * @param id The of the quiz to delete
     */
    public void deleteQuizById(long id) {
        quizRepositoryJPA.deleteById(id);
    }

    /**
     * Methods finds all the questions using the QuestionRepositoryJPA
     *
     * @return The list containing the questions if any
     */
    public List<Question> findAllQuestions() {
        return questionRepositoryJPA.findAll();
    }

    /**
     * Method finds the question by id using the QuestionRepositoryJPA
     *
     * @param id The id of the question to find
     * @return Optional question
     */
    public Optional<Question> findQuestionById(long id) {
        return questionRepositoryJPA.findById(id);
    }

    /**
     * Method finds questions which are related to a quiz by the given quiz id using the QuestionRepositoryJPA
     *
     * @param id The id of the quiz
     * @return List of questions which are related to a quiz by the given quiz id
     */
    public List<Question> findQuestionByQuizId(long id) {
        return questionRepositoryJPA.findByQuizzes_Id(id);
    }

    /**
     * Method creates a new question using the QuestionRepositoryJPA
     *
     * @param question The question object to create
     * @return The created question object
     */
    public Question createQuestion(Question question) {
        return questionRepositoryJPA.save(question);
    }

    /**
     * Method deletes question by the question id using the QuestionRepositoryJPA
     *
     * @param id The id of the question to delete
     */
    public void deleteQuestionById(long id) {
        questionRepositoryJPA.deleteById(id);
    }

    /**
     * Method creates a new question option using the QuestionOptionRepositoryJPA
     *
     * @param questionOption The question option to create
     * @return The created question option
     */
    public QuestionOption createQuestionOption(QuestionOption questionOption) {
        return questionOptionRepositoryJPA.save(questionOption);
    }

    /**
     * Method finds the question option by id using the QuestionOptionRepositoryJPA
     *
     * @param id The id of the question option to find
     * @return Optional question option
     */
    public Optional<QuestionOption> findQuestionOptionById(long id) {
        return questionOptionRepositoryJPA.findById(id);
    }
}
