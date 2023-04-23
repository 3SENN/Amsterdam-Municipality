package nl.hva.amsserver.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.hva.amsserver.APIConfig;
import nl.hva.amsserver.exceptions.ConflictException;
import nl.hva.amsserver.exceptions.InternalServerError;
import nl.hva.amsserver.exceptions.ResourceNotFound;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.counting.CountingRepositoryJPA;
import nl.hva.amsserver.repositories.levels.LevelsLockedRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import nl.hva.amsserver.security.JWToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Rest controller for User
 *
 * @author Harmohat Khangura & Arunn Lingeswaran
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    APIConfig apiConfig;
    @Autowired
    UserRepositoryJPA userRepositoryJPA;
    @Autowired
    LevelsLockedRepositoryJPA levelsLockedRepositoryJPA;
    @Autowired
    private CountingRepositoryJPA countingRepositoryJPA;


    /**
     * Method gets all the existing users
     *
     * @return The users list
     * @throws ResourceNotFound Throws exception when the users list is empty (data was empty/not found)
     */
    @GetMapping
    public List<User> getAllUsers() throws ResourceNotFound {
        // Creating new list of type User and inserting user data if any with the parametrized constructor call
        List<User> users = new ArrayList<>(userRepositoryJPA.findAll());

        // When there are no users found the method will throw a resource not found exception
        if (users.isEmpty()) throw new ResourceNotFound("Users are not found.");

        return users;
    }

    /**
     * Methods gets the total amount of users
     * @return the total amount of users
     */
    @GetMapping("/total/amount")
    public long getTotalAmountOfUsers() {
        return userRepositoryJPA.count();
    }

    /**
     * Retrieve user by id
     * @param id - specified id
     * @return User
     */
    @GetMapping("{id}")
    public User getUserById(@PathVariable long id) {
        Optional<User> user = userRepositoryJPA.findById(id);

        // When the user to get is present, the method will return the user
        if (user.isPresent()) return user.get();

        // When the was not found a resource not found exception will be thrown
        throw new ResourceNotFound(String.format("User with id: [%d] is not found", id));
    }

    /**
     * Retrieve user by email
     * @param email - specified User
     * @return User
     */
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        Optional<User> user = userRepositoryJPA.findByEmail(email);

        // When the user to get is present, the method will return the user
        if (user.isEmpty()) throw new ResourceNotFound(String.format("User with email:[%s] is not found", email));

        return user.get();
    }

    /**
     * Method adds a new user to users
     *
     * @param user The user object to add
     * @return ResponseEntity with a body which contains the created user object and status code = 200.
     * @throws ConflictException Throws exception when email already exists
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) throws ConflictException, InternalServerError {
        Optional<User> _user = userRepositoryJPA.findByEmail(user.getEmail());

        // When the email already exists the method will throw a conflict exception
        if (_user.isPresent())
            throw new ConflictException(String.format("Email: [%s] already exists", user.getEmail()));

        try {
            User newUser = userRepositoryJPA.save(user);

            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(null);
        }
    }


    /**
     * Update user by id
     * @param id - specified user
     * @param user - user object with the new values
     * @return ResponseEntity<User> - the updated user
     */
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        Optional<User> userToFind = userRepositoryJPA.findById(id);

        if(userToFind.isEmpty())
            throw new ResourceNotFound(String.format("User with id: [%d] is not found", id));

        // When the user to edit is present the method will update the fields
        User userToUpdate = userToFind.get();

        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setIsAdmin(user.getIsAdmin());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setUpdatedAt(LocalDateTime.now());

        try {
            return new ResponseEntity<>(userRepositoryJPA.save(userToUpdate), HttpStatus.OK);
        } catch(Exception error) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update profile by email
     * First name, last name, email will be updated.
     *
     * @param email - specified email
     * @param node - DTO object
     * @return ResponseEntity<String> - JWT Token
     */
    @PutMapping("/email/profile/{email}")
    public ResponseEntity<String> updateProfile(@PathVariable String email, @RequestBody ObjectNode node) {
        Optional<User> userToFind = userRepositoryJPA.findByEmail(email);

        String firstName = node.get("firstName").asText();
        String lastName = node.get("lastName").asText();
        String newEmail = node.get("email").asText();
        String profilePictureUrl = node.get("profilePicture").asText();

        if (userToFind.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        User user = userToFind.get();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(newEmail);
        user.setProfilePhoto(profilePictureUrl);
        user.setUpdatedAt(LocalDateTime.now());

        String fullName = user.getFirstName() + " " + user.getLastName();

        JWToken jwToken = new JWToken(fullName, user.getId(), user.getEmail(), String.valueOf(user.getIsAdmin()), user.getProfilePhoto());
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity());

//        System.out.println(tokenString);

        userRepositoryJPA.save(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(tokenString);
    }

    /**
     * Update password by email
     * @param email - specified email
     * @param node - DTO object
     * @return  ResponseEntity<User> - user
     */
    @PutMapping("/email/password/{email}")
    public ResponseEntity<User> updatePassword(@PathVariable String email, @RequestBody ObjectNode node) {
        Optional<User> userToFind = userRepositoryJPA.findByEmail(email);

        if (userToFind.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        User userToUpdate = userToFind.get();

        String newPassword = node.get("newPassword").asText();

        userToUpdate.setPassword(newPassword);
        userToUpdate.setUpdatedAt(LocalDateTime.now());

        try {
            return new ResponseEntity<>(userRepositoryJPA.save(userToUpdate), HttpStatus.OK);
        } catch(Exception error) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteById(@PathVariable long id) {
        Optional<User> user = userRepositoryJPA.findById(id);
        if (user.isEmpty()) throw new ResourceNotFound(String.format("User with id: [%d] is not found", id));

        try {
            User _user = user.get();
//            _user.setLevelsLocked(null);
            _user.getEventList().clear();
//            _user.getCountings().clear();
            userRepositoryJPA.save(_user);
//            System.out.println(_user.getLevelsLocked());
            userRepositoryJPA.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception error) {
            error.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
