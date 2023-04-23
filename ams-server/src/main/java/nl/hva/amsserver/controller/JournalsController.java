package nl.hva.amsserver.controller;

import nl.hva.amsserver.exceptions.ResourceNotFound;
import nl.hva.amsserver.models.journal.JournalCard;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.models.dto.JournalDTO;
import nl.hva.amsserver.repositories.journals.JournalRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This class contains all logic behind the HTTP requests: GET, POST, PUT, DELETE
 *
 * @author Ryan Koning
 */
@RestController
@RequestMapping("/journal")
public class JournalsController {
    @Autowired
    JournalRepositoryJPA journalRepositoryJPA;

    @Autowired
    UserRepositoryJPA userRepositoryJPA;

    @GetMapping(path = "test", produces = "application/json")
    public List<JournalCard> testCards() {
        return List.of(
//                new JournalCard(0, "Level 1", "23-2-2020", "Wij gaan samen delen en spelen"),
//                new JournalCard(1, "Level 2", "23-2-2020", "Wij gaan samen delen en spelen"),
//                new JournalCard(2, "Level 3", "23-2-2020", "Wij gaan samen delen en spelen")
        );

    }


    /**
     * Returns all cards
     *
     * @return - all cards
     */
    @GetMapping(path = "")
    public List<JournalCard> getAllCards() {
        return this.journalRepositoryJPA.findAll();
    }

    /**
     * Returns card with given id
     *
     * @param id - id of card
     * @return - card with given id
     */
    @GetMapping(path = "{id}")
    public JournalCard getOneCard(@PathVariable() int id) {
        //Find card by id
        Optional<JournalCard> journalCard = this.journalRepositoryJPA.findById(id);

        //Check if card is found
        if (journalCard.isPresent()) {
            //Getting card if it is found
            return journalCard.get();
        }

        //If card is not found throw ResourceNotFound exception
        throw new ResourceNotFound("There is no journalCard with id: " + id);
    }

    /**
     * Returns all cards of the user with id = user.id
     *
     * @param id - id of user
     * @return - all cards from user
     */
    @GetMapping(path = "/user/{id}")
    public List<JournalCard> getCardForUser(@PathVariable() long id) {
        //Find card by user id
        return this.journalRepositoryJPA.findByUserId(id);
    }

    /**
     * Adds card to database
     *
     * @param journal - card to add
     * @return - card that is added
     */
    @PostMapping(path = "")
    public ResponseEntity<JournalCard> saveCard(@RequestBody() JournalDTO journal) {
        //Finding user by user id of given journal card
        Optional<User> user = userRepositoryJPA.findById(journal.getUserId());

        //If there is no user assigned to the card throw exception
        if (user.isEmpty()) throw new ResourceNotFound(String.format("User with id=%d not found", journal.getId()));

        //Instantiate journal card
        JournalCard journalCard = new JournalCard(journal, user.get());

        //Saving the card in the database
        this.journalRepositoryJPA.save(journalCard);

        //Returning the card
        return ResponseEntity.ok().body(journalCard);
    }

    /**
     * Updates card with given id
     *
     * @param id - id of card to update
     * @param journalCard - card to update
     * @return - updated card
     */
    @PutMapping(path = "{id}")
    public JournalCard updateCard(@PathVariable() int id, @RequestBody() JournalCard journalCard) {
        System.out.println("dit is card" + journalCard + id);

        //Finding the card by given id
        Optional<JournalCard> updateJournalCard = this.journalRepositoryJPA.findById(id);

        //Checking if card is found and saving the new given changed card again with same id as before
        if (updateJournalCard.isPresent()) {
            JournalCard _journalCard = updateJournalCard.get();
            _journalCard = journalCard;
            return this.journalRepositoryJPA.save(_journalCard);
        }

        //If card was not found throw exception
        throw new ResourceNotFound("There is no journalCard with id: " + id);

    }

    /**
     * Delete card with given id
     *
     * @param id - id of card to delete
     * @return - deleted card
     */
    @DeleteMapping(path = "{id}")
    public JournalCard deleteCard(@PathVariable() int id) {
        //Finding card by given id
        Optional<JournalCard> deleteJournalCard = this.journalRepositoryJPA.findById(id);

        //Checking if card is found
        if (deleteJournalCard.isPresent()) {

            //If card is found delete it by id and return the deleted card
            this.journalRepositoryJPA.deleteById(id);
            return deleteJournalCard.get();
        }

        //If card is not found throw exception
        throw new ResourceNotFound("There is no journalCard with id: " + id);

    }
}
