package nl.hva.amsserver.repositories.counting;

import nl.hva.amsserver.models.counting.Counting;
import nl.hva.amsserver.models.counting.CountingLabels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * The interface Counting Label repository jpa.
 * <br/>
 * @see <a href="https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa">Introduction to Spring Data JPA</a>
 * <br/>
 * When Spring Data creates a new Repository implementation, it analyses all the methods defined by the interfaces
 * and tries to automatically generate queries from the method names
 *
 * @author Jordy Mol & Harmohat Khangura
 */
@Transactional
@Component
public interface CountingLabelRepositoryJPA extends JpaRepository<CountingLabels, Long> {

    /**
     * Method finds the labels by counting object
     *
     * @param countingLabel The Counting object
     * @return List of Counting labels which are searched by the counting object
     */
    List<CountingLabels> findByCountingIs(Optional<Counting> countingLabel);

    /**
     * Method deletes counting labels by counting object
     *
     * @param counting The counting object
     */
    void deleteByCounting(Counting counting);

}
