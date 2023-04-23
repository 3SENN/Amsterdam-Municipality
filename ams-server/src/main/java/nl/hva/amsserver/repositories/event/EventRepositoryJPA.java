package nl.hva.amsserver.repositories.event;

import nl.hva.amsserver.models.event.Event;
import nl.hva.amsserver.models.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


/**
 * The interface Event repository jpa.
 * <br/>
 * @see <a href="https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa">Introduction to Spring Data JPA</a>
 * <br/>
 * When Spring Data creates a new Repository implementation, it analyses all the methods defined by the interfaces
 * and tries to automatically generate queries from the method names
 *
 * @author Harmohat Khangura
 */
@Transactional
@Component
public interface EventRepositoryJPA extends JpaRepository<Event, Long> {
    /**
     * Method finds all events which are related to the given user id
     *
     * @param id The id of the user
     * @return List of all the events which are related to the given user id
     */
    List<Event> findAllByUserId(long id);

    /**
     * Methods finds all the events for which the user did not register for
     *
     * @param id The id of the user
     * @return List of all the events for which the user did not register for
     */
    List<Event> findByUserIdNot(long id);

    /**
     * Method deletes the quiz by the quiz object
     *
     * @param quiz The quiz object to delete
     */
    void deleteByQuiz(Quiz quiz);

    /**
     * Method counts all events by quiz id
     *
     * @param id The id of the quiz
     * @return the total amount of quizzes by quiz id
     */
    long countByQuiz_Id(long id);
}
