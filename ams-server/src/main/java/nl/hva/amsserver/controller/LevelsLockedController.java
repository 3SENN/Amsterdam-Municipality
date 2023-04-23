package nl.hva.amsserver.controller;

import nl.hva.amsserver.exceptions.InternalServerError;
import nl.hva.amsserver.exceptions.ResourceNotFound;
import nl.hva.amsserver.models.levels.LevelsLocked;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.levels.LevelsLockedRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/levelsLocked")
public class LevelsLockedController {

    @Autowired
    LevelsLockedRepositoryJPA levelsLockedRepositoryJPA;

    @Autowired
    UserRepositoryJPA userRepositoryJPA;

    @GetMapping
    public List<LevelsLocked> getAllLevelsLocked() {
        List<LevelsLocked> levelsLocked = new ArrayList<>(levelsLockedRepositoryJPA.findAll());

        if (levelsLocked.isEmpty()) {
            throw new ResourceNotFound("No locked levels found");
        }

        return levelsLocked;
    }

    @GetMapping("{userId}")
    public Optional<LevelsLocked> getLevelsLockedByUserId(@PathVariable long userId) {
        Optional<User> requestedBy = userRepositoryJPA.findById(userId);

        if (requestedBy.isEmpty()) {
            throw new ResourceNotFound("No user found with id: " + userId);
        }

        Optional<LevelsLocked> levelsLocked = levelsLockedRepositoryJPA.findByUserIdIs(requestedBy);

        if (levelsLocked.isEmpty()) {
            throw new ResourceNotFound("No locked levels found for user id: " + userId);
        }

        return levelsLocked;
    }

    @PostMapping
    public ResponseEntity<LevelsLocked> addLevelsLocked(@RequestBody LevelsLocked levelsLocked, @RequestParam long userId) {
        try {
            Optional<User> requestedBy = userRepositoryJPA.findById(userId);

            if (requestedBy.isEmpty()) {
                throw new ResourceNotFound("No user found with id: " + userId);
            }

            levelsLocked.setUserId(requestedBy.get());
            LevelsLocked levelsLockedSaved = levelsLockedRepositoryJPA.save(levelsLocked);

            return new ResponseEntity<>(levelsLockedSaved, HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(error.getMessage());
        }
    }

    @PutMapping("{userId}")
    public ResponseEntity<LevelsLocked> updateLevelsLocked(@PathVariable long userId, @RequestBody LevelsLocked levelsLocked) {
        try {
            Optional<User> requestedBy = userRepositoryJPA.findById(userId);

            if (requestedBy.isEmpty()) {
                throw new ResourceNotFound("No user found with id: " + userId);
            }

            Optional<LevelsLocked> levelsLockedData = levelsLockedRepositoryJPA.findByUserIdIs(requestedBy);

            if (levelsLockedData.isEmpty()) {
                throw new ResourceNotFound("No data found for locked levels");
            }

            LevelsLocked newLevelsLocked = levelsLockedData.get();
            newLevelsLocked.setLevels(levelsLocked.getLevels());

            LevelsLocked newLevelsLockedUpdated = levelsLockedRepositoryJPA.save(newLevelsLocked);

            return new ResponseEntity<>(newLevelsLockedUpdated, HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(error.getMessage());
        }
    }

}
