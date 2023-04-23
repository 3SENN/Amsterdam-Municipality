package nl.hva.amsserver.controller;


import nl.hva.amsserver.exceptions.InternalServerError;
import nl.hva.amsserver.exceptions.ResourceNotFound;
import nl.hva.amsserver.models.counting.Counting;
import nl.hva.amsserver.models.counting.CountingLabels;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.counting.CountingLabelRepositoryJPA;
import nl.hva.amsserver.repositories.counting.CountingRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Rest controller for Counting
 *
 * @author Jordy Mol & Harmohat Khangura
 */
@RestController
@RequestMapping("/counting")
public class CountingController {

    @Autowired
    CountingRepositoryJPA countingRepositoryJPA;

    @Autowired
    UserRepositoryJPA userRepositoryJPA;

    @Autowired
    CountingLabelRepositoryJPA countingLabelRepositoryJPA;

    /**
     * Method tries to find all counting levels
     *
     * @return List of all found counting levels
     * @throws ResourceNotFound Throws exception when the counting levels list is empty(no data found)
     */
    @GetMapping
    public List<Counting> getAllCountingLevels() {
        // Creating new list of type Counting and inserting data if there is data
        List<Counting> countingLevels = new ArrayList<>(countingRepositoryJPA.findAll());

        // Throws ResourceNotFound exception when no counting levels were found
        if (countingLevels.isEmpty()) {
            throw new ResourceNotFound("No counting levels found.");
        }

        // Returns the counting levels
        return countingLevels;
    }

    /**
     * Method gets the total amount of the played counting level by the users
     *
     * @return the total amount of the played counting level by the users
     */
    @GetMapping("/total/amount")
    public long getTotalAmountOfCountingLevels() {
        return countingRepositoryJPA.count();
    }

    /**
     * Method calculates the average score for the counting level
     *
     * @return map with the total number of users, total score, average score and percentage
     */
    @GetMapping("/scores")
    public Map<String, Integer> getScores() {
        Map<String, Integer> map = new HashMap<>();

        int totalScore = countingRepositoryJPA.totalScore().isEmpty() ? 0 : countingRepositoryJPA.totalScore().get();
        int averageScore = countingRepositoryJPA.averageScore().isEmpty() ? 0 : countingRepositoryJPA.averageScore().get();
        int percentage;

        if (totalScore == 0 || averageScore == 0) percentage = 0;
        else percentage = 100 / (totalScore / averageScore);

        // adding properties to the JSON object
        map.put("users", (int) countingRepositoryJPA.count());
        map.put("totalScore", totalScore);
        map.put("averageScore", averageScore);
        map.put("percentage", percentage);

        return map;
    }

    /**
     * Method searches for a counting level with a given id
     *
     * @param id The id of the counting level
     * @return The counting level searched by the given id
     * @throws ResourceNotFound Throws exception when no counting level was found with the given id
     */
    @GetMapping("{id}")
    public Counting getCountingById(@PathVariable long id) {
        // Searches for the counting level with the given id
        Optional<Counting> counting = countingRepositoryJPA.findById(id);

        // Returns the counting level if it exists
        if (counting.isPresent()) {
            return counting.get();
        }

        // Throws ResourceNotFound exception if no counting level was found with the given id
        throw new ResourceNotFound("No counting level found with id: " + id);
    }

    /**
     * Method will delete the played counting level by id
     *
     * @param id The id of the played counting level
     * @return ResponseEntity with the 200 OK status code only.
     * @throws ResourceNotFound    Throws exception when the played counting level was not found
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Counting> deleteById(@PathVariable long id) throws ResourceNotFound, InternalServerError {
        // Container counting object which may or may not contain a non-null value when finding played counting level by id
        Optional<Counting> counting = countingRepositoryJPA.findById(id);

        // Throws resource not found exception (404) when the played counting level was not found
        if (counting.isEmpty())
            throw new ResourceNotFound("No counting level found with id: " + id);

        Counting _counting = counting.get();

        try {
            countingLabelRepositoryJPA.deleteByCounting(_counting);
            countingRepositoryJPA.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception error) {
            error.printStackTrace();
            throw new InternalServerError(null);
        }
    }

    /**
     * Method searches for all the counting levels played by a user
     *
     * @param id The id of the user to search for counting levels
     * @return The counting levels played by the given user id
     * @throws ResourceNotFound Throws exception when no user was found with the given id
     * @throws ResourceNotFound Throws exception when the given user didn't play any counting levels
     */
    @GetMapping("/user/{id}")
    public List<Counting> getCountingLevelsByUserId(@PathVariable long id) {
        // Searches for the user with the given user id
        Optional<User> playedBy = userRepositoryJPA.findById(id);

        // Throws ResourceNotFound exception if no user was found with the given user id
        if (playedBy.isEmpty()) {
            throw new ResourceNotFound("No user found with id: " + id);
        }

        // Creates a new list of type Counting and inserts it with levels from the given user (if the user has played levels)
        List<Counting> countingLevels = new ArrayList<>(countingRepositoryJPA.findByUserIdIs(playedBy));

        // Throws ResourceNotFound exception if no counting levels were found for the given user
        if (countingLevels.isEmpty()) {
            throw new ResourceNotFound("No counting levels found for user id: " + id);
        }

        // Returns the counting levels
        return countingLevels;
    }

    /**
     * Method tries to add a counting level to the counting table
     *
     * @param counting  The counting object to add
     * @param userId    The id of the user who played the counting level
     * @return ResponseEntity with a body which contains the created counting object and status code 200
     * @throws ResourceNotFound Throws exception when the user was not found
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @PostMapping
    public ResponseEntity<Counting> addCountingLevel(@RequestBody Counting counting, @RequestParam long userId) {
        // Searches for a user with the given id
        Optional<User> playedBy = userRepositoryJPA.findById(userId);

        // Throws ResourceNotFound exception if no user was found with the given id
        if (playedBy.isEmpty()) {
            throw new ResourceNotFound("The user who played the counting level was not found.");
        }
        try {
            counting.setUserId(playedBy.get());
            Counting countingSaved = countingRepositoryJPA.save(counting);

            // Returns the created counting object and status code 200
            return new ResponseEntity<>(countingSaved, HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(error.getMessage());
        }
    }

    /**
     * Method tries to add a counting label to the countinglabels table
     *
     * @param countingLabel The countingLabel object to add
     * @param countingId    The id of the counting level to link the label to
     * @return ResponseEntity with a body which contains the created counting label object and status code 200
     * @throws ResourceNotFound Throws exception when the counting level was not found
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @PostMapping("/label")
    public ResponseEntity<CountingLabels> addCountingLabel(@RequestBody CountingLabels countingLabel,
                                                           @RequestParam long countingId) {
        // Searches for a counting level with the given id
        Optional<Counting> countingLevel = countingRepositoryJPA.findById(countingId);

        // Throws ResourceNotFound exception when no counting level was found with the given id
        if (countingLevel.isEmpty()) {
            throw new ResourceNotFound("Counting level was not found");
        }

        try {
            Counting counting = countingLevel.get();
            counting.associateLabel(countingLabel);

            CountingLabels countingLabelSaved = countingLabelRepositoryJPA.save(countingLabel);
            countingRepositoryJPA.save(counting);

            // Returns the created CountingLabel object and status code 200
            return new ResponseEntity<>(countingLabelSaved, HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(error.getMessage());
        }
    }

    /**
     * Method searches for the highscore from every user
     *
     * @return List of counting levels which are the highscores from every user
     * @throws ResourceNotFound Throws exception when no highscores were found
     */
    @GetMapping("/highscores")
    public List<Counting> getCountingHighscores() {
        // Create a list of type Counting and insert with counting levels which are highscores (if there is data)
        List<Counting> countingHighscores = new ArrayList<>(countingRepositoryJPA.findHighscores());

        // Throws ResourceNotFound exception when no highscores were found
        if (countingHighscores.isEmpty()) {
            throw new ResourceNotFound("No Highscores found");
        }

        // Returns list of highscores
        return countingHighscores;
    }

    /**
     * Method searches for all the labels associated to a counting level
     *
     * @param countingId The id of the counting level to get labels from
     * @return List of counting labels searched by the counting object
     * @throws ResourceNotFound Throws exception when counting level was not found
     * @throws ResourceNotFound Throws exception when no counting labels were found
     */
    @GetMapping("/label/{countingId}")
    public List<CountingLabels> getCountingLabelsForCounting(@PathVariable long countingId) {
        // Search for counting level with the given id
        Optional<Counting> counting = countingRepositoryJPA.findById(countingId);

        // Throws ResourceNotFound exception when counting level was not found
        if (counting.isEmpty()) {
            throw new ResourceNotFound("Counting levels was not found with id: " + countingId);
        }

        // Create a list of type CountingLabels and insert with counting labels associated to the counting level (if ther is data)
        List<CountingLabels> countingLabels = countingLabelRepositoryJPA.findByCountingIs(counting);

        // Throws ResourceNotFound exception if no labels were found
        if (countingLabels.isEmpty()) {
            throw new ResourceNotFound("No labels found for this counting level");
        }

        // Returns list of counting labels
        return countingLabels;
    }
}
