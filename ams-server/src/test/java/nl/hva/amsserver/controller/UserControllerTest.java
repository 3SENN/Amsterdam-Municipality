package nl.hva.amsserver.controller;

import nl.hva.amsserver.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the User Controller
 * NOTE:
 *  In `src/main/resources/data` there are several SQL files. These files contain the inserts, depending on which active
 *  profile is chosen. The user table contains 3 rows of data.
 *  DataInserter class provides data for /users api
 *
 * @author Arunn Lingeswaran
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class UserControllerTest {
    private User user;
    private int totalUsers = 9;
    @Autowired
    private TestRestTemplate restTemplate;

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
    }

    public void checkTotalAmountOfUsers(int totalUsers) {
        // Get request for all users
        ResponseEntity<Long> response = this.restTemplate.getForEntity("/users/total/amount", Long.class);

        assert response.getBody() != null;

        long totalAmountOfUsers = response.getBody();

        // Checking the total amount of users
        assertEquals(totalUsers, totalAmountOfUsers,
                String.format("The users table should contain %d users", totalUsers));
    }

    @Test
    public void T01_ACorrectStatusShouldBeReturnedWhenUserWasNotFound() {
        // Get request for user with email: johanb@gmail.com
        ResponseEntity<User> response = this.restTemplate.getForEntity("/user/johanb@gmail.com", User.class);

        // Checking status code
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "StatusCode: [404] should be returned when user by email: [johanb@gmail.com] was not found");
    }

    @Test
    public void T02_ACorrectStatusShouldBeReturnedWhenUserWasFound() {
        // Get request for user with email: john@gmail.com
        ResponseEntity<User> response = this.restTemplate.getForEntity("/user/john@gmail.com", User.class);

        // Checking status code
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "StatusCode: [404] should be returned when user by email: [john@gmail.com] was found");
    }

    @Test
    public void T03_UserTableHoldsData() {
        // Get request for all users
        ResponseEntity<User[]> response = this.restTemplate.getForEntity("/users", User[].class);

        // Checking status code
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when all users where found");

        User[] users = response.getBody();
        // verifies whether the object is not null
        assert users != null;

        // There is at least more than 1 object
        assertThat(users.length, is(greaterThan(0)));
    }

    @Test
    public void T04A_AUserPostCanBeExecuted() {
        // Post request
        ResponseEntity<User> response = this.restTemplate.postForEntity("/users", this.user, User.class);

        // Checking status code
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when user was successfully created");

        User postedUser = response.getBody();
        // verifies whether the object is not null
        assert postedUser != null;

        // Checking for the addresses (posted object - retrieved object)
        assertEquals(user.getEmail(), postedUser.getEmail(),
                "The email should be the same");

        // The AI should generate a unique id above 0
        assertThat(postedUser.getId(), is(greaterThan(0L)));

        totalUsers++;

        // Check length after a new user is added
        this.checkTotalAmountOfUsers(totalUsers);
    }

    @Test
    public void T04B_AUserPostCannotBeExecutedWithAnExistingEmail() {
        // Post request
        ResponseEntity<User> response = this.restTemplate.postForEntity("/users", this.user, User.class);

        // Checking status code
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode(),
                "StatusCode: [409] should be returned when an user exists with that email");
    }

    @Test
    public void T05_AUserCanBeDeleted() {
        // Deleting user with id: 2 and 5
        this.restTemplate.delete("/users/2");
        this.restTemplate.delete("/users/5");

        // Initial user value = 9
        // User posted = 10 users
        // Deleting 2 users means 10 - 2, but because we didn't update the totalUsers variable we do - 1
        this.checkTotalAmountOfUsers(totalUsers - 1);
    }

    @Test
    public void T06_ANonExistingUserCannotBeDeleted() {
        int USER_ID = 100;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(headers);

        ResponseEntity<User> response = this.restTemplate.exchange("/users/" + USER_ID, HttpMethod.DELETE, entity, User.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                String.format("StatusCode: [404] should be returned when user with id: [%d] was not found", USER_ID));
    }
}
