package nl.hva.amsserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.minidev.json.JSONObject;
import nl.hva.amsserver.models.dto.LoginDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AuthenticationControllerTest {

    private User user;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        user = new User("Test",
                "User",
                "testuserme@gmail.com",
                "https://ui-avatars.com/api/?name=Test+Test",
                "12345678!",
                false,
                LocalDateTime.now(),
                LocalDateTime.now());

        this.restTemplate.postForEntity("/users", this.user, User.class);
    }


    @Test
    public void testRegisterUser() {
        // Create a new user to register
        User user = new User("Test",
                "Test123",
                "test123@@gmail.com",
                "https://ui-avatars.com/api/?name=Test+Test123",
                "12345678!",
                false,
                LocalDateTime.now(),
                LocalDateTime.now());


        // Send a POST request to the /register endpoint
        ResponseEntity<User> response = restTemplate.postForEntity("/auth/register", user, User.class);

        // Assert that the response has a status of 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the returned user has the same email as the user we created
        User registeredUser = response.getBody();
        assertEquals(user.getEmail(), registeredUser.getEmail());
    }

    @Test
    public void testLoginUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", this.user.getEmail());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange("/auth/login", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Authorization"));
    }

//    @Test
//    public void testLoginUserWithIncorrectCredentials() {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("email", "fake@email.com"); // provide incorrect email
//        jsonObject.put("password", "fakepassword"); // provide incorrect password
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
//        ResponseEntity<String> response = restTemplate.exchange("/auth/login", HttpMethod.POST, entity, String.class);
//
//        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//    }
//
//
//    @Test
//    public void testLoginUserWithMissingCredentials() {
//        ObjectNode objectNode = new ObjectMapper().createObjectNode();
//        objectNode.put("email", "3@gmail.com");
//        objectNode.put("password", "salam");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> entity = new HttpEntity<>(objectNode.toString(), headers);
//        ResponseEntity<String> response = restTemplate.exchange("/auth/login", HttpMethod.POST, entity, String.class);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }

}

