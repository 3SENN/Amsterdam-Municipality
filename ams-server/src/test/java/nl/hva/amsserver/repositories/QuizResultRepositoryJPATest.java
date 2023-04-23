package nl.hva.amsserver.repositories;

import nl.hva.amsserver.DataInserter;
import nl.hva.amsserver.models.quiz.QuizResult;
import nl.hva.amsserver.repositories.quiz.QuizResultRepositoryJPA;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@Import(DataInserter.class)
public class QuizResultRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private QuizResultRepositoryJPA quizResultRepositoryJPA;

    @Test
    void T00_injectedComponentsAreNotNull() {
        assertNotNull(entityManager);
        assertNotNull(quizResultRepositoryJPA);
    }
    @Test
    public void T01_testRepoFindScores(){
        List<QuizResult> quizResultList = this.quizResultRepositoryJPA.findQuizHighscores();
        assertNotNull(quizResultList);
    }

    @Test
    public void T02_noDuplicateScores(){
        List<QuizResult> quizResultList = this.quizResultRepositoryJPA.findQuizHighscores();
        for (int i = 0; i < quizResultList.size(); i++) {
            for (int j = 0; j < quizResultList.size(); j++) {
                if (i != j){
                    assertThat(quizResultList.get(i)).isNotEqualTo(quizResultList.get(j));
                    assertThat(i).isNotEqualTo(j);
                }
            }
        }
    }

}
