package nl.hva.amsserver.controller;


import nl.hva.amsserver.APIConfig;
import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.quiz.QuizResult;
import nl.hva.amsserver.models.user.User;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the QuizResult Controller
 * @author Senne Chin
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class QuizResultControllerTest {

    private Quiz quiz;
    private User user;
    private QuizResult quizResult;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private APIConfig apiConfig;

    @BeforeEach
    public void setup() {
        user = new User("Johan",
                "Brown",
                "johanb@gmail.com",
                "https://ui-avatars.com/api/?name=Johan+Brown",
                "johanbrown123!",
                false,
                LocalDateTime.now(),
                LocalDateTime.now());

        quiz = new Quiz(
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

        quizResult = new QuizResult(
                user, quiz, 100
        );
    }

    @Test
    public void T01_QuizResultHasResults() {
        Long zero = 0L;
        Long twenty = 20l;
        // Get request for amount of scores in database
        ResponseEntity<Long> response = this.restTemplate.getForEntity("/quiz/result/total/amount", Long.class);
        // Checking status code
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when amount of results are found");
        // verifies whether the object is not null
        assert response.getBody() != null;
        long totalAmountOfResults = response.getBody();
        // There is at least more than 1 score
        assertThat(totalAmountOfResults, is(greaterThan(zero)));
        // There is less than 20 scores
        assertThat(twenty, is(greaterThan(totalAmountOfResults)));

    }
    @Test
    public void T02_QuizResultHasUserData() {
        // Get request for User data in every Quiz-Result
        ResponseEntity<User[]> response = this.restTemplate.getForEntity("/quiz/result", User[].class);
        // Checking status code
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when all quiz results where found");
        User[] userData = response.getBody();
        // verifies whether the object is not null
        assert userData != null;
        // There is at least more than 1 object
        assertThat(userData.length, is(greaterThan(0)));
    }

    @Test
    public void T03_QuizResultHasScoreData() {
        // Get request for calculated Score data in Quiz-Result
        ResponseEntity<String> response =  this.restTemplate.getForEntity("/quiz/result/scores",String.class);
        // Checking status code
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when all quizzes where found");
        // Checking if response is not null
        assertNotNull(response);
    }

    @Test
    public void T04_ACorrectStatusShouldBeReturnedWhenQuizResultIsNotFound() {
        // Get request for Quiz-Result with quizId: 0
        ResponseEntity<QuizResult> response = this.restTemplate.getForEntity("/quiz/result/by/0", QuizResult.class);

        // Checking status code
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "StatusCode: [404] should be returned when quiz by id: [0] was not found");
    }
}
