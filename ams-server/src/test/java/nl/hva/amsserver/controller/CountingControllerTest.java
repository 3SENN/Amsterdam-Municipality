package nl.hva.amsserver.controller;

import nl.hva.amsserver.models.counting.*;
import nl.hva.amsserver.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Counting Controller
 *
 * @author Jordy Mol
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CountingControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private Counting countingLevel;
    private CountingPublicSpaces countingPublicSpaces;
    private CountingOV countingOV;
    private CountingLabels countingLabel;
    private User user;

    @BeforeEach
    public void setup() {
        countingPublicSpaces = new CountingPublicSpaces(
                1,
                2,
                0,
                1,
                3,
                0,
                1,
                4
        );
        countingOV = new CountingOV(
                2,
                1,
                0,
                3,
                4,
                1,
                0,
                2
        );
        countingLabel = new CountingLabels(
                "Deaf person",
                0,
                1
        );
        user = new User(
                "Dylan",
                "Hendriks",
                "dylanHendriks@gmail.com",
                "https://ui-avatars.com/api/?name=Dylan+Hendriks",
                "DylanHendriks04",
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        countingLevel = new Counting(
                "Provinciale weg 16, Ijmuiden, Netherlands",
                LocalDateTime.now(),
                "Station Ijmuiden, Ijmuiden, Netherlands",
                "Centraal Station, Amsterdam, Netherlands",
                "Station Zwolle, Zwolle, Netherlands",
                "Burgemeester de Vos van Steenwijklaan, Zwolle, Netherlands",
                LocalDateTime.now().plusHours(2),
                countingPublicSpaces,
                countingOV,
                400
        );
        countingLevel.setUserId(user);
    }

    @Test
    public void T01_ACorrectStatusShouldBeReturnedWhenCountingLevelWasNotFound() {
        // GET request for counting level with id: 0
        ResponseEntity<Counting> response = this.restTemplate.getForEntity("/counting/0", Counting.class);

        // Checking status code
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "StatusCode: [404] should be returned when counting level with id: [0] was not found");
    }

    @Test
    public void T02_ACorrectStatusShouldBeReturnedWhenCountingLevelWasFound() {
        // GET request for counting level with id: 25
        ResponseEntity<Counting> response = this.restTemplate.getForEntity("/counting/25", Counting.class);

        // Checking status code
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when counting level with id: [16] was found");
    }

    @Test
    public void T03_ACountingPostCanBeExecuted() {
        int userId = 140;
        // POST request
        ResponseEntity<Counting> response = this.restTemplate.postForEntity("/counting?userId=" + userId, this.countingLevel, Counting.class);

        // Checking status code
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when counting levels was successfully posted");

        Counting countingPosted = response.getBody();
        // Check if posted counting level is not null
        assert countingPosted != null;

        // Check if starting points from counting levels are the same
        assertEquals(countingLevel.getStartingPoint(), countingPosted.getStartingPoint(),
                "Starting points should be the same");

        // Check if the posted counting level was given an id
        assertThat(countingPosted.getId(), is(greaterThan(0L)));
    }

    @Test
    public void T04_ACountingPostCannotBeExecutedWhenUserNotExists() {
        int userId = 500;
        // POST request
        ResponseEntity<Counting> response = this.restTemplate.postForEntity("/counting?userId=" + userId, this.countingLevel, Counting.class);

        // Checking status code
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                String.format("StatusCode: [404] should be returned when the user with id: [%d] was not found", userId));
    }

    @Test
    public void T05_ACountingLabelPostCanBeExecuted() {
        int countingId = 25;
        // POST request
        ResponseEntity<CountingLabels> response = this.restTemplate.postForEntity("/counting/label?countingId=" + countingId,
                this.countingLabel, CountingLabels.class);

        // Checking status code
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when counting label was successfully posted");

        CountingLabels countingLabelPosted = response.getBody();
        // Check if posted counting label is not null
        assert  countingLabelPosted != null;

        // Check if label names are the same
        assertEquals(countingLabel.getName(), countingLabelPosted.getName(),
                "Label names should be the same");

        // Check if posted counting label was given an id
        assertThat(countingLabelPosted.getId(), is(greaterThan(0L)));
    }

    @Test
    public void T06_ACountingLabelPostCannotBeExecutedWhenCountingLevelNotExists() {
        int countingId = 500;
        // POST request
        ResponseEntity<CountingLabels> response = this.restTemplate.postForEntity("/counting/label?countingId=" + countingId,
                this.countingLabel, CountingLabels.class);

        // Checking status code
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                String.format("StatusCode: [404] should be returned when the counting level with id: [%d] was not found", countingId));
    }
}
