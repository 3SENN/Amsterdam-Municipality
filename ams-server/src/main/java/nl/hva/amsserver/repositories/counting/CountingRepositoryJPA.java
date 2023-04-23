package nl.hva.amsserver.repositories.counting;

import nl.hva.amsserver.models.counting.Counting;
import nl.hva.amsserver.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The interface Counting repository jpa.
 * <br/>
 * @see <a href="https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa">Introduction to Spring Data JPA</a>
 * <br/>
 * When Spring Data creates a new Repository implementation, it analyses all the methods defined by the interfaces
 * and tries to automatically generate queries from the method names
 *
 * @author Jordy Mol & Harmohat Khangura
 */

@Component
public interface CountingRepositoryJPA extends JpaRepository<Counting, Long> {

    /**
     * Method finds all counting levels by user object
     *
     * @param user The user object
     * @return List of counting levels which are searched by the user object
     */
    List<Counting> findByUserIdIs(Optional<User> user);

    /**
     * Method finds the highscore for every user that has played the counting game
     *
     * @return List of counting levels which are the highscores
     */
    @Query("SELECT c FROM Counting c LEFT JOIN Counting h ON c.userId = h.userId AND c.score < h.score WHERE h.score IS NULL")
    List<Counting> findHighscores();

    /**
     * Method finds the total score that has been achieved in the counting game
     *
     * @return The combined score from the counting levels
     */
    @Query("SELECT SUM(c.score) FROM Counting c")
    Optional<Integer> totalScore();

    /**
     * Method finds the average score from all counting levels
     *
     * @return The average score from the counting game
     */
    @Query("SELECT SUM(c.score)/COUNT(c) FROM Counting c")
    Optional<Integer> averageScore();


}
