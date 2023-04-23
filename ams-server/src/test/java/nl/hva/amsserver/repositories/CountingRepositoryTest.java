package nl.hva.amsserver.repositories;

import nl.hva.amsserver.models.counting.Counting;
import nl.hva.amsserver.repositories.counting.CountingRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Counting Repository
 *
 * @author Jordy Mol
 */

@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CountingRepositoryTest {
    @Autowired
    private CountingRepositoryJPA countingRepositoryJPA;
    @Autowired
    private UserRepositoryJPA userRepositoryJPA;
    private final long COUNTING_ID = 25L;

    @Test
    void T00_InjectedComponentsAreNotNull() {
        assertNotNull(countingRepositoryJPA);
        assertNotNull(userRepositoryJPA);
    }

    @Test
    void T01_CountingLevelCanBeFoundById() {
        Optional<Counting> counting = this.countingRepositoryJPA.findById(this.COUNTING_ID);

        assertTrue(counting.isPresent(), "Counting level with id: [25] should be found");
    }

    @Test
    void T02_CountingLevelCanBeDeletedById() {
        this.countingRepositoryJPA.deleteById(this.COUNTING_ID);
        Optional<Counting> counting = this.countingRepositoryJPA.findById(this.COUNTING_ID);

        assertTrue(counting.isEmpty(), "Counting level shouldn't exist");
    }
}
