package nl.hva.amsserver.repositories.levels;

import nl.hva.amsserver.models.levels.LevelsLocked;
import nl.hva.amsserver.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The interface Levels locked repository jpa.
 * <br/>
 * @see <a href="https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa">Introduction to Spring Data JPA</a>
 * <br/>
 * When Spring Data creates a new Repository implementation, it analyses all the methods defined by the interfaces
 * and tries to automatically generate queries from the method names
 *
 * @author Jordy Mol
 */
@Component
public interface LevelsLockedRepositoryJPA  extends JpaRepository<LevelsLocked, Long> {

    /**
     * Method finds levels locked object by user object
     *
     * @param user The user object
     * @return LevelsLocked object searched by the user object
     */
    Optional<LevelsLocked> findByUserIdIs(Optional<User> user);
}
