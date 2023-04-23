package nl.hva.amsserver.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.hva.amsserver.APIConfig;
import nl.hva.amsserver.exceptions.InternalServerError;
import nl.hva.amsserver.exceptions.ResourceNotFound;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import nl.hva.amsserver.security.JWToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    APIConfig apiConfig;
    @Autowired
    UserRepositoryJPA userRepositoryJPA;

    @PostMapping("/register")
    public User RegisterUser(@Valid @RequestBody User user) {
        try {
            return userRepositoryJPA.save(user);
        } catch (Exception e) {
            throw new InternalServerError("Couldn't register user");
        }
    }

//    @PostMapping("/login")
//    public User login(@RequestBody LoginDTO user) {
//        try {
//            Optional<User> searchedUser = userRepositoryJPA.findByEmail(user.getEmail());
//            if (searchedUser.isEmpty())
//                throw new ResourceNotFound(String.format("User with email=%s was not found", user.getEmail()));
//
//            //            if(foundUser.getPassword().equals(user.getPassword())) {
////                return foundUser;
////            }
//            return searchedUser.get();
//
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            throw new InternalServerError("Couldn't login user");
//        }
//    }


    @PostMapping(path = "/login")
    public ResponseEntity<String> createUser(@RequestBody ObjectNode signInInfo) {
        if (signInInfo == null) throw new ResourceNotFound("Could not find user");

        String email = signInInfo.get("email").asText();

        Optional<User> searchedUser = userRepositoryJPA.findByEmail(email);
        if (searchedUser.isEmpty())
            throw new ResourceNotFound(String.format("User with email=%s was not found", email));

        User user = searchedUser.get();
        String fullName = user.getFirstName() + " " + user.getLastName();
        JWToken jwToken = new JWToken(fullName, user.getId(), user.getEmail(), String.valueOf(user.getIsAdmin()), user.getProfilePhoto());
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity());

        System.out.println(tokenString);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(tokenString);
    }
}