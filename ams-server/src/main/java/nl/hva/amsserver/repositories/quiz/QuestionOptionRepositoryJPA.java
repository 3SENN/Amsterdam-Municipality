package nl.hva.amsserver.repositories.quiz;

import nl.hva.amsserver.models.quiz.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The interface Question option repository jpa.
 * <br/>
 * @see <a href="https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa">Introduction to Spring Data JPA</a>
 * <br/>
 * When Spring Data creates a new Repository implementation, it analyses all the methods defined by the interfaces
 * and tries to automatically generate queries from the method names
 *
 * @author Harmohat Khangura
 */
public interface QuestionOptionRepositoryJPA extends JpaRepository<QuestionOption, Long> {
}
