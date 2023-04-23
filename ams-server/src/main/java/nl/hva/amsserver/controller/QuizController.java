package nl.hva.amsserver.controller;

import nl.hva.amsserver.exceptions.InternalServerError;
import nl.hva.amsserver.exceptions.ResourceNotFound;
import nl.hva.amsserver.models.quiz.Question;
import nl.hva.amsserver.models.quiz.QuestionOption;
import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.event.EventRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import nl.hva.amsserver.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * The type QuizController
 * <br/>
 * Responsible for all data for quiz. The Rest Controller Allows the class to handle the requests
 * (provides the REST web service) made by the client which are related to the quiz.
 * The base URI for the class: `/quiz`
 *
 * @author Harmohat Khangura
 */
@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private UserRepositoryJPA userRepositoryJPA;
    @Autowired
    private EventRepositoryJPA eventRepositoryJPA;

    /**
     * Methods tries to find all quizzes with using the QuizService
     *
     * @return ResponseEntity with a body which contains the list which contains all the quizzes and status code = 200.
     * @throws ResourceNotFound Throws exception when the quizzes list is empty (data was empty/not found)
     */
    @GetMapping()
    public ResponseEntity<List<Quiz>> getAllQuizzes() throws ResourceNotFound {
        // Creating new list of type Quiz and inserting quiz data if any with the parametrized constructor call
        List<Quiz> quizzes = new ArrayList<Quiz>(quizService.findAll());

        // Throws resource not found exception (404) if the quizzes list is empty (no data)
        if (quizzes.isEmpty()) throw new ResourceNotFound("Quizzes are not found.");

        // Throws the quizzes when present with 200 OK code
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    /**
     * Method gets the total amount of quizzes with using the QuizService
     *
     * @return The total amount of quizzes
     */
    @GetMapping("/total/amount")
    public long getTotalAmountOfEvents() {
        return quizService.getTotalAmountOfQuizzes();
    }

    /**
     * Gets total amount of questions with using the QuizService
     *
     * @return the total amount of questions
     */
    @GetMapping("questions/total/amount")
    public long getTotalAmountOfQuestions() {
        return quizService.getTotalAmountOfQuestions();
    }


    /**
     * Methods tries to find the quiz by id with using the QuizService
     *
     * @param quizId The id of the quiz to find
     * @return ResponseEntity with a body which contains the quiz object and status code = 200.
     * @throws ResourceNotFound Throws exception when the quiz was not found
     */
    @GetMapping("{quizId}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable long quizId) throws ResourceNotFound {
        // A Quiz container object which may or may not contain a non-null value
        Optional<Quiz> quiz = quizService.findQuizById(quizId);

        // When the quiz is present the method will return the quiz with a 200 OK code
        if (quiz.isPresent()) return new ResponseEntity<>(quiz.get(), HttpStatus.OK);

        // Throws resource not found exception (404) when the quiz was not found
        throw new ResourceNotFound("The quiz with id:[" + quizId + "] was not found.");
    }

    /**
     * Method tries to delete the event with using the QuizService
     *
     * @param quizId The id of the quiz to delete
     * @return ResponseEntity with the 200 OK status code only.
     * @throws ResourceNotFound    Throws exception when the quiz was not found
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @DeleteMapping("{quizId}")
    public ResponseEntity<Quiz> deleteQuizById(@PathVariable long quizId) throws ResourceNotFound, InternalServerError {
        // A Quiz container object which may or may not contain a non-null value
        Optional<Quiz> quiz = quizService.findQuizById(quizId);

        // Throw resource not found exception (404) when the quiz was found
        if (quiz.isEmpty())
            throw new ResourceNotFound("The quiz to delete with id:[" + quizId + "] was not found.");

        try {
            Quiz _quiz = quiz.get();

            // removing the quiz from the events
            eventRepositoryJPA.deleteByQuiz(_quiz);
            _quiz.remove();
            // Saving the changes
            quizService.createQuiz(_quiz);
            quizService.deleteQuizById(quizId);
            // Returns 200 OK code when deleted
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception error) {
            error.printStackTrace();
            throw new InternalServerError(null);
        }
    }


    /**
     * Method tries to add the quiz to the quizzes
     *
     * @param quiz The quiz object to add
     * @param id   The id of the user who created the quiz
     * @return ResponseEntity with a body which contains the created quiz object and status code = 200.
     * @throws ResourceNotFound    Throws exception when the user was not found
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @PostMapping
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz, @RequestParam long id) throws ResourceNotFound,
            InternalServerError {
        // User container object which may or may not contain a non-null value
        Optional<User> createdBy = userRepositoryJPA.findById(id);
        // Throw resource not found exception (404) when the user was found
        if (createdBy.isEmpty())
            throw new ResourceNotFound(String.format("The user with id:[%d] was not found", id));

        try {
            quiz.setCreatedBy(createdBy.get());

            Quiz quizCreated = quizService.createQuiz(quiz);
            // Returns the created quiz object and 200 OK code when added
            return new ResponseEntity<>(quizCreated, HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(null);
        }
    }


    /**
     * Method tries to update the existing quiz
     *
     * @param id   The id of the quiz to update
     * @param quiz The quiz object which contains the updated values
     * @return ResponseEntity with a body which contains the updated quiz object and status code = 200.
     * @throws ResourceNotFound Throws exception when the quiz to update was not found
     */
    @PutMapping("{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable long id, @RequestBody Quiz quiz) throws ResourceNotFound {
        // Quiz container object which may or may not contain a non-null value
        Optional<Quiz> optionalQuiz = quizService.findQuizById(id);

        // Throw resource not found exception (404) when the quiz was found
        if (optionalQuiz.isEmpty())
            throw new ResourceNotFound("The quiz to update with id:[" + id + "] was not found.");

        Quiz _quiz = optionalQuiz.get();
        _quiz = quiz;
        _quiz.setUpdatedAt(LocalDateTime.now());

        // Returns the updated quiz object and 200 OK code when changed
        return new ResponseEntity<>(quizService.createQuiz(_quiz), HttpStatus.OK);
    }


    /**
     * Method tries to find all the questions with using the QuizService
     *
     * @return ResponseEntity with a body which contains the questions list and status code = 200.
     * @throws ResourceNotFound Throws exception when the question were not found
     */
    @GetMapping("questions")
    public ResponseEntity<List<Question>> getAllQuestions() throws ResourceNotFound {
        List<Question> questions = new ArrayList<Question>(quizService.findAllQuestions());

        // Throws resource not found exception (404) if the questions list is empty (no data)
        if (questions.isEmpty()) throw new ResourceNotFound("There are no questions available.");

        // Returns the questions and 200 OK code when present
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    /**
     * Methods tries to find the question by id with using the QuizService
     *
     * @param questionId The id of the question to find
     * @return ResponseEntity with a body which contains the question object and status code = 200.
     * @throws ResourceNotFound Throws exception when the question was not found
     */
    @GetMapping("questions/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable long questionId) throws ResourceNotFound {
        // Question container object which may or may not contain a non-null value
        Optional<Question> quizQuestion = quizService.findQuestionById(questionId);

        // Returns the question and 200 OK code when present
        if (quizQuestion.isPresent()) return new ResponseEntity<>(quizQuestion.get(), HttpStatus.OK);

        // Return resource not found exception when the question was found
        throw new ResourceNotFound("The question for quiz with id:[" + questionId + "] was not found.");
    }


    /**
     * Method changes the relation between the quiz and question. The method will remove the question
     * from the quiz relation and the quiz will remove the question relation.
     * one-to-many
     *
     * @param quizId     The id of the quiz which contains the questions
     * @param questionId The id of the question of which contains the quiz
     * @return ResponseEntity with a body which contains the updated quiz object and status code = 200.
     * @throws ResourceNotFound 1. Throws exception when quiz was not found
     *                          2. Throws exception when question was not found
     */
    @PutMapping("{quizId}/questions/{questionId}")
    public ResponseEntity<Quiz> removeQuestionsFromQuiz(@PathVariable long quizId, @PathVariable long questionId)
            throws ResourceNotFound {
        // Quiz container object which may or may not contain a non-null value
        Optional<Quiz> quiz = quizService.findQuizById(quizId);
        // Question container object which may or may not contain a non-null value
        Optional<Question> question = quizService.findQuestionById(questionId);

        // Throw resource not found exception when the quiz was found
        if (quiz.isEmpty())
            throw new ResourceNotFound(String.format("The quiz with id:[%d] was not found", quizId));
        // Throw resource not found exception when the question was found
        if (question.isEmpty())
            throw new ResourceNotFound(String.format("The question with id:[%d] was not found", questionId));

        // remove the question from the quiz
        quiz.get().removeQuestion(question.get());
        // remove the quiz from the question
        question.get().getQuizzes().remove(quiz.get());
        quizService.createQuestion(question.get());
        Quiz _quiz = quizService.createQuiz(quiz.get());

        return new ResponseEntity<>(_quiz, HttpStatus.OK);
    }

    /**
     * Method finds all the questions for the given quiz id
     *
     * @param quizId The id of the quiz for which the questions should be found
     * @return ResponseEntity with a body which contains questions list and status code = 200.
     * @throws ResourceNotFound Throws exception when the questions where not found for the quiz
     */
    @GetMapping("{quizId}/questions")
    public ResponseEntity<List<Question>> getQuestionsForQuiz(@PathVariable long quizId) throws ResourceNotFound {
        List<Question> questions = quizService.findQuestionByQuizId(quizId);

        // Sorting by block number
        questions.sort(Comparator.comparing(Question::getBlock));

        // Returns the questions and 200 OK code when present
        if (!questions.isEmpty()) return new ResponseEntity<>(questions, HttpStatus.OK);

        // Throw resource not found exception (404) when the questions were found
        throw new ResourceNotFound("The questions for quiz with id:[" + quizId + "] were not found.");
    }


    /**
     * Method adds a new question to questions
     *
     * @param question The question object to create
     * @return ResponseEntity with a body which contains the created question object and status code = 200.
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @PostMapping("questions")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) throws InternalServerError {
        try {
            Question _question = quizService.createQuestion(question);
            // Returns the question and 200 OK code when added
            return new ResponseEntity<>(_question, HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(null);
        }
    }


    /**
     * Methods adds the question to the quiz
     *
     * @param quizId     The id of the quiz for which the question should be added to
     * @param questionId The id of the question, which will be added to the quiz
     * @return ResponseEntity with a body which contains the question object and status code = 200.
     * @throws ResourceNotFound    1. Throws exception when quiz was not found
     *                             2. Throws exception when question was not found
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @PostMapping("{quizId}/questions/{questionId}")
    public ResponseEntity<Question> addQuestion(@PathVariable long quizId, @PathVariable long questionId)
            throws ResourceNotFound, InternalServerError {
        try {
            // Quiz container object which may or may not contain a non-null value
            Optional<Quiz> quiz = quizService.findQuizById(quizId);
            // Question container object which may or may not contain a non-null value
            Optional<Question> question = quizService.findQuestionById(questionId);

            // Throw resource not found exception when the quiz was found
            if (quiz.isEmpty())
                throw new ResourceNotFound(String.format("The quiz with id:[%d] was not found", quizId));
            // Throw resource not found exception when the question was found
            if (question.isEmpty())
                throw new ResourceNotFound(String.format("The question with id:[%d] was not found", questionId));

            // Extracting the non-null value with .get
            Question _question = question.get();
            // Adding the question to the quiz
            _question.getQuizzes().add(quiz.get());

            return new ResponseEntity<>(quizService.createQuestion(_question), HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(null);
        }
    }

    /**
     * Method updates the existing question
     *
     * @param id       The id of the question to update
     * @param question The question object which contains the updated values
     * @return ResponseEntity with a body which contains the updates question object and status code = 200.
     * @throws ResourceNotFound Throws exception when the question to update was not found
     */
    @PutMapping("question/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable long id, @RequestBody Question question)
            throws ResourceNotFound {
        // Question container object which may or may not contain a non-null value
        Optional<Question> optionalQuestion = quizService.findQuestionById(id);

        // Throw resource not found exception when the question was found
        if (optionalQuestion.isEmpty())
            throw new ResourceNotFound(String.format("The question to update with id:[%d] was not found", id));

        Question _question = optionalQuestion.get();
        // keeps the relation
        question.setQuizzes(_question.getQuizzes());
        _question = question;
        return new ResponseEntity<>(quizService.createQuestion(_question), HttpStatus.OK);
    }

    /**
     * Method tries to delete the question by id
     *
     * @param questionId The id of the question to delete
     * @return ResponseEntity with the 200 OK status code only.
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @DeleteMapping("question/{questionId}")
    public ResponseEntity<Question> deleteQuestionById(@PathVariable long questionId) throws InternalServerError {
        try {
            quizService.deleteQuestionById(questionId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(null);
        }
    }


    /**
     * Method updates the existing (question) option
     *
     * @param id             The id of the question option to update
     * @param questionOption The question option object which contains the updated values
     * @return ResponseEntity with a body which contains the question option object and status code = 200.
     * @throws ResourceNotFound Throws exception when the question option to update was not found
     */
    @PutMapping("question/option/{id}")
    public ResponseEntity<QuestionOption> updateQuizQuestionOption(@PathVariable long id, @RequestBody QuestionOption questionOption)
            throws ResourceNotFound {
        // Question option container object which may or may not contain a non-null value
        Optional<QuestionOption> optionalQuizQuestionOption = quizService.findQuestionOptionById(id);

        if (optionalQuizQuestionOption.isPresent()) {
            QuestionOption _QuestionOption = optionalQuizQuestionOption.get();
            questionOption.setQuestion(_QuestionOption.getQuestion());
            _QuestionOption = questionOption;
            return new ResponseEntity<>(quizService.createQuestionOption(_QuestionOption), HttpStatus.OK);
        } else {
            throw new ResourceNotFound(String.format("The question option to update with id:[%d] was not found", id));
        }
    }

    /**
     * Methods sets the relation between the question and question option
     *
     * @param questionOption The question option that will be linked to the question
     * @param questionId     The id of question to which the option will be linked
     * @return ResponseEntity with a body which contains the question option and status code = 200.
     * @throws ResourceNotFound    Throws exception when the question was not found
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @PostMapping("{questionId}/option")
    public ResponseEntity<QuestionOption> addQuizQuestionOption(@RequestBody QuestionOption questionOption, @PathVariable long questionId)
            throws ResourceNotFound, InternalServerError {
        try {
            // Question container object which may or may not contain a non-null value
            Optional<Question> quizQuestion = quizService.findQuestionById(questionId);

            // Throw resource not found exception when the question was found
            if (quizQuestion.isEmpty())
                throw new ResourceNotFound(String.format("The quiz question with id:[%d] was not found", questionId));

            questionOption.setQuestion(quizQuestion.get());

            QuestionOption _QuestionOption = quizService.createQuestionOption(questionOption);
            return new ResponseEntity<>(_QuestionOption, HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(null);
        }
    }


    /**
     * Method finds the quizzes where user is not signed up for
     *
     * @param userId The id of the user
     * @return The quizzes where user did not sign up for
     * @throws ResourceNotFound Throws exception when there are no quizzes where user did not sign up for
     */
    @GetMapping("not/registered/{userId}")
    public List<Quiz> quizzes(@PathVariable long userId) throws ResourceNotFound {
        List<Quiz> quizzes = new ArrayList<>(quizService.getQuizzesWhereUserIsNotSignedUpFor(userId));

        if (quizzes.isEmpty())
            throw new ResourceNotFound(String.format("Couldn't find any quizzes where user with id: [%d] is not signed up for", userId));

        return quizzes;
    }
}
