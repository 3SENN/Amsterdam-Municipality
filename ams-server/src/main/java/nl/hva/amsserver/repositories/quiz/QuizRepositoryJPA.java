package nl.hva.amsserver.repositories.quiz;

import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The interface Quiz repository jpa.
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
public interface QuizRepositoryJPA extends JpaRepository<Quiz, Long> {
    /**
     * Find quiz by created by.
     *
     * @param createdBy the user who created the quiz
     * @return the list of quizzes
     */
    List<Quiz> findByCreatedBy(User createdBy);
}
