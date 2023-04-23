package nl.hva.amsserver.controller;

import nl.hva.amsserver.DataInserter;
import nl.hva.amsserver.models.quiz.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Tests for the Quiz Controller
 * NOTE:
 * In `src/main/resources/data` there are several SQL files. These files contain the inserts, depending on which active
 * profile is chosen. The quiz table contains 4 rows of data.
 *
 * @author Harmohat Khangura
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class QuizControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataInserter dataInserter;

    private int totalQuizzes = 4;

    private Quiz quizToPost, quiz0, quiz1, quiz2, quiz3;

    @BeforeEach
    public void setup() {
        this.quiz0 = this.dataInserter.getQuizzes().get(0);
        this.quiz1 = this.dataInserter.getQuizzes().get(1);
        this.quiz2 = this.dataInserter.getQuizzes().get(2);
        this.quiz3 = this.dataInserter.getQuizzes().get(3);

        quizToPost = new Quiz(
                "Level 2 - Quiz [TEST]",
                5,
                "2",
                null,
                "WBH 05A22",
                6,
                LocalDateTime.of(2022, 12, 2, 10, 30, 0),
                LocalDateTime.of(2022, 12, 2, 12, 0, 0),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    /**
     * Method checks if the amount of total quizzes equals the given amount of total quizzes
     *
     * @param totalQuizzes Total amount of quizzes
     */
    public void checkTotalAmountOfQuizzes(int totalQuizzes) {
        // Get request for all quizzes
        ResponseEntity<Long> response = this.restTemplate.getForEntity("/quiz/total/amount", Long.class);

        // verifies whether the body from the response is not null
        assert response.getBody() != null;

        // Gets and stores the value from the body
        long totalAmountOfQuizzes = response.getBody();

        // Checking the total amount of quizzes
        assertEquals(totalQuizzes, totalAmountOfQuizzes,
                String.format("The quizzes table should contain %d quizzes", totalQuizzes));
    }

    @Test
    public void T01_ACorrectStatusShouldBeReturnedWhenQuizWasNotFound() {
        long QUIZ_ID = 0;

        // Get request for quiz with id: 0
        ResponseEntity<Quiz> response = this.restTemplate.getForEntity("/quiz/" + QUIZ_ID, Quiz.class);

        // Status code 404 should be returned, when the quiz is not found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                String.format("StatusCode: [404] should be returned when quiz by id: [%d] was not found", QUIZ_ID));
    }

    @Test
    public void T02_ACorrectStatusShouldBeReturnedWhenQuizWasFound() {
        // Get request for quiz with id
        ResponseEntity<Quiz> response = this.restTemplate.getForEntity("/quiz/" + this.quiz0.getId(), Quiz.class);

        // Status code 200 should be returned, when the quiz is found
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                String.format("StatusCode: [200] should be returned when quiz by id: [%d] was found", this.quiz0.getId()));
    }

    @Test
    public void T03A_QuizTableHoldsData() {
        // Get request for all quizzes
        ResponseEntity<Quiz[]> response = this.restTemplate.getForEntity("/quiz", Quiz[].class);

        // Status code 200 should be returned
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when all quizzes where found");

        // Gets and stores the data from the body as array
        Quiz[] quizzes = response.getBody();

        // verifies whether the object is not null
        assert quizzes != null;

        // There is at least more than 1 object
        assertThat(quizzes.length, is(greaterThan(0)));
    }

    @Test
    public void T03B_4QuizzesShouldBePresent() {
        this.checkTotalAmountOfQuizzes(this.totalQuizzes);
    }

    @Test
    public void T04_TheCorrectEmailOfTheCreatorOFQuiz11ShouldBeReturned() {
        String ADMIN_EMAIL = "john@gmail.com";

        // Get request for quiz with id
        ResponseEntity<Quiz> response = this.restTemplate.getForEntity("/quiz/" + this.quiz1.getId(), Quiz.class);

        // Status code 200 should be returned, when the quiz was found
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                String.format("StatusCode: [200] should be returned when quiz with id: [%d] was found", this.quiz1.getId()));

        // Gets and stores the value from the body
        Quiz quiz = response.getBody();

        // verifies whether the object is not null
        assert quiz != null;

        // Checking the emails of the creator
        assertEquals(ADMIN_EMAIL, quiz.getCreatedBy().getEmail(),
                String.format("The email of the creator of quiz with id: [%d] should be: %s", this.quiz1.getId(), ADMIN_EMAIL));
    }

    @Test
    public void T05A_AQuizPostCanBeExecuted() {
        int ADMIN_ID = 1;

        // Post request with the id of the admin as query param
        ResponseEntity<Quiz> response = this.restTemplate.postForEntity("/quiz?id=" + ADMIN_ID, this.quizToPost, Quiz.class);

        // Status code 200 should be returned, when the quiz was successfully created
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when quiz was successfully created");

        // Gets and stores the value from the body
        Quiz quizPosted = response.getBody();

        // verifies whether the object is not null
        assert quizPosted != null;

        // Checking for the addresses (posted object - retrieved object)
        assertEquals(this.quizToPost.getAddress(), quizPosted.getAddress(),
                "The address should be the same");

        // Auto Increment should generate a unique id above 0
        assertThat(quizPosted.getId(), is(greaterThan(0L)));

        // Incrementing the total quizzes by 1
        totalQuizzes++;

        // Check length after a new quiz is added
        this.checkTotalAmountOfQuizzes(totalQuizzes);
    }

    @Test
    public void T05B_AQuizPostCannotBeExecutedWhenAdminNotExists() {
        int ADMIN_ID = 900;

        // Post request with the id of the admin as query param
        ResponseEntity<Quiz> response = this.restTemplate.postForEntity("/quiz?id=" + ADMIN_ID, this.quizToPost, Quiz.class);

        // Status code 404 should be returned, when the quiz was not found by the id of the admin
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                String.format("StatusCode: [404] should be returned when the admin with id: [%d] was not found", ADMIN_ID));
    }

    @Test
    public void T06_AQuizCanBeUpdated() {
        int AMOUNT_TO_REGISTER = 10;

        // Get request for quiz with id
        ResponseEntity<Quiz> getResponse = this.restTemplate.getForEntity("/quiz/" + this.quiz2.getId(), Quiz.class);

        // Status code should be 200 when the quiz was found
        assertEquals(HttpStatus.OK, getResponse.getStatusCode(),
                String.format("StatusCode: [200] should be returned when quiz by id: [%d] was found", this.quiz2.getId()));

        // Gets and stores the value from the body
        Quiz quiz = getResponse.getBody();

        // verifies whether the object is not null
        assert quiz != null;

        // Updating fields
        quiz.setAmountToRegister(AMOUNT_TO_REGISTER);
        quiz.setUpdatedAt(LocalDateTime.now());

        // Creating new HttpEntity to use in combination with the rest template, body contains updated object
        HttpEntity<Quiz> requestEntity = new HttpEntity<Quiz>(quiz);

        // Execute the PUT method to the given URI template, writing the given request entity to the request
        ResponseEntity<Quiz> response = this.restTemplate.exchange("/quiz/" + quiz.getId(),
                HttpMethod.PUT, requestEntity, Quiz.class);

        // Status code 200 should be returned when the quiz was successfully updated
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when quiz was successfully updated");

        // Gets and stores the value form the body
        Quiz quizUpdated = response.getBody();

        // verifies whether the object is not null
        assert quizUpdated != null;

        // The field AMOUNT_TO_REGISTER from the retrieved object should equal value from the updated object
        assertEquals(AMOUNT_TO_REGISTER, quizUpdated.getAmountToRegister(),
                String.format("Updated field should contain the value: [%d]", AMOUNT_TO_REGISTER));
    }

    @Test
    public void T07_AQuizCanBeDeleted() {
        int QUIZ_ID = 383838311;

        // Constructs a new, empty instance of the HttpHeaders object.
        HttpHeaders headers = new HttpHeaders();
        // Sets the list of acceptable
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        // Creates a new HttpEntity with the given headers
        HttpEntity<Quiz> entity = new HttpEntity<Quiz>(headers);

        // Executes the HTTP delete method to the given URI template
        ResponseEntity<Quiz> response = this.restTemplate.exchange("/quiz/" + QUIZ_ID, HttpMethod.DELETE, entity, Quiz.class);

        // Status code 404 should be returned when the quiz was not found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                String.format("StatusCode: [404] should be returned when quiz with id: [%d] was not found", QUIZ_ID));

        // Deleting quiz with id
        this.restTemplate.delete("/quiz/" + this.quiz3.getId());

        // Initial amount of quizzes = 4 + [T05] = 5 - 1 = 4
        this.checkTotalAmountOfQuizzes(totalQuizzes);
    }
}
