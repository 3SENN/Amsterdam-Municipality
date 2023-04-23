package nl.hva.amsserver.controller;

import nl.hva.amsserver.exceptions.InternalServerError;
import nl.hva.amsserver.exceptions.ResourceNotFound;
import nl.hva.amsserver.models.dto.CountParticipants;
import nl.hva.amsserver.models.event.Event;
import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.models.dto.EventProcessor;
import nl.hva.amsserver.repositories.event.EventRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import nl.hva.amsserver.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * The type EventController
 * <br/>
 * Responsible for all data for events. The Rest Controller Allows the class to handle the requests
 * (provides the REST web service) made by the client which are related to the events.
 * The base URI for the class: `/events`
 *
 * @author Harmohat Khangura
 */
@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    EventRepositoryJPA eventRepositoryJPA;
    @Autowired
    UserRepositoryJPA userRepositoryJPA;
    @Autowired
    QuizService quizService;

    /**
     * Methods tries to find all events with using the EventRepositoryJPA
     *
     * @return The list containing all the events
     * @throws ResourceNotFound Throws exception when the events list is empty (data was empty/not found)
     */
    @GetMapping
    public List<Event> getAllEvents() throws ResourceNotFound {
        // Creating new list of type Event and inserting event data if any with the parametrized constructor call
        List<Event> events = new ArrayList<>(eventRepositoryJPA.findAll());

        // Throws resource not found exception (404) if the events list is empty (no data)
        if (events.isEmpty()) throw new ResourceNotFound("The events were not found");
        // Returns the events when present
        return events;
    }

    /**
     * Method gets all the quizzes available. For each individual quiz the total amount of people registered for the
     * quiz is being calculated and is being added to a list.
     *
     * @return The list containing all the calculated participants for a quiz
     * @throws ResourceNotFound Throws exception when the quizzes list is empty (data was empty/not found)
     */
    @GetMapping("/total/registered")
    public List<CountParticipants> getCountParticipantsForAllQuizzes() throws ResourceNotFound {
        // Creating new list of CountParticipants
        List<CountParticipants> countParticipants = new ArrayList<>();
        // Creating new list of Quiz which will contain all quizzes
        List<Quiz> quizzes = quizService.findAll();
        // Throws resource not found exception (404) if the quizzes list is empty (no data)
        if (quizzes.isEmpty()) throw new ResourceNotFound("The quizzes were not found");

        // Loops through the quizzes
        for (Quiz quiz : quizzes) {
            // Creates new CountParticipants object
            CountParticipants countParticipant = new CountParticipants(
                    quiz.getId(),
                    // Finds quiz by ID and if found -> get the total amount to register
                    Objects.requireNonNull(quizzes.stream()
                                    .filter(q -> q.getId() == quiz.getId()).
                                    findFirst().orElse(null))
                            .getAmountToRegister(),
                    eventRepositoryJPA.countByQuiz_Id(quiz.getId())
            );
            countParticipants.add(countParticipant);
        }
        return countParticipants;
    }

    /**
     * Methods tries to find the event by id with using the EventRepositoryJPA
     *
     * @param eventId The id of the event to find
     * @return The event object
     * @throws ResourceNotFound Throws exception when the event was not found/does not exist
     */
    @GetMapping("{eventId}")
    public Event getEventByEventId(@PathVariable long eventId) throws ResourceNotFound {
        //  Container event object which may or may not contain a non-null value when finding event by id
        Optional<Event> event = eventRepositoryJPA.findById(eventId);

        // Throws resource not found exception (404) if the event was not found
        if (event.isEmpty()) throw new ResourceNotFound(String.format("The event with id [%d] was not found", eventId));

        return event.get();
    }

    /**
     * Methods tries to find all events by the user id with using the EventRepositoryJPA
     *
     * @param userId The id of the user for which the events should be found
     * @return The events list
     * @throws ResourceNotFound Throws exception when the events list is empty (data was empty/not found)
     */
    @GetMapping("user/{userId}")
    public List<Event> getAllEventsByUserId(@PathVariable long userId) throws ResourceNotFound {
        // Creating new list of type Event and inserting event data (finding by user id) if any with
        // the parametrized constructor call
        List<Event> events = new ArrayList<>(eventRepositoryJPA.findAllByUserId(userId));

        // Throws resource not found exception (404) if the events list is empty (no data)
        if (events.isEmpty())
            throw new ResourceNotFound(String.format("The events for the user with id: [%d] were not found", userId));

        return events;
    }

    /**
     * Methods tries to find all the events for which the user did not register for with using the EventRepositoryJPA
     *
     * @param userId The id of the user for which the events should be found for which the user did not register for
     * @return The list of events for which the user did not register or
     * @throws ResourceNotFound Throws exception when the events for which the user did not register for were not found
     */
    @GetMapping("user/exclude/{userId}")
    public List<Event> getAllEventsByUserIdNot(@PathVariable long userId) throws ResourceNotFound {
        // Creating new list of type Event and inserting event data (looks for events where the user id is not related to)
        // if any with the parametrized constructor call
        List<Event> events = new ArrayList<>(eventRepositoryJPA.findByUserIdNot(userId));

        // Throws resource not found exception (404) if the events list is empty (no data)
        if (events.isEmpty())
            throw new ResourceNotFound(String.format("The events for the user with id: [%d] were not found", userId));

        return events;
    }

    /**
     * Method adds the given data to the events. The EventProcessor contains the user and quiz id. The method will try
     * to find the user by id en store it as a user object and try to find the quiz by id en store it as a quiz object.
     *
     * @param eventProcessor Contains the user id and quiz id
     * @return The saved event object
     * @throws ResourceNotFound    1. Throws exception when the quiz was not found
     *                             2. Throws exception when the user was not found
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @PostMapping
    public ResponseEntity<Event> addToEvent(@RequestBody EventProcessor eventProcessor) throws ResourceNotFound, InternalServerError {
        try {
            // Container quiz object which may or may not contain a non-null value when finding quiz by id
            Optional<Quiz> quiz = quizService.findQuizById(eventProcessor.getQuiz());
            // Container user object which may or may not contain a non-null value when finding user by id
            Optional<User> user = userRepositoryJPA.findById(eventProcessor.getUser());

            // Throws resource not found exception (404) when the quiz was not found
            if (quiz.isEmpty())
                throw new ResourceNotFound(String.format("The quiz with id:[%d] was not found", eventProcessor.getQuiz()));
            // Throws resource not found exception (404) when the user was not found
            if (user.isEmpty())
                throw new ResourceNotFound(String.format("The user with id:[%d] was not found", eventProcessor.getUser()));

            Event _event = eventRepositoryJPA.save(new Event(quiz.get(), user.get()));
            return new ResponseEntity<>(_event, HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(error.getMessage());
        }
    }

    /**
     * Method tries to delete the event with using the EventRepositoryJPA
     *
     * @param id The id of the event to delete
     * @return ResponseEntity with the 200 OK status code only.
     * @throws ResourceNotFound    Throws exception when the event was not found
     * @throws InternalServerError Throws exception when server encountered an unexpected condition that prevented it
     *                             from fulfilling the request
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Event> deleteById(@PathVariable long id) throws ResourceNotFound, InternalServerError {
        try {
            // Container event object which may or may not contain a non-null value when finding event by id
            Optional<Event> event = eventRepositoryJPA.findById(id);

            // Throws resource not found exception (404) when the event was not found
            if (event.isEmpty())
                throw new ResourceNotFound(String.format("The event with id:[%d] was not found", id));

            eventRepositoryJPA.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception error) {
            throw new InternalServerError(null);
        }
    }

}
