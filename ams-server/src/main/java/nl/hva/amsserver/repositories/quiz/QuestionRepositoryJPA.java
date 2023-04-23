package nl.hva.amsserver.repositories.quiz;

import nl.hva.amsserver.models.quiz.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * The interface Question repository jpa.
 * <br/>
 * @see <a href="https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa">Introduction to Spring Data JPA</a>
 * <br/>
 * When Spring Data creates a new Repository implementation, it analyses all the methods defined by the interfaces
 * and tries to automatically generate queries from the method names
 *
 * @author Harmohat Khangura
 */
@Component
@Transactional
public interface QuestionRepositoryJPA extends JpaRepository<Question, Long> {
    /**
     * Method finds all the questions which belong to a quiz by the given quiz id
     * @param id The id of the quiz
     * @return The questions list which belong to the quiz
     */
    List<Question> findByQuizzes_Id(long id);
}
