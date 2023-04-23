package nl.hva.amsserver.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.hva.amsserver.exceptions.InternalServerError;
import nl.hva.amsserver.exceptions.ResourceNotFound;
import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.quiz.QuizResult;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.quiz.QuizResultRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import nl.hva.amsserver.service.QuizResultService;
import nl.hva.amsserver.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/quiz/result")
public class QuizResultController {
    /**
     * The QuizResult service.
     */
    @Autowired
    QuizResultService quizResultService;
    @Autowired
    QuizService quizService;
    @Autowired
    UserRepositoryJPA userRepositoryJPA;
    @Autowired
    private QuizResultRepositoryJPA quizResultRepositoryJPA;

    /**
     * Gets all quiz results.
     *
     * @return the quiz results
     */
    @GetMapping()
    public ResponseEntity<List<QuizResult>> getAllQuizResults() {
        List<QuizResult> quizResults = new ArrayList<>(quizResultService.findAll());

        // Return resource not found exception when nothing is found
        if (quizResults.isEmpty()) throw new ResourceNotFound("QuizResults are not found.");
        // Returns the quizzes when present with 200 OK code
        return new ResponseEntity<>(quizResults, HttpStatus.OK);
    }

    /**
     * Method gets all the existing quiz results
     *
     * @return ResponseEntity with a body which contains the quiz results list and status code = 200.
     * @throws ResourceNotFound Throws exception when the quiz results list is empty (data was empty/not found)
     */
    @GetMapping("all")
    public ResponseEntity<List<QuizResult>> getAllResults() throws ResourceNotFound {
        List<QuizResult> quizResults = new ArrayList<>(quizResultRepositoryJPA.findAll());

        // Throws resource not found exception (404) if the quiz results list is empty (no data)
        if (quizResults.isEmpty()) throw new ResourceNotFound("The results are not found.");

        return new ResponseEntity<>(quizResults, HttpStatus.OK);
    }

    /**
     * Method gets the total amount of quiz results
     *
     * @return the total amount of quiz results
     */
    @GetMapping("/total/amount")
    public long getTotalAmountOfQuizResults() {
        return quizResultService.getTotalAmountOfQuizResults();
    }

    /**
     * Method gets the scores for the quiz results which contains the total number of users, total score, average score
     * and percentage
     *
     * @return the scores for the quiz results
     */
    @GetMapping("/scores")
    public Map<String, Integer> getScores() {
        return quizResultService.getScores();
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<QuizResult>> findByUserId(@PathVariable long userId) {
        List<QuizResult> quizResultUser = new ArrayList<QuizResult>(quizResultService.findById(userId));

        if (quizResultUser.isEmpty()) throw new ResourceNotFound("Quizzes are not found.");
        return new ResponseEntity<>(quizResultUser, HttpStatus.OK);
    }

    /**
     * Method deletes the quiz result by id
     *
     * @param id The id of the quiz result to delete
     * @return ResponseEntity with the 200 OK status code only.
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @DeleteMapping("{id}")
    public ResponseEntity<QuizResult> deleteById(@PathVariable long id) throws InternalServerError {
        try {
            quizResultRepositoryJPA.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception error) {
            error.printStackTrace();
            throw new InternalServerError(null);
        }
    }

    @GetMapping("by/{quizId}")
    public ResponseEntity<List<QuizResult>> findByQuizId(@PathVariable long quizId) {

        Optional<Quiz> quiz = quizService.findQuizById(quizId);

        if (quiz.isEmpty()) throw new ResourceNotFound(String.format("The quiz with id:[%d] was not found", quizId));
        List<QuizResult> results = quizResultRepositoryJPA.findByQuizId(quiz.get());
        results.sort(Comparator.comparing(QuizResult::getScore).reversed());

        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    /**
     * Method gets the quiz result by user id and quiz id
     *
     * @param quizId The id of the quiz
     * @param userId The id of the user
     * @return ResponseEntity with a body which contains the quiz result object and status code = 200.
     * @throws ResourceNotFound     1. Throws exception when the quiz was not found
     *                              2. Throws exception when the user was not found
     *                              3. Throws exception when the quiz result was not found
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @GetMapping("{quizId}/user/{userId}")
    public ResponseEntity<QuizResult> findByUserIdAndQuizId(@PathVariable long quizId, @PathVariable long userId)
            throws ResourceNotFound, InternalServerError {
        try {
            // A Quiz container object which may or may not contain a non-null value
            Optional<Quiz> quiz = quizService.findQuizById(quizId);
            // A User container object which may or may not contain a non-null value
            Optional<User> user = userRepositoryJPA.findById(userId);

            // Throw resource not found exception (404) when the quiz was found
            if (quiz.isEmpty())
                throw new ResourceNotFound(String.format("The quiz with id:[%d] was not found", quizId));
            // Throw resource not found exception (404) when the user was found
            if (user.isEmpty())
                throw new ResourceNotFound(String.format("The user with id:[%d] was not found", userId));

            QuizResult quizResult = quizResultService.findByUserAndQuiz(user.get(), quiz.get());

            if (quizResult != null) return new ResponseEntity<>(quizResult, HttpStatus.OK);

            // Throw resource not found exception (404) when the quiz result was found
            throw new ResourceNotFound("QuizResult was not found");
        } catch (Exception error) {
            error.printStackTrace();
            throw new InternalServerError(null);
        }
    }

    @PostMapping("/results")
    public ResponseEntity<QuizResult> addUserScore(@RequestBody ObjectNode node) {
        try {
            long userId = node.get("userId").asLong();
            long quizId = node.get("quizId").asLong();
            int score = Integer.parseInt(node.get("score").asText());

            Optional<Quiz> quiz = quizService.findQuizById(quizId);
            Optional<User> user = userRepositoryJPA.findById(userId);

            if (quiz.isEmpty())
                throw new ResourceNotFound(String.format("The quiz with id:[%d] was not found", quizId));
            if (user.isEmpty())
                throw new ResourceNotFound(String.format("The user with id:[%d] was not found", userId));

            QuizResult quizResult = new QuizResult(user.get(), quiz.get(), score);

            return new ResponseEntity<>(quizResultRepositoryJPA.save(quizResult), HttpStatus.OK);
        } catch (Exception error) {
            error.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
