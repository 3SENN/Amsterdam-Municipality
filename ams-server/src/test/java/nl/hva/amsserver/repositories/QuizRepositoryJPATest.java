package nl.hva.amsserver.repositories;

import nl.hva.amsserver.DataInserter;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.quiz.QuizRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import nl.hva.amsserver.models.quiz.Quiz;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@Import(DataInserter.class)
public class QuizRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuizRepositoryJPA quizRepositoryJPA;

    @Autowired
    private UserRepositoryJPA userRepositoryJPA;

    @Autowired
    private DataInserter dataInserter;

    private Quiz quiz, quizToSave;

    @BeforeEach
    public void setup() {
        this.quiz = this.dataInserter.getQuizzes().get(0);

        this.quizToSave = new Quiz(
                "Level 2 - [TEST-001]",
                5,
                "2",
                null,
                "WBH-A08",
                0,
                LocalDateTime.of(2023, 2, 2, 15, 0, 0),
                LocalDateTime.of(2002, 2, 2, 16, 0, 0),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    @Test
    void T00_injectedComponentsAreNotNull() {
        assertNotNull(entityManager);
        assertNotNull(quizRepositoryJPA);
    }

    @Test
    void T01_QuizCanBeFoundById() {
        // Gets the quiz by id
        Quiz quiz = this.quizRepositoryJPA.getReferenceById(this.quiz.getId());

        assertThat(this.quiz.getId()).isEqualTo(quiz.getId());
    }

    @Test
    void T02_whenSavedThenFindsByCreatedBy() {
        // Quiz container object which may or may not contain a non-null value
        Optional<User> user = this.userRepositoryJPA.findById(1L);

        // Quiz should be present
        assertThat(user.isPresent()).isTrue();

        // Setting the value of the created by for quiz
        user.ifPresent(this.quizToSave::setCreatedBy);
        
        // Saves the given quiz
        this.quizRepositoryJPA.save(this.quizToSave);
        // The created by value should not be null
        assertThat(this.quizRepositoryJPA.findByCreatedBy(user.get())).isNotNull();
    }

    @Test
    void T03_QuizCanBeDeletedById() {
        // Deletes the given quiz with the given id
        this.quizRepositoryJPA.deleteById(this.quiz.getId());

        // Quiz container object which may or may not contain a non-null value
        Optional<Quiz> quiz = this.quizRepositoryJPA.findById(this.quiz.getId());

        // Quiz should be empty
        assertTrue(quiz.isEmpty());
    }
}
