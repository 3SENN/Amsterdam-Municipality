package nl.hva.amsserver.repositories;


import nl.hva.amsserver.models.quiz.Question;
import nl.hva.amsserver.models.quiz.QuestionOption;
import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.repositories.quiz.QuestionRepositoryJPA;
import nl.hva.amsserver.repositories.quiz.QuizRepositoryJPA;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class QuestionRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private QuestionRepositoryJPA questionRepositoryJPA;
    @Autowired
    private QuizRepositoryJPA quizRepositoryJPA;


    @Test
    public void testFindByQuizzes_Id() {
        Quiz quiz = new Quiz();
        quizRepositoryJPA.save(quiz);
        Question question = new Question("What is the capital of France?", "Test question", 2, 1, false);
        question.getQuizzes().add(quiz);
        questionRepositoryJPA.save(question);
        List<Question> questions = questionRepositoryJPA.findByQuizzes_Id(quiz.getId());
        assertEquals(1, questions.size());
        assertEquals(question.getQuestion(), questions.get(0).getQuestion());
    }

    @Test
    public void testFindQuestionByQuizId() {
        Quiz quiz = new Quiz();
        quizRepositoryJPA.save(quiz);
        Question question1 = new Question("What is the capital of France?", "Test question", 2, 1, false);
        question1.getQuizzes().add(quiz);
        Question question2 = new Question("What is the capital of Germany?", "Test question", 2, 1, false);
        question2.getQuizzes().add(quiz);
        questionRepositoryJPA.save(question1);
        questionRepositoryJPA.save(question2);
        List<Question> questions = questionRepositoryJPA.findByQuizzes_Id(quiz.getId());
        assertEquals(2, questions.size());
        assertTrue(questions.stream().anyMatch(question -> question.getQuestion().equals("What is the capital of France?")));
        assertTrue(questions.stream().anyMatch(question -> question.getQuestion().equals("What is the capital of Germany?")));
    }


    @Test
    public void testDissociateQuestionOption() {
        Question question = new Question("What is the capital of France?", "Test question", 2, 1, false);
        QuestionOption questionOption = new QuestionOption("Paris", true, question);
        question.getQuestionOptions().add(questionOption);
        questionRepositoryJPA.save(question);
        assertTrue(question.dissociatesQuestionOption(questionOption));
        assertEquals(0, question.getQuestionOptions().size());
    }



}
