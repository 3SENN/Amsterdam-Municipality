package nl.hva.amsserver.repositories;

import nl.hva.amsserver.DataInserter;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for UserRepositoryJPA
 * @Author Arunn Lingeswaran
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@Import(DataInserter.class)
public class UserRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepositoryJPA userRepositoryJPA;

    private final long USER_ID = 1L;

    @Test
    void T00_injectedComponentsAreNotNull() {
        assertNotNull(entityManager);
        assertNotNull(userRepositoryJPA);
    }

    @Test
    void T01_UserCanBeFoundById() {
        User user = userRepositoryJPA.getReferenceById(this.USER_ID);
        assertThat(this.USER_ID).isEqualTo(user.getId());
    }

    @Test
    void T02_UserCanBeDeletedById() {
        userRepositoryJPA.deleteById(this.USER_ID);
        Optional<User> user = userRepositoryJPA.findById(this.USER_ID);

        assertTrue(user.isEmpty());
    }
}
