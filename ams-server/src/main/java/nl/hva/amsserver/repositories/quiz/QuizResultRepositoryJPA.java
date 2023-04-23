package nl.hva.amsserver.repositories.quiz;


import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.quiz.QuizResult;
import nl.hva.amsserver.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * The interface Quiz result repository jpa.
 * <br/>
 * @see <a href="https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa">Introduction to Spring Data JPA</a>
 * <br/>
 * When Spring Data creates a new Repository implementation, it analyses all the methods defined by the interfaces
 * and tries to automatically generate queries from the method names
 *
 * @author Senne Chin Tsoen Jien, Arunn Lingeswaran & Harmohat Khangura
 */
public interface QuizResultRepositoryJPA extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findByUserId(long id);

    /**
     * Method finds the quiz results by quiz object
     *
     * @param quiz The quiz object
     * @return List of quiz results which are searched by the quiz object
     */
    List<QuizResult> findByQuizId(Quiz quiz);

    /**
     * Method finds the quiz results without duplicate scores
     *
     * @return List of quiz results which are searched by the query.
     */

    @Query("SELECT c FROM QuizResult AS c LEFT JOIN QuizResult AS t ON c.userId = t.userId AND c.score < t.score WHERE t.score IS NULL")
    List<QuizResult> findQuizHighscores();

    /**
     * Method finds the quiz result by user object and quiz object
     *
     * @param user The user object
     * @param quiz The quiz object
     * @return The quiz result which is searched by the user and quiz object
     */
    QuizResult findByUserIdAndQuizId(User user, Quiz quiz);

    /**
     * Method sums the total score of all the quiz results
     * @return The total score of all the quiz results
     */
    @Query("SELECT SUM(q.score) FROM QuizResult q")
    Optional<Integer> totalScore();

    /**
     * Methods calculates the average score of the quiz results.
     * Query sums all the scores and divides by the total number of quiz results
     * @return the average score of the quiz results
     */
    @Query("SELECT SUM(q.score)/COUNT(q) FROM QuizResult q")
    Optional<Integer> averageScore();
}
